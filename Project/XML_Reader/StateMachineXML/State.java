package StateMachineXML;

import java.util.LinkedList;

public class State {

	private LinkedList<Edge> edges;
	private String stateName, className;
	
	
	public State(String className ,String stateName , LinkedList<Edge> edges) {
		this.stateName = stateName;
		this.edges = edges;
		this.className = className;
	}

	public LinkedList<Edge> getEdges() {
		return edges;
	}

	public String getStateName() {
		return stateName;
	}
	
	public String getClassName() {
		return className;
	}

	public void addEdge(Edge edge){
		this.edges.add(edge);
	}

}
