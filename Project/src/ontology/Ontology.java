package ontology;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map.Entry;
import java.util.Set;


import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;

import statecharAnalizer.Analizer;
import statecharAnalizer.BooleanAnalizer;
import statecharAnalizer.StatecharHolder;
import testGenerator.UnitTestStruct;

import codeCreator.ClassCreator;
import codeCreator.CodeCreator;
import exceptions.SameFieldException;

import StateMachineXML.Edge;
import StateMachineXML.State;
import StateMachineXML.StateMachineXMLReader;
import attempts.ReadXMLFile;
import attempts.XMLProduct;

import static common.CommonFunctions.*;
import static main.Steps.*;


public class Ontology {
	

		
	private LinkedList<State> statesList;
	private LinkedList<XMLProduct> ontologyList;
	
	/**
	 * map.key   = class name
	 * map.value = class type
	 */
	private HashMap<String, String> globalClassMap;
	
	private LinkedList<String> code;
	
	private StatecharHolder statecharHolder;
	
	public Ontology() throws SameFieldException, IOException
	{
		this.code = new LinkedList<>();
		this.globalClassMap = new HashMap<String, String>();
		this.execute();
	}
	
	
	
	private void execute() throws SameFieldException, IOException
	{
		ReadXMLFile readerOntology = new ReadXMLFile(ONTOLOGY_PATH);
		readerOntology.execute();
		this.ontologyList = readerOntology.getProductList();	
		StateMachineXMLReader readerStates = new StateMachineXMLReader(ONTOLOGY_STATES_PATH);
		readerStates.execute();
		this.statesList = readerStates.getList();
		statecharHolder = new StatecharHolder(this.ontologyList ,this.statesList );
			
		
	}

	public String  findCoincidence( String string , String line ) throws Exception 
	{
		SystemCalls sc = null;
		if ((sc = SystemCalls.getSystemCallSolver(line))!=null){
			sc.injectMockCreation(globalClassMap , string, line);
			return sc.createUnitTest(globalClassMap, string);
		}
		UnitTestStruct unitTestStruct = new UnitTestStruct( string );
		Set<String> objects;
		Set<String> mockObjects = new HashSet<>();
		if( !(objects = containObject(line)).isEmpty() ){
			mockObjects = getMockObjects(objects);
		}
		
		String retString = "";
		for(XMLProduct prod : this.ontologyList){
			if(string.toLowerCase().contains(prod.getName().toLowerCase().trim()))
			{
				String temp = "";
				if(mockObjects.isEmpty()){
					temp = "\t"+this.classNameGenerate(prod.getName(),  string)+"\n";
					retString += addToCode(temp);
				}
				
				if( !this.addToCode(temp).isEmpty() ){
					unitTestStruct.addClass( temp.trim().substring(0,temp.trim().indexOf("=")).trim() );
				}
			}
		}
		for (XMLProduct prod : this.ontologyList) {
			int parameterNumbers = getParametersNumbers( string );
			
			for( String s : prod.getAttribute() )
			{
				if (string.contains("|") && string.substring(string.indexOf("|")).toLowerCase().contains( s ) ) {
					parameterNumbers-- ;
					String temp = "\t" + this.classAttributeValueGenerate( prod.getName(),s,  string)+"\n";
					retString += temp;
					unitTestStruct.setInUSe( s );
				}
				
			}
			
			for( String s : prod.getAttribute() )
			{
				if( parameterNumbers != 0 && string.toLowerCase().contains( s.toLowerCase() ))
				{
					parameterNumbers--;
					String temp = "\t" + this.classAttributeValueGenerate( prod.getName(),s.replace(" ", "_"),  string)+"\n";
					retString += temp;
					unitTestStruct.setInUSe( s );
				}
			}
			
		}

		for( State s : statesList )
		{
			for(Edge e : s.getEdges())
			{
				if(string.toLowerCase().contains(e.getName().toLowerCase())){
					if(mockObjects.isEmpty()){
						String temp = "\t"+this.testFunctionGeneretor( s , e , string , unitTestStruct )+"\n";
						retString += temp;
					}else{
						retString = createMockObject(mockObjects.iterator().next(), e.getName()  , s.getClassName() , getStringFromFirstStringToSecondString(string, "|", "|") );
					}
					
				}
			}
		}
		return retString;
	}






	private Set<String> getMockObjects(Set<String> objects) {
		Set<String> mockObjects = new HashSet<>(); 
		for (String o : objects){
			boolean flag = false;
			for (XMLProduct prod : this.ontologyList){
				if (o.trim().equalsIgnoreCase(prod.getName().trim())){
					flag = true;
				}
			}
			if (!flag){mockObjects.add(o);}
		}
		return mockObjects;
	}



	private Set<String> containObject(String line) {
		Set<String> classesSet = new HashSet<>();
		String[] wordArray = line.split(" ");
		int i = 1;
		while (i<wordArray.length && ( wordArray[i].trim().isEmpty() || Character.isLetter(wordArray[i].charAt(0)))){
			if(wordArray[i].trim().isEmpty()){i++;continue;}
			String temp = "";
			while (i<wordArray.length  && Character.isUpperCase(wordArray[i].charAt(0))){
				temp += " "+wordArray[i++];
			}
			if(!temp.isEmpty()){
				classesSet.add(temp.trim());
			}else{
				i++;
			}
			
		}
		return classesSet;
	}



