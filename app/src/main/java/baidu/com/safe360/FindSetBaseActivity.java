package baidu.com.safe360;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;

public abstract class FindSetBaseActivity extends AppCompatActivity {
    //获取手势识别器//----必须注册到屏幕触摸事件中
    private GestureDetector gestureDetector;
    protected SharedPreferences sp;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sp=getSharedPreferences("config",MODE_PRIVATE);
       gestureDetector=new GestureDetector(this,new mylsitener());
    }


    private class mylsitener extends GestureDetector.SimpleOnGestureListener {
        /**
         *
         * @param e1  按下事件 按下坐标
         * @param e2  抬起坐标
         * @param velocityX  移动速率x轴方向的
         * @param velocityY
         * @return
         */
        @Override
        public boolean onFling(@NonNull MotionEvent e1, @NonNull MotionEvent e2, float velocityX, float
        velocityY) {
            float startX = e1.getRawX();
            float startY = e1.getRawY();
            float endX = e2.getRawX();
            float endY = e2.getRawY();

            if (startX - endX > 66&&Math.abs(startY-endY)<65) {
                nextActivity();
            }
            if (endX - startX > 66&&Math.abs(startY-endY)<65) {
                preActivity();
            }
            return true;
        }
    }
    //        必须注册到屏幕触摸事件中


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        gestureDetector.onTouchEvent(event);
        return super.onTouchEvent(event);

    }

    public void clickPre(View view) {
        preActivity();

    }

    public void clickNext(View view) {
        nextActivity();
    }

    //因为父类不知道上一步操作下一步操作具体代码是什么,
    //所以创建一个抽象方法,让子类实现这个方法,根据自己的特性去实现相应的操作
    public abstract void nextActivity();//下一步操作

    public abstract void preActivity();

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //true:可以屏蔽返回键
//            return true;
            preActivity();
        }

        return super.onKeyDown(keyCode, event);
    }
}
