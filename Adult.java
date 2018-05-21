/**
 * 
 */
package MiniNet_2;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javafx.scene.text.Text;

import java.util.Map.Entry;

/**
 * @author s3647369_Yidian_He
 *
 */
public class Adult extends Person {
	
	public Adult(String firstname, String lastname, String image, String status, char gender, int age, String state) {
		super(firstname, lastname, image, status, gender, age, state);
	}
	
	public static Colleague colleague = new Colleague() {
		@Override
		// Put thee colleague relation into the mapping
		public void becomeColleague(String a, String b) throws NotToBeColleaguesException{
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
			if(p1 instanceof Child || p2 instanceof Child) {
				throw new NotToBeColleaguesException();
			}else {
				System.out.println("Put a new colleague relation");
				Connections.map_r.put(new Connections(a, b), "colleague"); 
				MainStage.pane.setBottom(new Bottom(new Text("Success!")));
			}
		}

		@Override
		// Check if the two people are colleagues
		public boolean areColleague(String a, String b) {
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
	            		&& map_r_Entry.getValue().equals("colleague"))
	            	i = 0; }
			if(i == -1) 
				return false;
			else
				return true;		
		}

		@Override
		// Remove the relation from the mapping
		public void removeColleague(String a) {
			Set<Entry<Connections,String>> set_r = Connections.map_r.entrySet();
			Iterator<Map.Entry<Connections,String>> itera_r_Entry = set_r.iterator();
			while(itera_r_Entry.hasNext()){  						
				Map.Entry<Connections,String> map_r_Entry = itera_r_Entry.next(); 						
	            if(map_r_Entry.getKey().geta().equals(a) || map_r_Entry.getKey().getb().equals(a) 
	            		&& map_r_Entry.getValue().equals("colleague"))
	            	itera_r_Entry.remove(); 
	        }		
		}

		@Override
		// Return a list of colleagues' names of the person
		public ArrayList<String> getColleagueList(String s) {
			ArrayList<String> colleagues = new ArrayList<String>();
			Set<Entry<Connections,String>> set_r = Connections.map_r.entrySet();
			Iterator<Map.Entry<Connections,String>> itera_r_Entry = set_r.iterator();
			Map.Entry<Connections,String> map_r_Entry;
			while(itera_r_Entry.hasNext()){  						
				map_r_Entry = itera_r_Entry.next(); 
				if(map_r_Entry.getKey().geta().equals(s) && map_r_Entry.getValue().equals("colleague")) {
					colleagues.add(map_r_Entry.getKey().getb()); }
				else if(map_r_Entry.getKey().getb().equals(s) && map_r_Entry.getValue().equals("colleague")) {
					colleagues.add(map_r_Entry.getKey().geta()); }
			}
			return colleagues;
		}		
	};
	
	public static Couple couple = new Couple() {
		String spouse = null;	
		@Override
		// Check if the two people are couple
		public boolean areCouple(String a, String b) { 
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
	            		&& map_r_Entry.getValue().equals("couple"))
	            	i = 0; }
			if(i == -1) 
				return false;
			else
				return true;
		}
		@Override
		// Check if the person has a spouse
		public boolean hasSpouse(String s) {
			int i = -1;
			Set<Entry<Connections,String>> set_r = Connections.map_r.entrySet();
			Iterator<Map.Entry<Connections,String>> itera_r_Entry = set_r.iterator();
			while(itera_r_Entry.hasNext()){  						
				Map.Entry<Connections,String> map_r_Entry = itera_r_Entry.next(); 						
	            if(map_r_Entry.getValue().equals("couple") && map_r_Entry.getKey().geta().equals(s)) {
	            	i = 0;
	            	spouse = map_r_Entry.getKey().getb(); 
	            	break; }          	   
	            else if(map_r_Entry.getValue().equals("couple") && map_r_Entry.getKey().getb().equals(s)) {
	            	i = 0;
	            	spouse = map_r_Entry.getKey().geta(); 
	            	break; }
	            else {
	            	spouse = null; }
	        }
			if(i == -1) 
				return false;
			else
				return true;		
		}
		@Override
		// Put the couple relation into the mapping
		public void becomeCouple(String a, String b) throws NotToBeCoupledException, NoAvailableException {
			// If at least one person's name is not exist or missing names			
			if(MainStage.tf_prt1.getText().trim().equals("") || MainStage.tf_prt2.getText().trim().equals(""))
				MainStage.pane.setBottom(new Bottom(new Text("")));			
			if(!Person.map_ps.containsKey(a) || !Person.map_ps.containsKey(b))
				MainStage.pane.setBottom(new Bottom(new Text("The two person should be exist in the MiniNet.")));
			// If at least one member is not an adult 
			else if(!(Person.map_ps.get(a) instanceof Adult) || !(Person.map_ps.get(b) instanceof Adult)) {
				System.out.println("Throw NotToBeCoupledException");
				throw new NotToBeCoupledException(a); }			
			// If at least one of them is already connected with another adult as a couple
			else if((hasSpouse(a) && getSpouse(a)!=b) || (hasSpouse(b) && getSpouse(b)!=a)) {
				System.out.println("Throw NoAvailableException");
				throw new NoAvailableException(); }
			// Else if the two people are already have connected as couple
			else if(areCouple(a, b)) {	
				throw new NoAvailableException(); }
			// Connect the two people as couple
			else {
				System.out.println("Put new couple relation.");
				MainStage.pane.setBottom(new Bottom(new Text("Success!")));
				if(a.compareTo(b) < 0)
					Connections.map_r.put(new Connections(a,b), "couple");
				else
					Connections.map_r.put(new Connections(b,a), "couple"); }
		}
		@Override
		// Return the person's spouse name
		public String getSpouse(String s) { return spouse; }
		@Override
		public void removeCouple(String a) {
			Set<Entry<Connections,String>> set_r = Connections.map_r.entrySet();
			Iterator<Map.Entry<Connections,String>> itera_r_Entry = set_r.iterator();
			while(itera_r_Entry.hasNext()){  						
				Map.Entry<Connections,String> map_r_Entry = itera_r_Entry.next(); 						
	            if(map_r_Entry.getKey().geta().equals(a) || map_r_Entry.getKey().getb().equals(a) 
	            		&& map_r_Entry.getValue().equals("couple"))
	            	itera_r_Entry.remove(); 
	        }
		}	
	};
	
}
