package lierl.other.jvm;

/**
 * @Author: lierl
 * @Description:
 * @Date: 2018/6/9 21:49
 */
public class Demo2 {
    static {
        a = 1;//a变量可写不可读
    }

    public static int a = 10;
    public static void main(String[] args) {

        System.out.println(Demo2.a);//10
    }
}
