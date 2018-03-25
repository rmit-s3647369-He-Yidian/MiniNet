/**
 * 
 */
package MiniNet;


/**
 * @author s3655108_Yifan_Lao
 *
 */
class Dependents extends Persons{

	/**
	 * 
	 */
	private Adults father;
	private Adults mother;
	
	/**
	 * @param name
	 * @param gender
	 * @param age
	 */
	public Dependents(String name, char gender, int age, String image, Adults father,Adults mother) {
		super(name, gender, age, image);
		this.father = father;
		this.mother = mother;
	}
	public Dependents(String name, char gender, int age, Adults father, Adults mother) {
		this(name, gender, age, "", father, mother);
	}

	/**
	 * @return the father
	 */
	public Persons getFather() {
		return father;
	}
	/**
	 * @return the mother
	 */
	public Persons getMother() {
		return mother;
	}
	@Override
	public boolean canBecomeFriends(Persons ps1, Persons ps2) {
		//Constraints for dependents that can have friends
		if(ps2 instanceof Dependents && !ps1.getName().equals(ps2.getName()) && ps1.getAge() > 2 && ps2.getAge() > 2 
				&& Math.abs(ps1.getAge()-ps2.getAge()) <= 3 && ((Dependents) ps1).getFather()!=((Dependents) ps2).getFather()) {
			return true;
		}
		//object type constraint
		else if(ps2 instanceof Adults){
			System.out.println("A denpendent cannot become friends with an adult.");
			return false;
		}
		//name constraints
		else if(ps1.getName().equals(ps2.getName())){
			System.out.println("A person cannot make friends with himself/herself.");
			return false;
		}
		//age constraints
		else if(ps2.getAge() <= 2) {
			System.out.println("The dependent is " + ps2.getAge() + " years old.");
			System.out.println("A person who is 2 years old or younger does not have any friends.");
			return false;
		}
		//gap of age constraints
		else if((ps1.getAge()-ps2.getAge()) > 3 ) {
			System.out.println("The age difference between these two young friends cannot be more than 3 years.");
			System.out.println(ps1.getName() + " is " + (ps1.getAge()-ps2.getAge()) + " years older than " + ps2.getName());
			return false;
		}
		else if((ps1.getAge()-ps2.getAge()) < -3 ) {
			System.out.println("The age difference between these two young friends cannot be more than 3 years.");
			System.out.println(ps1.getName() + " is " + (ps2.getAge()-ps1.getAge()) + " years younger than " + ps2.getName());
			return false;
		}
		//constraints that only can make friends with dependents from other family
		else if(ps2 instanceof Dependents) {
			if(((Dependents) ps1).getFather()==((Dependents) ps2).getFather()) {
				System.out.println("The two dependent have the same parents.");
			}
			return false;
		}
		else {
			return false;
		}
	}
	
	


	

}
