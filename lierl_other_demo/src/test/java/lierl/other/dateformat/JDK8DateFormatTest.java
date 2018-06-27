package lierl.other.dateformat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @Author: lierl
 * @Description: 
 * @Date: 2018/6/23 15:03
 */
public class JDK8DateFormatTest {
    public static void main(String[] args) {
        String dateStr= "2018年06月20日";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy年MM月dd日");
        LocalDate date= LocalDate.parse(dateStr, formatter);
        System.out.println(date);

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy年MM月dd日 hh:mm a");
        String nowStr = now.format(format);
        System.out.println(nowStr);
        String __aa = "222";
        print(__aa);
    }

    public static void print(String ss){
        System.out.println(ss);
    }
}
