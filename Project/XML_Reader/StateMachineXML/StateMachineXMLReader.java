package StateMachineXML;

import java.io.File;
import java.util.LinkedList;

import org.apache.commons.lang.WordUtils;
import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException; 

public class StateMachineXMLReader{
	
	private LinkedList<State> list;
	private String pathXMLFile;
	
	public StateMachineXMLReader(String path){
		this.pathXMLFile = path;
		this.list = new LinkedList<>();
	}

    public void execute (){
    try {
    		
            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
            Document doc = docBuilder.parse (new File(this.pathXMLFile));

            doc.getDocumentElement ().normalize ();
          
            NodeList list = doc.getElementsByTagName("ontology_state");
            for(int j=0; j<list.getLength() ; j++){
            
	            NodeList listOfStates = list.item(j).getChildNodes();
	            
	            String stateName=null , className = null;
 	            LinkedList<Edge> edges = new LinkedList<Edge>();
 	            
	            for(int s=0; s<listOfStates.getLength() ; s++){
	            	
	 	            
	            	if( listOfStates.item(s).getNodeName().equalsIgnoreCase("class_name") ){
	            		
	            		className = WordUtils.capitalize(listOfStates.item(s).getTextContent().replace(" ", "_"));
	            		
	            	}
	            	if( listOfStates.item(s).getNodeName().equalsIgnoreCase("state")){
		            	NodeList listOfEdges = listOfStates.item(s).getChildNodes();
		            	
		            	
		            	
		            	for(int i=0; i<listOfEdges.getLength() ; i++){
		            		if(listOfEdges.item(i).getNodeName().equalsIgnoreCase("state_name")){
		            			stateName = listOfEdges.item(i).getTextContent();
		            		}
		            		if(listOfEdges.item(i).getNodeName().equalsIgnoreCase("edge")){
		            			NodeList edgeData = listOfEdges.item(i).getChildNodes();
		            			String edgeName = null , edgeDirection = null;
		            			for(int k = 0 ; k < edgeData.getLength() ; k++ ){
		            				
		            				if( edgeData.item(k).getNodeName().equalsIgnoreCase("edge_name") ){
		            					edgeName = edgeData.item(k).getTextContent();
		            				}
		            				if( edgeData.item(k).getNodeName().equalsIgnoreCase("edge_to")){
		            					edgeDirection = edgeData.item(k).getTextContent();
		            				}
		            			}
		            			edges.add(new Edge( edgeName , edgeDirection));
		            			
		            		}
		            		
		            	}
		            	this.list.add(new State(className, stateName, edges));
	            	}
	            	
	            	
	       
	            }
            }

        }catch (Throwable t) {
        	t.printStackTrace ();
        	System.exit (0);
        }

    }//end of method
    
    public LinkedList<State> getList(){
    	return this.list;
    	
    }
    
    public static void main(String[] args) {
    	
    	StateMachineXMLReader r = new StateMachineXMLReader("/home/anton/Documents/project/shop_ontology_state.xml");
    	r.execute();
    	
    	for(State s : r.getList()){
    		System.out.println("Name : "+s.getStateName());
    		System.out.println("Class Name : "+s.getClassName());
    		for(Edge e : s.getEdges()){
    			System.out.println("\t edge name : "+e.getName() + "\n\t dir : " + e.getDirection());
    		}
    	}
	}
}
