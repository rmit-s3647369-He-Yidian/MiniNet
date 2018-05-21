/**
 * 
 */
package MiniNet_2;

/**
 * @author s3647369_Yidian_He
 *
 */

	
class NoParentException extends Exception {		
	private String name;
	public NoParentException(String s) {
		 this.name = s;
	}
	public String getNoParentMessage() {
		return name + " is not added because of missing parents.";
	}
}

class NoAvailableException extends Exception {
	public NoAvailableException() {}
	public String getHasSpouseMessage() {
		return "The two people cannot become couples.";
	}
	public String getAlreadyCoupleMessage() {
		return "They are already couples.";
	}
}

class NotToBeCoupledException extends Exception {
	public NotToBeCoupledException(String s) {}
	public String NotToBeCoupledMessage(String s) {
		return "Only adult can become couples.";
	}
}

class TooYoungException extends Exception {
	public TooYoungException() {}
	public String getTooYoungMessage() {
		return "Young child cannot make friends.";
	}
}

class NotToBeFriendsException extends Exception {
	public NotToBeFriendsException() {}
	public String getNotToBeFriendsMessage() {
		return "They cannot be friends.";
	}
}
	
class NotToBeColleaguesException extends Exception {
	public NotToBeColleaguesException() {}
	public String NotToBeColleaguesMessage() {
		return "Children cannot make colleagues.";
	}
}

class NotToBeClassmatesException extends Exception {
	public NotToBeClassmatesException() {}
	public String NotToBeClassmatesMessage() {
		return "Young Children cannot make classmates.";
	}
}