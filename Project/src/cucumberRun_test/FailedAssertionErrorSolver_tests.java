package cucumberRun_test;

import static commonTestsClasses.TestConstants.PRODUCT_CLASS;
import static commonTestsClasses.TestConstants.SHOPING_CART_CLASS;
import static commonTestsClasses.TestConstants.TEST_THEN;
import static commonTestsClasses.TestConstants.TEST_WHEN;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import ontology.Ontology;

import org.junit.Before;
import org.junit.Test;

import testGenerator.TestCreator;

import codeCreator.CodeCreator;
import cucumberRun.FailedAssertionErrorSolver;

public class FailedAssertionErrorSolver_tests 
{
	private static final String CUCUMBER_OUTPUT = 
			"Feature: Adding to a shopping cart\n"+
			"\n"+
			"\tScenario: Add items to shopping cart factorygirl                                           # /home/anton/Documents/project/shop/features/shop.features:3\n"+
		    "\t\tGiven empty shopping cart                                                                  # /home/anton/Documents/project/shop/features/step_definitions/shop_steps.rb:4\n"+
		    "\t\tWhen I add <quantity> 1 of Product <name> \"A\" that cost <price> 30$ to shopping cart       # /home/anton/Documents/project/shop/features/step_definitions/shop_steps.rb:8\n"+
		    "\t\tAnd I add <quantity> 2 items of Product <name> \"B\" that costs <price> 50$ to shopping cart # /home/anton/Documents/project/shop/features/step_definitions/shop_steps.rb:15\n"+
		    "\t\tAnd tax is <tax> 8% percent                                                                # /home/anton/Documents/project/shop/features/step_definitions/shop_steps.rb:22\n"+
		    "\t\tThen shopping cart contains <quantity> 3 items                                             # /home/anton/Documents/project/shop/features/step_definitions/shop_steps.rb:26\n"+
		    "\t\tFailed assertion, no message given. (MiniTest::Assertion)\n"+
		    "\t\t/home/anton/Documents/project/shop/features/step_definitions/shop_steps.rb:27:in `/^shopping cart contains <quantity> (\\d+) items$/'\n"+
		    "\t\t/home/anton/Documents/project/shop/features/shop.features:9:in `Then shopping cart contains <quantity> 3 items'\n"+
		    "\n"+
		    "Failing Scenarios:\n"+
		    "cucumber /home/anton/Documents/project/shop/features/shop.features:3 # Scenario: Add items to shopping cart factorygirl\n"+
		    "\n"+
		    "1 scenario (1 failed)\n"+
			"5 steps (1 failed, 4 passed)\n"+
			"0m0.002s\n";
	
	private static final String ASSERT_LINE = "\tassert quantity == @shopping_cart.contains( ).to_s\n",
								CLASS_NAME = "shopping_cart",
								FEATURES_LINE = "\t\t/home/anton/Documents/project/shop/features/shop.features:9:in `Then shopping cart contains <quantity> 3 items'";	
	private FailedAssertionErrorSolver errorSolver;
	private Ontology ontology;
	
	@Before
	public void setUp() throws Exception 
	{	
		CodeCreator.getInstance().addClassToMap(SHOPING_CART_CLASS);
		CodeCreator.getInstance().addClassToMap(PRODUCT_CLASS);
		TestCreator.getInstance().addStep(TEST_THEN);
		TestCreator.getInstance().addStep(TEST_WHEN);
		ontology = mock(Ontology.class);
	
		Object[] arr = {"shopping_cart"};
		when(ontology.getGlobalClasses()).thenReturn(arr);
		
		when(ontology.getClassType("shopping_cart") ).thenReturn("Shopping_cart");
		
		this.errorSolver = new FailedAssertionErrorSolver(CUCUMBER_OUTPUT , ontology);
	}

	@Test
	public void findClassNameTest() throws NoSuchMethodException, SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException, InvocationTargetException 
	{
		Method findClassNameMethod = this.errorSolver.getClass().getDeclaredMethod("findThisClassName", String.class , Ontology.class);
		findClassNameMethod.setAccessible(true);
		assertEquals("shopping_cart", findClassNameMethod.invoke(this.errorSolver,ASSERT_LINE , this.ontology));
	}
	
	@Test
	public void findMethodOrFieldNameTest() throws NoSuchMethodException, SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException, InvocationTargetException 
	{
		Method findMethodOrFieldNameMethod = this.errorSolver.getClass().getDeclaredMethod("findMethodOrFieldName", String.class , String.class);
		findMethodOrFieldNameMethod.setAccessible(true);
		assertEquals("contains", findMethodOrFieldNameMethod.invoke(this.errorSolver,ASSERT_LINE , CLASS_NAME));
	}
	
	@Test
	public void generateLogicTest() throws NoSuchMethodException, SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException, InvocationTargetException 
	{
		Method generateLogicMethod = this.errorSolver.getClass().getDeclaredMethod("generateLogic", String.class , String.class );
		generateLogicMethod.setAccessible(true);
		assertEquals("return 3", generateLogicMethod.invoke(this.errorSolver , ASSERT_LINE , FEATURES_LINE));
	}

}
