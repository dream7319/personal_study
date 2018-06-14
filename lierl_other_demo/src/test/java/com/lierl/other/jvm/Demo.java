package com.lierl.other.jvm;

/**
 * @Author: lierl
 * @Description:
 * @Date: 2018/6/9 21:10
 */
public class Demo {
    public static int a = 10;
    static {
        a = a + 1;
        System.out.println(a);
    }

    public static void main(String[] args) {
        Demo demo = new Demo();
        System.out.println(demo.a);
    }
}
