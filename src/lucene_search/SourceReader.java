package lucene_search;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class SourceReader {
	
	public SourceReader() {
		
	}
	
  public String  ReadFile() {
	  String line;
	  line= null;
	  try {
      FileReader file = new FileReader("directoryToIndex.txt");
      BufferedReader buff = new BufferedReader(file);
      boolean eof = false;
      while (!eof) {
       line = buff.readLine();
        if (line == null)
         eof = true;
        
        else
        	System.out.println("File path "+ line);
        break;
      }
      buff.close();
    } catch (IOException e) {
      System.out.println("Error - " + e.toString());
    }
    
   return line; 
  }
}