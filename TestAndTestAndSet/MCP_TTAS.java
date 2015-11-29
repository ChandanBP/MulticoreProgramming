////////////////////////////////////////////////////////////////////////////////////////////////////////
//  File Name : MultiCoreHW1_V1.java																  //
//  Main method for the execution of helper methods. 												  //
// @ Author : Chandan Parameswaraiah (cbp140230) & Deepti Deshpande (dxd132630)						  //
///////////////////////////////////////////////////////////////////////////////////////////////////////

import java.io.*;
import java.util.Random;
import java.math.*;

public class MCP_TTAS {
	static int No_of_threads;
	static int No_of_cores;
	static int LBound ;
	static int UBound ;
	static int Initial_mean ;
	static int current_mean ;
	static int Step_up_for_mean_time;
	static int No_of_hits_to_CS ;
	static int No_of_iterations ;
	static double lambda;
	public MCP_TTAS(int No_of_threads,int No_of_cores, int LBound, int UBound,int Initial_mean,int Step_up_for_mean_time, double lambda,int No_of_hits_to_CS,int No_of_iterations){
		this.No_of_threads = No_of_threads; 
		this.No_of_cores = No_of_cores;
		this.LBound = LBound ;
		this.UBound = UBound ;
		this.Initial_mean = Initial_mean;
		this.Step_up_for_mean_time  = Step_up_for_mean_time ;
		this.lambda = lambda ;
		this.No_of_hits_to_CS = No_of_hits_to_CS ;
		this.No_of_iterations = No_of_iterations;
	}
	@SuppressWarnings("deprecation")
	public void test() throws InterruptedException{
		long cummulative_time = 0;
		long now = 0;
		long later = 0;
		for(int j = 0;j<No_of_iterations ;j++){
			int final_count = 0;
			now = System.currentTimeMillis() ;
			for(int p=0;p<No_of_hits_to_CS;p++){
				TTASLock obj = new TTASLock();
				Thread myThreads[] = new Thread[No_of_threads];
					for (int i = 0; i < No_of_threads; i++) {
						myThreads[i] = new Thread(new Helper(obj,lambda));
					}
					for (int i = 0; i < No_of_threads; i++) {
						myThreads[i].start();
						myThreads[i].join();
					}
					final_count += obj.cr.get();
				
				
			}
			later = System.currentTimeMillis() ;
			System.out.println("Total time taken for iteration "+j+" = "+(later - now));
			cummulative_time += (later - now);
			System.out.println("Expected output: " + (No_of_threads * No_of_hits_to_CS ) + "\t Final output: "+final_count);
		}
		System.out.println(No_of_threads+","+ current_mean+","+(cummulative_time/No_of_iterations)+","+((double)(No_of_threads * No_of_hits_to_CS)/(cummulative_time/No_of_iterations)));
	}
	public static boolean pow_of_two(int no_of_threads){
		if((~no_of_threads & 1 ) == 1){
			return true;
		}
		else
			return false;
	}
	public static void main(String[] args) throws InterruptedException{
//		String fileName = "Config.conf";
		
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
	
	            while((line = bufferedReader.readLine()) != null) {
	            	String input = line.toString().split(" = ")[0];
	            	switch(input){
	            	case "No_of_threads" : No_of_threads = Integer.parseInt(line.toString().split(" = ")[1]);
	            							break;
	            	case "No_of_cores" : No_of_cores = Integer.parseInt(line.toString().split(" = ")[1]);
											break;
	            	case "LBound" : LBound = Integer.parseInt(line.toString().split(" = ")[1]);
											break;
	            	case "UBound" : UBound = Integer.parseInt(line.toString().split(" = ")[1]);
											break;
	            	case "Initial_mean" : Initial_mean = Integer.parseInt(line.toString().split(" = ")[1]);
											break;
	            	case "Step_up_for_mean_time" : Step_up_for_mean_time = Integer.parseInt(line.toString().split(" = ")[1]);
											break;					
	            	case "No_of_hits_to_CS" : No_of_hits_to_CS = Integer.parseInt(line.toString().split(" = ")[1]) ;
	            							break;
	            	case "No_of_iterations" : No_of_iterations = Integer.parseInt(line.toString().split(" = ")[1]);
											break;
	            	}
	            }   
	            bufferedReader.close();         
	        }
	        catch(FileNotFoundException ex) {
	            System.out.println("Unable to open file '" + fileName + "'");                
	        }
	        catch(IOException ex) {
	            System.out.println("Error reading file '" + fileName + "'");
	        } 
			lambda = (double)1 / Initial_mean;
			No_of_hits_to_CS  = No_of_hits_to_CS / No_of_threads ;
			if(pow_of_two(No_of_threads)){
				if(No_of_threads > (2*No_of_cores)){
					System.out.println("Executing more number of threads than the number of cores");
				}
				for(int i =LBound ; i<=UBound;i=i+Step_up_for_mean_time ){
					current_mean = i;
					System.out.println("Iterations no :"+i);
					new MCP_TTAS(No_of_threads,No_of_cores, LBound, UBound,current_mean,Step_up_for_mean_time, lambda,No_of_hits_to_CS, No_of_iterations).test();
				}
				
			}
			else{
				System.out.println("'"+fileName+"' Config file has incorrect number of threads defined for variable <No_of_threads> ");
			}
		}
	}

}
