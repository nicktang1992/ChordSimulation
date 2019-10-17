package chord;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Queue;
import static chord.Configuration.*;
import static chord.ChordSimulationUtil.*;

public class ChordNode {


	private String IPAddress;
	public ChordNode[] fingerTable;
	private int hash;
	private ChordNode predecessor;
	
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
		while(!inInterval(
				currNode.hash, 
				currNode.fingerTable[0].hash,
				false, true,
				id
				)) {
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
			if(inInterval(
					this.hash, 
					id, 
					false, false,
					this.fingerTable[power].hash
					)) {
				return this.fingerTable[power];
			}
		}
		return this;
	}
	

	
	public void add(ChordNode attachingNode) {
		if(attachingNode == null) {
			for(int power = 0; power < HASH_LENGTH; power++) {
				this.fingerTable[power] = this;
				this.predecessor = this;
			}
		}else {
			initFingerTable(attachingNode);
			updateOthers();
		}
	}
	
	private void initFingerTable(ChordNode attachingNode) {
		// Insert this node to it proper position
		this.fingerTable[0] = attachingNode.findSuccessor(addPow(this.getHash(),0));
		this.predecessor = this.fingerTable[0].predecessor;
		this.fingerTable[0].predecessor = this;
		this.predecessor.fingerTable[0] = this;
		
		for(int power = 1; power < HASH_LENGTH; power++) {
			if(inInterval(
					this.hash,
					this.fingerTable[power-1].hash, 
					true, true,
					(this.hash + (int) Math.pow(2, power))%HASH_UPPER_LIMIT
					)) {
				this.fingerTable[power] = this.fingerTable[power-1];
			}else {
				this.fingerTable[power] = 
						attachingNode.findSuccessor((this.hash + (int) Math.pow(2, power))%HASH_UPPER_LIMIT);
			}
		}
	}
	
	private void updateOthers() {
		for(int power = 0; power < HASH_LENGTH; power++) {
			ChordNode nodePointingCurrentNode = this.findPredecessor(
					(this.hash - (int) Math.pow(2, power) + HASH_UPPER_LIMIT)
					%HASH_UPPER_LIMIT
					);
			if(nodePointingCurrentNode.getSuccessor().getHash()
					==(this.hash - (int) Math.pow(2, power) + HASH_UPPER_LIMIT)
					%HASH_UPPER_LIMIT) {
				nodePointingCurrentNode = nodePointingCurrentNode.getSuccessor();
			}
			nodePointingCurrentNode.updateFingerTables(this,power);
		}
	}
	
	private void updateFingerTables(ChordNode addingNode, int power) {
		if(addingNode==this) {
			return;
		}
		if(inInterval(
				this.hash, 
				this.fingerTable[power].hash, 
				true, true, 
				addingNode.hash
				)) {
			this.fingerTable[power] = addingNode;
			this.predecessor.updateFingerTables(addingNode, power);
		}
	}
	
	
	public void remove() {
		// Update other finger tables
		// remove successor or predecessor
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

	public ChordNode getPredecessor() {
		return predecessor;
	}

	public void setPredecessor(ChordNode predecessor) {
		this.predecessor = predecessor;
	}

	public ChordNode getSuccessor() {
		return this.getFingerTableEntry(0);
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
		return ""+hash;
	}
	
	
	//Return whether target is between start and end. 
	public static boolean inInterval (int intervalStart, int intervalEnd, boolean includeStart, boolean includeEnd, int target) {
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
		
		// if target falls on one of the endpoint, and this endpoint is included, return true
		if(includeStart && intervalStart==target) {
			return true;
		}
		
		if(includeEnd && intervalEnd == target) {
			return true;
		}
		
		// check if target falls in exclusive interval
		if(intervalStart == intervalEnd) {
			//if start equals end, the interval contains all circle.
			return true;
		}else if(intervalStart < intervalEnd) {
			if(target> intervalStart && target < intervalEnd) {
				return true;
			}else {
				return false;
			}
		}else { // if start > end
			if(target > intervalStart || target < intervalEnd) {
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
