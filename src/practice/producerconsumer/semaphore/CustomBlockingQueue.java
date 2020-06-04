package practice.producerconsumer.semaphore;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Semaphore;

public class CustomBlockingQueue<E> {
	
	private final Queue<E> buffer;
	private Semaphore writePermits;
	private Semaphore readPermits;

	public CustomBlockingQueue(int size) {
		super();
		this.buffer = new LinkedList<>();
		this.writePermits = new Semaphore(size);
		this.readPermits = new Semaphore(0);
	}

	public void put(E e) throws InterruptedException {
		writePermits.acquire();
		System.out.println("permits left put = " + writePermits.availablePermits());
		try {
			buffer.add(e);
		} finally {
			//writePermits.release();
			readPermits.release();
		}
	}

	public E take() throws InterruptedException {
		readPermits.acquire();
		//writePermits.acquire();
		System.out.println("permits left to take = " + readPermits.availablePermits());
		try {
			E item = buffer.remove();
			return item;
		} finally {
			writePermits.release();
		}
	}

}
