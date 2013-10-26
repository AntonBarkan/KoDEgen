package codeCreator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import static Ontology.Ontology.*;
import Exceptions.ClassNameNotFoundException;
import Exceptions.SameFieldException;
import attempts.XMLProduct;

public class CodeCreator {

	
	
	private HashMap< String , ClassCreator > classMap;
	
	private CodeCreator(){
		this.classMap = new HashMap<>();
	}
	
	private static class SingletonHolder{
		private static final CodeCreator HOLDER_INSTANCE = new CodeCreator();
	}
	
	public static CodeCreator getInstance(){
		return SingletonHolder.HOLDER_INSTANCE;
	}
	
	public void addClassToMap(String className){
		ClassCreator c = new ClassCreator(className);
		classMap.put(c.getClassName(), c);
	}
	
	public void addClassToMap(ClassCreator c){
		classMap.put(c.getClassName(), c);
	}

	public void addClassToMap(XMLProduct gettedClass) {
		
		ClassCreator c = new ClassCreator(gettedClass.getName());
		for(String attribute : gettedClass.getAttribute()){
			try {
				c.addField(attribute);
			} catch (SameFieldException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		this.classMap.put(c.getClassName(),c);
		
	}
	
	public void generateClasses(){
		for(Map.Entry<String, ClassCreator> entry : classMap.entrySet()){
			String classString = entry.getValue().generateCode();
			File file = new File(PATH+"step_definitions/"+entry.getKey()+".rb");
			try {
				file.createNewFile();
				FileWriter fileWriter = new FileWriter(file);
				BufferedWriter stream = new BufferedWriter(fileWriter);
				stream.write(classString+"\n");
				stream.flush();
				stream.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public ClassCreator getClassCode(String className) throws ClassNameNotFoundException {
		if(this.classMap.get(className)!=null){
			return this.classMap.get(className);
		}
		throw new ClassNameNotFoundException();
		
	}

	public LinkedList<String> getAllClassesNames() {
		LinkedList<String> list = new LinkedList<>();
		list.addAll(this.classMap.keySet());
		return list;		
	}
		
}
