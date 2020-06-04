package practice.producerconsumer.customblocking;

public class Consumer {

	private final CustomBlockingQueue<Product> queue;

	private final String name;

	public Consumer(String name, CustomBlockingQueue<Product> queue) {
		this.name = name;
		this.queue = queue;
	}

	/**
	 * Consume item from the queue.
	 */
	public void consume() throws InterruptedException {
		Product product = queue.take();
		System.out.println("Consumer {" + name + "} consumed product ID {" + product.getId() + "} produced by {"
				+ product.getProducer() + "}");
		//Thread.sleep((long)(Math.random() * 1000));

	}

}
