package CucumberRun;

public class ErrorSolverFactory {
	

	public ErrorSolver getErrorSolver(int error, String cucumberOutput){
		if ( error == ErrorSolver.NO_METHOD_ERROR ){
			return new NoMethodErrorSolver(cucumberOutput);
		}
		if ( error == ErrorSolver.ARGUMENT_ERROR){
			return new ArgumentErrorSolver(cucumberOutput);
		}
		return null;
	}
	
}
