package scenariosUtils;

import static common.Constants.END_OF_LINE;
import static common.Constants.COMMENT;

public class ScenarioHolder {

	private String scenarioName;
	private String scenarioContent = "";
	
	public ScenarioHolder(String name, String[] scenarioContent) {
		this.scenarioName = name;
		for (String line : scenarioContent){
			if( !line.startsWith(COMMENT) && !line.trim().isEmpty() ){
				this.scenarioContent += line + END_OF_LINE;
			}
		}
		
	}

	public String getScenario() {
		return "Scenario: " + this.scenarioName  + END_OF_LINE + this.scenarioContent;
	}


}
