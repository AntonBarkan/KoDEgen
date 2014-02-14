package testGenerator;

import java.util.Vector;

import ontology.Ontology;

import cucumberRun.GetData;


import static main.Steps.*;
import static constants.Loggers.GLOBAL_LOG;
import static main.Steps.*;


public class StepsGenerator {
	
	
	private static final String FROM_TEXT = "You can implement step definitions for undefined" +
											" steps with these snippets:\n\n", 
								TO_TEXT   = "If you want snippets in a different programming language,";
	
	private Ontology ontology;
	private String data;
	

	private Vector<String> lines;
	public StepsGenerator( Ontology ontology )
	{
		this.ontology = ontology;
		data = GetData.runCucumber(PATH+""+PROJECT_NAME+".features");
		lines = getScenario(data);
		GLOBAL_LOG.info( "->" + data.length() );
		data = data.substring( data.indexOf(FROM_TEXT)+FROM_TEXT.length() );
		if (data.contains(TO_TEXT)){
			data = data.substring( 0 , data.indexOf( TO_TEXT ) );
		}
		
	}
	
	public void execute() throws Exception{
		for(String s : dataParser( data , lines ) ) {
			TestCreator.getInstance().addStep( s ) ;
		}
	}
	
	private String[] dataArr;
	private int index=-1;
	
	private String[] dataParser( String data, Vector<String> lines ) throws Exception
	{
		dataArr = data.split("end\n\n");
		for(index = 0 ; index < dataArr.length ; index++){
			if( index>1 && dataArr[index].trim().startsWith("Given") && dataArr[index-1].trim().startsWith("Then")){
				this.ontology.cleanClassMap();
			}
			dataArr[index] += "end\n";
			dataArr[index] = fillCode(dataArr[index], lines.get(index) , index);
		}

		return dataArr;
	}
	
	private String fillCode(String step,String line , int testIndex) throws Exception
	{
		String retVal = "";
		String[] dataAr = step.split("\n");
		dataAr[0] = this.rewriteArguments(dataAr[0]);
		dataAr[1] = "\n"+ this.ontology.findCoincidence(dataAr[0],featureHolder.getLine(index));
		for(String s : dataAr){
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
	
	public String getTest(int i){
		if (i>index) return "";
		return dataArr[i];
	}
	
	public void addToTest(int i,String s){
		dataArr[i] = dataArr[i].substring(0, dataArr[i].lastIndexOf("\n"));
		dataArr[i] = dataArr[i].substring(0, dataArr[i].lastIndexOf("\n"));
		dataArr[i] +=  "\n" + s + "end\n";
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
			if(!( s.trim().startsWith("Feature:") || s.trim().startsWith("Scenario:") || s.trim().isEmpty() || s.trim().startsWith("#") )){
				if( !s.trim().isEmpty() ){
					lines.add(s.trim());
				}
			}
		}
		return lines;
	}

	
	public String getData() {
		return data;
	}

	public String getLineForTest(int i) {
		return lines.get(i).substring(0,lines.get(i).indexOf("#"));
	}

	
	
}
