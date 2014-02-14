package scenariosUtils;

import java.util.LinkedList;
import java.util.List;

import javax.annotation.PostConstruct;

import exceptions.SameFieldException;
import exceptions.featuresFileExeptions.FeatureNameException;


import static common.Constants.COMMENT;
import static common.Constants.END_OF_LINE;

import static java.util.Arrays.copyOfRange;

public class FeatureHolder {
	private String featureName;
	private List<ScenarioHolder> scenarios;
	private ScenarioLineHolder scenarioLineHolder;
	
	public FeatureHolder() {
		this.scenarioLineHolder = new ScenarioLineHolder();
	}
	
	@PostConstruct
	public void postConstruct(String textForParsing) throws FeatureNameException, SameFieldException{
		this.scenarios  = new LinkedList<>();
		this.readFeature(textForParsing);
	}

	private void readFeature(String text) throws FeatureNameException, SameFieldException {
		String[] textLines = text.split(END_OF_LINE);
		int lineNumber = 0;
		for( ; lineNumber < textLines.length ; lineNumber++ ){
			if (textLines[lineNumber].trim().isEmpty() || textLines[lineNumber].trim().startsWith(COMMENT)){continue;}
			else if(textLines[lineNumber].startsWith( "Feature" )){ 
				this.featureName = parsName(textLines[lineNumber++]);
				
				break;
			}
			else{ throw new FeatureNameException(); }
		}
		while(textLines[lineNumber].trim().isEmpty() || textLines[lineNumber].trim().startsWith(COMMENT)){lineNumber++;};
		if(!textLines[lineNumber].trim().startsWith("Scenario")){throw new SameFieldException();} 
		while(lineNumber < textLines.length){
			int tempLineNumber = lineNumber++;
			while (lineNumber < textLines.length && !textLines[lineNumber].trim().startsWith("Scenario")){lineNumber++;};
			scenarios.add(new ScenarioHolder(parsName(textLines[tempLineNumber++]) , copyOfRange(textLines, tempLineNumber, lineNumber)));
		}
		for (ScenarioHolder sh : scenarios){
			this.scenarioLineHolder.addLines(sh.scenarioContent);
		}
	}
	
	private String parsName(String line){
		return this.featureName = line.substring(line.indexOf(":")+1).trim();
	}
	
	public int getScenarioNumber(){
		return this.scenarios.size();
	}
	
	public String getScenario(int index){
		return  "Feature: " + this.featureName + END_OF_LINE + this.scenarios.get(index).getScenario();
	}

	public List<ScenarioHolder> getScenarios() {
		return this.scenarios;
	}
	
	public boolean linesFromSameScenario(String firstLine,String secondLine) throws Exception{
		for (ScenarioHolder scenario : scenarios){
			if (scenario.lineFromScenario(firstLine)){
				if( scenario.lineFromScenario(secondLine)){return true;}
			}
		}
		return false;
	}
	
	public String getLine(int i){
		return this.scenarioLineHolder.getLineI(i);
	}

}
