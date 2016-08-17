import java.util.Random;


public class PetersonLock implements Runnable{

    int id;
    int index;
	static int No_of_iterations;
	static Algo algoObj;
	static double lambda;
	
    public PetersonLock(int n,Algo algo,int index,double lambda,int No_of_iterations){ 
		
    	id = n;
    	PetersonLock.algoObj = algo; 
    	this.index = index;
    	PetersonLock.lambda = lambda ;
    	PetersonLock.No_of_iterations = No_of_iterations;
    	
	}
    
    public static int get_next_rand(double lambda ){
        Random r = new Random();
        double u = r.nextDouble()  ;
        int rand_no = (int) ((Math.log(1 - u ))/(-lambda)) ;
      //  System.out.println("rand wait time ="+(rand_no));
    return rand_no ;
    }

    @Override
	public void run() {

//    		System.out.println("index"+index);
	    	algoObj.lock(index);
	    	try {
				Thread.sleep(get_next_rand(lambda));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
//	    	System.out.println("Number of threads active "+Thread.activeCount());
	    	algoObj.criticalSection();
	    	algoObj.unlock(index);
    	}
//	}
	
}
