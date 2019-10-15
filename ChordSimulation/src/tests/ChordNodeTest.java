package tests;

import static org.junit.Assert.*;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import org.junit.*;
import chord.*;
import static chord.Configuration.*;
import static chord.ChordSimulationUtil.*;


public class ChordNodeTest {
	
	public static final int TEST_NODES = 5;

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
	
	@Test
	public void addNodeTest() {
		
		//generate test nodes
		ArrayList<ChordNode> nodes = new ArrayList<>();
		ArrayList<Integer> hashes = new ArrayList<>();
		
		byte[] randomArray = new byte[10];
		for(int i = 0; i < TEST_NODES; i++) {
			new Random().nextBytes(randomArray);
			ChordNode testNode = new ChordNode(new String(randomArray, Charset.forName("UTF-8")));
		
			while(hashes.contains(testNode.getHash())) {
				new Random().nextBytes(randomArray);
				testNode = new ChordNode(new String(randomArray, Charset.forName("UTF-8")));
			}
			
			nodes.add(testNode);
			hashes.add(testNode.getHash());
		}
		
		Collections.sort(hashes);
		
		//adding all nodes to the system
		System.out.println("adding node 0 with hash "+ nodes.get(0).getHash());
		nodes.get(0).add(null);
		
		for(int i = 1; i< nodes.size();i++) {
			System.out.println("adding node "+ i +" with hash "+ nodes.get(i).getHash());
			nodes.get(i).add(nodes.get(i-1));
		}
		
		for(ChordNode n : nodes) {
			for(int i = 0; i<HASH_LENGTH; i++) {
				assertEquals("finger "+ i + " of node "+ n.getHash() + " is " + n.getFingerTableEntry(i).getHash() + " instead of " + getNextResponsibleHash(hashes,addPow(n.getHash(),i)),
						n.getFingerTableEntry(i).getHash(),
						getNextResponsibleHash(hashes,addPow(n.getHash(),i)));
			}
			assertEquals("predicessor of " + n.getHash() + " is " + n.getPredicessor().getHash(),
					n.getPredicessor().getFingerTableEntry(0),n);
		}
		
		System.out.println();
		printAll(nodes.get(0));
		
	}
	
	private int getNextResponsibleHash(ArrayList<Integer> hashes, int target) {
		int index = Collections.binarySearch(hashes, target);
		if(index < 0) {
			index = -index-1;
			if(index == hashes.size()) {
				index = 0;
			}
		}
		return hashes.get(index);
	}
	
}
