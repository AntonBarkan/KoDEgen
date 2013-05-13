package CucumberRun;

public abstract class ErrorSolver {
	public static final int NO_METHOD_ERROR = ErrorFinder.ERROR_LIST.lastIndexOf("(NoMethodError)");
	public static final int ARGUMENT_ERROR = ErrorFinder.ERROR_LIST.lastIndexOf("(ArgumentError)");
		
	protected abstract void addToCode();
	
	protected String findLineWithErrorSentence(String cucumberOutput ,String errorSentence){
		String[] lines = cucumberOutput.split("\n");
		for ( String line : lines ){
			if ( line.contains(errorSentence) ){
				return line;
			}
		}
		 
		return null;
	}
	
	protected String  getStringFromFirstStringToSecondString(String line,String firstString , String secondString){
		String returnString  = line.substring( line.indexOf(firstString)+firstString.length());
		returnString = returnString.substring(0,returnString.indexOf(secondString));
		return returnString;
		
	}
}