	private int getParametersNumbers(String line) {
		if( !line.contains("|")){return 0;}
		line = line.substring( line.indexOf("|") );
		return line.split(",").length ;
	}



	private String addToCode(String temp) 
	{
		if( !code.contains(temp) )
		{
			if(temp.trim().startsWith("@"))
			{
				code.add(temp);
			}
			return temp;
		}
		return "";
	}



	private String classAttributeValueGenerate(String className , String attribute, String line) 
	{
		attribute = attribute.toLowerCase();
		String retString = "";
		if( //this.globalClassList.contains(this.claseNameFinder(className, line)) ){
			this.globalClassMap.keySet().contains(this.findClassName(className, line)))
		{
			retString = "@";
		}
		if (line.startsWith("Then"))
		{	
			if (line.contains("print")){
				return retString;
			}
			return
					retString + "" + this.findClassName( className, line) + "." +
					"stub(:" + attribute + ").and_return(" + attribute + ")"
					+ "\n" +
					"\tassert " + attribute + " == " + retString + "" + 
					this.findClassName( className, line) + "." + attribute + ".to_s" ;  
		}
		else
		{
			return retString + this.findClassName(className, line) + "." + attribute + 
					" = " + 
					((getParametersNumbers(line)==1)?getStringFromFirstStringToSecondString(line, "|", "|"):attribute) ;
		}		//TODO lineHolder testHolder
	}



	private String testFunctionGeneretor(State state, Edge edge , String line , UnitTestStruct unitTestStruct) 
	{
		String retString = "";
		if( this.globalClassMap.keySet().contains(this.findClassName(state.getClassName(), line)) )
		{
			retString = "@";
		}
		
		if(line.startsWith("Then"))
		{
			return 
					retString + this.findClassName(state.getClassName(), line) + ".stub(:" + edge.getName().replaceAll(" ", "_") + ").and_return(" + 
					unitTestStruct.getParamettersString(state.getClassName()) + ")"	+ "\n" +
					 "\t" + "assert " + unitTestStruct.getParamettersString(state.getClassName()) + " == " + retString 
					+ this.findClassName(state.getClassName(), line) + "." + edge.getName().replaceAll(" ", "_") + "( ).to_s";
		}
		return retString + this.findClassName( state.getClassName() , line ) + "."
			+edge.getName().replaceAll(" ", "_") + "(" +
			unitTestStruct.getParamettersString(this.findClassName( state.getClassName() , line )) + ")" ;
		
	}
	
	private String findClassName(String className , String line)
	{
			return className.replaceAll(" ", "_").toLowerCase();
	}



	private String classNameGenerate(String name , String line) 
	{
		String retString = "";
		if( line.startsWith("Given") || (this.globalClassMap.keySet().contains(this.findClassName(name, line))) )
		{
			retString += "@";
			this.globalClassMap.put( findClassName( name , line ) , WordUtils.capitalize(name.replace(" ", "_")) );
		}
		return retString+this.findClassName( name , line ) + " = " 
				+ WordUtils.capitalize(name.replace(" ", "_"))+".new";
	}

	
	public void generateCode(){
		for( XMLProduct prod : ontologyList ){
			ClassCreator c = new ClassCreator(prod.getName());
			for( String s : prod.getAttribute()){
				try {
					c.addField(s);
				} catch (SameFieldException e) {
					e.printStackTrace();
				}
			}
			CodeCreator.getInstance().addClassToMap(c);
		}		
	}
	
	public Object[] getGlobalClasses(){
		return this.globalClassMap.keySet().toArray();
	}



	public String getClassType(String className) {
		return this.globalClassMap.get(className);
	}
	
	public void cleanClassMap(){
		this.globalClassMap.clear();
		this.code.clear();
	}
	
	private Set<String> getClassNames(String classType){
		Set<String> classes = new HashSet<>(); 
		for (Entry<String,String> s : globalClassMap.entrySet()){
			if (s.getValue().trim().equalsIgnoreCase(classType.trim())){
				classes.add(s.getKey());
			}
		}
		return classes;
		
	}
	
	private String createMockObject(String name, String method , String className ,  String paramsString){
		
		name=name.trim().replace(" ", "_");
		Set<String> classes = getClassNames(className);
		if (classes.size()!=1){
			//FIXME
		}
		
		return "\t" + StringUtils.lowerCase(name) + " = mock( \"" + StringUtils.capitalize(name) + " \")\n"+
				"\t" + StringUtils.lowerCase(name) + ".stub!( :" + method + " ).with( " + paramsString  + " ) do \n" +
				"\t\t" +   "@" + classes.iterator().next() + "." + method + "( " + paramsString + " ) \n" +
				"\tend\n" +
				"\t" + StringUtils.lowerCase(name) + "." + method + "( " + paramsString + " )\n";
			
	}
	
}
