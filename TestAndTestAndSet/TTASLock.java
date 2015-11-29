import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class TTASLock implements Lock {
	AtomicBoolean state = new AtomicBoolean(false);
	commonResource cr = new commonResource();
	public void lock() {
	while (true) {
		while (state.get()) {};
		if (!state.getAndSet(true))
		return;
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
public boolean tryLock(long arg0, TimeUnit arg1) throws InterruptedException {
	// TODO Auto-generated method stub
	return false;
}
}