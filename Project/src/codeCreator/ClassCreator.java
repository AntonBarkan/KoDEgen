package codeCreator;

import java.util.LinkedList;

import org.apache.commons.lang.WordUtils;

import Exceptions.ExistTwoMethodsWithThisName;
import Exceptions.MethodNameNotFoundException;
import Exceptions.SameFieldException;
import Exceptions.SameFunctionExeption;

public class ClassCreator {
	private String className;
	private LinkedList<Method> methods;
	private LinkedList<String> classFields;

	public ClassCreator(String className) {
		this.className = WordUtils.capitalize(className.replace(" ", "_"));
		this.methods = new LinkedList<>();
		this.classFields = new LinkedList<>();
	}
	
	public void addMethod(Method method) throws SameFunctionExeption{
		if(this.methods.contains(method))
			throw new SameFunctionExeption();
		this.methods.add(method);
	}
	
	public Method getMethod(String name) throws ExistTwoMethodsWithThisName, MethodNameNotFoundException{
		Method method = null;
		for(Method m : this.methods){
			if(m.getMethodName().equals(name)){
				if( method == null ){ method = m;}
				else{ throw new ExistTwoMethodsWithThisName(); }
			}
		}
		if( method == null){
			throw new MethodNameNotFoundException();
		}
		return method;
	}
	
	public Method getMethod(String name , int numberOfParameters) throws MethodNameNotFoundException{
		for(Method method : this.methods){
			if(method.getMethodName().equals(name) && method.getNumberOfParameters() == numberOfParameters){
				return method;
			}
		}
		throw new MethodNameNotFoundException();
	}
	
	public void addField(String field) throws SameFieldException{
		field = field.replace(' ', '_');
		if(this.classFields.contains(field))
			throw new SameFieldException();
		this.classFields.add(field);
	}
	
	public String generateCode(){
		String generatedCode="";
		generatedCode += "class " + this.className + "\n";
		
		for(String field: this.classFields ){
			generatedCode += "\tattr_accessor :"+ field + "\n";
		}
		
		for(Method method:this.methods){
			generatedCode += "\n"+method.generateCode() + "\n";
		}
		
		generatedCode += "end";
		return generatedCode;
	}
	
	
	public String getClassName() {
		return className;
	}
	
	public String toString(){
		return this.className;
	}

	

}
