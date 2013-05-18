package TestGenerator;

import java.util.Vector;

public class UnitTestStruct {
	private String[] parameters;
	private boolean[] inUse; 
	private Vector<String> classesCreatedInTest;

	public UnitTestStruct(String ... parameters) {
		this.parameters = parameters;
		this.inUse = new boolean[parameters.length];
		this.classesCreatedInTest = new Vector<>();
	}
	
	public UnitTestStruct(String line){
		this.parameters = this.getParametersArray(line);
		this.inUse = new boolean[parameters.length];
		this.classesCreatedInTest = new Vector<>();
	}

	private String[] getParametersArray(String line) {
		if(!(line!=null && line.contains("|"))){
			String[] array = { "" }; 
			return array;
		}
		line = line.substring(line.indexOf("|")+1);
		line = line.substring(0,line.indexOf("|")).trim();
		String[] arraya = line.split(",");
		for( int  i = 0 ; i < arraya.length ; i++){
			arraya[i] = arraya[i].trim();
		}
		return arraya;
	}
	
	public void setInUSe(String parameter){
		parameter = parameter.trim();
		for( int i = 0 ; i < this.parameters.length ; i++ ){
			if( this.parameters[i].equalsIgnoreCase(parameter) ){
				this.inUse[i]=true;
			}
		}
	}
	
	public String getParamettersString(){
		
		String retString = "",
				classesString = this.getClassesString(),
				parametersString = this.getNotInUseParametersString();
		
		if( classesString.trim().isEmpty() ){
			retString = parametersString;
		}else if( parametersString.trim().isEmpty() ){
			retString = classesString;
		}else{
			retString = parametersString + " , " + classesString;
		}	
		return retString;
	}

	private String getNotInUseParametersString() {
		String notInUseParametersString =  "";
		for( int i = 0 ; i < this.parameters.length ; i++ ){
			if(!this.inUse[i]){
				notInUseParametersString += this.parameters[i] + " , ";
			}
		}
		if( notInUseParametersString.length() < 3 ){
			return "";
		}
		return notInUseParametersString.substring( 0 , notInUseParametersString.length()-2 ).trim();
	}
	
	private String getClassesString(){
		
		String classesString =  "";
		if( this.classesCreatedInTest.isEmpty() ){
			return classesString;
		}
		for( String className : this.classesCreatedInTest ){
			classesString += className + " , ";
		}
		return classesString.substring( 0 , classesString.length()-2 ).trim();
	}
	
	public void addClass(String className){
		this.classesCreatedInTest.add( className );
	}
	
	
	

}
