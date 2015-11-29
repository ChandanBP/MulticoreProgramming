import java.io.InputStreamReader;
import java.util.Random;


public class Helper implements Runnable{

   
	TASLock tasLock;
	double lambda;
	
    public Helper(TASLock algo,double lambda){
		
    	this.tasLock = algo;
    	this.lambda = lambda ;
    	
	}
    public static int get_next_rand(double lambda ){
        Random r = new Random();
        double u = r.nextDouble()  ;
        int rand_no = (int) ((Math.log(1 - u ))/(-lambda)) ;
    return rand_no ;
    }

    @Override
	public void run() {
	    	tasLock.lock();
	    	try {
				Thread.sleep(get_next_rand(lambda));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
	    	tasLock.criticalSection();
	    	tasLock.unlock();
    	}
}
