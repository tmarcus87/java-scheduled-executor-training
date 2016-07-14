import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author ono_takahiko
 * @since 16/07/14
 */
@Slf4j
public class Main {

	private ScheduledExecutorService scheduledExecutorService;

	public Main() throws InterruptedException {
		this.scheduledExecutorService =
				Executors.newSingleThreadScheduledExecutor();

		MyRunnable runnable = new MyRunnable();

		scheduledExecutorService.scheduleAtFixedRate(runnable, 0L, 10L, TimeUnit.MILLISECONDS);
		log.info("start");

		Thread.sleep(5000);

		log.info("shutdown");
		scheduledExecutorService.shutdown();
		scheduledExecutorService.awaitTermination(2000L, TimeUnit.MILLISECONDS);
	}

	public static void main(String... args) throws InterruptedException {
		new Main();
	}

	@Slf4j
	public static class MyRunnable implements Runnable {

		private AtomicInteger counter = new AtomicInteger();

		@Override
		public void run() {
			for (int i = 0; i < 10; i++) {
				log.info("tick {}", counter.get());
				if (counter.incrementAndGet() >= 50) {
					throw new RuntimeException();
				}
			}
		}

		public int getValue() {
			return counter.get();
		}

	}
}
