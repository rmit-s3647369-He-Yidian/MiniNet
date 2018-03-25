/**
 * 
 */
package MiniNet;

import java.util.ArrayList;

/**
 * @author s3647369_Yidian_He
 *
 */
public class Couples {
	
	private Persons adult1;
	private Persons adult2;
	protected static ArrayList<Couples> couple = new ArrayList <Couples> ();
	private static String spouseName = null;
	/**
	 * @return the adult1
	 */
	public Persons getAdult1() {
		return adult1;
	}
	/**
	 * @return the adult2
	 */
	public Persons getAdult2() {
		return adult2;
	}
	/**
	 * @param couple1
	 * @param couple2
	 */
	public Couples(Persons couple1, Persons couple2) {
		this.adult1 = couple1;
		this.adult2 = couple2;
	}
	public static boolean hasSpouse(Persons personHasSpouse) {
		int spouseProfile = -1;	
		for(int i=0; i<couple.size(); i++) {
			/* the person is in an object, and parse another person's name to spouseName */
			if(personHasSpouse==couple.get(i).getAdult1()) {				
				spouseProfile = i;
				spouseName = couple.get(i).getAdult2().getName();
				break;
			}else if(personHasSpouse==couple.get(i).getAdult2()) {
				spouseProfile = i;
				spouseName = couple.get(i).getAdult1().getName();
				break;
			}
		}
		if(spouseProfile == -1) {
			return false;  //the person does not have a spouse
		}else {			
			return true;   //the person has a spouse
		}
	}
	public static String printSpouse(Persons spouse) {
		return spouseName;
	}
	public static boolean isCouple(Persons adlt1, Persons adlt2) {
		int cp = -1;
		for(int i=0; i<couple.size(); i++) {
			if((adlt1==couple.get(i).getAdult1() && adlt2==couple.get(i).getAdult2()) 
					|| (adlt1==couple.get(i).getAdult2() && adlt1==couple.get(i).getAdult1()) ) {
				cp = i;
				break;
			}
		}
		if(cp==-1) {
			return false;
		}else {
			return true;
		}
	}
	public static void becomeCouple(Persons couple1, Persons couple2) {		
		if(isCouple(couple1, couple2)==true) {							 /*the two persons are both in an object*/
			System.out.println("They are already couples.");
		}else if(hasSpouse(couple1)==true || hasSpouse(couple2)==true) { /*one of the person is in an object*/
			System.out.println("One of the person already has a spouse. They cannot be couples.");
		}else {															 /*the object is added*/
			couple.add(new Couples(couple1, couple2));
			System.out.println("Success!");
		}		
	}
	public static void removeCouple(Persons ps) {
		for(int i=0; i<couple.size(); i++) {
			/*the person has a spouse*/
			if(couple.get(i).getAdult1()==ps || couple.get(i).getAdult2()==ps) {
				couple.remove(couple.get(i));   //the couple connection removed
			}
		}
	}
	
	
}
