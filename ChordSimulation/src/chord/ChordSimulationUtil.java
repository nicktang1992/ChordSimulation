package chord;

import static chord.Configuration.*;

public class ChordSimulationUtil {

	public static void printAll(ChordNode startNode) {
		printNode(startNode);
		ChordNode currentNode = startNode.getFingerTableEntry(0);
		while(currentNode!=startNode) {
			printNode(currentNode);
			currentNode = currentNode.getFingerTableEntry(0);
		}
	}
	
	public static void printNode(ChordNode currentNode) {
		System.out.println("Current Hash "+currentNode.getHash());
		for(int power = 0; power<HASH_LENGTH; power++) {
			System.out.println("    Finger Table index " + power + " : " + currentNode.fingerTable[power].getHash());
		}
		System.out.println("    Predicessor : "+ currentNode.getPredecessor().getHash());
		System.out.println();
	}
	
	public static int addPow(int input, int power) {
		validPowerInput(power);
		return (input + (int)Math.pow(2, power))%HASH_UPPER_LIMIT;
	}
	
	
	public static int minusPow(int input, int power) {
		validPowerInput(power);
		input = input - (int) Math.pow(2, power);
		while(input < 0) {
			input += HASH_UPPER_LIMIT;
		}
		return input;
	}
	
	public static void validPowerInput(int power) {
		try {
			if(power<0||power >= HASH_LENGTH) {
				throw new Exception("Power out of bound : "+ power);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
