package common;

public final class CommonFunctions {
	
	public static String  getStringFromFirstStringToSecondString(String line,String firstString , String secondString)
	{
		if(!line.contains(firstString))
		{
			return null;
		}
		String returnString  = line.substring( line.indexOf(firstString)+firstString.length());
		if(!line.contains(secondString))
		{
			return line.substring( line.indexOf( firstString ) + firstString.length() );
		}
		returnString = returnString.substring(0,returnString.indexOf(secondString));
		return returnString;
	}
}
