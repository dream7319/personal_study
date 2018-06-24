package lierl.other.dateformat;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author: lierl
 * @Description:
 * @Date: 2018/6/23 14:45
 */
public class DateUtils {
    private static ThreadLocal<DateFormat> sdf = new ThreadLocal<DateFormat>(){
        @Override
        protected DateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }
    };
    public static String format(Date date) {
        return sdf.get().format(date);
    }

    public static Date parse(String date) throws ParseException {
        return sdf.get().parse(date);
    }
}
