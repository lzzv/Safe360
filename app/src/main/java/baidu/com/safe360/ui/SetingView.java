package baidu.com.safe360.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import baidu.com.safe360.R;

/**
 * Created by Dawn on 2016/12/20 16:45
 * Email  lzvgogo@gmail.com
 * Title：
 */
public class SetingView extends RelativeLayout {
    private TextView title;
    private TextView des;
    private CheckBox checkBox;
    private String deson, desoff;

    public SetingView(Context context) {
        super(context);
        init();
    }

    public SetingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();

    }

    /*布局文件使用时调用*/
    public SetingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
   /*     *//*获取属性个数*//*
        int count=attrs.getAttributeCount();
        Log.i("tmd", "输出______"+count);
        for (int i = 0; i <count ; i++) {
            Log.i("tmd", "输出______"+attrs.getAttributeValue(i));
        }*/
//        attrs.getAttributeValue("http://schemas.android.com/apk/res-auto", "title");
        deson = attrs.getAttributeValue("http://schemas.android.com/apk/res-auto", "des_on");
        desoff = attrs.getAttributeValue("http://schemas.android.com/apk/res-auto", "des_off");

        title.setText(attrs.getAttributeValue("http://schemas.android.com/apk/res-auto", "title"));

        if (isCheckde()) {
            des.setText(deson);
        } else {
            des.setText(desoff);

        }
    }

    private void init() {

        View view = View.inflate(getContext(), R.layout.settinglayout, this);
        /*初始化信息*/
        title = (TextView) view.findViewById(R.id.text_title_set);
        des = (TextView) view.findViewById(R.id.tv_des_seting);
        checkBox = (CheckBox) view.findViewById(R.id.checkBox1);

    }

    /*shsh设置标题*/
    public void setTitle(String title1) {
        title.setText(title1);
    }

    /*内容*/
    public void setDes(String des1) {
        des.setText(des1);
    }

    /*选中*/
    public void setCheckde(boolean isChecked) {
        checkBox.setChecked(isChecked);
        if (isCheckde()) {
            des.setText(deson);
        } else {
            des.setText(desoff);

        }
    }

    /*获取checkbox状态*/
    public boolean isCheckde() {

        return checkBox.isChecked();
    }
}
