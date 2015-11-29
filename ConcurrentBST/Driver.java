package concurrentDS;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Driver 
{
	
	 int No_of_threads;
	 int No_of_iterations ;
	 int keyspace;
	 int total_operations;
	 int search;
	 int insert;
	 int delete;
	Thread threadObj[];
	
	
	public static boolean pow_of_two(int no_of_threads){
		
		if((~no_of_threads & 1 ) == 1){
			return true;
		}
		else
			return false;
		
	}

	public static void main(String[] args) throws InterruptedException {
		

		if(args.length == 0){
			System.out.println("Configuration file not specified!");
		}
		else{
			String fileName = args[0];
			String line = null;
			
			
			
			try {
	            FileReader fileReader = 
	                new FileReader(fileName);
	
	            BufferedReader bufferedReader = 
	                new BufferedReader(fileReader);
	
	            
	            Driver driverObj = new Driver();
	            while((line = bufferedReader.readLine()) != null) {
	            	String input = line.toString().split(" = ")[0];
	            	int val = Integer.parseInt(line.toString().split(" = ")[1]);
	            	switch(input){
	            	case "KEYSPACE": driverObj.keyspace = Integer.parseInt(line.toString().split(" = ")[1]);
						break;
	            	case "TOTAL_OPERTATIONS": driverObj.total_operations = val;
						break;
	            	case "SEARCH%": driverObj.search = driverObj.total_operations * val /100;
						break;
	            	case "INSERT%": driverObj.insert = driverObj.total_operations * val /100;
						break;
	            	case "DELETE%": driverObj.delete = driverObj.total_operations * val/100;
						break;
	            	case "NUM_THREADS": driverObj.No_of_threads = val;
                    break; 
	            	}
	            }
	            bufferedReader.close(); 
	            TestCaseGeneration tcg = new TestCaseGeneration(driverObj.keyspace,driverObj.insert,driverObj.search,driverObj.delete);
	            System.out.println("Generating the test scripts...");
	    		tcg.genTestScripts();
	    		System.out.println("Complete! Test scripts are in TestCaseFile.txt");
	    		for(int td = 2;td<=32;td=td+2){
	    			boolean treeValid = false;
	    			long cummulative_time = 0;
		    		long now = 0;
		    		long later = 0;
//		    		System.out.println("Performing concurrent operations on basic tree...");
		    		double time = 0.0;
			    		for(int iter = 0;iter<3;iter++){
				    		driverObj.threadObj = new Thread[td];
				    		BST bst = new BST(driverObj.keyspace);
		//		    		System.out.println("Constructing the basic tree for "+keyspace+" ...");
				    		bst.constructTree(0, driverObj.keyspace);
		//		    		System.out.println("Initial tree built");
		    		
			    			now = System.currentTimeMillis() ;
			    			for (int i = 0; i < td; i++) {
			    				driverObj.threadObj[i] = new Thread(new RunThreads(bst,driverObj.keyspace), "Thread"+(i+1));
			    				
			    			}
			    			for (int i = 0; i < td; i++) {
			    				driverObj.threadObj[i].start();
			    				driverObj.threadObj[i].join();
							}
			    			
			    		later = System.currentTimeMillis() ;
			    		cummulative_time = later - now;
			    		bst.inOrder(bst.root);
			    		treeValid = bst.isSorted();
			    		
//			    		if(treeValid)
//			    			System.out.println("Check 2:Final tree is valid");
//			    		else
//			    			System.out.println("Check 2:Final tree is invalid");
			    			time += (cummulative_time/1000.00/td);
			    		}
//		    		System.out.println("Total threads:"+td+ "Throughput is "+(total_operations/(time/3))+" operations/sec");
			    		System.out.println(td+","+(driverObj.total_operations/(time/3))+","+treeValid);
	    		}
	        }
			
	        catch(FileNotFoundException ex) {
	            System.out.println("Unable to open file '" + fileName + "'");                
	        }
			
			
	        catch(IOException ex) {
	            System.out.println("Error reading file '" + fileName + "'");
	        } 
			
		}
	
	}
}
