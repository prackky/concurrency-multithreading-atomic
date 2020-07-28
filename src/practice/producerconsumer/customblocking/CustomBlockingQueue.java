package practice.producerconsumer.customblocking;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class CustomBlockingQueue<E> {

	private Queue<E> queue;
	private int max = 16;
	private final ReentrantLock lock = new ReentrantLock(); 
	private final Condition notEmpty = lock.newCondition(); // producer after producing a product uses this to signal all awaiting consumer threads
	private final Condition notFull = lock.newCondition(); // consumer after consuming a product uses this to signal all awaiting producer threads

	public CustomBlockingQueue(int size) {
		super();
		this.queue = new LinkedList<>();
		this.max = size;
	}

	public void put(E e) throws InterruptedException {
		lock.lock();
		System.out.println("before put Queue size = " + queue.size());
		try {
			while (queue.size() == max) {
				notFull.await();
			}
			queue.add(e);
			notEmpty.signalAll();
		} finally {
			lock.unlock();
		}
	}

	public E take() throws InterruptedException {
		lock.lock();
		System.out.println("before take Queue size = " + queue.size());
		try {
			while (queue.size() == 0) {
				notEmpty.await();
			}
			E item = queue.remove();
			notFull.signalAll();
			return item;
		} finally {
			lock.unlock();
		}
	}

}
