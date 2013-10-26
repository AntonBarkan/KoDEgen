package main;
import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import codeCreator.CodeCreator;
import cucumberRun.ErrorFinder;

import Exceptions.ClassNameNotFoundException;
import Exceptions.SameFieldException;
import Ontology.Ontology;
import TestGenerator.StepsGenerator;
import TestGenerator.TestCreator;
import static Ontology.Ontology.*;


public class Steps {
	
	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		File stepDefinitionsDirectory = new File(PATH+"step_definitions/");
		if (stepDefinitionsDirectory.exists()){
			FileUtils.cleanDirectory(stepDefinitionsDirectory);
		}else{
			stepDefinitionsDirectory.mkdir();
		}
		Ontology ontology = null;
		try {
			ontology = new Ontology();
		} catch (SameFieldException | IOException e) {
			e.printStackTrace();
			System.exit(-1);
		}
		ontology.generateCode();
		
		new StepsGenerator(ontology);
		CodeCreator.getInstance().generateClasses();
		TestCreator.getInstance().generateTests();
		try {
			new ErrorFinder(ontology);
		} catch (ClassNameNotFoundException e) {
			e.printStackTrace();
		}
	}

}
