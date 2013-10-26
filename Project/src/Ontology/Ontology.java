package Ontology;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;


import org.apache.commons.lang.WordUtils;

import codeCreator.ClassCreator;
import codeCreator.CodeCreator;

import Exceptions.SameFieldException;
import StateMachineXML.Edge;
import StateMachineXML.State;
import StateMachineXML.StateMachineXMLReader;
import TestGenerator.UnitTestStruct;
import attempts.ReadXMLFile;
import attempts.XMLProduct;


public class Ontology {
	public static final String 
	/*
		    PROJECT_NAME = "shop",
			PATH = "/home/anton/Documents/project/shop/features/",
			ONTOLOGY_PATH = "/home/anton/Documents/project/shop/shop.xml" ,
			ONTOLOGY_STATES_PATH = "/home/anton/Documents/project/shop/shop_ontology_state.xml",
			STEP_FILE_PATH = "/home/anton/Documents/project/shop/features/step_definitions/shop_steps.rb";
		*/
	PROJECT_NAME = "ATM",
	PATH = "/home/anton/Documents/project/ATM/features/",
	ONTOLOGY_PATH = "/home/anton/Documents/project/ATM/ATM.xml" ,
	ONTOLOGY_STATES_PATH = "/home/anton/Documents/project/ATM/ATM_ontology_state.xml",
	STEP_FILE_PATH = "/home/anton/Documents/project/ATM/features/step_definitions/ATM_steps.rb";

		
	private LinkedList<State> statesList;
	private LinkedList<XMLProduct> ontologyList;
	
	/**
	 * map.key   = class name
	 * map.value = class type
	 */
	private HashMap<String, String> globalClassMap;
	
	private LinkedList<String> code;
	
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
			
		
	}

	public String  findCoincidence( String string , String line ) 
	{
		UnitTestStruct unitTestStruct = new UnitTestStruct( string );
		String retString = "";
		for(XMLProduct prod : this.ontologyList){
			if(string.toLowerCase().contains(prod.getName().toLowerCase().trim()))
			{
				
				String temp = "\t"+this.classNameGenerate(prod.getName(),  string)+"\n";
				retString += addToCode(temp);		
				
				if( !this.addToCode(temp).isEmpty() ){
					unitTestStruct.addClass( temp.trim().substring(0,temp.trim().indexOf("=")).trim() );
				}
			}
		}
		
		for( XMLProduct prod : this.ontologyList )
		{
			int parameterNumbers = getParametersNumbers( string );
			for( String s : prod.getAttribute() )
			{
				if( string.contains("|") && 	string.substring(string.indexOf("|")).toLowerCase().contains( s ) )
				{
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
					String temp = "\t"+this.testFunctionGeneretor( s , e , string , unitTestStruct )+"\n";
					retString += temp;
				}
			}
		}
		return retString;
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
		if(line.startsWith("Then"))
		{
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
					" = " + attribute ;
		}
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
					retString + this.findClassName(state.getClassName(), line) + ".stub(:" + edge.getName() + ").and_return(" + 
					unitTestStruct.getParamettersString(state.getClassName()) + ")"	+ "\n" +
					 "\t" + "assert " + unitTestStruct.getParamettersString(state.getClassName()) + " == " + retString 
					+ this.findClassName(state.getClassName(), line) + "." + edge.getName()+"( ).to_s";
		}
		return retString + this.findClassName( state.getClassName() , line ) + "."
			+edge.getName() + "(" +
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
}
