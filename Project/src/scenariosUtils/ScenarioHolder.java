package scenariosUtils;

import static common.Constants.END_OF_LINE;
import static common.Constants.COMMENT;



public class ScenarioHolder {
	
	private static final String FROM_TEXT = "You can implement step definitions for undefined" +
			" steps with these snippets:\n\n", 
								TO_TEXT   = "If you want snippets in a different programming language,";


	private String scenarioName;
	private String scenarioContent = "";
	private String cucumberOutput;
	
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

	public void setCucumberOutput(String cucumberOutput) {
		cucumberOutput = cucumberOutput.substring( cucumberOutput.indexOf(FROM_TEXT)+FROM_TEXT.length() );
		cucumberOutput = cucumberOutput.substring( 0 , cucumberOutput.indexOf( TO_TEXT ) );
		this.cucumberOutput = cucumberOutput;
	}
	
	public String getCucumberOutput(){
		return this.cucumberOutput;
	}


}
