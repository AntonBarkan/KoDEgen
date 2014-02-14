package statecharAnalizer;


import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import attempts.XMLProduct;

import codeCreator.CodeCreator;

import StateMachineXML.State;

import static common.Constants.*;

public class BooleanAnalizer implements Analizer {
	private Map<XMLProduct, List<State>> functionsMap;
	
	public BooleanAnalizer(Map<XMLProduct, List<State>> functionsMap) {
		this.functionsMap = functionsMap;
	}
	
	private void analize(){
		for (Map.Entry<XMLProduct, List<State>> entry : functionsMap.entrySet()) {
			this.analizeFunctions(entry);
		}
	}
	
	private void analizeFunctions(Map.Entry<XMLProduct, List<State>> entry){
		List<String> functionNames = new LinkedList<>();
		for (State state : entry.getValue()){
			functionNames.add(state.getClassName());
		}
		for (String functionName : functionNames){
			if (functionName.contains(NOT_PREFEX)){
				for (String functionNameIn : functionNames){
					if (!functionName.equals(functionNameIn) && functionName.contains(functionNameIn)){
						
					}
				}
			}
		}
		
	}
	
	
	
	
}
