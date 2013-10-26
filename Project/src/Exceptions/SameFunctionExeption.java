package Exceptions;

public class SameFunctionExeption extends Exception{

	
	@Override
	public String getMessage() {
		return "Can not add two same function to class";
	}
}
