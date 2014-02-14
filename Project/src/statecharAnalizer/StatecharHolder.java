package statecharAnalizer;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import codeCreator.CodeCreator;

import StateMachineXML.State;
import attempts.XMLProduct;

public class StatecharHolder {
	
	private Map<XMLProduct, List<State>> functionsMap;
	private Analizer analizer ; 
	private CodeCreator codeCreator = CodeCreator.getInstance();
	
	
	public StatecharHolder(List<XMLProduct> ontologyList , List<State> stateList) {
		this.functionsMap = new HashMap<>();
		for (XMLProduct xmlProduct : ontologyList){
			this.functionsMap.put(xmlProduct, new LinkedList<State>());
			for (State state : stateList){
				if (state.getStateName().trim().equalsIgnoreCase(xmlProduct.getName())){
					this.functionsMap.get(xmlProduct).add(state);
				}
			}
		}
		setAnalizer(new BooleanAnalizer(this.functionsMap));
	}


	public Analizer getAnalizer() {
		return analizer;
	}


	public void setAnalizer(Analizer analizer) {
		this.analizer = analizer;
	}
	
	
	
}
