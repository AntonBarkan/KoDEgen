package codeCreator_test;

import static org.junit.Assert.*;
import static commonTestsClasses.TestConstants.*;

import org.apache.commons.lang.WordUtils;
import org.junit.Before;
import org.junit.Test;

import codeCreator.CodeCreator;


public class CodeCreator_tests {

		
	
	@Before
	public void setUp() throws Exception {
		CodeCreator.getInstance().addClassToMap(FIRST_TEST_CLASS_NAME);
		CodeCreator.getInstance().addClassToMap(SECOMD_TEST_CLASS_NAME);
		CodeCreator.getInstance().addClassToMap(THIRD_TEST_CLASS_NAME);
	}

	@Test
	public void test() {
		assertTrue( CodeCreator.getInstance().getAllClassesNames().contains(WordUtils.capitalize(FIRST_TEST_CLASS_NAME)));
		assertTrue( CodeCreator.getInstance().getAllClassesNames().contains(WordUtils.capitalize(SECOMD_TEST_CLASS_NAME)));
		assertTrue( CodeCreator.getInstance().getAllClassesNames().contains(WordUtils.capitalize(THIRD_TEST_CLASS_NAME)));
		
		
	}

	

}
