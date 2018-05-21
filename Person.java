/**
 * 
 */
package MiniNet_2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javafx.scene.text.Text;

/**
 * @author s3647369_Yidian_He
 *
 */
abstract class Person {

	/**
	 * @param args
	 */
	private String firstname;
	private String lastname;
	private String image;
	private String status;
	private char gender;
	private int age;	
	private String state;
	static Map<String,Person> map_ps = new HashMap<String,Person>();  // Create mapping of person, K:name, V:profile
	
	/**
	 * @return the firstname
	 */
	public String getFirstName() {
		return firstname;
	}
	/**
	 * @param name the firstname to set
	 */
	public void setFirstName(String firstname) {
		this.firstname = firstname;
	}
	/**
	 * @return the lastname
	 */
	public String getLastName() {
		return lastname;
	}
	/**
	 * @param name the lastname to set
	 */
	public void setLastName(String lastname) {
		this.lastname = lastname;
	}
	/**
	 * @return the gender
	 */
	public char getGender() {
		return gender;
	}
	/**
	 * @param gender the gender to set
	 */
	public void setGender(char gender) {
		this.gender = gender;
	}
	/**
	 * @return the age
	 */
	public int getAge() {
		return age;
	}
	/**
	 * @param age the age to set
	 */
	public void setAge(int age) {
		this.age = age;
	}
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * @return the states
	 */
	public String getState() {
		return state;
	}
	/**
	 * @param states the states to set
	 */
	public void setState(String state) {
		this.state = state;
	}
	/**
	 * @return the image
	 */
	public String getImage() {
		return image;
	}
	/**
	 * @param image the image to set
	 */
	public void setImage(String image) {
		this.image = image;
	}
	/**
	 * @param name
	 * @param gender
	 * @param age
	 * @param status
	 */
	public Person(String firstname, String lastname, String image, String status, char gender, int age, String state) {
		this.firstname = firstname;
		this.lastname = lastname;
		this.gender = gender;
		this.age = age;
		this.status = status;
		this.state = state;
		this.image = image;
	}
	
	public static Family family = new  Family() {
		private String fp1 = "";
		private String fp2 = "";
		@Override
		// Put parent relation into the mapping
		public void addParent(String s, String a, String b) {
			if(a.compareTo(s) < 0) 
				Connections.map_r.put(new Connections(a, s), "parent"); 
			else if(a.compareTo(s) > 0) 
				Connections.map_r.put(new Connections(s, a), "parent");
			if(b.compareTo(s) < 0) 
				Connections.map_r.put(new Connections(b, s), "parent");
			else if(b.compareTo(s) > 0) 
				Connections.map_r.put(new Connections(s, b), "parent");
			System.out.println("Put new parent relations");
		}
		@Override
		// Return two parents' names
		public String getParent(String s) {
			Person p = null;
			Set<Entry<String,Person>> set_ps = Person.map_ps.entrySet();
			Iterator<Map.Entry<String,Person>> itera_ps_Entry = set_ps.iterator();
			while(itera_ps_Entry.hasNext()) {
				Map.Entry<String,Person> map_ps_Entry = itera_ps_Entry.next();
				if(map_ps_Entry.getKey().equals(s)) {
					p = (Child) map_ps_Entry.getValue(); 
					break; }				
			}
			fp1 = ((Child)p).getParent1();
			fp2 = ((Child)p).getParent2();
			return ((Child)p).getParent1()+" & "+((Child)p).getParent2();
		}
		@Override
		// Return a list of children's names
		public ArrayList<String> getChildren(String s) {
			ArrayList<String> children = new ArrayList<String>();
			Set<Entry<Connections,String>> set_r = Connections.map_r.entrySet();
			Iterator<Map.Entry<Connections,String>> itera_r_Entry = set_r.iterator();
			Map.Entry<Connections,String> map_r_Entry;
			while(itera_r_Entry.hasNext()){  						
				map_r_Entry = itera_r_Entry.next(); 
				if(map_r_Entry.getKey().geta().equals(s) && map_r_Entry.getValue().equals("parent")) {
					children.add(map_r_Entry.getKey().getb()); }
				else if(map_r_Entry.getKey().getb().equals(s) && map_r_Entry.getValue().equals("parent")) {
					children.add(map_r_Entry.getKey().geta()); }
			}
			return children;
		}
		@Override
		// Return a String of siblings' names
		public String getSibling(String s) {
			String str = "";
			getParent(s);
			Set<Entry<String,Person>> set_ps = Person.map_ps.entrySet();
			Iterator<Map.Entry<String,Person>> itera_ps_Entry = set_ps.iterator();
			while(itera_ps_Entry.hasNext()) {
				Map.Entry<String,Person> map_ps_Entry = itera_ps_Entry.next();
				if(map_ps_Entry.getValue() instanceof Child && !(map_ps_Entry.getKey().equals(s))) {
					String p11 = ((Child)map_ps_Entry.getValue()).getParent1(); 
					String p22 = ((Child)map_ps_Entry.getValue()).getParent2();
					if((fp1.equals(p11)&&fp2.equals(p22)) || (fp1.equals(p22)&&fp2.equals(p11)))
						str = str.concat(map_ps_Entry.getKey()+" | "); }}
			return str;
		}
		@Override
		// Check if the two children are sibling
		public boolean areSibling(String a, String b) {
			int i = 0;
			getParent(a);
			Set<Entry<String,Person>> set_ps = Person.map_ps.entrySet();
			Iterator<Map.Entry<String,Person>> itera_ps_Entry = set_ps.iterator();
			while(itera_ps_Entry.hasNext()) {
				Map.Entry<String,Person> map_ps_Entry = itera_ps_Entry.next();
				if(map_ps_Entry.getKey().equals(b)) {
					String p11 = ((Child)map_ps_Entry.getValue()).getParent1(); 
					String p22 = ((Child)map_ps_Entry.getValue()).getParent2();
					if((fp1.equals(p11)&&fp2.equals(p22)) || (fp1.equals(p22)&&fp2.equals(p11))) {
						i = 1;
						break; }}}
			if(i==1)
				return true;  // The two people are sibling
			else
				return false;  //The two people are not sibling
		}
		@Override
		// Remove parent relations
		public void removeParent(String a) {
			Set<Entry<Connections,String>> set_r = Connections.map_r.entrySet();
			Iterator<Map.Entry<Connections,String>> itera_r_Entry = set_r.iterator();
			while(itera_r_Entry.hasNext()){  						
				Map.Entry<Connections,String> map_r_Entry = itera_r_Entry.next(); 						
	            if(map_r_Entry.getKey().geta().equals(a) || map_r_Entry.getKey().getb().equals(a) 
	            		&& map_r_Entry.getValue().equals("parent"))
	            	itera_r_Entry.remove(); 
	        }
		}		
	};
	
