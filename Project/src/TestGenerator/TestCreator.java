package TestGenerator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Map;

import CodeCreator.ClassCreator;
import CucumberRun.ErrorFinder;


public class TestCreator {
	public static final String PROJECT_NAME = "shop_steps";
	
	
	private LinkedList<String> testSteps;
	
	private TestCreator(){
		this.testSteps = new LinkedList<>();
	}
	
	private static class SingletonHolder{
		private static final TestCreator HOLDER_INSTANCE = new TestCreator();
	}
	
	public static TestCreator getInstance(){
		return SingletonHolder.HOLDER_INSTANCE;
	}
	
	public void addStep(String string){
		this.testSteps.addLast(string);
	}

	public void generateTests() {
		File file = new File(ErrorFinder.PATH+"step_definitions/"+PROJECT_NAME+".rb");
		try {
			file.createNewFile();
			FileWriter fileWriter = new FileWriter(file);
			BufferedWriter stream = new BufferedWriter(fileWriter);
				stream.write("require \"test/unit/assertions\"\nWorld(Test::Unit::Assertions)\n\n");
				for(String s: this.testSteps ){	
			
					stream.write(s+"\n");
				}
				stream.flush();
				stream.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		
	}
	

}
