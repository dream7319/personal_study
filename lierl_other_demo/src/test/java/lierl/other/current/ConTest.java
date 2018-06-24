package lierl.other.current;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author lierlei@xingyoucai.com
 * @create 2018-06-08 17:38
 **/
public class ConTest {
	public static void main(String args[]) {

		final Queue sharedQ = new LinkedList();

		Thread producer = new Producer(sharedQ);
		Thread consumer = new Consumer(sharedQ);

		producer.start();
		consumer.start();

	}
}
class Producer extends Thread {
	private final Queue sharedQ;

	public Producer(Queue sharedQ) {
		super("Producer");
		this.sharedQ = sharedQ;
	}

	@Override
	public void run() {

		for (int i = 0; i < 4; i++) {
			synchronized (sharedQ) {
				//waiting condition - wait until Queue is not empty
				while (sharedQ.size() >= 1) {
					try {
						System.out.println("Queue is full, waiting");
						sharedQ.wait();
					} catch (InterruptedException ex) {
						ex.printStackTrace();
					}
				}
				System.out.println("producing : " + i);
				sharedQ.add(i);
				sharedQ.notify();
			}
		}
	}
}

class Consumer extends Thread {
	private final Queue sharedQ;

	public Consumer(Queue sharedQ) {
		super("Consumer");
		this.sharedQ = sharedQ;
	}

	@Override
	public void run() {
		while (true) {

			synchronized (sharedQ) {
				//waiting condition - wait until Queue is not empty
				while (sharedQ.size() == 0) {
					try {
						System.out.println("Queue is empty, waiting");
						sharedQ.wait();
					} catch (InterruptedException ex) {
						ex.printStackTrace();
					}
				}
				int number = (int) sharedQ.poll();
				System.out.println("consuming : " + number);
				sharedQ.notify();

				//termination condition
				if (number == 3) {
					break;
				}
			}
		}
	}
}
