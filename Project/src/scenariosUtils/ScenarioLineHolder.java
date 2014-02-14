package scenariosUtils;


import java.util.LinkedHashSet;

import static common.Constants.*;
import static common.CommonFunctions.*;

public class ScenarioLineHolder {

	LinkedHashSet<String> lines;
	
	public ScenarioLineHolder() {
		lines = new LinkedHashSet<>();
	}
	
	public void addLines(String text){
		String[] parsedText = text.split(END_OF_LINE);
		for (String line : parsedText){
			line = line.substring(line.indexOf(" "),line.length()).trim();
			if (line.contains("\"")){
				lines.add(line.replace("\""+getStringFromFirstStringToSecondString(line, "\"", "\"")+"\"" ,"").trim());
			}else if (line.contains("$")){
				String[] tempLine = line.split(" ");
				for (String s : tempLine){
					if (s.startsWith("$")){
						lines.add(line.replace(s, " ").trim());
					}
				}
			}else{
				
				lines.add(line.trim());
			}
			
		}
	}
	
	public String getLineI(int i){
		return lines.toArray(new String[0])[i];
	}
}
