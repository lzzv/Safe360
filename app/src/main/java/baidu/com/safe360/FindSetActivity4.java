package baidu.com.safe360;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class FindSetActivity4 extends FindSetBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_set4);

    }

    @Override
    public void nextActivity() {
        //保存是否是第一次进入防盗界面的,此处应设置为不是
        SharedPreferences.Editor editor=sp.edit();

        editor.putBoolean("first",false);
        editor.commit();
        Intent intent = new Intent(this, LostFindActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void preActivity() {
        Intent intent = new Intent(this, FindSetActivity3.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.pre_enter_set_anim,R.anim.pre_exit_set_anim);

    }
}
