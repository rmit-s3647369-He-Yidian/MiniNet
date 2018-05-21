/**
 * 
 */
package MiniNet_2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * @author s3647369_Yidian_He
 *
 */

interface Family {
	public void addParent(String s, String a, String b);
	public String getParent(String s);
	public ArrayList<String> getChildren(String s);
	public String getSibling(String s);
	public boolean areSibling(String a, String b);
	public void removeParent(String a);
}

interface Friend {
	public boolean areFriends(String a, String b);
	public void becomeFriend(String a, String b) throws TooYoungException, NotToBeFriendsException;
	public ArrayList<String> getFriendList(String s);
	public void removeFriend(String a);
}

interface Colleague {
	public void becomeColleague(String a, String b) throws NotToBeColleaguesException;
	public boolean areColleague(String a, String b);
	public ArrayList<String> getColleagueList(String s);
	public void removeColleague(String a);
}

interface Classmate {
	public void becomeClassmate(String a, String b) throws NotToBeClassmatesException;
	public boolean areClassmate(String a, String b);
	public ArrayList<String> getClassmateList(String s);
	public void removeClassmate(String a);
}

interface Couple {
	public boolean areCouple(String a, String b);
	public boolean hasSpouse(String s);
	public void becomeCouple(String a, String b) throws NotToBeCoupledException, NoAvailableException;
	public String getSpouse(String s);
	public void removeCouple(String a);
}

class Connections {
	private String a = null;
	private String b = null;
	// Create a mapping of relations,K:two people,V:relation
	static Map<Connections,String> map_r = new HashMap<Connections,String>();  
	public Connections(String a, String b) { this.a=a; this.b=b; }
	public String geta() { return a; }
	public String getb() { return b; }	
}
