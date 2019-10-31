package visualization;

import static chord.ChordSimulationUtil.addPow;
import static chord.ChordSimulationUtil.printAll;
import static chord.Configuration.HASH_LENGTH;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collections;

import org.graphstream.graph.*;
import org.graphstream.graph.implementations.*;
import chord.*;

public class GraphStreamDemo {
	public static final int TEST_NODES = 5;
	static int getNextResponsibleHash(ArrayList<Integer> hashes, int target) {
		int index = Collections.binarySearch(hashes, target);
		if(index < 0) {
			index = -index-1;
			if(index == hashes.size()) {
				index = 0;
			}
		}
		return hashes.get(index);
	}
	static String styleSheet =
            "node {" +
            		"fill-color: #DDDDDD;" +
            		"stroke-mode:  plain;" +
            		"stroke-color: black;" +
            "}" +
            "node.marked {" +
            		"fill-color: red;" +
		    "}" +
			"node.restore {" +
				"fill-color: #DDDDDD;" +
			"}";
	
	public static void main(String[] args) {
		Graph graph = new SingleGraph("Graph");
		graph.addAttribute("ui.stylesheet", styleSheet);
        graph.setAutoCreate(true);
        graph.setStrict(false);
        graph.display();
		//build the graph circle
		graph.addNode("0");
		for(int i = 1; i < 16; i++) {
			graph.addNode(i + "");
			graph.addEdge(i + "," + Integer.toString(i - 1), i + "", Integer.toString(i - 1));
		}
		graph.addEdge("0,15", "0", "15");
		//add node identifier to the graph
		for (Node node : graph) {
	        node.addAttribute("ui.label", node.getId());
	    }
		try 
		{ 
		Thread.currentThread().sleep(5000);
		} 
		catch(Exception e){}
		//generate test nodes
		ChordNode n10 = new ChordNode("10");
		n10.setHash(10);
		graph.getNode(10).setAttribute("ui.class", "marked");
		try 
		{ 
		Thread.currentThread().sleep(5000);
		} 
		catch(Exception e){}
		ChordNode n4 = new ChordNode("4");
		n4.setHash(4);
		graph.getNode(4).setAttribute("ui.class", "marked");
		try 
		{ 
		Thread.currentThread().sleep(5000);
		} 
		catch(Exception e){}
		ChordNode n14 = new ChordNode("14");
		n14.setHash(14);
		graph.getNode(14).setAttribute("ui.class", "marked");
		try 
		{ 
		Thread.currentThread().sleep(5000);
		} 
		catch(Exception e){}
		ChordNode n0 = new ChordNode("0");
		n0.setHash(0);
		graph.getNode(0).setAttribute("ui.class", "marked");
		try 
		{ 
		Thread.currentThread().sleep(5000);
		} 
		catch(Exception e){}
		ChordNode n12 = new ChordNode("12");
		n12.setHash(12);
		graph.getNode(12).setAttribute("ui.class", "marked");
		try 
		{ 
		Thread.currentThread().sleep(5000);
		} 
		catch(Exception e){}
		n10.add(null);
		n4.add(n10);
		System.out.println("adding 4 to 10");
		//printNode(n153);
		
		n14.add(n4);
		System.out.println("adding 14 to 4");
		//printNode(n153);

		n0.add(n14);
		System.out.println("adding 0 to 14");
		//printNode(n153);

		n12.add(n0);
		System.out.println("adding 12 to 0");
		//printNode(n153);
		
		printAll(n12);
		System.out.println();
		try 
		{ 
		Thread.currentThread().sleep(5000);
		} 
		catch(Exception e){}
		n10.remove();
		graph.getNode(10).setAttribute("ui.class", "store");
		
		printAll(n12);
	}

	
}

