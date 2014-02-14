package ontology;

import java.util.List;
import java.util.Map;



public class GivenStepSolver implements StepSolver {

	private Map<String,String> 	globalClassMap;
	private List<GivenStepSolver>	previousGivenSolvedSteps;
}
