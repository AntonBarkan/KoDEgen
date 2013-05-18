package CucumberRun;

import java.util.LinkedList;
import static Ontology.Ontology.*;

public class ErrorFinder {
	
	public static final LinkedList<String> ERROR_LIST = new LinkedList<>();
	private static final ErrorSolverFactory FACTORY = new ErrorSolverFactory();
	
	static{
		
		ERROR_LIST.add("(NoMethodError)");
		ERROR_LIST.add("(ArgumentError)");
		
	}
	
	
	public ErrorFinder(){
		int i = 10;
		while(i--!=0){		
			String output = GetData.runCucumber(PATH + PROJECT_NAME + ".features");
			System.out.println(output);
			for( String errorNode : ERROR_LIST ){
				if( output.contains(errorNode) ){
					ErrorSolver solver = FACTORY.getErrorSolver(ERROR_LIST.indexOf(errorNode), output);
				}
			}
		}
		
	}
}
