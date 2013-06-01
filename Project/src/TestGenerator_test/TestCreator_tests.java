package TestGenerator_test;

import static org.junit.Assert.*;
import static commonTestsClasses.TestConstants.*;

import org.junit.Before;
import org.junit.Test;

import TestGenerator.TestCreator;

public class TestCreator_tests {
	
	
		

	@Before
	public void setUp() throws Exception {
		TestCreator.getInstance().addStep(TEST_GIVEN);
		TestCreator.getInstance().addStep(TEST_WHEN);
		TestCreator.getInstance().addStep(TEST_THEN);
	}

	@Test
	public void findTestTest() {
		assertEquals(TEST_THEN ,TestCreator.getInstance().findStep(TEST_THEN));
	}

}
