package baidu.com.safe360;

import android.app.Dialog;
import android.content.Context;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Intent;
import android.content.SharedPreferences;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import baidu.com.safe360.Utils.MD5Util;

public class HomeActivity extends AppCompatActivity {
    private GridView gv;
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        gv = (GridView) findViewById(R.id.gv_home);
        TextView marquee = (TextView) findViewById(R.id.marquee);
        marquee.requestFocus();//代码中必须有获取焦点..
        sp = getSharedPreferences("mima", MODE_PRIVATE);

        gv.setAdapter(new MyAdapter());

        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int posion, long id) {
                /*根据条目位置判断点击*/
                switch (posion) {
                    case 0:
                        if (TextUtils.isEmpty(sp.getString("password", ""))) {
                            setPasswordDialog();
                        } else {
                            showEndterDialog();
                        }


                        break;

                    case 8:
                        Intent intent = new Intent(HomeActivity.this, SettingActivity.class);
                        startActivity(intent);
                        break;

                }

            }
        });
    }


    /*设置手机防盗密码对话框*/
    private Dialog dialog;

    private void showEndterDialog() {
        AlertDialog.Builder dia = new AlertDialog.Builder(this);

        View view = View.inflate(getApplicationContext(), R.layout.enterdialog, null);
        dia.setView(view);
        dia.setCancelable(false);

        dialog = dia.create();
        dialog.show();
        final EditText password_set = (EditText) view.findViewById(R.id.edittext_password_set);
        Button passok = (Button) view.findViewById(R.id.button);
        Button passCancle = (Button) view.findViewById(R.id.button2);
        Button but_eyes = (Button) view.findViewById(R.id.but_eyes);
        but_eyes.setOnClickListener(new View.OnClickListener() {
            int count = 0;

            @Override

            public void onClick(View view) {
                if (count % 2 == 0) {
                    password_set.setInputType(0);
                } else {
                    password_set.setInputType(129);
                }
                count++;
            }
        });


        passok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String password = password_set.getText().toString().trim();

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(HomeActivity.this, "请输入密码", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (MD5Util.passwordMD5(password).equals(sp.getString("password", ""))) {
                    dialog.dismiss();

                    Toast.makeText(HomeActivity.this, "密码正确" + sp.getString("password", ""),
                            Toast.LENGTH_SHORT).show();
                    //待跳转
                    Intent intent = new Intent(HomeActivity.this, LostFindActivity.class);
                    startActivity(intent);

                } else {
                    Toast.makeText(HomeActivity.this, "密码错误,请重新输入", Toast.LENGTH_SHORT).show();
                }
            }
        });
        passCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

    private void setPasswordDialog() {
        AlertDialog.Builder dia = new Builder(this);
        View view = View.inflate(getApplicationContext(), R.layout.dialog, null);
        dia.setCancelable(false);
        dia.setView(view);
        dialog = dia.create();
        dialog.show();

        final EditText password_confim = (EditText) view.findViewById(R.id
                .edittext_password_denglu);
        final EditText password_set = (EditText) view.findViewById(R.id.edittext_password_set);
        Button passok = (Button) view.findViewById(R.id.button);
        Button passCancle = (Button) view.findViewById(R.id.button2);

        passok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String password = password_set.getText().toString().trim();

                String passwordconfim = password_confim.getText().toString().trim();
                if (TextUtils.isEmpty(password) | TextUtils.isEmpty(passwordconfim)) {
                    Toast.makeText(HomeActivity.this, "请输入密码", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (MD5Util.passwordMD5(password).equals(MD5Util.passwordMD5(passwordconfim))) {
                    /*1保存密码2,隐藏对话框
                    * */
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("password", MD5Util.passwordMD5(password));
                    editor.commit();//提交信息

                    dialog.dismiss();
                    Toast.makeText(HomeActivity.this, "密码设置成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(HomeActivity.this, "两次密码不一致,请重新输入", Toast.LENGTH_SHORT).show();
                }
            }
        });
        passCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

    }

    private class MyAdapter extends BaseAdapter {
        int[] imageId = {R.drawable.safe, R.drawable.callmsgsafe, R.drawable.app, R.drawable
                .taskmanager, R.drawable.netmanager, R.drawable.trojan, R.drawable.sysoptimize, R
                .drawable.atools, R.drawable.settings,};
        String[] names = {"手机防盗", "通讯卫士", "软件管理", "进程管理", "流量统计", "手机杀毒", "缓存清理", "高级工具", "设置中心"};

        @Override
        public int getCount() {
            return 9;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override/*条目样式*/
        public View getView(int position, View convertView, ViewGroup parent) {
          /*  TextView textView=new TextView(getApplicationContext());
            textView.setText("第"+position+"条目");*/
            View view = View.inflate(getApplicationContext(), R.layout.home_9, null);
            ImageView iv_item_icon = (ImageView) view.findViewById(R.id.imageView_1);
            TextView tv = ((TextView) view.findViewById(R.id.text1));
            iv_item_icon.setImageResource(imageId[position]);
            tv.setText(names[position]);

            return view;
        }
    }
}






















