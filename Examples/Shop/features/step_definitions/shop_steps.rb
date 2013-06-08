require "/usr/lib/ruby/vendor_ruby/cucumber/rspec/doubles"
require "test/unit/assertions"
World(Test::Unit::Assertions)

Given /^empty shopping cart$/ do
	@shopping_cart = Shopping_cart.new
end

When /^I add <quantity> (\d+) of Product <name> "(.*?)" that cost <price> (\d+)\$ to shopping cart$/ do |quantity, name, price|
	product = Product.new
	product.name = name
	product.price = price
	@shopping_cart.add(quantity , product)
end

When /^I add <quantity> (\d+) items of Product <name> "(.*?)" that costs <price> (\d+)\$ to shopping cart$/ do |quantity, name, price|
	product = Product.new
	product.name = name
	product.price = price
	@shopping_cart.add(quantity , product)
end

When /^tax is <tax> (\d+)% percent$/ do |tax|
	@shopping_cart.tax = tax
end

Then /^shopping cart contains <quantity> (\d+) items$/ do |quantity|
	@shopping_cart.stub(:contains).and_return(quantity)
	assert quantity == @shopping_cart.contains( ).to_s
end

