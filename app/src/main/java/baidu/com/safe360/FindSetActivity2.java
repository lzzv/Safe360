package baidu.com.safe360;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import baidu.com.safe360.ui.SetingView;

public class FindSetActivity2 extends FindSetBaseActivity {
    private SetingView setingView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_set2);
        setingView = (SetingView) findViewById(R.id.find_set2zidingyi);


        if (TextUtils.isEmpty(sp.getString("sim", ""))) {
            //没有绑定
           setingView.setCheckde(false);
        }else{
            //绑定SIM卡
            setingView.setCheckde(true);
        }

        setingView.setOnClickListener(new View.OnClickListener() {
            SharedPreferences.Editor editor = sp.edit();

            @Override
            public void onClick(View view) {

                if (setingView.isCheckde()) {
                    //解绑
                    setingView.setCheckde(false);
                    editor.putString("sim", "");
                } else {    //绑定SIM卡
                    TelephonyManager telephonyManager = (TelephonyManager) getSystemService
                            (TELEPHONY_SERVICE);
                    String sim = telephonyManager.getSimSerialNumber();
                    setingView.setCheckde(true);
                    //baocun保存sim卡号

                    editor.putString("sim", sim);

                    Log.i("tmd", "输出______" + telephonyManager.getSimSerialNumber());
                }
                editor.commit();
            }
        });
    }


    @Override
    public void nextActivity() {
        if(setingView.isCheckde()) {
            Intent intent = new Intent(this, FindSetActivity3.class);
            startActivity(intent);
            finish();
            overridePendingTransition(R.anim.next_enter_set_anim, R.anim.next_1_exit_anim);
        }else{
            Toast.makeText(FindSetActivity2.this, "你没绑定SIM卡,将无法接收到报警短信", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void preActivity() {
        Intent intent = new Intent(this, FindSetActivity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.pre_enter_set_anim, R.anim.pre_exit_set_anim);

    }
}
