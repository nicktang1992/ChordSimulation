package chord;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import static chord.ChordSimulationUtil.*;

public class Simulation {

	public static void main(String[] args) {
		/*
		Scanner myObj = new Scanner(System.in);  // Create a Scanner object
		System.out.println(ChordNode.hashIP("100.90.9.0"));
		System.out.println("Menu");
		System.out.println("1.Add an IP address");
		System.out.println("2.Delete an IP address");
		System.out.println("3.Search an IP address");
	    System.out.println("Enter your choice");
	    
	    String input = myObj.nextLine();  // Read user input
	    System.out.println("Please input your IP address");
	    if(input == "1") {
	    	
	    }
		
		ArrayList<ChordNode> nodes = new ArrayList();
		
		ChordNode a175 = new ChordNode("1.0.11.200");
		ChordNode b252 = new ChordNode("1.1.11.200");
		ChordNode c151 = new ChordNode("1.0.110.200");
		ChordNode d054 = new ChordNode("1.0.11.100");
		ChordNode e229 = new ChordNode("1.20.11.20");
		
		nodes.add(a175);
		nodes.add(b252);
		nodes.add(c151);
		nodes.add(d054);
		nodes.add(e229);
		
		for(int i =0;i< nodes.size();i++) {
			System.out.println(nodes.get(i).toString());
		}
		System.out.println();
				
		//c151.add(null);
		//a175.add(c151);
		*/
		ChordNode n93 = new ChordNode("93");
		n93.setHash(93);
		
		ChordNode n153 = new ChordNode("153");
		n153.setHash(153);
		
		ChordNode n14 = new ChordNode("14");
		n14.setHash(14);
		
		ChordNode n161 = new ChordNode("161");
		n161.setHash(161);
		
		ChordNode n165 = new ChordNode("165");
		n165.setHash(165);
		
		n93.add(null);
		n153.add(n93);
		System.out.println("adding 153 to 93");
		//printNode(n153);
		
		n14.add(n153);
		System.out.println("adding 14 to 153");
		//printNode(n153);

		n161.add(n14);
		System.out.println("adding 161 to 14");
		//printNode(n153);

		n165.add(n161);
		System.out.println("adding 165 to 161");
		//printNode(n153);
		
		printAll(n165);
		System.out.println();

		n93.remove();
		
		printAll(n165);
		
		
		//printAll(n14);
		
		//a175.add(null);
		//c151.add(a175);
		
		//ChordSimulationUtil.printAll(a175);

		//System.out.println("\033[H\033[2J");

		//d054.add(c151);
		
		//ChordSimulationUtil.printAll(a175);
		
		//e229.add(a175);
		//b252.add(d054);
		
		//ChordSimulationUtil.printAll(a175);


		/*for(int i =0;i< nodes.size();i++) {
			ChordNode curr = a175;
			System.out.println(curr.toString());
			curr = curr.getFingerTableEntry(0);
		}*/
		
		//printAll(e);
		

		
		//System.out.println(Configuration.HASH_UPPER_LIMIT);

	}
}