package tests;

import static org.junit.Assert.*;
import org.junit.*;

import static chord.ChordSimulationUtil.*;

public class ChordUtilTest {

	@Test
	public void powerTest() {

		assertEquals(151, addPow(150,0));
		assertEquals(154, addPow(150,2));
		assertEquals(72, addPow(200,7));

		assertEquals(72, minusPow(200,7));
		assertEquals(228, minusPow(100,7));
		
	}
	
}
