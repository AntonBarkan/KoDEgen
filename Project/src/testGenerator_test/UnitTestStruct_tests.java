package testGenerator_test;

import static org.junit.Assert.*;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.Before;
import org.junit.Test;

import testGenerator.UnitTestStruct;


public class UnitTestStruct_tests {
	
	private UnitTestStruct str;

	@Before
	public void setUp() throws Exception {
		this.str = new UnitTestStruct("When /^I add <quantity> (\\d+) of Product <name> \"(.*?)\" to shopping cart$/ do |quantity, name, price|");
	}

	@Test
	public void test_getParametersArrayMethod_lineWithParameters() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Method getParametersArrayMethod = this.str.getClass().getDeclaredMethod("getParametersArray", String.class);
		getParametersArrayMethod.setAccessible(true);
		Object[] array = {"quantity", "name", "price" } ;		
		assertArrayEquals( array , (Object[])getParametersArrayMethod.invoke(this.str ,"When /^I add <quantity> (\\d+) of Product <name> \"(.*?)\" to shopping cart$/ do |quantity, name, price|"));
	}
	
	@Test
	public void test_getParametersArrayMethod_emptyLne() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Method getParametersArrayMethod = this.str.getClass().getDeclaredMethod("getParametersArray", String.class);
		getParametersArrayMethod.setAccessible(true);
		Object[] array = {""} ;		
		assertArrayEquals( array , (Object[])getParametersArrayMethod.invoke(this.str ,"Given /^empty shopping cart$/ do"));
	}
	
	@Test
	public void test_setInUSe() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchFieldException {
		Method setInUSeMethod = this.str.getClass().getDeclaredMethod("setInUSe", String.class);
		setInUSeMethod.setAccessible(true);
		setInUSeMethod.invoke(this.str ,"name");
		Field inUSeField = this.str.getClass().getDeclaredField("inUse");
		inUSeField.setAccessible(true);
		assertEquals(false, ((boolean[])inUSeField.get(this.str))[0]);
		assertEquals(true, ((boolean[])inUSeField.get(this.str))[1]);
		assertEquals(false, ((boolean[])inUSeField.get(this.str))[2]);
	}
	
	@Test
	public void test_getNotInUseParametersString() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Method getParametersArrayMethod = this.str.getClass().getDeclaredMethod("getNotInUseParametersString");
		getParametersArrayMethod.setAccessible(true);	
		this.str.setInUSe("quantity");
		this.str.setInUSe("price");
		assertEquals( "name" , getParametersArrayMethod.invoke( this.str ) );
	}
	
	@Test
	public void test_getNotInUseParametersString_emptyString() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Method getParametersArrayMethod = this.str.getClass().getDeclaredMethod("getNotInUseParametersString");
		getParametersArrayMethod.setAccessible(true);
		this.str.setInUSe("quantity");
		this.str.setInUSe("price");
		this.str.setInUSe("name");
		assertEquals( "" , getParametersArrayMethod.invoke( this.str ) );
	}
	
	@Test
	public void test_getClassesString() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Method getParametersArrayMethod = this.str.getClass().getDeclaredMethod("getClassesString");
		getParametersArrayMethod.setAccessible(true);	
		this.str.addClass("class1");
		this.str.addClass("class2");
		assertEquals( "class1 , class2" , getParametersArrayMethod.invoke( this.str ) );
	}
	
	@Test
	public void test_getClassesString_emptyString() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Method getParametersArrayMethod = this.str.getClass().getDeclaredMethod("getClassesString");
		getParametersArrayMethod.setAccessible(true);	
		assertEquals( "" , getParametersArrayMethod.invoke( this.str ) );
	}
	
	@Test
	public void getParamettersString_test_objrctInList()
	{
		this.str = new UnitTestStruct("When /^<amount> \\$(\\d+) are withdrawn from ATM$/ do |amount|");
		this.str.addClass("atm");
		assertEquals("amount", this.str.getParamettersString("atm"));
	}
	
	@Test
	public void getParamettersString_test_objectNotInList()
	{
		this.str = new UnitTestStruct("When /^<amount> \\$(\\d+) are withdrawn from ATM$/ do |amount|");
		this.str.addClass("atm");
		assertEquals("amount , atm", this.str.getParamettersString("test"));
	}
	
	@Test
	public void setClassInUse_test() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException
	{
		Method setClassInUseMethod = this.str.getClass().getDeclaredMethod("setClassInUse", String.class);
		setClassInUseMethod.setAccessible(true);
		Method getNotInUseClassesStringMethod = this.str.getClass().getDeclaredMethod("getNotInUseClassesString");
		getNotInUseClassesStringMethod.setAccessible(true);
		this.str.addClass("atm");
		this.str.addClass("testClass");
		assertEquals("atm , testClass", getNotInUseClassesStringMethod.invoke(this.str));
		setClassInUseMethod.invoke(this.str, "notExistClass");
		assertEquals("atm , testClass", getNotInUseClassesStringMethod.invoke(this.str));
		setClassInUseMethod.invoke(this.str, "atm");
		assertEquals("testClass", getNotInUseClassesStringMethod.invoke(this.str));
		
	}

}
