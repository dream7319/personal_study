package lierl.other.dateformat;


import java.text.ParseException;

/**
 * @Author: lierl
 * @Description:
 * @Date: 2018/6/23 14:41
 */
public class DateFormatTest {
    public static void main(String[] args) {
        for (int i = 0; i < 3; i++) {
            new Format().start();
        }
    }

}
class Format extends Thread{
    @Override
    public void run() {
        while (true){
            try {
                this.join(2000);
                try {
                    System.out.println(this.getName() + ":" + DateUtils.parse("2018-06-20 01:18:20"));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
