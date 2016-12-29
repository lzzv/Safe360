package baidu.com.safe360.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.provider.Telephony;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;

/**
 * Created by Dawn on 2016/12/26 21:55
 * Email  lzvgogo@gmail.com
 * Title：
 */
public class BootCompletereceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("tmd", "输出___手机重启了___");
        SharedPreferences sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        String sp_sim = sp.getString("sim", "");//hu获取保存的SIM卡号
        //2再次获取本地SIM卡号
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(context
                .TELECOM_SERVICE);
        String sim = telephonyManager.getSimSerialNumber();
        if (!TextUtils.isEmpty(sp_sim) && !TextUtils.isEmpty(sim)) {
            //4.判断两个SIM卡是否一致,如果一致就不发送报警短信,不一致发送报警短信
            if (!sp_sim.equals(sim)) {
                //发送报警短信
                //短信的管理者
                SmsManager smsManager = SmsManager.getDefault();
                //destinationAddress : 收件人
                //scAddress :　短信中心的地址　　一般null
                //text : 短信的内容
                //sentIntent : 是否发送成功
                //deliveryIntent : 短信的协议  一般null

                smsManager.sendTextMessage("18300605821", null, "救我---  ---!!", null, null);

            }
        }
    }
}












