package baidu.com.safe360;

import android.content.Intent;
import android.os.Bundle;

public class FindSetActivity extends FindSetBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_set);

    }
    @Override
    public void nextActivity() {
        Intent intent = new Intent(this, FindSetActivity2.class);
        startActivity(intent);
        finish();
        //执行平移动画..在res创建动画文件夹anim ,创建xml动画文件
        /*前面一个进入动画,出去动画*/
        overridePendingTransition(R.anim.next_enter_set_anim,R.anim.next_1_exit_anim);

    }

    @Override
    public void preActivity() {

    }

}
