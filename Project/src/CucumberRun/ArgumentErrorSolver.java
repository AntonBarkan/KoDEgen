package CucumberRun;

import javax.lang.model.type.PrimitiveType;

import CodeCreator.ClassCreator;
import CodeCreator.CodeCreator;
import CodeCreator.Method;
import Exeptions.ClassNameNotFoundException;
import Exeptions.MethodNameNotFoundException;
import Exeptions.SameParameterExeption;

public class ArgumentErrorSolver extends ErrorSolver {
	
	private String methodName , className ;
	private int expectedNumberOfParameters, correctNumberOfParameters;

	public ArgumentErrorSolver(String cucumberOutput) {
		String errorLine = findLineWithErrorSentence(cucumberOutput, ErrorFinder.ERROR_LIST.get(ErrorSolver.ARGUMENT_ERROR));
		String methodNameLine = findLineAfterErrorSentence(cucumberOutput);
		this.methodName = this.getStringFromFirstStringToSecondString(methodNameLine, "`", "'" );
		this.expectedNumberOfParameters = Integer.parseInt(this.getStringFromFirstStringToSecondString(errorLine, "(" , " for" ));
		this.correctNumberOfParameters = Integer.parseInt(this.getStringFromFirstStringToSecondString(errorLine, "for " , ")" ));
		
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
		try {
			ClassCreator problemClass = CodeCreator.getInstance().getClassCode( this.className );
			Method problemMethod = problemClass.getMethod( this.methodName, this.correctNumberOfParameters);
			for(int i = 0 ; i < this.expectedNumberOfParameters ; i++){
				problemMethod.addParameter("parameter"+i);
			}
		} catch (ClassNameNotFoundException | MethodNameNotFoundException | SameParameterExeption e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	

}
