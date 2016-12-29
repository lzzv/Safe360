package baidu.com.safe360.Utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;

/**
 * Created by Dawn on 2016/12/17 19:23
 * Email  lzvgogo@gmail.com
 * Title：流信息转化成字符信息自己封装工具类
 */
public class StreamUtil {
    public static String parserStreamUtil(InputStream in) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        StringWriter sw = new StringWriter();
        String str = null;

        while ((str = br.readLine()) != null) {
            sw.write(str);
        }
        if (sw != null && br != null) {
            sw.close();
            br.close();

        }

        return sw.toString();
    }


}





































