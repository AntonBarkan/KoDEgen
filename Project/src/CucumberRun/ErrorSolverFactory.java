package CucumberRun;

import Exeptions.ClassNameNotFoundException;
import Ontology.Ontology;

public class ErrorSolverFactory {
	

	public ErrorSolver getErrorSolver(int error, String cucumberOutput ,Ontology ontology) throws ClassNameNotFoundException{
		if ( error == ErrorSolver.NO_METHOD_ERROR ){
			return new NoMethodErrorSolver(cucumberOutput);
		}
		
		if ( error == ErrorSolver.ARGUMENT_ERROR){
			return new ArgumentErrorSolver(cucumberOutput);
		}
		
		if ( error == ErrorSolver.FAILED_ASSERTION_ERROR){
			FailedAssertionErrorSolver er = new FailedAssertionErrorSolver(cucumberOutput,ontology);
			
			return er;
		}
		
		return null;
	}
	
}
