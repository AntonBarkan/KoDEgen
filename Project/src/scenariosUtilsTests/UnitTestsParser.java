package scenariosUtilsTests;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import cucumberRun.GetData;
import exceptions.SameFieldException;
import exceptions.featuresFileExeptions.FeatureNameException;

import scenariosUtils.FeatureHolder;
import scenariosUtils.ScenarioHolder;


import static main.Steps.*;
import static common.Constants.END_OF_LINE;

public class UnitTestsParser {
	
	private FeatureHolder featureHolder; 
	
	public UnitTestsParser( String featuresFilePath ) throws FeatureNameException, SameFieldException, IOException {
		
		this.featureHolder = new FeatureHolder();
		this.featureHolder.postConstruct(readFile(featuresFilePath));
		createScenarios();
	}

	private String readFile(String featuresFilePath) throws IOException {
		BufferedReader feature = new BufferedReader(new FileReader(new File(featuresFilePath)));
		String featuresFileData = "";
		String line;
		while((line = feature.readLine())!=null){
			featuresFileData += line + END_OF_LINE;
		}
		feature.close();
		return featuresFileData;
	}

	private void createScenarios() throws IOException {
		File tempDir = new File(PATH + File.pathSeparator + "temp");
		tempDir.mkdir();
		tempDir = new File(tempDir.getCanonicalPath() + File.pathSeparator + "features" );
		tempDir.mkdir();
		
		for (ScenarioHolder scenarioHolder : this.featureHolder.getScenarios() ){
			File file = new File(tempDir.getPath() + File.pathSeparator + PROJECT_NAME + ".features");
			BufferedWriter feature = new BufferedWriter(new FileWriter(file));
			feature.write(scenarioHolder.getScenario());
			scenarioHolder.setCucumberOutput(GetData.runCucumber( file.getCanonicalPath() ));
			FileUtils.cleanDirectory(tempDir);
			feature.close();
		}
		
	}
	
	
}
