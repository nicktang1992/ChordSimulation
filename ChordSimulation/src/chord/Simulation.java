package chord;

import java.io.IOException;
import java.util.ArrayList;
import static chord.ChordSimulationUtil.*;

public class Simulation {

	public static void main(String[] args) {
		System.out.println(ChordNode.hashIP("100.90.9.0"));
		
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
		
		ChordNode n65 = new ChordNode("65");
		n65.setHash(65);
		
		ChordNode n129 = new ChordNode("129");
		n129.setHash(129);
		
		n65.add(null);
		n129.add(n65);
		
		printAll(n65);
		
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