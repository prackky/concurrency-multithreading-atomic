package practice.producerconsumer.blockingqueue;

import java.util.concurrent.BlockingQueue;

public class Producer {
	
	private int id;
	private final String producerName;
	private final BlockingQueue<Product> productQueue;
	
	public Producer(String name, BlockingQueue<Product> queue) {
	    this.producerName = name;
	    this.productQueue = queue;
	  }
	
	public void produce() throws InterruptedException {
		Product product = new Product(++id, producerName);
		this.productQueue.put(product);
		System.out.println("Producer {" + producerName + "} produced product ID {" + id + "}");
		//Thread.sleep((long)(Math.random() * 1000));
	}

}
