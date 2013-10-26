package codeCreator_test;

import static org.junit.Assert.*;

import java.lang.reflect.Field;
import java.util.LinkedList;

import org.junit.Before;
import org.junit.Test;

import codeCreator.ClassCreator;
import codeCreator.Method;

import Exceptions.ExistTwoMethodsWithThisName;
import Exceptions.MethodNameNotFoundException;
import Exceptions.SameFieldException;
import Exceptions.SameFunctionExeption;
import Exceptions.SameParameterExeption;

public class ClassCreator_test {
	private ClassCreator classCreator;
	private static final String CLASS_NAME_FOR_ADDING = "testClass",
								CLASS_NAME_CORRECT = "TestClass",	
			FIRST_CLASS_FIELD = "firstTestField",
			FIRST_METHOD_NAME = "firstMethod" , SECOND_METHOD_NAME = "secondMethod",
			FIRST_METHOD_PARAMETER = "firstParameter";

	@Before
	public void setUp() throws Exception {
		this.classCreator = new ClassCreator(CLASS_NAME_FOR_ADDING);
	}


	@Test
	public void addMetodTest_expectedTrue() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException, SameFunctionExeption {
		Method method = new Method(FIRST_METHOD_NAME);
		this.classCreator.addMethod(method);
		LinkedList<Method> methods=new LinkedList<>();
		methods.add(method);
		Field test = this.classCreator.getClass().getDeclaredField("methods");
		test.setAccessible(true);
		assertEquals(methods, test.get(this.classCreator));
	}
	
	@Test
	public void nameClassTest() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException{
		Field test = this.classCreator.getClass().getDeclaredField("className");
		test.setAccessible(true);
		assertEquals(CLASS_NAME_CORRECT, test.get(this.classCreator));
	}
	
	@Test(expected = SameFunctionExeption.class)
	public void addMetodTest_expectedError() throws SameFunctionExeption {
		Method methodFirst = new Method(FIRST_METHOD_NAME);
		Method methodSecond = new Method(FIRST_METHOD_NAME);
		this.classCreator.addMethod(methodFirst);
		this.classCreator.addMethod(methodSecond);		
	}
	
	@Test
	public void addClassFieldTest_expectedTrue() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException, SameFieldException{
		LinkedList<String> fields = new LinkedList<>();
		this.classCreator.addField(FIRST_CLASS_FIELD);
		fields.add(FIRST_CLASS_FIELD);
		Field test = this.classCreator.getClass().getDeclaredField("classFields");
		test.setAccessible(true);
		assertEquals(fields, test.get(this.classCreator));
	}
	
	@Test( expected = SameFieldException.class )
	public void addClassField_expectedException() throws SameFieldException{
		this.classCreator.addField(FIRST_CLASS_FIELD);
		this.classCreator.addField(FIRST_CLASS_FIELD);
	}
	
	@Test
	public void test_getMethod_expectedTrue() throws SameFunctionExeption, ExistTwoMethodsWithThisName, MethodNameNotFoundException{
		Method method = new  Method(FIRST_METHOD_NAME);
		this.classCreator.addMethod(method);
		this.classCreator.addMethod(new Method(SECOND_METHOD_NAME));
		assertEquals(method, this.classCreator.getMethod(FIRST_METHOD_NAME));
	}
	
	@Test( expected = MethodNameNotFoundException.class )
	public void test_getMethod_expectedMethodNameNotFoundException() throws SameFunctionExeption, ExistTwoMethodsWithThisName, MethodNameNotFoundException{
		Method method = new  Method(FIRST_METHOD_NAME);
		this.classCreator.addMethod(method);
		this.classCreator.getMethod(SECOND_METHOD_NAME);
	}
	
	@Test( expected = ExistTwoMethodsWithThisName.class )
	public void test_getMethod_expectedExistTwoMethodsWithThisName() throws SameFunctionExeption, ExistTwoMethodsWithThisName, MethodNameNotFoundException, SameParameterExeption{
		Method method = new  Method(FIRST_METHOD_NAME);
		this.classCreator.addMethod(method);
		Method secondMethod = new Method(FIRST_METHOD_NAME);
		secondMethod.addParameter(FIRST_METHOD_PARAMETER);
		this.classCreator.addMethod(secondMethod);
		this.classCreator.getMethod(FIRST_METHOD_NAME);
	}
	
	@Test
	public void test_getMethodWithNumberOfParameters_expectedTrue() throws SameFunctionExeption, ExistTwoMethodsWithThisName, MethodNameNotFoundException{
		Method method = new  Method(FIRST_METHOD_NAME);
		this.classCreator.addMethod(method);
		this.classCreator.addMethod(new Method(SECOND_METHOD_NAME));
		assertEquals(method, this.classCreator.getMethod(FIRST_METHOD_NAME,0));
	}
	
	@Test
	public void test_getMethodWithNumberOfParameters_expectedExistTwoMethodsWithThisName() throws SameFunctionExeption, ExistTwoMethodsWithThisName, MethodNameNotFoundException, SameParameterExeption{
		Method method = new  Method(FIRST_METHOD_NAME);
		this.classCreator.addMethod(method);
		Method secondMethod = new Method(FIRST_METHOD_NAME);
		secondMethod.addParameter(FIRST_METHOD_PARAMETER);
		this.classCreator.addMethod(secondMethod);
		assertEquals(secondMethod, this.classCreator.getMethod(FIRST_METHOD_NAME,1));
	}
	
	@Test( expected = MethodNameNotFoundException.class )
	public void test_getMethoWithNumberOfParametersd_expectedMethodNameNotFoundException() throws SameFunctionExeption, ExistTwoMethodsWithThisName, MethodNameNotFoundException{
		Method method = new  Method(FIRST_METHOD_NAME);
		this.classCreator.addMethod(method);
		this.classCreator.getMethod(SECOND_METHOD_NAME,0);
	}
	
}
