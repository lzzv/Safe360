package baidu.com.safe360;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EdgeEffect;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

import org.json.JSONObject;

import java.io.File;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import baidu.com.safe360.Utils.StreamUtil;

public class SplashActivity extends AppCompatActivity {
    private static final int MSG_UPDATE_DIALOG = 1;
    private static final int MSG_ENDER = 0;
    private static final int MSG_SERVER_ERROR = 2;
    private static final int MSG_EXCEPTION = 4;
    private String versionName;
    private TextView tv_versionName_Sp, tv_download_sp;

    private String code, apkurl, des;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_UPDATE_DIALOG:
                    //弹出对话框
                    showdialog();
                    break;
                case MSG_ENDER:
                    enterHome();
                    break;
                case MSG_SERVER_ERROR:
                    Toast.makeText(SplashActivity.this, "网络连接服务器异常,请检查网络", Toast.LENGTH_SHORT)
                            .show();
                    enterHome();
                    break;
                case MSG_EXCEPTION:
                    Toast.makeText(SplashActivity.this, "异常", Toast.LENGTH_SHORT)
                            .show();
                    enterHome();
                    break;
            }
        }
    };

    private SharedPreferences sp_sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv_versionName_Sp = (TextView) findViewById(R.id.tv_versionName_splash);
        tv_versionName_Sp.setText("版本号:" + getVersionName());

        tv_download_sp = (TextView) findViewById(R.id.tv_Downlad_sp);

        sp_sp = getSharedPreferences("Box_set", MODE_PRIVATE);
        if (sp_sp.getBoolean("update", true)) {
            update();
        } else {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    SystemClock.sleep(1500);
                    enterHome();
                }
            }).start();

        }

    }

    /**
     * ta弹出对话框方法
     */
    private void showdialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        /**
         * 设置对话框点击其他不可消失
         */
        builder.setCancelable(false);

        builder.setTitle("发现新版本" + code);
        builder.setIcon(R.drawable.ic_launcher);
        builder.setMessage(des);
        //sh设置取消升级按钮
        builder.setPositiveButton("升级", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                download();


            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                //1,隐藏对话框  2,跳转到主界面
                dialog.dismiss();
                enterHome();
            }
        });
        //---------------必须设置一下才会显示对话框
//        builder.create().show();或者用下面的
        builder.show();

    }

    /**
     * 下载新版本 l利用xutils框架下载
     */
    private void download() {
        //判断是否挂在SDCard
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            HttpUtils httpUtils = new HttpUtils();
            httpUtils.download(apkurl, "/mnt/sdcard/safe360_2.apk", new RequestCallBack<File>() {
                @Override//下载成功调用
                public void onSuccess(ResponseInfo<File> responseInfo) {
                    Log.i("tmd", "输出______下载成功");
                    installapk();

                }

                @Override
                public void onFailure(HttpException error, String msg) {

                }

                /**
                 *  显示当前的下载进度
                 * @param total 下载总进度
                 * @param current  下载当前进度
                 * @param isUploading 是否支持断点续传
                 */
                @Override
                public void onLoading(long total, long current, boolean isUploading) {
                    super.onLoading(total, current, isUploading);
                    //设置下载进度的textview可见,同时设置相应的下载进度
                    tv_download_sp.setVisibility(View.VISIBLE);//设置下载可见
                    tv_download_sp.setText(100 * current / total + "%");
                    //安装新版本


                }
            });

        }
    }

    /**
     * 安装apk
     */
    private void installapk() {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
//        intent.setData(Uri.fromFile(new File("/mnt/sdcard/safe360_2.apk")));
//        intent.setType("application/vnd.android.package-archive");
        //此两种方法会互相覆盖上一中,改为如下
        intent.setDataAndType(Uri.fromFile(new File("/mnt/sdcard/safe360_2.apk")),
                "application/vnd.android.package-archive");
        //调用系统的安装apk,
        /**
         * requestCode区分从那个Activity传过来的
         */
        startActivityForResult(intent, 0);//退出时会调用当前的的Activity的onActivityResult方法

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        enterHome();
    }

    /***
     * 跳转主页面
     */
    private void enterHome() {

        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * 检查是否需要升级
     */
    private void update() {
        //1,连接服务器,检查是否有版本更新,联网操作,耗时操作4.0以后放在子线程中操作
        new Thread() {
            public void run() {
                Message message = Message.obtain();//创建弹出对话框的handler的message载体
//                super.run();

                long befortime = System.currentTimeMillis();

                try {

                    URL url = new URL("http://10.11.58.101:8080/updateinfo.html");
                    //1.1.2获取连接操作
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();//http协议,
                    // httpClient
                    //1.1.3设置超时时间
                    conn.setConnectTimeout(3000);//设置连接超时时间
                    //1.1.4设置请求方式
                    conn.setRequestMethod("GET");//post
                    int responseCode = conn.getResponseCode();
                    if (responseCode == 200) {
                        //连接成功,获取服务器返回的数据,code : 新版本的版本号     apkurl:新版本的下载路径      des:描述信息,
                        // 告诉用户增加了哪些功能,修改那些bug
                        //获取数据之前,服务器是如何封装数据xml  json
                        Log.i("tmd", "输出______成功" + responseCode);
                        InputStream inputStream = conn.getInputStream();
                        String json = StreamUtil.parserStreamUtil(inputStream);
                        //获得json数据之后,进行解析
                        JSONObject jsonObject = new JSONObject(json);
                        //获取json内部数据
                        code = jsonObject.getString("code");
                        apkurl = jsonObject.getString("apkurl");
                        des = jsonObject.getString("des");
                        Log.i("tmd", "输出__ +code+apkurl+des__" + code + apkurl + des);
                        //检查是否有新版本
                        if (code.equals(getVersionName())) {
                            //m没有新版本
                            message.what = MSG_ENDER;

                        } else {
                            //弹出对话框下载新版本,\
                            //子线程并不能更新ui创建handler
                            message.what = MSG_UPDATE_DIALOG;
                            //   handler.sendMessage(message);//不管有没有异常都会执行,
                        }
                    } else {
                        //连接失败
                        Log.i("tmd", "shi失败");
                        message.what = MSG_SERVER_ERROR;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    message.what = MSG_EXCEPTION;
                } finally { //不管有没有异常都会执行,
                    long endtime = System.currentTimeMillis();
                    int time = (int) (endtime - befortime);
                    if (time < 1500) {
                        SystemClock.sleep(1500 - time);
                    }
                    handler.sendMessage(message);
                }
            }
        }.start();
    }

    /**
     * 获取当前程序版本号
     */
    private String getVersionName() {
        //包的管理者,获文件重的版本信息builderGradle
        PackageManager packageManager = getPackageManager();
        //flags:指定信息的标签,0:获取基础信息包名,版本号..

        try {

            PackageInfo packageInfo = packageManager.getPackageInfo(getPackageName(), 0);
            //获取版本号名称
            versionName = packageInfo.versionName;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return versionName;
    }

}







































































































































































































































































