package scenariosUtilsTests;

import org.junit.Before;
import org.junit.Test;

import scenariosUtils.ScenarioHolder;

import static common.Constants.END_OF_LINE;

import static org.junit.Assert.*;

public class ScenarioHolderTests {
	
	
	private static final String  SCENARIO_NAME = "Successful withdrawal from an account";
	private static final String[] SCENARIO_CONTENT = {
		"Given account has a balance of <balance> $100",
		"When <amount> $20 are withdrawn from ATM ",
		"Then the account balance should be <balance> $80"		
	};
	

	private ScenarioHolder scenarioHolder;
	
	@Before
	public void setUp() throws Exception 
	{
		this.scenarioHolder = new ScenarioHolder(SCENARIO_NAME, SCENARIO_CONTENT);
	}
	
	private static final String EXPECTED_RESULT = "" +
			"Scenario: Successful withdrawal from an account" + END_OF_LINE +
			"Given account has a balance of <balance> $100" + END_OF_LINE +
			"When <amount> $20 are withdrawn from ATM " + END_OF_LINE +
			"Then the account balance should be <balance> $80" + END_OF_LINE ;
			
	
	@Test
	public void testScenarioHolderConstaractor(){
		assertEquals( EXPECTED_RESULT , this.scenarioHolder.getScenario() );
	}
}
