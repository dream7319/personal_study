package lierl.other.current;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Semaphore;

/**
 * @Author: lierl
 * @Description:
 * @Date: 2018/6/10 17:33
 */
class Buffer {

    List<Integer> buffer = new LinkedList<Integer>();
    // 互斥量，控制buffer的互斥访问
    private Semaphore mutex = new Semaphore(1);

    // canProduceCount可以生产的数量（表示缓冲区可用的数量）。 通过生产者调用acquire，减少permit数目
    private Semaphore canProduceCount = new Semaphore(10);

    // canConsumerCount可以消费的数量。通过生产者调用release，增加permit数目
    private Semaphore canConsumerCount = new Semaphore(0);
    Random rn = new Random(10);

    public void get() throws InterruptedException {
        canConsumerCount.acquire();
        try {
            mutex.acquire();
            int val = buffer.remove(0);
            System.out.println(Thread.currentThread().getName() + " 正在消费数据为：" + val + "    buffer目前大小为：" + buffer.size());
        } finally {
            mutex.release();
            canProduceCount.release();
        }

    }

    public void put() throws InterruptedException {
        canProduceCount.acquire();
        try {
            mutex.acquire();
            int val = rn.nextInt(10);
            buffer.add(val);
            System.out.println(Thread.currentThread().getName() + " 正在生产数据为：" + val + "    buffer目前大小为：" + buffer.size());
        } finally {
            mutex.release();
            // 生产者调用release，增加可以消费的数量
            canConsumerCount.release();
        }

    }
}

public class SemaphoreProducerComsumer1 {

    public static void main(String[] args) {
        final Buffer buffer = new Buffer();
        startProducer(buffer);
        startProducer(buffer);
        startConsumer(buffer);
        startConsumer(buffer);

    }

    public static void startProducer(final Buffer buffer) {
        new Thread(() -> {
            try {
                while (true) {
                    buffer.put();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }).start();
    }

    public static void startConsumer(final Buffer buffer) {
        new Thread(() -> {
            try {
                while (true) {
                    buffer.get();
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }).start();
    }

}