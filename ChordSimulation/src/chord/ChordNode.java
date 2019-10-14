package chord;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Queue;
import static chord.Configuration.*;

public class ChordNode {


	private String IPAddress;
	public ChordNode[] fingerTable;
	private int hash;
	private ChordNode predicessor;
	private ChordNode successor;
	
	/*
	 * For concurrency implementation 
	 */
	//private Queue<EventMessage> messages;
	
	public ChordNode(String IPAddress) {
		this.IPAddress =  IPAddress;
		hash = hashIP(this.IPAddress);
		fingerTable = new ChordNode[HASH_LENGTH];
	}
	
	public static int hashIP(String s) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-1");
			byte[] hash = md.digest(s.getBytes());
			byte mbyte = hash[hash.length-1];
			return Byte.toUnsignedInt(mbyte);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	public ChordNode findSuccessor(int id) {
		return findPredecessor(id).fingerTable[0];
	}
	
	public ChordNode findPredecessor(int id) {
		ChordNode currNode = this;
		while(!inInterval(currNode.hash, currNode.fingerTable[0].hash,id)) {
			currNode = currNode.closestPreceedingFinger(id);
		}
		return currNode;
	}
	
	public ChordNode closestPreceedingFinger(int id) {
		try {
			if(!validHash(id)) {
				throw new Exception("Id out of range with value " + id);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		for(int power = HASH_LENGTH-1; power >= 0; power--) {
			if(inInterval(this.hash, id, this.fingerTable[power].hash)) {
				return this.fingerTable[power];
			}
		}
		return this;
	}
	

	
	public void add(ChordNode attachingNode) {
		if(attachingNode == null) {
			for(int power = 0; power < HASH_LENGTH; power++) {
				this.fingerTable[power] = this;
				this.predicessor = this;
			}
		}else {
			initFingerTable(attachingNode);
			updateOthers();
		}
	}
	
	private void initFingerTable(ChordNode attachingNode) {
		// Insert this node to it proper position
		this.fingerTable[0] = attachingNode.findSuccessor(this.hash+1);
		this.predicessor = this.fingerTable[0].predicessor;
		this.fingerTable[0].predicessor = this;
		
		for(int power = 1; power < HASH_LENGTH; power++) {
			if(inInterval(this.hash,this.fingerTable[power-1].hash, 
					(this.hash + (int) Math.pow(2, power))%HASH_UPPER_LIMIT)) {
				this.fingerTable[power] = this.fingerTable[power-1];
			}else {
				this.fingerTable[power] = 
						attachingNode.findSuccessor((this.hash + (int) Math.pow(2, power))%HASH_UPPER_LIMIT);
			}
		}
	}
	
	private void updateOthers() {
		for(int power = 1; power < HASH_LENGTH; power++) {
			ChordNode nodePointingCurrentNode = 
					this.findPredecessor((this.hash - (int) Math.pow(2, power-1)+HASH_UPPER_LIMIT)%HASH_UPPER_LIMIT);
			updateFingerTables(nodePointingCurrentNode,power-1);
		}
	}
	
	private void updateFingerTables(ChordNode addingNode, int power) {
		if(inInterval(this.hash,this.fingerTable[power].hash,addingNode.hash)) {
			this.fingerTable[power] = addingNode;
			this.predicessor.updateFingerTables(addingNode, power);
		}
	}
	
	public void remove() {
		System.out.println("Removing node to be implemented");
	}
	
	
	public String getIPAddress() {
		return IPAddress;
	}

	public void setIPAddress(String iPAddress) {
		IPAddress = iPAddress;
	}

	public ChordNode[] getFingerTable() {
		return fingerTable;
	}
	
	public ChordNode getFingerTableEntry(int power) {
		return this.fingerTable[power];
	}

	public void setFingerTable(ChordNode[] fingerTable) {
		this.fingerTable = fingerTable;
	}

	public int getHash() {
		return hash;
	}

	public void setHash(int hash) {
		this.hash = hash;
	}

	public ChordNode getPredicessor() {
		return predicessor;
	}

	public void setPredicessor(ChordNode predicessor) {
		this.predicessor = predicessor;
	}

	public ChordNode getSuccessor() {
		return successor;
	}

	public void setSuccessor(ChordNode successor) {
		this.successor = successor;
	}

	/*
	public Queue<EventMessage> getMessages() {
		return messages;
	}*/

/*	public void setMessages(Queue<EventMessage> messages) {
		this.messages = messages;
	}
	*/
	
	public String toString() {
		return IPAddress+' '+ hash;
	}
	
	
	//Return whether target is between start (inclusive) and end (exclusive)
	public static boolean inInterval (int intervalStart, int intervalEnd, int target) {
		try {
			if(!validHash(intervalStart)) {
				throw new Exception("Interval start out of range with value "+ intervalStart);
			}
			if(!validHash(intervalEnd)) {
				throw new Exception("Interval end out of range with value "+ intervalEnd);
			}
			if(!validHash(target)) {
				throw new Exception("Target out of range with value "+ target);
			}

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		if(intervalStart == intervalEnd) {
			return true;
		}else if(intervalStart < intervalEnd) {
			if(target>= intervalStart && target > intervalEnd) {
				return true;
			}else {
				return false;
			}
		}else { // if start > end
			if(target >= intervalStart || target < intervalEnd) {
				return true;
			}else {
				return false;
			}
		}
		
	}
	
	private static boolean validHash(int hash) {
		if(hash>=0 && hash < HASH_UPPER_LIMIT) {
			return true;
		}else {
			return false;
		}
	}
	
}
