package TestGenerator;

import java.util.Vector;

import cucumberRun.GetData;


import Ontology.Ontology;
import static Ontology.Ontology.*;


public class StepsGenerator {
	
	
	private static final String FROM_TEXT = "You can implement step definitions for undefined steps with these snippets:\n\n", 
								TO_TEXT   = "If you want snippets in a different programming language,";
	
	private Ontology ontology;
	
	public StepsGenerator(Ontology ontology)
	{
		this.ontology = ontology;
		String data = GetData.runCucumber(PATH+""+PROJECT_NAME+".features");
		Vector<String> lines = getScenario(data);
		data = data.substring( data.indexOf(FROM_TEXT)+FROM_TEXT.length() );
		data = data.substring(0, data.indexOf(TO_TEXT));
		for(String s : dataParser(data, lines)){
			TestCreator.getInstance().addStep(s);
		}
	}
	
	
	private String[] dataParser( String data, Vector<String> lines )
	{
		String[] dataArr = data.split("end\n\n");
		for(int i = 0 ; i < dataArr.length ; i++){
			dataArr[i] += "end\n";
			dataArr[i] = fillCode(dataArr[i], lines.get(i));
		}

		return dataArr;
	}
	
	private String fillCode(String step,String line)
	{
		String retVal = "";
		String[] dataArr = step.split("\n");
		dataArr[0] = this.rewriteArguments(dataArr[0]);
		dataArr[1] = "\n"+ this.ontology.findCoincidence(dataArr[0],line);
		for(String s : dataArr){
			retVal += s;
		}
		return retVal+"\n";	
	}
	
	private String rewriteArguments(String line) 
	{
		String[] args = this.getArgsArray(line);
		for(int  i = 0 ; i < args.length ; i++ ){
			line = line.replace("arg"+(i+1), args[i]);
		}
		return line;
		
	}


	private String[] getArgsArray(String line) 
	{
		String[] splittedLine = line.split("<");
		String arr[] = new String[splittedLine.length-1];
		for(int i = 0 ; i < splittedLine.length-1 ; i++ ){
			 arr[i]= splittedLine[i+1].substring(0,splittedLine[i+1].indexOf(">"));
		}
		return arr;
		
	}


	private Vector<String> getScenario(String data)
	{
		Vector<String> lines = new Vector<>();
		for(String s : data.split("\n")){
			if(!( s.trim().startsWith("Feature:") || s.trim().startsWith("Scenario:") || s.trim().isEmpty())){
				lines.add(s.trim());
			}
		}
		return lines;
	}
	
	
}
