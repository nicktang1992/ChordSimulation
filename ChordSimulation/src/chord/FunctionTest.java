package chord;

import static chord.Configuration.HASH_UPPER_LIMIT;

public class FunctionTest {
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
