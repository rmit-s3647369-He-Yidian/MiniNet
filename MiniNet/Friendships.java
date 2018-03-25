/**
 * 
 */
package MiniNet;

import java.util.ArrayList;

/**
 * @author s3647369_Yidian_He
 *
 */
public class Friendships {
	
	private Persons person1;
	private Persons person2;
	protected static ArrayList<Friendships> friends = new ArrayList <Friendships> ();
	/**
	 * @return the person1
	 */
	public Persons getPerson1() {
		return person1;
	}
	/**
	 * @return the person2
	 */
	public Persons getPerson2() {
		return person2;
	}
	/**
	 * @param person1
	 * @param person2
	 */
	public Friendships(Persons person1, Persons person2) {
		this.person1 = person1;
		this.person2 = person2;
	}
	public static void becomeFriends(Persons ps1, Persons ps2) {
		/* the first person can have friends and the two persons are not couples */
		if(ps1.canBecomeFriends(ps1, ps2)==true && Couples.isCouple(ps1, ps2)==false) {
			friends.add(new Friendships(ps1, ps2));
			System.out.println("Success!");
		}
		else {
			System.out.println("They cannot become friends.");
		}
	}
	public static boolean areFriends(Persons ps1, Persons ps2) {
		int fp = -1;
		for(int i=0; i<friends.size(); i++) {
			/* the two persons are both in an object of friendlist array */
			if((ps1==friends.get(i).getPerson1() && ps2==friends.get(i).getPerson2()) 
					|| (ps1==friends.get(i).getPerson2() && ps2==friends.get(i).getPerson1()) ) {
				fp = i;
				break; 		//find the connection and end the loop
			}
		}
		if(fp==-1) {
			return false;	//the two persons are not connected in the friendlist
		}else {
			return true;	//the two persons are connected
		}
	}
	public static String printFriendList(Persons ps) {
		String friendList = "";
		for(int i=0; i<friends.size(); i++) {
			/* the person is in the friendships connection list */
			if(ps==friends.get(i).getPerson1()) {
				friendList = friendList.concat(friends.get(i).getPerson2().getName() + " | ");
			}else if(ps==friends.get(i).getPerson2()) {
				friendList = friendList.concat(friends.get(i).getPerson1().getName() + " | ");
			}
		}
		return friendList;
	}
	public static void removeOneFriend(Persons ps1, Persons ps2) {	
		/*if the two persons are friends, start to remove the connection*/
		if(areFriends(ps1, ps2)==true) {						
			for(int i=0; i<friends.size(); i++) {
				if((ps1==friends.get(i).getPerson1() && ps2==friends.get(i).getPerson2())
						|| (ps1==friends.get(i).getPerson2() && ps2==friends.get(i).getPerson1())) {
					friends.remove(friends.get(i));			   //the connection can be removed
					System.out.println("Removed!");
				}
			}
		}else {												    
			System.out.println("They are not friends.");       //they are not friends, fail to remove
		}		
	}
	public static void removeAllFriends(Persons ps) {
		for(int i=0; i<friends.size(); i++) {
			/*the person is in one of the connection*/
			if((ps==friends.get(i).getPerson1() || ps==friends.get(i).getPerson2())) {
				friends.remove(friends.get(i));    //the connection is removed
			}
		}
	}
	
	
	
}
