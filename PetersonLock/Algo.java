public class Algo {

	boolean flag[][];
	boolean victim[][];
	int num_threads;
	int level[];
	int column[];
	int backTrace[][];
    int jIndex[];
    int depth;
    int bti[];
    static int counter;
    commonResource cr;
    
	public Algo(int n){
		num_threads = n;
	    counter = 0;
		level = new int[n];
	    column = new int[n];
	    depth = (int) (Math.log(n)/Math.log(2));
	    jIndex = new int[depth+1];
	    bti = new int[n];
        backTrace = new int[n][depth+1];
	    victim = new boolean[depth+1][n];
        flag = new boolean[depth+1][n];
        cr = new commonResource();
	}
	
	public void criticalSection(){
		cr.increment();
	}
	
	public void lock(int index){
//		System.out.println("thread "+(index+1)+" entered lock");
		int r =0;
		int j,c;
		
	while(r<depth){
		
		if (r == 0) {
			
			// To set the thread in binary tree
			column[index] = jIndex[r];

			// jIndex is for next free position in a respective level
			jIndex[r]+=1;
			
			backTrace[index][0] = column[index];
		}

		c = column[index];
		if(c==0 || c ==1){
			j = 1-c;
		}
		else if(c%2 == 0){
			j = c+1;
		}
		else{
			j = c-1;
		}
		
		victim[r][c] = true;
		flag[r][c] = true;
		
	    while(flag[r][j] && victim[r][c]){
	    }
		
		level[index]+=1;
		column[index] = jIndex[r+1];
		jIndex[r+1]+=1;
		backTrace[index][bti[index]] = column[index];
		bti[index]+=1;
		r = level[index];
	}
//		System.out.println("thread "+(index+1)+" left lock");
	}
	
	public void unlock(int index){
		
//		System.out.println("thread "+(index+1)+" entered unlock");
		int r = level[index]-1;
		int t = depth-1;
		int c = backTrace[index][t];
		
		while(r>=0){
			flag[r][c] = false;
			r--;
			t--;
			if(t>=0){
				c = backTrace[index][t];
			}
		}
		
		level[index] = 0;
		column[index] = backTrace[index][0];
//		System.out.println("thread "+(index+1)+" left unlock");
	}
}
