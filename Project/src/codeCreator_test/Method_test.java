package codeCreator_test;

import static org.junit.Assert.*;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Before;
import org.junit.Test;

import codeCreator.Method;
import exceptions.SameParameterExeption;


public class Method_test {
	private Method methode;

	public static final String TEST_NAME = "testMethod";
	public static final String FIRST_TEST_PARAMETER = "firstTestParameter",
			SECOND_TEST_PARAMETER = "secondTestParameter",
			THIRTH_TEST_PARAMETER = "thirthTestParameter";
	
	@Before
	public void setUp() throws Exception {
		this.methode = new Method(TEST_NAME);
	}


	@Test
	public void checkIfConstractorSetMethodName() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		Field testName = this.methode.getClass().getDeclaredField("methodName");
		testName.setAccessible(true);
		assertEquals(testName.get(this.methode), TEST_NAME);
	} 
	
	@Test
	public void checkBasicMethodStructure(){
		Matcher m = Pattern.compile("\\s*def\\s*"+TEST_NAME+"\\s*end\\s*").matcher(this.methode.generateCode());
		assertTrue(m.matches());		
	} 
	
	
	@Test
	public void checkAdingOneParammeters() throws Exception{
		this.methode.addParameter(FIRST_TEST_PARAMETER);
		LinkedList<String> testList = new LinkedList<>();
		testList.add(FIRST_TEST_PARAMETER);
		Field testName = this.methode.getClass().getDeclaredField("methodParameters");
		testName.setAccessible(true);
		assertEquals(testName.get(this.methode), testList);
	}
	
	@Test
	public void checkCodeGenerationAfterAdingOneParammeters() throws Exception{
		this.methode.addParameter(FIRST_TEST_PARAMETER);

		Matcher m = Pattern.compile("\\s*def\\s*"+TEST_NAME+"\\("+FIRST_TEST_PARAMETER+
				"\\)"+"\\s*end\\s*").matcher(this.methode.generateCode());
		boolean b = m.matches();
		assertTrue(b);
	
	}
	
	@Test
	public void checkAding3Parammeters() throws Exception{
		this.methode.addParameter(FIRST_TEST_PARAMETER);
		this.methode.addParameter(SECOND_TEST_PARAMETER);
		this.methode.addParameter(THIRTH_TEST_PARAMETER);
		LinkedList<String> testList = new LinkedList<>();
		testList.add(FIRST_TEST_PARAMETER);
		testList.add(SECOND_TEST_PARAMETER);
		testList.add(THIRTH_TEST_PARAMETER);
		Field testName = this.methode.getClass().getDeclaredField("methodParameters");
		testName.setAccessible(true);
		assertEquals(testName.get(this.methode), testList);
	}
	
	@Test
	public void checkCodeGenerationAfterAding3Parammeters() throws Exception{
		this.methode.addParameter(FIRST_TEST_PARAMETER);
		this.methode.addParameter(SECOND_TEST_PARAMETER);
		this.methode.addParameter(THIRTH_TEST_PARAMETER);
		
		Matcher m = Pattern.compile("\\s*def\\s*"+TEST_NAME+"\\("+FIRST_TEST_PARAMETER+
				"\\,\\s*"+SECOND_TEST_PARAMETER+"\\,\\s*"+THIRTH_TEST_PARAMETER+
				"\\)"+"\\s*end\\s*").matcher(this.methode.generateCode());
		boolean b = m.matches();
		assertTrue(b);
	}
	
	@Test(expected = SameParameterExeption.class)
	public void checkCodeGenerationExeption() throws SameParameterExeption {
		this.methode.addParameter(FIRST_TEST_PARAMETER);
		this.methode.addParameter(FIRST_TEST_PARAMETER);
	}
	
	@Test
	public void checkIfTwoMethodEquals_expectedFalse(){
		Method otherMethod = new Method(TEST_NAME);
		assertTrue(this.methode.equals(otherMethod));
	}
	
	@Test 
	public void checkNumberOfParameters_expected0(){
		assertEquals(0, this.methode.getNumberOfParameters());
	}
	
	@Test 
	public void checkNumberOfParameters_expected3() throws SameParameterExeption{
		this.methode.addParameter(FIRST_TEST_PARAMETER);
		this.methode.addParameter(SECOND_TEST_PARAMETER);
		this.methode.addParameter(THIRTH_TEST_PARAMETER);
		assertEquals(3, this.methode.getNumberOfParameters());
	}
	
	@Test
	public void check_getMethodName(){
		assertEquals(TEST_NAME, this.methode.getMethodName());
	}
	
	@Test
	public void check_removeParameter() throws SameParameterExeption, NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException{
		this.methode.addParameter(FIRST_TEST_PARAMETER);
		this.methode.addParameter(SECOND_TEST_PARAMETER);
		this.methode.addParameter(THIRTH_TEST_PARAMETER);
		
		this.methode.removeParameter(SECOND_TEST_PARAMETER);
		
		LinkedList<String> testList = new LinkedList<>();
		testList.add(FIRST_TEST_PARAMETER);;
		testList.add(THIRTH_TEST_PARAMETER);
		Field testName = this.methode.getClass().getDeclaredField("methodParameters");
		testName.setAccessible(true);
		assertEquals(testName.get(this.methode), testList);
	}
	
	
	

}
