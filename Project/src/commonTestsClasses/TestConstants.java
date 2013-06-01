package commonTestsClasses;

public class TestConstants {

	public static final String
	TEST_GIVEN = "Given /^empty shopping cart$/ do\n" +
			"\t@shopping_cart = Shopping_cart.new\n" +
			"end"
			,
	TEST_WHEN = "When /^tax is <tax> (\\d+)% percent$/ do |tax|\n"+
			"\t@shopping_cart.tax = tax\n"+
			"end"
			,
	TEST_THEN = "Then /^shopping cart contains <quantity> (\\d+) items$/ do |quantity|"+
			"\tassert quantity == @shopping_cart.contains( ).to_s\n"+
			"end"
			;
	
	public static final String 
		SHOPING_CART_CLASS = "Shopping_cart"
			,
		PRODUCT_CLASS = "Product"
			;
	
	

	public static final String 
		FIRST_TEST_CLASS_NAME = "first_class"
			,
		SECOMD_TEST_CLASS_NAME = "second_class"
			,
		THIRD_TEST_CLASS_NAME = "third_class"
					;
	

}
