package com.lierl.other.jvm;

/**
 * @Author: lierl
 * @Description:
 * @Date: 2018/6/9 22:08
 */
public class StackDemo {
    private static int index = 1;
    public static void test(){
        index++;
        test();
    }

    /**
     * 栈的深度
     * -Xss
     * 线程自己指定的stack size 1M
     * @param args
     */
    //StackOverflowError
    public static void main(String[] args) {
        try {
            test();
        } catch (Throwable e) {
            System.out.println("stack deep : "+ index);
            System.out.println(e);
        }
    }
}
