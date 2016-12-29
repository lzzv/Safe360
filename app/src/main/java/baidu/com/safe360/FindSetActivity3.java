package baidu.com.safe360;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class FindSetActivity3 extends FindSetBaseActivity {
    private EditText safenum_ed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_set3);
        safenum_ed = (EditText) findViewById(R.id.ed_safenum);
        safenum_ed.setText(sp.getString("safenum", ""));


    }

    protected void clickcontacts(View view) {
        Intent intent1 = new Intent(this, ContactsActivity.class);
        //当打开的界面退出时,会调用现在的界面中OnActivityResults方法;
        startActivityForResult(intent1, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            String num = data.getStringExtra("num");
            safenum_ed.setText(num);
        }
    }

    @Override
    public void nextActivity() {
        String safenum = safenum_ed.getText().toString().trim();

        if (TextUtils.isEmpty(safenum)) {
            Log.i("tmd", "输出___安全号码界面上3333___");
            Toast.makeText(FindSetActivity3.this, "请输入安全号码,重要!!", Toast.LENGTH_SHORT).show();
            return;
        }
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("safenum", safenum);

        Intent intent = new Intent(this, FindSetActivity4.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.next_enter_set_anim, R.anim.next_1_exit_anim);


    }

    @Override
    public void preActivity() {
        Intent intent = new Intent(this, FindSetActivity2.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.pre_enter_set_anim, R.anim.pre_exit_set_anim);

    }
}
