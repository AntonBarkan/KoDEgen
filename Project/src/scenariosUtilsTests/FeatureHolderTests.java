package scenariosUtilsTests;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


import org.junit.Before;
import org.junit.Test;

import scenariosUtils.FeatureHolder;


public class FeatureHolderTests {
	
	FeatureHolder featureHolder;
	
	@Before
	public void setUp() throws Exception 
	{
		this.featureHolder = new FeatureHolder();
	}
	
	@Test
	public void testParsName() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Method method = FeatureHolder.class.getDeclaredMethod("parsName", String.class );
		method.setAccessible(true);
		assertEquals((String)method.invoke( this.featureHolder ,"Feature: Account Withdrawal"),"Account Withdrawal");
		
	}
}
