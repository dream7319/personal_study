package lierl.other.jvm;

import java.util.concurrent.TimeUnit;

/**
 * @Author: lierl
 * @Description:
 * @Date: 2018/6/9 22:39
 */
public class ThreadDemo extends Thread{
    private volatile boolean stop = false;//true表示停止， false表示继续执行

    @Override
    public void run(){
        System.out.println(Thread.currentThread().getName());
        int i = 0;
        while(!stop){
            i ++;
        }
        System.out.println(i);
    }

    public void stopMe(){
        System.out.println(Thread.currentThread().getName());
        this.stop = true;
    }

    public static void main(String[] args) throws InterruptedException {
        ThreadDemo demo = new ThreadDemo();
        demo.start();//执行run方法  子线程
        TimeUnit.MILLISECONDS.sleep(50);
        demo.stopMe();//主线程
    }
}
