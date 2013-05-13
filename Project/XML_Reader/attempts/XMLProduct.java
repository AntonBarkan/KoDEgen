package attempts;

import java.util.LinkedList;

public class XMLProduct {
	private String name;
	private LinkedList<String> attribute;
	
	public XMLProduct(){
		this.attribute = new LinkedList<>();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LinkedList<String> getAttribute() {
		return attribute;
	}

	public void addAttribute(String attribute) {
		this.attribute.add( attribute);
	}
	
	
	
}
