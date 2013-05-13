import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import CodeCreator.CodeCreator;
import CucumberRun.ErrorFinder;
import CucumberRun.ErrorSolver;
import Exeptions.SameFieldException;
import Ontology.Ontology;
import TestGenerator.StepsGenerator;
import TestGenerator.TestCreator;


public class Steps {
	
	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		File dir = new File(ErrorFinder.PATH+"step_definitions/");
		FileUtils.cleanDirectory(dir);
		Ontology ontology = null;
		try {
			ontology = new Ontology();
		} catch (SameFieldException | IOException e) {
			e.printStackTrace();
			System.exit(-1);
		}
		new StepsGenerator(ontology);
		ontology.generateCode();
		CodeCreator.getInstance().generateClasses();
		TestCreator.getInstance().generateTests();

		new ErrorFinder();
	}

}
