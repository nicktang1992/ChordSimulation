package chord;

import static chord.Configuration.HASH_LENGTH;

public class ChordSimulationUtil {

	public static void printAll(ChordNode startNode) {
		ChordNode currentNode = startNode;
		do {
			printNode(currentNode);
			currentNode = currentNode.getFingerTableEntry(0);
		}while(currentNode != startNode.getPredicessor());
	}
	
	public static void printNode(ChordNode currentNode) {
		System.out.println("Current Hash "+currentNode.getHash());
		for(int power = 0; power<HASH_LENGTH; power++) {
			System.out.println("    Finger Table index " + power + " : " + currentNode.fingerTable[power].getHash());
		}
		System.out.println();
	}
}
