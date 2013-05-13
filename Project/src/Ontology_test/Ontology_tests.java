package Ontology_test;

import static org.junit.Assert.*;


import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedList;

import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;
import Ontology.Ontology;
import StateMachineXML.Edge;
import StateMachineXML.State;



public class Ontology_tests {

	private Ontology ontology;

	@Before
	public void setUp() throws Exception {
		this.ontology = new Ontology();//mock(Ontology.class);
	}

	@Test
	public void testCreatingNewObjectWithoutName() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchFieldException {
		Method method = this.ontology.getClass().getDeclaredMethod("classNameGenerate", String.class , String.class);
		method.setAccessible(true);
		assertEquals((String)method.invoke(this.ontology ,"shopping cart","Given empty shopping cart"),"@shopping_cart = Shopping_cart.new");
		Field field = this.ontology.getClass().getDeclaredField("globalClassList");
		field.setAccessible(true);
		LinkedList<String> list = new LinkedList<>();
		list.add("shopping_cart");
		assertEquals(list, field.get(this.ontology));
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
		Method method = this.ontology.getClass().getDeclaredMethod("claseNameFinder", String.class , String.class);
		method.setAccessible(true);
		assertEquals("shopping_cart",(String)method.invoke(this.ontology ,"shopping cart","Given empty shopping cart"));
	}
	
	@Test
	public void testClaseNameFindeWithNoName() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Method method = this.ontology.getClass().getDeclaredMethod("claseNameFinder", String.class , String.class);
		method.setAccessible(true);
		assertEquals("product",(String)method.invoke(this.ontology ,"product","When I add <quantity> 1 of Product <name> \"A\" to shopping cart"));
	}
	
	@Test
	public void testClassAttributeValueGenerate() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchFieldException {
		Method method = this.ontology.getClass().getDeclaredMethod("classAttributeValueGenerate", String.class,String.class,String.class );
		method.setAccessible(true);
		Field globalClassListField = this.ontology.getClass().getDeclaredField("globalClassList");
		globalClassListField.setAccessible(true);
		LinkedList<String> list = new LinkedList<>();
		list.add("shopping_cart");
		globalClassListField.set(this.ontology,list);
		assertEquals("@shopping_cart.tax = tax",((String)method.invoke(this.ontology ,"shopping cart","Tax","And tax <value> 8 percent")).trim());
	}
	
	@Test
	public void testgetParametersString() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Method method = this.ontology.getClass().getDeclaredMethod("getParametersString", String.class );
		method.setAccessible(true);
		assertEquals("quantity, name",((String)method.invoke(this.ontology ,"When /^I add <quantity> (\\d+) of Product <name> \"(.*?)\" to shopping cart$/ do |quantity, name|")).trim());
	}
	
	@Test
	public void tets_testFunctionGeneretor() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchFieldException {
		Method method = this.ontology.getClass().getDeclaredMethod("testFunctionGeneretor" ,State.class,Edge.class, String.class);
		method.setAccessible(true);
		State state = mock(State.class);
		Edge edge = mock(Edge.class);
		when(edge.getName()).thenReturn("add");
		when(state.getClassName()).thenReturn("shopping cart");
		Field field = this.ontology.getClass().getDeclaredField("globalClassList");
		field.setAccessible(true);
		LinkedList<String> list = new LinkedList<>();
		list.add("shopping_cart");
		field.set(this.ontology, list);
		assertEquals("@shopping_cart.add(quantity, name)",(String)method.invoke(   this.ontology ,state,edge,"When /^I add <quantity> (\\d+) of Product <name> \"(.*?)\" to shopping cart$/ do |quantity, name|"));
	}
	
	@Test
	public void tets_testFunctionGeneretorAssertTest() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchFieldException {
		Method method = this.ontology.getClass().getDeclaredMethod("testFunctionGeneretor" ,State.class,Edge.class, String.class);
		method.setAccessible(true);
		State state = mock(State.class);
		Edge edge = mock(Edge.class);
		when(edge.getName()).thenReturn("contains");
		when(state.getClassName()).thenReturn("shopping cart");
		Field field = this.ontology.getClass().getDeclaredField("globalClassList");
		field.setAccessible(true);
		LinkedList<String> list = new LinkedList<>();
		list.add("shopping_cart");
		field.set(this.ontology, list);
		assertEquals("assert quantity == @shopping_cart.contains( )",(String)method.invoke(   this.ontology ,state,edge,"Then /^shopping cart contains <quantity> (\\d+) items$/ do |quantity|"));
	}

}
