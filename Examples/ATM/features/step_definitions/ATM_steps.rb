require "/usr/lib/ruby/vendor_ruby/cucumber/rspec/doubles"
require "test/unit/assertions"
World(Test::Unit::Assertions)

Given /^account has a balans of <balance> \$(\d+)$/ do |balance|
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

