package attempts;

import java.io.File;
import java.util.LinkedList;

import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException; 

public class ReadXMLFile{
	
	private LinkedList<XMLProduct> list;
	private String pathXMLFile;
	
	public ReadXMLFile(String path){
		this.pathXMLFile = path;
		this.list = new LinkedList<>();
	}

    public void execute (){
    try {
    		
            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
            Document doc = docBuilder.parse (new File(this.pathXMLFile));

            // normalize text representation
            doc.getDocumentElement ().normalize ();
            System.out.println ("Root element of the doc is " + 
                 doc.getDocumentElement().getNodeName());


            NodeList listOfPersons = doc.getElementsByTagName("class");
            int totalPersons = listOfPersons.getLength();
            System.out.println("Total no of people : " + totalPersons);

            for(int s=0; s<listOfPersons.getLength() ; s++){
            	
            	XMLProduct product = new XMLProduct();

                Node firstPersonNode = listOfPersons.item(s);
                if(firstPersonNode.getNodeType() == Node.ELEMENT_NODE){


                    Element firstPersonElement = (Element)firstPersonNode;

                    //-------receive class name
                    NodeList lastNameList = firstPersonElement.getElementsByTagName("name");
                    Element lastNameElement = (Element)lastNameList.item(0);

                    NodeList textLNList = lastNameElement.getChildNodes();
                    product.setName(((Node)textLNList.item(0)).getNodeValue().trim());

                    //----receive attribute
                    NodeList attrList = firstPersonElement.getElementsByTagName("attribute");
                    for(int i = 0; i < attrList.getLength();i++){          	
                    	product.addAttribute(
                    			((Node)((Element)attrList.item(i)).getChildNodes().item(0))
                    			.getNodeValue().trim());
                    }


                }//end of if clause

                this.list.add(product);
            }//end of for loop with s var


        }catch (SAXParseException err) {
        System.out.println ("** Parsing error" + ", line " 
             + err.getLineNumber () + ", uri " + err.getSystemId ());
        System.out.println(" " + err.getMessage ());

        }catch (SAXException e) {
        Exception x = e.getException ();
        ((x == null) ? e : x).printStackTrace ();

        }catch (Throwable t) {
        t.printStackTrace ();
        }
        //System.exit (0);

    }//end of method
    
    public LinkedList<XMLProduct> getProductList(){
    	return this.list;
    	
    }
}
