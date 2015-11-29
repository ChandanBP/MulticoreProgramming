package concurrentDS;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

public class RunThreads implements Runnable 
{

	int keySpace;
    BST bst;
	
    public RunThreads(BST bst,int keyspace) {
    	
    	this.keySpace = keyspace;
    	this.bst = bst;
    	
	}
	
	
	@Override
	public void run() {
		
		File file = new File("TestCaseFile.txt");
		BufferedReader input = null;
			
		try{
			Random rand = new Random();
			Thread.sleep(rand.nextInt(100));
			String line = "";
			input = new BufferedReader(new FileReader(file));
			while((line = input.readLine()) != null){
				
				int key = Integer.parseInt(line.split(" ")[1]);
				switch(line.split(" ")[0]){
				
					case "Search" :
						boolean s = bst.search(key);
							//System.out.println(Thread.currentThread().getName()+line+" ->Complete! Function return value:"+s);
					break;
					case "Insert" : 
						boolean i =bst.insertKey(key);
							//System.out.println(Thread.currentThread().getName()+" "+line+" ->Complete! Function return value:"+i);
					break;
					case "Delete" : 
						boolean r =bst.remove(key);
						//	System.out.println(Thread.currentThread().getName()+" "+line+" ->Complete! Function return value:"+r);
					break;
					case "TestDelete" : 
						boolean td =bst.remove(key);
						//	System.out.println("Check 1:"+Thread.currentThread().getName()+" "+line+" ->Marked for deleteion "+ td);
					break;
					case "TestSearch" : 
						boolean ts =bst.search(key);
						//	System.out.println("Check 1:"+Thread.currentThread().getName()+" "+line+" ->Search "+ts);
					break;
				}
			}
		}catch ( IOException | InterruptedException e ){
			e.printStackTrace();
		}
	
		
	}

	
}
