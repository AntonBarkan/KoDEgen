package cucumberRun;

import java.lang.reflect.Method;

import codeCreator.ClassCreator;
import codeCreator.CodeCreator;
import Exeptions.ClassNameNotFoundException;
import Exeptions.ExistTwoMethodsWithThisName;
import Exeptions.MethodNameNotFoundException;
import Ontology.Ontology;
import TestGenerator.TestCreator;

public class FailedAssertionErrorSolver extends ErrorSolver 
{

	private String problemTest;
	
	private String className, methodName;
	
	private Ontology ontology;
	
	public FailedAssertionErrorSolver(String cucumberOutput , Ontology ontology ) throws ClassNameNotFoundException
	{		
		String errorLine = findLineWithErrorSentence(cucumberOutput, ErrorFinder.ERROR_LIST.get(ErrorSolver.FAILED_ASSERTION_ERROR));
		if( errorLine.contains("no message given") )
		{
			this.problemTest=TestCreator.getInstance().findStep
					( 
							"Then "+
							this.getStringFromFirstStringToSecondString(this.getNextLine(errorLine , cucumberOutput), "`", "'")
					);
		}
		String[] arr = problemTest.split("\n");
		String thisClassName = null;
		for(int i=1 ; i < arr.length ; i++ )
		{
			if( arr[i].contains("assert") )
			{	
				thisClassName = this.findThisClassName(arr[i] , ontology);
				ClassCreator classCreator =  CodeCreator.getInstance().getClassCode(ontology.getClassType(thisClassName));
				codeCreator.Method method = null; 
				try 
				{
					method = classCreator.getMethod( this.findMethodOrFieldName( arr[ i ] , thisClassName ) );
				} 
				catch (ExistTwoMethodsWithThisName e) 
				{
					e.printStackTrace();
				} 
				catch (MethodNameNotFoundException e){}
				
				if ( method != null )
				{
					method.addLogic(this.generateLogic( arr[ i ] , this.getNextLine(this.getNextLine(errorLine , cucumberOutput ),cucumberOutput) ));
				}
			}
			CodeCreator.getInstance().generateClasses();
		}
	
		
		
		
		
		
		
	}
	
	private String generateLogic(String line , String  lineInFutures ) 
	{
		
		line = this.getStringFromFirstStringToSecondString(line, "assert ", " ==");
		lineInFutures = this.getStringFromFirstStringToSecondString(lineInFutures, "`", "'");
		lineInFutures = lineInFutures.substring( lineInFutures.indexOf( "<" + line + ">" ) + ("<" + line + ">").length()  );
		return "return "+lineInFutures.trim().substring( 0 , lineInFutures.trim().indexOf(" ") );
	}

	private String findThisClassName(String line , Ontology ontology) throws ClassNameNotFoundException  
	{
		
		for( Object className : ontology.getGlobalClasses() )
		{
			if( line.contains(className.toString()) )
			{
				return className.toString();
			}
		}
		
		throw new ClassNameNotFoundException();
		
		
				
	}
	
	private String findMethodOrFieldName(String line  ,String className){
		if( this.getStringFromFirstStringToSecondString( line , className+"." , "(" ) != null ){
			return this.getStringFromFirstStringToSecondString( line , className+"." , "(" );
		}
		if( this.getStringFromFirstStringToSecondString( line , className+"." , "\n" ) != null ){
			return this.getStringFromFirstStringToSecondString(line, className+".", "\n");
		}
		return null;
	}

	private String getNextLine(String line , String cucumberOutput)
	{
		String[] arr = cucumberOutput.split("\n");
		int i ;
		for( i = 0 ; i < arr.length ; i++ )
		{
			if( arr[i].trim().equalsIgnoreCase(line.trim()) )
			{
				break;
			}
		}
		return arr[i+1];
		
	}
	
	@Override
	protected void addToCode() {
		
		
	}



	

}
