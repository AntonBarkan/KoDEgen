package exceptions;

public class SameParameterExeption extends Exception {
	
	private String message;
	
	public SameParameterExeption(String message){
		this.message = message;
	}
	
	@Override
	public String getMessage() {
		return "Can not add two same parameters to function :" + message;
	}
}
