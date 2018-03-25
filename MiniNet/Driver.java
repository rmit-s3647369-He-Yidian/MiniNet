/**
 * 
 */
package MiniNet;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * @author s3647369_Yidian_He
 *
 */
public class Driver {
	
	/**
	 * @param args
	 */	
	private int num = -1;  										//the selected person's profile
	private static ArrayList<Persons> ps = new ArrayList <Persons> (); //store persons object	
	private final int listSize = 500;  								//size of the net to store the persons	
	
	//ensure the person is in the net and save the number represent the person object
	public boolean checkIfExist(String inputName) {
		num = -1;
		for(int i=0; i<ps.size(); i++) {
			/**the selected person's name can be found*/
			if(ps.get(i).getName().equals(inputName)) {
				num = i;  //save the number represent the person object
				break;
			}
		}
		if(num == -1) {
			return false;
		}else {
			return true;
		}				
	}	
	public void ListAllPersons() {
		System.out.println("__________________________");
		for(int i=0; i<ps.size(); i++) {
			System.out.println(ps.get(i).getName());
		}
	}	
	public void addPersonsProfile() {	
		if(ps.size() == listSize) {							/* the list size is full */	
			System.out.println("The MiniNet is full, you cannot add more profile.");
			return; //back to menu
		}		
		System.out.println("Please enter person's detail" + "\n>>>Name M/F Age");   
		System.out.print(">>>");
		String line = (new Scanner(System.in)).nextLine();	
		String[] record = line.split("\\s");
		
		if(checkIfExist(record[0]) == true) {						/* the person is already exist */		
			System.out.println("The person's name is already in the MiniNet, please try another.");
			return; //back to menu
		}	
		try {
			if(Integer.parseInt(record[2]) > 16 ) {					/* the person become an adult */		
				ps.add(new Adults(record[0], record[1].charAt(0), Integer.parseInt(record[2])));
				System.out.println("The person's profile has been recorded!");		
			}			
			else if(Integer.parseInt(record[2]) <= 16) {				/* the person become a dependent */		
				int fatherProfile = -1, motherProfile = -1;
				System.out.println("The person is a dependent, please enter his/her parents' names.");			
				System.out.print("Father's name: ");
				String addFather = (new Scanner(System.in)).nextLine();		
				checkIfExist(addFather);
				if(checkIfExist(addFather) == false) {				/* the father is not exist*/		
					System.out.println("The father's name is not in the MiniNet, please add father's profile first!");
					return; //back to menu
				}
				fatherProfile = num;										
				System.out.print("Mother's name: ");	
				String addMother = (new Scanner(System.in)).nextLine();	
				checkIfExist(addMother);	
				if(checkIfExist(addMother) == false) {				/* the mother is not exist*/			
					System.out.println("The mother's name is not in the MiniNet, please add mother's profile first!");
					return; //back to menu
				}
				motherProfile = num;		
				/* the parents are not couples and one of them already has a spouse*/
				if((Couples.hasSpouse((Adults)ps.get(fatherProfile))==true || Couples.hasSpouse((Adults)ps.get(motherProfile))==true)
							&& Couples.isCouple((Adults)ps.get(fatherProfile), (Adults)ps.get(motherProfile))==false) {
					System.out.println("One of the adults already has a spouse. Fail to add the dependent's profile.");
					return;													
				}
				ps.add(new Dependents(record[0], record[1].charAt(0), Integer.parseInt(record[2]), (Adults)ps.get(fatherProfile),(Adults)ps.get(motherProfile)));
				System.out.println("The person's profile has been recorded!");   
				/* the parents are not couples*/
				if(Couples.hasSpouse((Adults)ps.get(fatherProfile))==false && Couples.hasSpouse((Adults)ps.get(motherProfile))==false
							&& Couples.isCouple((Adults)ps.get(fatherProfile), (Adults)ps.get(motherProfile))==false) {					
					Couples.becomeCouple((Adults)ps.get(fatherProfile), (Adults)ps.get(motherProfile));
					System.out.println("The two parents become the couple.");
				}			
			}
		}catch(Exception e) {
			System.out.println("Wrong input format. Please try again.");
			return;
		}
	}	
	public void selectPerson() {
		System.out.print("Enter a person's name: ");
		String selectedName = new Scanner(System.in).nextLine();
		/*selected person is in the net*/
		if(checkIfExist(selectedName)==true) {							 
			todoWithPerson();								//then continue with the manage menu
		}else {
			System.out.println("Cannot find the person, please add the person's profile first.");
			menu();
		}		
	}
	public void viewProfile(Persons person) {
		String profile = "Image: %s\nName: %s\nGender: %c\nAge: %d";	//format of profile
		System.out.println("__________________________");
		System.out.println(String.format(profile, person.getImage(), person.getName(), 
				person.getGender(), person.getAge()));		
		/*if adults*/
		if(person instanceof Adults) {								//display status, spouse and children's name
			System.out.println("Status: " + ((Adults)person).getStatus());
			/*if the adult has spouse*/
			if(Couples.hasSpouse(person)==true) {						//display spouse's name
				System.out.println("Spouse: " + Couples.printSpouse(person));
			}
			String children = "";								//children's name
			for(int i=0; i<ps.size(); i++) {							
				/*if the object is a dependent, get parents' name*/
				if(ps.get(i) instanceof Dependents) {
					if(((Dependents) ps.get(i)).getFather().getName() == person.getName() 
							|| ((Dependents) ps.get(i)).getMother().getName() == person.getName()) {
						children = children.concat(ps.get(i).getName() + " | ");
					}								
				}	
			}
			System.out.println("Children: " + children);					//display children's name
		}
		/*if dependents*/
		if(person instanceof Dependents) {							//display parents' name
			System.out.println("Father: " + ((Dependents)person).getFather().getName() 
					+ "\nMother: " + ((Dependents)person).getMother().getName());		
		}	
		System.out.println("Friend List: " + Friendships.printFriendList(person));		//display friend list										
		System.out.println("__________________________");		
	}	
	//be invoked after selecting a valid person and can invoke£º	
	public void todoWithPerson() {	
		int todoChoice = -1;
		int selectedperson = num;
		Persons selectedPerson = ps.get(selectedperson);
		do {
			try {
				System.out.println("What do you want to do with the person?" 
									 + "\n=========================="
									 + "\n1. View profile"
									 + "\n2. Manage profile"
									 + "\n3. Manage relationships"
									 + "\n4. Remove from the MiniNet"
									 + "\n0. Go back"
									 + "\n==========================");
				System.out.print("Select a number: ");
				todoChoice = new Scanner(System.in).nextInt();	
				switch(todoChoice) {
					case 1: 
						viewProfile(selectedPerson);
						break;
					case 2: 	
						manageProfile();
						break;
					case 3: 	
						manageRelationships();	
						break;
					case 4: 	
						remove(selectedPerson);
						return;
					case 0: 					
						break;
					default:
						System.out.println("Invalid number, please enter again.");
						break;
				}
			}catch(Exception e) {
				System.out.println("Wrong input. Please enter an integer.");
			}
		}while(todoChoice!=0);	
	}
	public void manageRelationships() {
		int relationChoice = -1;								//choice variable
		String name = null;									//object of input name, ready for check if exist in the net
		Persons ps1 = ps.get(num);								//store the object of the selected person
		do {
			try {
				System.out.println("\n=====Manage Relationships====="
								 + "\n1. Add new friends		    "  
								 + "\n2. Remove one of the friends 	"						 
								 + "\n3. Become couples				"  
								 + "\n0. Go back					"  
								 + "\n==============================");
				System.out.print("Select a number: ");
				relationChoice = new Scanner(System.in).nextInt();	
				switch(relationChoice) {
					case 1: 												//add friends
						if(ps1.getAge()<=2) {					//ensure the selected person can have friends
							System.out.println("The dependent is " + ps1.getAge() + " years old.");
							System.out.println("A person who is 2 years old or younger does not have any friends.");	
							break;
						}
						System.out.println("Input friend's name: ");
						name = new Scanner(System.in).nextLine();	
						checkIfExist(name);
						if(checkIfExist(name)==true) {				//ensure the input person is in the net
							Friendships.becomeFriends(ps1, ps.get(num));
							break;
						}else{
							System.out.println("The name is not in the MiniNet, please add the person's profile first!");
							return;
						}
					case 2:		
						System.out.println("Input friend's name: ");
						name = new Scanner(System.in).nextLine();	
						checkIfExist(name);									//remove one of the friends
						if(checkIfExist(name)==true) {						//ensure the input person is in the net
							Friendships.removeOneFriend(ps1, ps.get(num));
							break;
						}else{
							System.out.println("The name is not in the MiniNet.");
							return;
						}
					case 3: 												//become couples
						if(ps1 instanceof Dependents) {						//ensure adults to have spouses
							System.out.println("A dependent cannot become couples.");	
							break;
						}
						System.out.println("Input spouse's name: ");		
						name = new Scanner(System.in).nextLine();	
						checkIfExist(name);							//ensure the input person is in the net
						if(checkIfExist(name)==true && ps1 instanceof Adults) {   
							Couples.becomeCouple(ps1, ps.get(num));
							break;
						}else{
							System.out.println("The name is not in the MiniNet, please add the person's profile first!");
							return;
						}
					case 0:
						return;
					default:												//give hint if a wrong input number
						System.out.println("Invalid number, please enter again.");
						break;
				}
			}catch(Exception e) {
				System.out.println("Wrong input. Please enter an integer.");
			}
		}while(relationChoice!=0);
		todoWithPerson();
	}
	public void manageProfile() {
		int profileChoice = -1;
		String message = null;
		while(profileChoice!=0) {
			try {
				System.out.println("\n=====Profile======"
							  	 + "\n1. Change name	"  
							     + "\n2. Change age		" 
						 		 + "\n3. Update status	"  
						 		 + "\n4. Upload an image"
						 		 + "\n0. Go back		"  
						 		 + "\n==================");
				System.out.print("Select a number: ");
				profileChoice = new Scanner(System.in).nextInt();		
				switch(profileChoice) {
					case 1: 
						System.out.println("Input new name:");
						message = new Scanner(System.in).nextLine();
						ps.get(num).setName(message);
						System.out.println("Success!");
						break;
					case 2: 
						System.out.println("Input age:");
						message = new Scanner(System.in).nextLine();
						ps.get(num).setAge(Integer.parseInt(message));
						System.out.println("Success!");
						break;
					case 3: 
						if(ps.get(num) instanceof Adults) {
							System.out.println("Input new status:");
							message = new Scanner(System.in).nextLine();
							((Adults)ps.get(num)).setStatus(message);
							System.out.println("Success!");
						}else {
							System.out.println("Dependents do not have status.");
						}
						break;
					case 4: 	
						System.out.println("Press Enter to upload an image.");
						message = ps.get(num).getName() + ".jpg";
						ps.get(num).setImage(message);
						System.out.println("Success!");
						break;
					case 0:	
						return;					
					default:
						System.out.println("Invalid number, please enter again.");
						break;
				}
			}catch(Exception e) {
				System.out.println("Wrong input. Please enter an integer.");
			}
		}
		todoWithPerson();
	}
	public void menu() {
		int firstChoice = -1;					
		do {
			try {
				System.out.println("\n=======MiniNet Menu======="
							     + "\n1. List all persons		" 
								 + "\n2. Add a new person		"  
								 + "\n3. Select a person by name"  
								 + "\n4. Check for friendships	"  
								 + "\n5. Exit					"  
								 + "\n==========================");
				System.out.print("Select a number: ");
				firstChoice = new Scanner(System.in).nextInt();
				switch(firstChoice) {
					case 1: 
						ListAllPersons();
						break;
					case 2: 
						addPersonsProfile();
						break;
					case 3: 
						selectPerson();
						break;
					case 4: 
						String[] record = null;
						Persons p1; Persons p2;
						System.out.println("Please enter two names:" + "\n>>>Name1 Name2");   
						System.out.print(">>>");
						String line = (new Scanner(System.in)).nextLine();	
						record = line.split("\\s");							
						if(checkIfExist(record[0])==true) {
							p1 = ps.get(num);
						}else {
							System.out.println("Cannot find the persons.");
							break;
						}
						if(checkIfExist(record[1])==true) {
							p2 = ps.get(num);
						}else {
							System.out.println("Cannot find the persons.");
							break;
						}
						checkForFriendships(p1, p2);					
						break;
					case 5:
						System.out.println("You exit the system.");
						return;
					default:
						System.out.println("Invalid number, please enter again.");
						break;
				}	
			}catch(Exception e) {
				System.out.println("Wrong input. Please enter an integer.");
			}			
		}while(firstChoice!=5);		
	}
	//initialize some data to the MiniNet
	public void drive() {
		ps.add(new Adults("jack", 'M', 30));			
		ps.add(new Adults("jenny", 'F', 29));
		ps.add(new Adults("alex", 'M', 32));
		ps.add(new Adults("alice", 'F', 24));
		Couples.couple.add(new Couples(ps.get(0), ps.get(1)));
		Couples.couple.add(new Couples(ps.get(2), ps.get(3)));
		ps.add(new Dependents("jimmy", 'M', 7, (Adults)ps.get(0), (Adults)ps.get(1)));	
		ps.add(new Dependents("peter", 'M', 2, (Adults)ps.get(0), (Adults)ps.get(1)));	
		ps.add(new Dependents("henry", 'M', 14, (Adults)ps.get(2), (Adults)ps.get(3)));
		Friendships.friends.add(new Friendships(ps.get(0), ps.get(2)));
		menu();			
	}
	public void checkForFriendships(Persons ps1, Persons ps2) {
		/*the two persons are in the friendship connection*/
		if(Friendships.areFriends(ps1, ps2)) {
			System.out.println("They are friends.");
		}else {
			System.out.println("They are not friends");
		}
	}	
	public void remove(Persons rmvps) {
		boolean hasChildren = false;								//whether the person has children
		for(int i=0; i<ps.size(); i++) {							
			/*if the object is a dependent, get parents' name*/
			if(ps.get(i) instanceof Dependents) {
				/*the dependent's father or mother is the selected person*/
				if(((Dependents) ps.get(i)).getFather()==ps.get(num) || ((Dependents) ps.get(i)).getMother()==ps.get(num)) {
					hasChildren = true;						//the person has children
				}								
			}else {
				hasChildren = false;							//the person does not have children
			}
		}
		/*the person does not has children*/
		if(hasChildren==false) {
			ps.remove(rmvps);								//remove from the MiniNet
			Friendships.removeAllFriends(rmvps);						//remove all friends connection
			Couples.removeCouple(rmvps);							//remove couple connection
			System.out.println("Removed!");
		}else {			
			System.out.println("The person has children, so that cannot remove from the MiniNet.");
		}		
	}
	
}
