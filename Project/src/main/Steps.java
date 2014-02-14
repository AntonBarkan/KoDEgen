package main;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

import javax.swing.JOptionPane;

import ontology.Ontology;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggerFactory;

import scenariosUtils.FeatureHolder;
import testGenerator.StepsGenerator;
import testGenerator.TestCreator;

import codeCreator.CodeCreator;
import cucumberRun.ErrorFinder;

import static common.Constants.*;
import static main.Steps.PATH;


public class Steps {
	public static  String 

	/*
	PROJECT_NAME = "shop",
	PATH = "/home/anton/Documents/project/shop/features/",
	ONTOLOGY_PATH = "/home/anton/Documents/project/shop/shop.xml" ,
	ONTOLOGY_STATES_PATH = "/home/anton/Documents/project/shop/shop_ontology_state.xml",
	STEP_FILE_PATH = "/home/anton/Documents/project/shop/features/step_definitions/shop_steps.rb";
	*/
	/*
	PROJECT_NAME = "ATM",
	PATH = "/home/anton/Documents/project/ATM/features/",
	ONTOLOGY_PATH = "/home/anton/Documents/project/ATM/ATM.xml" ,
	ONTOLOGY_STATES_PATH = "/home/anton/Documents/project/ATM/ATM_ontology_state.xml",
	STEP_FILE_PATH = "/home/anton/Documents/project/ATM/features/step_definitions/ATM_steps.rb";
	*/
	PROJECT_NAME = "Loggin",
			PATH = "/home/anton/Documents/project/Loggin/features/",
			ONTOLOGY_PATH = "/home/anton/Documents/project/Loggin/Loggin.xml" ,
			ONTOLOGY_STATES_PATH = "/home/anton/Documents/project/Loggin/Loggin_ontology_state.xml",
			STEP_FILE_PATH = "/home/anton/Documents/project/Loggin/features/step_definitions/Loggin_steps.rb";
			
//PROJECT_NAME = "check-taker",
//PATH = "/home/anton/Documents/project/check-taker/features/",
//ONTOLOGY_PATH = "/home/anton/Documents/project/check-taker/check-taker.xml" ,
//ONTOLOGY_STATES_PATH = "/home/anton/Documents/project/check-taker/check-taker_ontology_state.xml",
//STEP_FILE_PATH = "/home/anton/Documents/project/check-taker/features/step_definitions/check-taker_steps.rb";



	
	public static StepsGenerator sg;
	public static FeatureHolder featureHolder; 
	



	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		
		
		onStart(args);
		File stepDefinitionsDirectory = new File(PATH+"step_definitions/");
		if (stepDefinitionsDirectory.exists()){
			FileUtils.cleanDirectory(stepDefinitionsDirectory);
		}else{
			stepDefinitionsDirectory.mkdir();
		}
		Ontology ontology = null;
		ontology = new Ontology();
		ontology.generateCode();
		CodeCreator.getInstance().generateClasses();
		sg = new StepsGenerator(ontology);
		featureHolder = new FeatureHolder();
		featureHolder.postConstruct(getFeatureFile());
		sg.execute();
		
		TestCreator.getInstance().generateTests();
		new ErrorFinder(ontology);

	}



	private static void onStart(String[] args) {
		
		if(args.length==5){
			StdOutErrLog.tieSystemOutAndErrToLog();
			PROJECT_NAME=args[0];
			PATH=args[1];
			ONTOLOGY_PATH=args[2];
			ONTOLOGY_STATES_PATH=args[3];
			STEP_FILE_PATH=args[4];
		}
		checkFoldersStructur();
		
	}



	private static String getFeatureFile() {
		String text = "";
		BufferedReader br = null;
		try {
			String sCurrentLine;
			br = new BufferedReader(new FileReader(PATH+PROJECT_NAME+".features"));
			while ((sCurrentLine = br.readLine()) != null) {
				text += sCurrentLine + END_OF_LINE;
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null){br.close();}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return text;
	}

	public static StepsGenerator getSg() {
		return sg;
	}

	public static void setSg(StepsGenerator sg) {
		Steps.sg = sg;
	}
	
	public static class StdOutErrLog {

	    private static final Logger logger = Logger.getLogger(StdOutErrLog.class);
	    public static void tieSystemOutAndErrToLog() {
	        System.setOut(createLoggingProxy(System.out));
	        System.setErr(createLoggingProxy(System.err));
	    }
	   
	    
	    public static PrintStream createLoggingProxy(final PrintStream realPrintStream) {
	        return new PrintStream(realPrintStream) {
	            public void print(final String string) {
	                logger.info(string);
	            }
	            public void println(final String string) {
	                logger.error(string);
	            }
	        };
	    }
	}
	
	
	private static void checkFoldersStructur(){
		File dir = null;
		if(!(dir = new File(PATH)).exists()){
			dir.mkdir();
		}
		if (dir.list(new FilenameFilter() {
			
			@Override
			public boolean accept(File arg0, String arg1) {
				if (arg1.endsWith(".features")){
					return true;
				}
				return false;
			}
		}).length==0){
			JOptionPane.showMessageDialog(null, "features file not foun.");
			System.exit(-1);
		}
		if(!(dir = new File(PATH+"step_definitions/")).exists()){
			dir.mkdir();
		}

		
	}

}
