package cucumberRun;

import java.util.LinkedList;

import Exeptions.ClassNameNotFoundException;
import Ontology.Ontology;
import static Ontology.Ontology.*;

public class ErrorFinder {
	
	public static final LinkedList<String> ERROR_LIST = new LinkedList<>();
	private static final ErrorSolverFactory FACTORY = new ErrorSolverFactory();
	
	static{
		
		ERROR_LIST.add("(NoMethodError)");
		ERROR_LIST.add("(ArgumentError)");
		ERROR_LIST.add("Failed assertion");
		
	}
	
	
	public ErrorFinder(Ontology ontology) throws ClassNameNotFoundException{
		int i = 4;
		while(i--!=0){		
			String output = GetData.runCucumber(PATH + PROJECT_NAME + ".features");
			for( String errorNode : ERROR_LIST ){
				if( output.contains(errorNode) ){	
					FACTORY.getErrorSolver(ERROR_LIST.indexOf(errorNode), output ,ontology);
				}
				
			}
		}
		
	}
}
