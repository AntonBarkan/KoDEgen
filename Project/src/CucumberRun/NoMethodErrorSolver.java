package CucumberRun;

import CodeCreator.CodeCreator;
import CodeCreator.Method;
import Exeptions.ClassNameNotFoundException;
import Exeptions.SameFunctionExeption;

public class NoMethodErrorSolver extends ErrorSolver {
	private String methodName, className;
	
	public NoMethodErrorSolver(String cucumberOutput){
		String errorLine = findLineWithErrorSentence(cucumberOutput, ErrorFinder.ERROR_LIST.get(ErrorSolver.NO_METHOD_ERROR));
		this.methodName = this.getStringFromFirstStringToSecondString(errorLine , "`" , "'");
		this.className = this.getStringFromFirstStringToSecondString(errorLine, "<", ":");
		this.addToCode();
	}
	
	protected void addToCode() {
		try{
			CodeCreator.getInstance().getClassCode(this.className).addMethod(new Method(this.methodName));
			CodeCreator.getInstance().generateClasses();
		}catch( ClassNameNotFoundException | SameFunctionExeption e){
			e.printStackTrace();
		}
		
	}
}
