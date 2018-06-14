package com.lierl.other.jvm;

/**
 * @Author: lierl
 * @Description:
 * @Date: 2018/6/9 22:53
 */
public class SynchronizedDemo {

    //共享变量
    private boolean flag = false;
    private int a = 1;
    private int result = 0;

    //写操作
    public synchronized void write(){
        flag = true;
        a = 2;
    }
    //读操作
    public synchronized void read(){
        if(flag){
            result = a*3;
        }
        System.out.println("result: " + result);
    }

    private class ReadWriteThread extends Thread{
        private boolean flag;

        public ReadWriteThread(boolean flag){
            this.flag = flag;
        }

        @Override
        public void run() {
            if(flag){
                write();
            }else{
                read();
            }
        }
    }

    /**
     * synchronized 可以保证只有一个线程执行类中的方法
     * @param args
     */
    public static void main(String[] args) {
        SynchronizedDemo demo = new SynchronizedDemo();
        demo.new ReadWriteThread(true).start();//写
        demo.new ReadWriteThread(false).start();//读
    }
}
