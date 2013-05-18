package CucumberRun;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


import CodeCreator.ClassCreator;
import CodeCreator.CodeCreator;
import CodeCreator.Method;
import Exeptions.ClassNameNotFoundException;
import Exeptions.MethodNameNotFoundException;
import Exeptions.SameParameterExeption;
import static Ontology.Ontology.*;

public class ArgumentErrorSolver extends ErrorSolver {
	
	private String methodName , className  ;
	private int expectedNumberOfParameters, correctNumberOfParameters, unitTestWithProblemMethodStartLineNumber;
	
	

	public ArgumentErrorSolver(String cucumberOutput) {
		String errorLine = findLineWithErrorSentence(cucumberOutput, ErrorFinder.ERROR_LIST.get(ErrorSolver.ARGUMENT_ERROR));

		String methodNameLine = findLineAfterErrorSentence(cucumberOutput);

		this.methodName = this.getStringFromFirstStringToSecondString(methodNameLine, "`", "'" );
		this.expectedNumberOfParameters = Integer.parseInt(this.getStringFromFirstStringToSecondString(errorLine, "(" , " for" ));
		this.correctNumberOfParameters = Integer.parseInt(this.getStringFromFirstStringToSecondString(errorLine, "for " , ")" ));
		
		this.unitTestWithProblemMethodStartLineNumber =
				Integer.parseInt(
						this.getStringFromFirstStringToSecondString(methodNameLine, ".rb:" , ":in" ));
		this.className = methodNameLine.substring( methodNameLine.lastIndexOf("/")+1 );
		this.className = this.className.substring( 0 , this.className.indexOf(".rb"));
		this.addToCode();
		CodeCreator.getInstance().generateClasses();
	}

	private String findLineAfterErrorSentence(String cucumberOutput) {
		String[] lines = cucumberOutput.split("\n");
		int i;
		for ( i = 0 ; i < lines.length ; i++ ){
			if ( lines[i].contains(ErrorFinder.ERROR_LIST.get(ErrorSolver.ARGUMENT_ERROR)) ){
				break;
			}
		}
		return lines[i+1];
	}

	@Override
	protected void addToCode() {
		try{
			ClassCreator problemClass = CodeCreator.getInstance().getClassCode( this.className );
			Method problemMethod = problemClass.getMethod( this.methodName, this.correctNumberOfParameters);
			String[] parameters = this.getMethodParameters();
			for (Object parameter : parameters ){
				problemMethod.addParameter(parameter.toString());
			}
			
		} catch ( SameParameterExeption | ClassNameNotFoundException | MethodNameNotFoundException  e) {
			
			e.printStackTrace();
		}
		
	}

	private String[] getMethodParameters() {
		String line = this.findLineWithProblemFunctionInUnitTest();		
		return this.getParameters( this.getStringFromFirstStringToSecondString(line, this.methodName+"(", ")") ) ;
		
	}

	private String[] getParameters(String stringFromFirstStringToSecondString) {
		if( stringFromFirstStringToSecondString == null ){
			return null;
		}
		String[] parameters = stringFromFirstStringToSecondString.split(",");
		for( int i = 0 ; i < parameters.length ; i++ ){
			parameters[i] = parameters[i].trim(); 
		}
		return parameters;
	}

	private String findLineWithProblemFunctionInUnitTest() {
		BufferedReader reader = null;
		try {
			 reader = new BufferedReader(new FileReader(new File(STEP_FILE_PATH)));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int i=1;
		String line="";
		try {
			while( (line = reader.readLine()) != null ){
				if( i++ < 8 ) { continue; }
				if( line.trim().equalsIgnoreCase("end") ) { return "" ; }
				if( line.contains( "."+this.methodName )) { return line; }
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
		
	}

	

}
