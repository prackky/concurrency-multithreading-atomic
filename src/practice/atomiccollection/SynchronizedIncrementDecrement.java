package practice.atomiccollection;

public class SynchronizedIncrementDecrement {

	public static void main(String[] args) throws InterruptedException {

		Counter count = new Counter();

		Runnable incrementor = () -> {
			for (int i = 0; i < 10000000; i++) {
				count.increment();
			}
		};

		Runnable decrementor = () -> {
			for (int i = 0; i < 10000000; i++) {
				count.decrement();
			}
		};

		Runnable getter = () -> {
			while (count.getCount() > 0) {
				System.out.println(Thread.currentThread().getName()+ " - Current count is greater than zero");
				try {
					Thread.sleep(2);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			while (count.getCount() < 0) {
				System.out.println(Thread.currentThread().getName()+ " - Current count is lesser than zero");
				try {
					Thread.sleep(2);
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
		System.out.println("Count after process = " + count.getCount());
	}
}

class Counter {
	private int count = 0;

	public void increment() {
		synchronized (this) {
			count ++;
		}
	}

	public void decrement() {
		synchronized (this) {
			count --;
		}
	}

	public int getCount() {
		synchronized (this) {
			return count;
		}
	}

	public void setCount(int count) {
		this.count = count;
	}
}
