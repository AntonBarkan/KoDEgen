package ontology;

import static common.CommonFunctions.*;

import java.util.Map;
import java.util.Map.Entry;

import testGenerator.StepsGenerator;

import codeCreator.CodeCreator;
import exceptions.ClassNameNotFoundException;
import exceptions.SameFieldException;

import main.Steps;


import static main.Steps.*;

public class PrintManager extends SystemCalls{

	@Override
	public String createUnitTest(Map<String,String> evokersMap, String line) {
		String params = getStringFromFirstStringToSecondString(line, "|", "|");
		
		String evoker = getEvoker(evokersMap, line);
		return "\t@" + evoker + ".print_manager.print( " + params + " )\n" +
			   "\tassert @" + evoker + ".print_manager.text == " + params + "\n";
	}

	@Override
	public void injectMockCreation(Map<String, String> evokersMap , String testLine, String line) throws Exception {
		String evoker = getEvoker(evokersMap, testLine);
		for (int i = 0 ; ; i++){
			if (Steps.getSg().getTest(i).isEmpty()){break;}
			if (Steps.getSg().getTest(i).contains(evokersMap.get(evoker)+".new") && 
					featureHolder.linesFromSameScenario(featureHolder.getLine(i), line.trim()) ){
				Steps.getSg().addToTest(i ,
				"\tprint_manager=mock(\"Print_Manager\")\n" +
				"\tmetaclass = class << print_manager; self; end\n" +
				"\tmetaclass.send :attr_accessor, :text\n" +
				"\tdef print_manager.print(text)\n" +
					"\t\t@text = text\n" + 
				"\tend\n" +
				"\t@"+ evoker + ".print_manager=print_manager\n");
			}
		}
		addAttributeToClass(evokersMap, evoker);
		
	}
	
	private void addAttributeToClass(Map<String,String> evokersMap, String className){
		try {
			CodeCreator.getInstance().getClassCode(evokersMap.get(className)).addField("print_manager");
		} catch (SameFieldException | ClassNameNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	private String getEvoker(Map<String,String> evokersMap, String line){
		String evoker = "";
		for (Entry<String,String> e : evokersMap.entrySet() ){
			if (line.contains(e.getValue())){
				if(evoker.isEmpty()){
					evoker = e.getKey();
				}else{
					//FIXME
				}
			}
		}
		return evoker;
	}
}