 	public static Classmate classmate = new Classmate() {
		@Override
		// Put the classmate relation into the mapping
		public void becomeClassmate(String a, String b) throws NotToBeClassmatesException {
			Person p1 = null;
			Person p2 = null;
			String c = null;
			if(a.compareTo(b) > 0) {
				c = a;
				a = b;
				b = c; }
			// Iterate to get the person object
			Set<Entry<String,Person>> set_ps = Person.map_ps.entrySet();
			Iterator<Map.Entry<String,Person>> itera_ps_Entry = set_ps.iterator();
			while(itera_ps_Entry.hasNext()) {
				Map.Entry<String,Person> map_ps_Entry = itera_ps_Entry.next(); 
				if((map_ps_Entry.getKey().equals(a) || map_ps_Entry.getKey().equals(b)) && p1==null)
					p1 = map_ps_Entry.getValue();
				else if((map_ps_Entry.getKey().equals(a) || map_ps_Entry.getKey().equals(b)) && p1!=null)
					p2 = map_ps_Entry.getValue(); }
			// Check for the two people			
			if(p1 instanceof YoungChild || p2 instanceof YoungChild) {
				throw new NotToBeClassmatesException();
			}else {
				System.out.println("Put a new classmate relation");
				Connections.map_r.put(new Connections(a, b), "classmate"); 
				MainStage.pane.setBottom(new Bottom(new Text("Success!")));
			}			
		}

		@Override
		// Check if the two people are classmates
		public boolean areClassmate(String a, String b) {
			int i = -1;
			String c = null;
			if(a.compareTo(b) > 0) {
				c = a;
				a = b;
				b = c; }
			Set<Entry<Connections,String>> set_r = Connections.map_r.entrySet();
			Iterator<Map.Entry<Connections,String>> itera_r_Entry = set_r.iterator();
			while(itera_r_Entry.hasNext()){  						
				Map.Entry<Connections,String> map_r_Entry = itera_r_Entry.next(); 						
	            if(map_r_Entry.getKey().geta().equals(a) && map_r_Entry.getKey().getb().equals(b) 
	            		&& map_r_Entry.getValue().equals("classmate"))
	            	i = 0; }
			if(i == -1) 
				return false;  // The two people are not classmates
			else
				return true;  // The two people are classmates
		}

		@Override
		// Remove the classmate relation from the mapping
		public void removeClassmate(String a) {
			Set<Entry<Connections,String>> set_r = Connections.map_r.entrySet();
			Iterator<Map.Entry<Connections,String>> itera_r_Entry = set_r.iterator();
			while(itera_r_Entry.hasNext()){  						
				Map.Entry<Connections,String> map_r_Entry = itera_r_Entry.next(); 						
	            if(map_r_Entry.getKey().geta().equals(a) || map_r_Entry.getKey().getb().equals(a) 
	            		&& map_r_Entry.getValue().equals("classmate"))
	            	itera_r_Entry.remove(); 
	        }			
		}

		@Override
		// Return a list of classmates' names
		public ArrayList<String> getClassmateList(String s) {
			ArrayList<String> classmates = new ArrayList<String>();
			Set<Entry<Connections,String>> set_r = Connections.map_r.entrySet();
			Iterator<Map.Entry<Connections,String>> itera_r_Entry = set_r.iterator();
			Map.Entry<Connections,String> map_r_Entry;
			while(itera_r_Entry.hasNext()){  						
				map_r_Entry = itera_r_Entry.next(); 
				if(map_r_Entry.getKey().geta().equals(s) && map_r_Entry.getValue().equals("classmate")) {
					classmates.add(map_r_Entry.getKey().getb()); }
				else if(map_r_Entry.getKey().getb().equals(s) && map_r_Entry.getValue().equals("classmate")) {
					classmates.add(map_r_Entry.getKey().geta()); }
			}
			return classmates;
		}
	};
	
