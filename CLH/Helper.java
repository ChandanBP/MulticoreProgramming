import java.util.Random;


public class Helper implements Runnable{

   
	CLHLock clhLock;
	double lambda;
	 
    public Helper(CLHLock algo,double lambda){
		
    	this.clhLock = algo;
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
    	clhLock.lock();
	    	try {
				Thread.sleep(get_next_rand(lambda));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
	    	clhLock.criticalSection();
	    	clhLock.unlock();
    	}
}
