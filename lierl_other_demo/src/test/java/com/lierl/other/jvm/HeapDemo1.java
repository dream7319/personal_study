package com.lierl.other.jvm;

/**
 * @Author: lierl
 * @Description:
 * @Date: 2018/6/9 23:23
 */
public class HeapDemo1 {

    public static void main(String[] args) {
        //堆可以使用的总内存
        System.out.println(Runtime.getRuntime().maxMemory()/1024.0/1024 + "MB");
        //堆剩余内存
        System.out.println(Runtime.getRuntime().freeMemory()/1024.0/1024 + "MB");
        //堆已经分配的内存
        System.out.println(Runtime.getRuntime().totalMemory()/1024.0/1024 + "MB");

    }
}
