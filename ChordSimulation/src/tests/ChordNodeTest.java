package tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.*;
import chord.*;

public class ChordNodeTest {

	@Test
	public void inIntervalTest(){
		assertTrue(FunctionTest.inInterval(151, 176, false, true, 155));
		
		assertFalse(FunctionTest.inInterval(151, 176, false, false,15));

		assertTrue(FunctionTest.inInterval(176, 151, true, false, 250));

		assertTrue(FunctionTest.inInterval(176, 151, false, true, 1));
		
		assertFalse(FunctionTest.inInterval(175, 150, false, false, 151));

		assertFalse(FunctionTest.inInterval(175, 150, false, true, 175));

		assertTrue(FunctionTest.inInterval(175, 150, true, true, 150));

		assertFalse(FunctionTest.inInterval(175, 150, true, false, 150));

	}
	
}
