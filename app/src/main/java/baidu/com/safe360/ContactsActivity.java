package baidu.com.safe360;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ViewUtils;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.HashMap;
import java.util.List;

import baidu.com.safe360.R;
import baidu.com.safe360.engine.ContactEngine;

public class ContactsActivity extends AppCompatActivity {
    private ListView listView;
    private List<HashMap<String, String>> list;
    @ViewInject((R.id.progressloading))
    private ProgressBar progressBar;
    private ProgressBar loading;
    private Handler handler = new Handler() {
        public void handlemessge(Message msg) {
            listView.setAdapter(new Myadapter());
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        //huo获取联系人

        loading = (ProgressBar) findViewById(R.id.progressloading);
        new Thread(new Runnable() {
            @Override
            public void run() {
                list = ContactEngine.getAllContactInfo(getApplicationContext());
                handler.sendEmptyMessage(0);
            }
        }).start();

        listView = (ListView) findViewById(R.id.listView_contacts);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent();
                intent.putExtra("num", list.get(i).get("phone"));
                setResult(RESULT_OK, intent);//s设置结果的方法,会将过传递给调用当前Activity的Activity
                finish();

            }
        });

    }

    private class Myadapter extends BaseAdapter {
        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public View getView(int i, View v, ViewGroup viewGroup) {
            View view = View.inflate(getApplicationContext(), R.layout.listcontacts, null);
        /*chushi初始化控件*/
            TextView tv_name = (TextView) view.findViewById(R.id.textname);
            TextView tv_num = (TextView) view.findViewById(R.id.textnum);
            String numphone = list.get(i).get("phone");
            String namephone = list.get(i).get("name");
            if (!TextUtils.isEmpty(namephone) & !TextUtils.isEmpty(numphone)) {
                tv_name.setText(namephone);
                tv_num.setText(numphone);
            }
            return view;
        }


        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }


    }
}
