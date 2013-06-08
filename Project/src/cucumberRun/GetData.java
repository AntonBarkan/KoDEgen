package cucumberRun;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class GetData {
	
	public static String runCucumber(String path){
		StringBuilder string  = new StringBuilder();
		Process p = null;
		try {
			p = Runtime.getRuntime().exec("cucumber " + path);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
		
		String line;
			try {
				while( (line = in.readLine()) != null ){
					
					string.append(line+"\n");
				
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return string.toString();
	}
	
}
