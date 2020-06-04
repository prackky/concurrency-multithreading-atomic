package practice.producerconsumer.blockingqueue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

public class MainClass {

	public static void main(String[] args) {
		BlockingQueue<Product> queue = new LinkedBlockingDeque<Product>(5); // 22,02,126

		ExecutorService executor = Executors.newFixedThreadPool(5);

		for (int i = 0; i < 2; i++) {
			final Producer producer = new Producer("Producer-" + i, queue);
			executor.submit(() -> {
				while (true) {
					producer.produce();
				}
			});
		}

		for (int i = 0; i < 3; i++) {
			final Consumer consumer = new Consumer("Consumer-" + i, queue);
			executor.submit(() -> {
				while (true) {
					consumer.consume();
				}
			});
		}

		executor.shutdown();
		try {
			executor.awaitTermination(10, TimeUnit.SECONDS);
			executor.shutdownNow();
		} catch (InterruptedException e) {
			System.out.println("Error waiting for ExecutorService shutdown");
		}
	}

}
