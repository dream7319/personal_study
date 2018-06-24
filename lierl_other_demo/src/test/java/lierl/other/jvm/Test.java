package lierl.other.jvm;

/**
 * @Author: lierl
 * @Description:
 * @Date: 2018/6/9 23:17
 */
public class Test {
    public static void abc(){
        byte[] b = new byte[2];
        b[0] = 1;
    }

    public static void main(String[] args) {
        long startTime  = System.currentTimeMillis();
        for (int i = 0; i < 1000000; i++) {
            abc();
        }

        long end = System.currentTimeMillis();
        System.out.println(end - startTime);
    }
}
