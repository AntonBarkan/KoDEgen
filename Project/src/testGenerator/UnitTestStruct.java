package testGenerator;

import java.util.HashMap;

public class UnitTestStruct {
	private String[] parameters;
	private boolean[] inUse; 
	private HashMap<String, Boolean> classesCreatedInTest;

	public UnitTestStruct(String ... parameters) {
		this.parameters = parameters;
		this.inUse = new boolean[parameters.length];
		this.classesCreatedInTest = new HashMap<>();
	}
	
	public UnitTestStruct(String line){
		this.parameters = this.getParametersArray(line);
		this.inUse = new boolean[parameters.length];
		this.classesCreatedInTest = new HashMap<>();
	}
	
	//---------------private methods-----------------------------------------

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
	
	
	private String getNotInUseParametersString() 
	{
		String notInUseParametersString =  "";
		for( int i = 0 ; i < this.parameters.length ; i++ )
		{
			if(!this.inUse[i]){
				notInUseParametersString += this.parameters[i] + " , ";
			}
		}
		if( notInUseParametersString.length() < 3 )
		{
			return "";
		}
		return notInUseParametersString.substring( 0 , notInUseParametersString.length()-2 ).trim();
	}
	
	
	@SuppressWarnings("unused")
	private String getClassesString()
	{
		String classesString =  "";
		if( this.classesCreatedInTest.isEmpty() )
		{
			return classesString;
		}
		for( String className : this.classesCreatedInTest.keySet() )
		{
			classesString += className + " , ";
		}
		return classesString.substring( 0 , classesString.length()-2 ).trim();
	}
	
	private String getNotInUseClassesString()
	{
		
		String notInUseClassesString =  "";
		if( this.classesCreatedInTest.isEmpty() )
		{
			return notInUseClassesString;
		}
		for( String className : this.classesCreatedInTest.keySet() )
		{
			if( this.classesCreatedInTest.get(className) )
			{
				notInUseClassesString += className + " , ";
			}
		}
		if( notInUseClassesString.isEmpty() )
		{
			return notInUseClassesString;
		}
		return notInUseClassesString.substring( 0 , notInUseClassesString.length()-2 ).trim();
	}
	
	private void setClassInUse(String className){
		if( this.classesCreatedInTest.containsKey( className )){
			this.classesCreatedInTest.put( className , false );
		}
	}
	
	
	
	//---------------public methods-----------------------------------------
	
	/**
	 * @return parameters string for functions called from unit test
	 */
	public String getParamettersString(String objectName)
	{
		this.setClassInUse( objectName );
		String retString = "",
				classesString = this.getNotInUseClassesString(),
				parametersString = this.getNotInUseParametersString();
		
		if( classesString.trim().isEmpty() )
		{
			retString = parametersString;
		}
		else 
			if( parametersString.trim().isEmpty() )
		{
			retString = classesString;
		}
		else
		{
			retString = parametersString + " , " + classesString;
		}	
		return retString;
	}
	
	public void addClass(String className)
	{
		this.classesCreatedInTest.put( className , true );
	}
	
	public void setInUSe(String parameter)
	{
		parameter = parameter.trim();
		for( int i = 0 ; i < this.parameters.length ; i++ )
		{
			if( this.parameters[i].equalsIgnoreCase(parameter) )
			{
				this.inUse[i]=true;
			}
		}
	}
	
	

}
