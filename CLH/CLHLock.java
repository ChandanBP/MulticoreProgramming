import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;


class QNode{
	boolean locked;
}

public class CLHLock{

	static AtomicReference<QNode>tail = new AtomicReference<QNode>();
	QNode myPred;
	QNode myNode;
	commonResource cr = new commonResource();
	
	public CLHLock(){
		myNode = new QNode();
		myPred = null;
		
		tail.set(new QNode());
		tail.get().locked = false;
	}

	public static AtomicReference<QNode> getTail() {
		return tail;
	}

	public static void setTail(AtomicReference<QNode> tail) {
		CLHLock.tail = tail;
	}

	public QNode getMyPred() {
		return myPred;
	}

	public void setMyPred(QNode myPred) {
		this.myPred = myPred;
	}

	public QNode getMyNode() {
		return myNode;
	}

	public void setMyNode(QNode myNode) {
		this.myNode = myNode;
	}
	
	public void lock(){
		
		QNode pred = tail.get(); 
		setMyPred(pred);
		
		myNode.locked = true;
		tail.set(myNode);
		
		while(pred.locked){}
	}
	
	public void criticalSection(){
		cr.increment();
	}
	
	public void unlock(){
		myNode.locked = false;
		setMyNode(getMyPred());
	}

}
