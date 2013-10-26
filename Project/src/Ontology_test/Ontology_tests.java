package Ontology_test;

import static org.junit.Assert.*;


import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;
import Ontology.Ontology;
import StateMachineXML.Edge;
import StateMachineXML.State;
import TestGenerator.UnitTestStruct;



public class Ontology_tests 
{

	private Ontology ontology;

	@Before
	public void setUp() throws Exception 
	{
		this.ontology = new Ontology();
	}
	
	

	@Test
	public void testCreatingNewObjectWithoutName() throws NoSuchMethodException, SecurityException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException, NoSuchFieldException 
	{
		Method method = this.ontology.getClass().getDeclaredMethod("classNameGenerate", String.class , String.class);
		method.setAccessible(true);
		assertEquals((String)method.invoke(this.ontology ,"shopping cart","Given empty shopping cart"),"@shopping_cart = Shopping_cart.new");
		Field field = this.ontology.getClass().getDeclaredField("globalClassMap");
		field.setAccessible(true);
				
		HashMap<String, String> map= new HashMap<>();
		map.put("shopping_cart", "Shopping_cart");
		assertEquals(map, field.get(this.ontology));
	}
	
	@Test
	public void testCreatingNewObjectWithName() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Method method = this.ontology.getClass().getDeclaredMethod("classNameGenerate", String.class , String.class);
		method.setAccessible(true);
		assertEquals( "product = Product.new" , (String)method.invoke(this.ontology ,"Product","When I add <quantity> 1 of Product <name> \"A\" to shopping cart"));
	}
	
	@Test
	public void testCreatingNewObjectWithNoName() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Method method = this.ontology.getClass().getDeclaredMethod("classNameGenerate", String.class , String.class);
		method.setAccessible(true);
		assertEquals((String)method.invoke(this.ontology ,"shopping cart","When I add <quantity> 1 of Product <name> \"A\" to shopping cart"),"shopping_cart = Shopping_cart.new");
	}
	
	@Test
	public void testclaseNameFindeWithName() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Method method = this.ontology.getClass().getDeclaredMethod("findClassName", String.class , String.class);
		method.setAccessible(true);
		assertEquals("shopping_cart",(String)method.invoke(this.ontology ,"shopping cart","Given empty shopping cart"));
	}
	
	@Test
	public void testClaseNameFindeWithNoName() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Method method = this.ontology.getClass().getDeclaredMethod("findClassName", String.class , String.class);
		method.setAccessible(true);
		assertEquals("product",(String)method.invoke(this.ontology ,"product","When I add <quantity> 1 of Product <name> \"A\" to shopping cart"));
	}
	
	@Test
	public void testClassAttributeValueGenerate_notThen() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchFieldException {
		Method method = this.ontology.getClass().getDeclaredMethod("classAttributeValueGenerate", String.class,String.class,String.class );
		method.setAccessible(true);
		Field globalClassListField = this.ontology.getClass().getDeclaredField("globalClassMap");
		globalClassListField.setAccessible(true);
		HashMap<String, String> map= new HashMap<>();
		map.put("shopping_cart", "Shopping_cart");
		globalClassListField.set(this.ontology, map);
		assertEquals("@shopping_cart.tax = tax",((String)method.invoke(this.ontology ,"shopping cart","Tax","And tax <value> 8 percent")).trim());
	}
	
	@Test
	public void testClassAttributeValueGenerate_withThen() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchFieldException {
		Method method = this.ontology.getClass().getDeclaredMethod("classAttributeValueGenerate", String.class,String.class,String.class );
		method.setAccessible(true);
		Field globalClassListField = this.ontology.getClass().getDeclaredField("globalClassMap");
		globalClassListField.setAccessible(true);
		
		HashMap<String, String> map= new HashMap<>();
		map.put("account", "Account");
		globalClassListField.set(this.ontology, map);
		assertEquals("@account.stub(:balance).and_return(balance)\n\tassert balance == @account.balance.to_s",((String)method.invoke( this.ontology , "account" ,
				"balance" ,"Then /^the account balance should be <balance> \\$(\\d+)$/ do |balance|")).trim()  );
	}
	
	
	@Test
	public void tets_testFunctionGeneretor() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchFieldException {
		Method method = this.ontology.getClass().getDeclaredMethod("testFunctionGeneretor" ,State.class,Edge.class, String.class, UnitTestStruct.class);
		method.setAccessible(true);
		State state = mock(State.class);
		Edge edge = mock(Edge.class);
		when(edge.getName()).thenReturn("add");
		when(state.getClassName()).thenReturn("shopping_cart");
		Field field = this.ontology.getClass().getDeclaredField("globalClassMap");
		field.setAccessible(true);
		
		HashMap<String, String> map= new HashMap<>();
		map.put("shopping_cart", "Shopping_cart");
		field.set(this.ontology, map);
		
		
		UnitTestStruct uts = mock(UnitTestStruct.class);
		when(uts.getParamettersString("shopping_cart")).thenReturn("quantity , name");
		assertEquals("@shopping_cart.add(quantity , name)",(String)method.invoke(   this.ontology ,state,edge,"When /^I add <quantity> (\\d+) of Product <name> \"(.*?)\" to shopping cart$/ do |quantity, name|",uts));
	}
	
	@Test
	public void tets_testFunctionGeneretorAssertTest() throws NoSuchMethodException, SecurityException, IllegalAccessException,
				IllegalArgumentException, InvocationTargetException, NoSuchFieldException 
	{
		Method method = this.ontology.getClass().getDeclaredMethod("testFunctionGeneretor" ,State.class,Edge.class, String.class,UnitTestStruct.class);
		method.setAccessible(true);
		State state = mock(State.class);
		Edge edge = mock(Edge.class);
		when(edge.getName()).thenReturn("contains");
		when(state.getClassName()).thenReturn("shopping_cart");
		Field field = this.ontology.getClass().getDeclaredField("globalClassMap");
		field.setAccessible(true);
		HashMap<String, String> map= new HashMap<>();
		map.put("shopping_cart", "Shopping_cart");
		field.set(this.ontology, map);
		UnitTestStruct uts = mock(UnitTestStruct.class);
		when(uts.getParamettersString("shopping_cart")).thenReturn("quantity");
		assertEquals("@shopping_cart.stub(:contains).and_return(quantity)\n\tassert quantity == @shopping_cart.contains( ).to_s",(String)method.invoke(   this.ontology ,state,edge,"Then /^shopping cart contains <quantity> (\\d+) items$/ do |quantity|",uts));
	}

}
