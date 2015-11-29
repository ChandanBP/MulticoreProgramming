package concurrentDS;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

class TestCaseGeneration {
	
	public static int KEY_SPACE;
	public static int INSERTS;
	public static int SEARCHS;
	public static int DELETES;
	
	
	public TestCaseGeneration(int k,int i,int s,int d){
		
		KEY_SPACE = k;
		INSERTS = i;
		SEARCHS = s;
		DELETES = d;
		
	}
	public void genTestScripts() throws IOException{
		Random r = new Random(); 

        int totalNums = 0; 
        int count = 0;
        BufferedWriter output = null;
        try {
            File file = new File("TestCaseFile.txt");
            output = new BufferedWriter(new FileWriter(file));
            totalNums = SEARCHS;
            int currVal = 0;
            
            while(count < totalNums-10) { 
                currVal = r.nextInt(KEY_SPACE);
                output.write("Search "+currVal+"\n");
                count++;
            }
            
            while(count < totalNums) { 
                currVal = r.nextInt(KEY_SPACE+100);
                output.write("Search "+currVal+"\n");
                count++;
            }
            totalNums = INSERTS;
            currVal = 0;
            count = 0;
            while(count < (totalNums/2)) { 
                currVal = r.nextInt(KEY_SPACE);
                output.write("Insert "+currVal+"\n");
                count++;
            }
            while(count < totalNums) { 
                currVal = r.nextInt(KEY_SPACE+1000);
                output.write("Insert "+currVal+"\n");
                count++;
            }
            totalNums = DELETES;
            currVal = 0;
            count = 0;
            while(count < totalNums) { 
                currVal =  r.nextInt(KEY_SPACE);
                output.write("Delete "+currVal+"\n");
                count++;
            }
            currVal = r.nextInt(KEY_SPACE);
            output.write("TestDelete "+currVal+"\n");
            output.write("TestSearch "+currVal+"\n");
        } catch ( IOException e ) {
            e.printStackTrace();
        } finally {
            if ( output != null ) output.close();
        }
	}

}
