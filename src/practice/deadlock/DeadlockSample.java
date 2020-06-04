package practice.deadlock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DeadlockSample {
	static int count = 0;

	public static void main(String[] args) throws InterruptedException {
		Lock lock1 = new ReentrantLock();
		Lock lock2 = new ReentrantLock();
		Runnable inc = () -> {
			try {
				lock1.lock();
				for (int i = 0; i < 1000; i ++) {
					lock2.lock();
					count++;
					lock2.unlock();
				}
			} finally {
				lock1.unlock();
			}
		};
		Runnable dec = () -> {
			try {
				lock2.lock(); // to correct this change this to lock1
				for (int i = 0; i < 1000; i ++) {
					lock1.lock(); // to correct this change this to lock2
					count--; 
					lock1.unlock();
				}
			} finally {
				lock2.unlock();
			}
		};
		Thread t1 = new Thread(inc);
		Thread t2 = new Thread(dec);

		t1.start();
		t2.start();

		t1.join();
		t2.join();

		System.out.println("Count = " + count);

	}

}