	public static Friend friend = new Friend() {
			
		@Override
		// Check if the two people are friends
		public boolean areFriends(String a, String b) {
			int i = -1;
			String c = null;
			if(a.compareTo(b) > 0) {
				c = a;
				a = b;
				b = c; }
			Set<Entry<Connections,String>> set_r = Connections.map_r.entrySet();
			Iterator<Map.Entry<Connections,String>> itera_r_Entry = set_r.iterator();
			while(itera_r_Entry.hasNext()){  						
				Map.Entry<Connections,String> map_r_Entry = itera_r_Entry.next(); 						
	            if(map_r_Entry.getKey().geta().equals(a) && map_r_Entry.getKey().getb().equals(b) 
	            		&& map_r_Entry.getValue().equals("friends"))
	            	i = 0; }			
			if(i == -1) 
				return false;  // The two people are not friends
			else
				return true;  // The two people are friends
		}
		
		@Override
		// Put the friends relation into the mapping
		public void becomeFriend(String a, String b) throws TooYoungException, NotToBeFriendsException {
			String c = null;
			Person p1 = null;
			Person p2 = null;
			if(a.compareTo(b) > 0) {
				c = a;
				a = b;
				b = c; }
			// Iterate to get the person object
			Set<Entry<String,Person>> set_ps = Person.map_ps.entrySet();
			Iterator<Map.Entry<String,Person>> itera_ps_Entry = set_ps.iterator();
			while(itera_ps_Entry.hasNext()) {
				Map.Entry<String,Person> map_ps_Entry = itera_ps_Entry.next(); 
				if((map_ps_Entry.getKey().equals(a) || map_ps_Entry.getKey().equals(b)) && p1==null)
					p1 = map_ps_Entry.getValue();
				else if((map_ps_Entry.getKey().equals(a) || map_ps_Entry.getKey().equals(b)) && p1!=null)
					p2 = map_ps_Entry.getValue(); }
			// check for the two person to become friends
			if(friend.areFriends(a, b)) {
				MainStage.pane.setBottom(new Bottom(new Text("They are already friends."))); }
			else if(p1 instanceof YoungChild || p2 instanceof YoungChild) {
				throw new TooYoungException(); }
			else if((p1 instanceof Adult && p2 instanceof Child)||(p2 instanceof Adult && p1 instanceof Child)) {
				throw new NotToBeFriendsException(); }
			else if(p1 instanceof Child && p2 instanceof Child) { 
				if(Person.family.areSibling(a, b)) {
					throw new NotToBeFriendsException(); }
				else if(Math.abs(p1.getAge()-p2.getAge()) > 3) {
					throw new NotToBeFriendsException();  }	}	
			else {		
				System.out.println("Put a new friends relation");
				Connections.map_r.put(new Connections(a, b), "friends"); 
				MainStage.pane.setBottom(new Bottom(new Text("Success!"))); }	
		}
		
		@Override
		// Remove the friends relation from the mapping
		public void removeFriend(String a) {
			Set<Entry<Connections,String>> set_r = Connections.map_r.entrySet();
			Iterator<Map.Entry<Connections,String>> itera_r_Entry = set_r.iterator();
			while(itera_r_Entry.hasNext()){  						
				Map.Entry<Connections,String> map_r_Entry = itera_r_Entry.next(); 						
	            if(map_r_Entry.getKey().geta().equals(a) || map_r_Entry.getKey().getb().equals(a) 
	            		&& map_r_Entry.getValue().equals("friends"))
	            	itera_r_Entry.remove(); 
	        }
		}

		@Override
		// Return a list of friends' names
		public ArrayList<String> getFriendList(String s) {
			ArrayList<String> friends = new ArrayList<String>();
			Set<Entry<Connections,String>> set_r = Connections.map_r.entrySet();
			Iterator<Map.Entry<Connections,String>> itera_r_Entry = set_r.iterator();
			Map.Entry<Connections,String> map_r_Entry;
			while(itera_r_Entry.hasNext()){  						
				map_r_Entry = itera_r_Entry.next(); 
				if(map_r_Entry.getKey().geta().equals(s) && map_r_Entry.getValue().equals("friends")) {
					friends.add(map_r_Entry.getKey().getb()); }
				else if(map_r_Entry.getKey().getb().equals(s) && map_r_Entry.getValue().equals("friends")) {
					friends.add(map_r_Entry.getKey().geta()); }
			}
			return friends;
		}
	};
	
}
