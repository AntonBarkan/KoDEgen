package cucumberRun;

public abstract class ErrorSolver {
	public static final int NO_METHOD_ERROR = ErrorFinder.ERROR_LIST.lastIndexOf("(NoMethodError)"),
			ARGUMENT_ERROR = ErrorFinder.ERROR_LIST.lastIndexOf("(ArgumentError)")
		    ,FAILED_ASSERTION_ERROR = ErrorFinder.ERROR_LIST.lastIndexOf("Failed assertion")
		    
		    
					;
	
		
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
	
	protected String  getStringFromFirstStringToSecondString(String line,String firstString , String secondString)
	{
		if(!line.contains(firstString))
		{
			return null;
		}
		String returnString  = line.substring( line.indexOf(firstString)+firstString.length());
		if(!line.contains(secondString))
		{
			return line.substring( line.indexOf( firstString ) + firstString.length() );
		}
		returnString = returnString.substring(0,returnString.indexOf(secondString));
		return returnString;
		
	}
}
