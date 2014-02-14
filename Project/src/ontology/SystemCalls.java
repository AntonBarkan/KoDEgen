package ontology;

import java.util.Map;

public abstract class SystemCalls {

	
	public static SystemCalls getSystemCallSolver(String text){
		//TODO Change parameter to scenarioHolder
		if (text.contains("Print")){
			return new PrintManager();
		}
		return null;
	}
	
	public abstract String createUnitTest(Map<String,String> evokers, String line);

	public void injectMockCreation(Map<String, String> evokersMap,
			String testLine, String line) throws Exception {
		// TODO Auto-generated method stub
		
	}
}
