package baidu.com.safe360;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class LostFindActivity extends AppCompatActivity {
    /*记录是否第一次进入防盗页面
    * 第一次进入设置界面,第二次进入防盗界面
    * */
    public SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sp = getSharedPreferences("config", MODE_PRIVATE);
        if (sp.getBoolean("first", true)) {
            Intent intent = new Intent(this, FindSetActivity.class);
            startActivity(intent);
            finish();
        } else {
            setContentView(R.layout.activity_lost_find);
        }
    }

    public void resetup(View v) {
        Intent intent = new Intent(this, FindSetActivity.class);
        startActivity(intent);
        finish();
    }


}
