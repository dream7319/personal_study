package com.lierl.other.roundrobin;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author lierlei@xingyoucai.com
 * @create 2018-06-07 11:17
 **/
public class NormalRoundRobin {

	List<Server> servers;
	//当前索引
	private int currentIndex;
	//服务器总数
	private int totalServer;

	public NormalRoundRobin(){
		servers = new ArrayList<>();
		servers.add(new Server("192.168.1.2"));
		servers.add(new Server("192.168.1.3"));
		servers.add(new Server("192.168.1.4"));
		servers.add(new Server("192.168.1.5"));
		servers.add(new Server("192.168.1.6"));
		servers.add(new Server("192.168.1.7"));
		servers.add(new Server("192.168.1.8"));
		totalServer = servers.size();
		currentIndex = totalServer - 1;
	}

	public Server round(){
		currentIndex = (currentIndex+1)%totalServer;
		return servers.get(currentIndex);
	}

	private static volatile AtomicInteger count = new AtomicInteger(0);

	public static void main(String[] args) {
		List<Server> servers= new ArrayList<>();
		servers.add(new Server("192.168.1.2"));
		servers.add(new Server("192.168.1.3"));
		servers.add(new Server("192.168.1.4"));
		servers.add(new Server("192.168.1.5"));
		servers.add(new Server("192.168.1.6"));
		servers.add(new Server("192.168.1.7"));
		servers.add(new Server("192.168.1.8"));

		ExecutorService executorService = Executors.newFixedThreadPool(1);
		while(true){
			executorService.execute(()->{
				int c = count.get()%servers.size();
				count.compareAndSet(count.get(), count.addAndGet(1));
				System.out.println(count.get() +"-->" + servers.get(c));
			});
		}


//		final CyclicBarrier b = new CyclicBarrier(14);

		// 带并发的轮询
//		for (int i = 0; i < 3; i++) {
//			new Thread(() -> {
//				try {
//					b.await();
//					System.out.println(Thread.currentThread().getName() + " " + r.round());
//				} catch (InterruptedException | BrokenBarrierException e) {
//					e.printStackTrace();
//				}
//			}, "thread" + i).start();
//		}
	}
}
