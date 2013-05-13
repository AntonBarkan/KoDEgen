package Ontology;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;


import org.apache.commons.lang.WordUtils;

import CodeCreator.ClassCreator;
import CodeCreator.CodeCreator;
import Exeptions.SameFieldException;
import StateMachineXML.Edge;
import StateMachineXML.State;
import StateMachineXML.StateMachineXMLReader;
import attempts.ReadXMLFile;
import attempts.XMLProduct;


public class Ontology {
	private String ontologyPath, ontologyStatesPath;
	private LinkedList<State> statesList;
	private LinkedList<XMLProduct> ontologyList;
	private LinkedList<String> globalClassList;
	private LinkedList<String> code;
	
	private static final String NAME_TAG = "<name>";
	
	
	public Ontology() throws SameFieldException, IOException{
		this.code = new LinkedList<>();
		this.globalClassList = new LinkedList<>();
		this.ontologyPath = "/home/anton/Documents/project/shop.xml";
		this.ontologyStatesPath =  "/home/anton/Documents/project/shop_ontology_state.xml";	
		this.execute();
	}
	
	
	
	private void execute() throws SameFieldException, IOException{
		ReadXMLFile readerOntology = new ReadXMLFile(this.ontologyPath);
		readerOntology.execute();
		this.ontologyList = readerOntology.getProductList();	
		StateMachineXMLReader readerStates = new StateMachineXMLReader(this.ontologyStatesPath);
		readerStates.execute();
		this.statesList = readerStates.getList();
			
		
	}

	

	public String  findCoincidence(String string,String line) {
		String retString = "";
		for(XMLProduct prod : this.ontologyList){
			if(string.toLowerCase().contains(prod.getName().toLowerCase())){
				
				String temp = "\t"+this.classNameGenerate(prod.getName(),  string)+"\n";
				retString += addToCode(temp);
				
				
			}
		}
		
		for( XMLProduct prod : this.ontologyList ){
			for( String s : prod.getAttribute() ){
				if(string.toLowerCase().contains(s)){
					String temp = "\t"+this.classAttributeValueGenerate(prod.getName(),s,  string)+"\n";
					retString += temp;
				}
			}
		}

		for( State s : statesList ){
			for(Edge e : s.getEdges()){
				if(string.toLowerCase().contains(e.getName().toLowerCase())){
					String temp = "\t"+this.testFunctionGeneretor( s,e ,string)+"\n";
					retString += temp;
				}
			}
		}
		return retString;
	}



	private String addToCode(String temp) {
		System.out.println(temp);
		
		if( !code.contains(temp) ){
			if(temp.trim().startsWith("@")){
				code.add(temp);
			}
			return temp;
		}
		return "";
	}



	private String classAttributeValueGenerate(String className , String attribute, String line) {
		attribute = attribute.toLowerCase();
		String retString = "";
		if( this.globalClassList.contains(this.claseNameFinder(className, line)) ){
			retString = "@";
		}
		
		
		return retString + this.claseNameFinder(className, line) + "." + attribute + " = " + attribute ;
	}



	private String testFunctionGeneretor(State state, Edge edge , String line) {
		String retString = "";
		if( this.globalClassList.contains(this.claseNameFinder(state.getClassName(), line)) ){
			retString = "@";
		}
		
		if(line.startsWith("Then")){
			return "assert " + this.getParametersString(line)+" == " + retString 
					+this.claseNameFinder(state.getClassName(), line)+"."+edge.getName()+"( )";
		}
		return retString+this.claseNameFinder(state.getClassName(), line)+"."+edge.getName()+"("+this.getParametersString(line)+")";
		
	}
	
	private String getParametersString(String line) {
		line = line.substring(line.indexOf("|")+1);
		if(line.contains("|")){
			return line.substring(0,line.indexOf("|"));
		}
		return "";

		
	}
	



	private String claseNameFinder(String className , String line){
		
			return className.replaceAll(" ", "_").toLowerCase();
		

	}



	private String classNameGenerate(String name , String line) {
		String retString = "";
		if( line.startsWith("Given") || (this.globalClassList.contains(this.claseNameFinder(name, line))) ){
			retString += "@";
			this.globalClassList.add( claseNameFinder( name , line ));
		}
		return retString+this.claseNameFinder( name , line ) + " = " + WordUtils.capitalize(name.replace(" ", "_"))+".new";
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
	

}
