package practice.atomiccollection;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicIncrementDecrement {

	private static AtomicInteger count = new AtomicInteger(0);

	public static void increment() {
		count.incrementAndGet();
	}

	public static void decrement() {
		count.decrementAndGet();
	}

	public static int getCount() {
		return count.get();
	}

	public static void main(String[] args) throws InterruptedException {

		Runnable incrementor = () -> {
			for (int i = 0; i < 10000000; i++) {
				increment();
			}
		};

		Runnable decrementor = () -> {
			for (int i = 0; i < 10000000; i++) {
				decrement();
			}
		};

		Runnable getter = () -> {
			while (getCount() > 0) {
				System.out.println(Thread.currentThread().getName()+ " - Current count is greater than zero");
				try {
					Thread.sleep(5);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			while (getCount() < 0) {
				System.out.println(Thread.currentThread().getName()+ " - Current count is lesser than zero");
				try {
					Thread.sleep(5);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};

		Thread i1 = new Thread(incrementor);
		Thread d1 = new Thread(decrementor);
		Thread i2 = new Thread(incrementor);
		Thread d2 = new Thread(decrementor);
		Thread g1 = new Thread(getter);
		Thread g2 = new Thread(getter);

		long start = System.currentTimeMillis();
		i1.start();
		i2.start();
		d1.start();
		d2.start();
		g1.start();
		g2.start();

		i1.join();
		i2.join();
		d1.join();
		d2.join();
		g1.join();
		g2.join();
		long end = System.currentTimeMillis();

		System.out.println("Time difference = " + (end - start) + "ms");
		System.out.println("Count after process = " + getCount());
	}
}
