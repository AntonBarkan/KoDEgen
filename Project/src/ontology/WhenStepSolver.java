package ontology;

import java.util.List;
import java.util.Map;

public class WhenStepSolver implements StepSolver {

	private Map<String, String> classMap;
	private List<GivenStepSolver>	givenSolvedSteps;
	private List<GivenStepSolver>	previousWhenSolvedSteps;
	
}
