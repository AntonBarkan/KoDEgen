package TestGenerator_test;

import static org.junit.Assert.*;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import static org.mockito.Mockito.*;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;

import Ontology.Ontology;
import static Ontology.Ontology.*;
import TestGenerator.StepsGenerator;

public class StepsGenerator_tests {

	private StepsGenerator gen;
	private Ontology ontology;
	
	@Before
	public void setUp() throws Exception {
		File dir = new File(PATH+"step_definitions/");
		FileUtils.cleanDirectory(dir);
		this.ontology = mock(Ontology.class);
		this.gen = new StepsGenerator(this.ontology);
	}

	@Test
	public void testRewriteArguments() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Method method = this.gen.getClass().getDeclaredMethod("rewriteArguments", String.class);
		method.setAccessible(true);
		assertEquals("When /^I add <quantity> (\\d+) of Product <name> \"(.*?)\" to shopping cart$/ do |quantity, name|"
				,(String)method.invoke(this.gen,"When /^I add <quantity> (\\d+) of Product <name> \"(.*?)\" to shopping cart$/ do |arg1, arg2|"));
	
	}
	
	@Test
	public void testGetArgsArray() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Method method = this.gen.getClass().getDeclaredMethod("getArgsArray", String.class);
		method.setAccessible(true);
		String[] arr = { "quantity", "name"};
		assertArrayEquals( (Object[])arr
				,(Object[])method.invoke(this.gen,"When /^I add <quantity> (\\d+) of Product <name> \"(.*?)\" to shopping cart$/ do |arg1, arg2"));
	
	}

}
