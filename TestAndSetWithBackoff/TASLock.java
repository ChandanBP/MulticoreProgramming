import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class TASLock implements Lock {
	
	commonResource cr = new commonResource();
	private AtomicBoolean state = new AtomicBoolean(false);
	private static  int MIN_DELAY = 0;
	private static  int MAX_DELAY = 100;
	public TASLock(int min,int max){
		MIN_DELAY = min;
		MAX_DELAY = max;
	}
	public void lock() {
		Backoff backoff = new Backoff(MIN_DELAY, MAX_DELAY);
			while (true) {
				while (state.get()) {};
					if (!state.getAndSet(true)) {
						return;
					} else {
						try {
							backoff.backoff();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
			}
	}
	public void criticalSection(){
		cr.increment();
	}
	public void unlock() {
		state.set(false);
	}
	@Override
	public void lockInterruptibly() throws InterruptedException {
	}
	@Override
	public Condition newCondition() {
		return null;
	}
	@Override
	public boolean tryLock() {
		return false;
	}
	@Override
	public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
		return false;
	}
}
