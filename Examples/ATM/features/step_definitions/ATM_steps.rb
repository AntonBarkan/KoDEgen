require "/usr/lib/ruby/vendor_ruby/cucumber/rspec/doubles"
require "test/unit/assertions"
World(Test::Unit::Assertions)

Given /^account has a balance of <balance> \$(\d+)$/ do |balance|
	@account = Account.new
	@account.balance = balance
end

When /^<amount> \$(\d+) are withdrawn from ATM$/ do |amount|
	atm = ATM.new
	atm.withdraw(amount)
end

Then /^the account balance should be <balance> \$(\d+)$/ do |balance|
	@account.stub(:balance).and_return(balance)
	assert balance == @account.balance.to_s
end

Given /^the account balance is <balance> \$(\d+)$/ do |balance|
	@account = Account.new
	@account.balance = balance
end

Given /^the Card is valid$/ do
	@card = Card.new
	@card.valid()
end

Given /^the ATM contains enough money$/ do
	@atm = ATM.new
end

When /^the Account request cash <amount> \$(\d+)$/ do |amount|
	@account.request(amount)
end

Then /^the ATM should dispense cash <dispense_cash> \$(\d+)$/ do |dispense_cash|
	@atm.stub(:dispense_cash).and_return(dispense_cash)
	assert dispense_cash == @atm.dispense_cash.to_s
end

Then /^the card should be <returned>"(.*?)"$/ do |returned|
	@card.stub(:returned).and_return(returned)
	assert returned == @card.returned.to_s
end

Given /^the ATM contains <amount> \$(\d+)$/ do |amount|
	@atm = ATM.new
end

Then /^the card should be <returned> "(.*?)"$/ do |returned|
	card = Card.new
	card.stub(:returned).and_return(returned)
	assert returned == card.returned.to_s
end

Given /^the ATM$/ do
	@atm = ATM.new
end

Given /^account$/ do
	@account = Account.new
end

Given /^the card is not_valid$/ do
	@card = Card.new
	@card.valid()
	@card.not_valid()
end

Then /^the card should be <retained>"(.*?)"$/ do |retained|
	@card.stub(:retained).and_return(retained)
	assert retained == @card.retained.to_s
end

