package baidu.com.safe360;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import baidu.com.safe360.ui.SetingView;

public class SettingActivity extends AppCompatActivity {
    private SetingView settingView;
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        /*ba保存文件信息名称,mode权限*/
        sp = getSharedPreferences("Box_set", MODE_PRIVATE);
        settingView = ((SetingView) findViewById(R.id.SetingView_zidingy));
        settingView.setTitle("提示更新");
        if (sp.getBoolean("update", true)) {
       /*     settingView.setDes("打开提示更新");*/
            settingView.setCheckde(true);
        } else {
          /*  settingView.setDes("关闭提示更新");*/
            settingView.setCheckde(false);
        }

        settingView.setOnClickListener(new View.OnClickListener() {
            SharedPreferences.Editor editor = sp.edit();

            @Override
            //需要关闭CheckBox自己的点击事件
            public void onClick(View view) {
                if (settingView.isCheckde()) {
                    settingView.setDes("关闭提示更新");
                    settingView.setCheckde(false);
                    /*保存好状态*/
                    editor.putBoolean("update", false);

                } else {
                    settingView.setDes("打开提示更新");
                    settingView.setCheckde(true);
                    editor.putBoolean("update", true);
                }
                editor.commit();
            }
        });
    }
}

























