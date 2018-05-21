/**
 * 
 */
package MiniNet_2;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import org.hsqldb.Server;
import java.util.Scanner;
import java.util.Set;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/**
 * @author s3647369_Yidian_He
 *
 */
public class Driver {
	
	String prt1 = ""; 
	String prt2 = "";
	Server hsqlServer = null;
	Connection con = null;
	ResultSet result = null;
	java.sql.Statement stmt = null;
	int effect = 0;
				
	public void importData() {			
		Scanner file = null;
		// Put relations from relations.txt to the relation mapping
		try { 
			file = new Scanner(new FileInputStream("relations.txt"));
			while (file.hasNextLine()) {
				String line_rl = file.nextLine();
				String[] s2 = line_rl.split(",\\s");
				Connections.map_r.put(new Connections(s2[0], s2[1]), s2[2]);
			}
		} catch (FileNotFoundException e) {
			System.out.println("Cannot find relations.txt");
			MainStage.pane.setBottom(new Bottom(new Text("Cannot find relations.txt")));
		} finally{
			file.close();
		}
		// Put adults to the person mapping
		try { 
			file = new Scanner(new FileInputStream("people.txt"));	
			while (file.hasNextLine()) {				
				String line_pf = file.nextLine();
				String[] s1 = line_pf.split(",\\s");
				String[] name = s1[0].split("\\s");
				s1[1] = s1[1].substring(1, s1[1].length()-1);
				s1[2] = s1[2].substring(1, s1[2].length()-1);
				if(Integer.parseInt(s1[4]) > 16) {
					addPs(name[0],name[1],s1[1],s1[2],s1[3].charAt(0),Integer.parseInt(s1[4]),s1[5]);}			
				else if(Integer.parseInt(s1[4]) <= 16) {		
					// Iterating in relations mapping to get parents' names
					Set<Entry<Connections,String>> set_r = Connections.map_r.entrySet();
					Iterator<Map.Entry<Connections,String>> itera_r_Entry = set_r.iterator();
					Map.Entry<Connections,String> map_r_Entry;
					while(itera_r_Entry.hasNext()){  						
						map_r_Entry = itera_r_Entry.next(); 
			            if((map_r_Entry.getKey().geta().equals(s1[0]) || map_r_Entry.getKey().getb().equals(s1[0]))
			            		&&map_r_Entry.getValue().equals("parent")) {
			            	if(map_r_Entry.getKey().geta().equals(s1[0]))
			            		prt1 = map_r_Entry.getKey().getb();
			            	else if(map_r_Entry.getKey().getb().equals(s1[0]))
			            		prt2 = map_r_Entry.getKey().geta(); }
			        }	
					addPs(name[0],name[1],s1[1],s1[2],s1[3].charAt(0),Integer.parseInt(s1[4]),s1[5]); }}
		}catch (FileNotFoundException e) {
				System.out.println("Cannot find people.txt");
				System.out.println("Trying to connect to database..");
				connectDB();
				MainStage.pane.setBottom(new Bottom(new Text("Cannot find people.txt\nThe program is connecting to the database.")));
		}finally {
			file.close();
		}		
	}	
	
	public void connectDB() {
		hsqlServer = new Server();
		hsqlServer.setLogWriter(null);
		hsqlServer.setSilent(true);
		hsqlServer.setDatabaseName(0, "TestDB");
		hsqlServer.setDatabasePath(0, "file:MYDB");
		hsqlServer.start();			
		try {
			//Registering the HSQLDB JDBC driver
			Class.forName("org.hsqldb.jdbc.JDBCDriver");
			//Creating the connection with HSQLDB
			con = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/testdb", "SA", "");
			if (con!= null){
				System.out.println("Connection created successfully.");			
//				cn = 1;
			}else{
				System.out.println("Problem with creating connection.");
				MainStage.pane.setBottom(new Bottom(new Text("Cannot find people.txt\nCannot connect to the database")));
			}	     
		} catch(Exception e) {
			e.printStackTrace(System.out);
		}				
		try {
			// Put adults into the mapping
            Class.forName("org.hsqldb.jdbc.JDBCDriver");
            con = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/testdb", "SA", "");
            stmt = con.createStatement();
            result = stmt.executeQuery("select * from people;");
			while(result.next()){
				if(result.getInt(6) > 16) 				
					addPs(result.getString(1), result.getString(2), result.getString(3), result.getString(4), result.getString(5).charAt(0), result.getInt(6), result.getString(7)); }
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
		try {
			// Put children or young children into the mapping
            Class.forName("org.hsqldb.jdbc.JDBCDriver");
            con = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/testdb", "SA", "");
            stmt = con.createStatement();
            result = stmt.executeQuery("select * from people;");
			while(result.next()){
				if(result.getInt(6) <= 16) {
					// Iterating in relations mapping to get parents' names
					Set<Entry<Connections,String>> set_r = Connections.map_r.entrySet();
					Iterator<Map.Entry<Connections,String>> itera_r_Entry = set_r.iterator();
					Map.Entry<Connections,String> map_r_Entry;
					while(itera_r_Entry.hasNext()){  						
						map_r_Entry = itera_r_Entry.next(); 
			            if((map_r_Entry.getKey().geta().equals(result.getString(1)+" "+result.getString(2)) || map_r_Entry.getKey().getb().equals(result.getString(1)+" "+result.getString(2)))
			            		&&map_r_Entry.getValue().equals("parent")) {
			            	if(map_r_Entry.getKey().geta().equals(result.getString(1)+" "+result.getString(2)))
			            		prt1 = map_r_Entry.getKey().getb();
			            	else if(map_r_Entry.getKey().getb().equals(result.getString(1)+" "+result.getString(2)))
			            		prt2 = map_r_Entry.getKey().geta(); }
			        }					
					addPs(result.getString(1), result.getString(2), result.getString(3), result.getString(4), result.getString(5).charAt(0), result.getInt(6), result.getString(7));			
				}
			}
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }		
		// end of stub code for in/out stub		
	}
	
	public void list() {	
		GridPane pslist = new GridPane();
		int i = 0;
		// Check for all people in the file
		for(Object o: Person.map_ps.values()) {			
			pslist.setAlignment(Pos.CENTER);
			pslist.setPadding(new Insets(15, 15, 15, 15));
			pslist.setVgap(5.5);
			// Place Person into the pane
			Button b = new Button(" " + ((Person)o).getFirstName() + " " + ((Person)o).getLastName());
			b.setPrefSize(200,30);
			b.setStyle("-fx-font: 12 Arial; -fx-background-color: #FFFFFF; -fx-background-radius: 0; -fx-border-radius: 0;");
			b.setOnMouseEntered(MouseEvent -> { 
				b.setStyle("-fx-font: 12 Arial; -fx-background-color: pink; -fx-background-radius: 0; -fx-border-radius: 0;");
			});
			b.setOnMouseExited(MouseEvent -> { 
				b.setStyle("-fx-font: 12 Arial; -fx-background-color: #FFFFFF; -fx-background-radius: 0; -fx-border-radius: 0;");
			});
			b.setOnMouseClicked(MouseEvent -> { 
				System.out.println("Viewed "+((Person)o).getFirstName() + " " + ((Person)o).getLastName()+" 's profile");
				viewProfile((Person)o); 
			});
			pslist.add(b,0,i);
			i++;	
		}	
		// Place node to the scroll pane
		MainStage.sp.setVbarPolicy(ScrollBarPolicy.ALWAYS);
		MainStage.sp.setPrefWidth(540);
		MainStage.sp.setPrefHeight(395);
		MainStage.sp.setContent(pslist);
		MainStage.sp.setFitToWidth(true);
        // Place text on the top
        Text tx = new Text("- Click to view profile -");
		tx.setFont(Font.font("Arial", FontWeight.BOLD, 16));	
		// Place scroll pane to the pane
		MainStage.pane.setTop(new Top(tx));
		MainStage.pane.setCenter(MainStage.sp);
	}
	
	public void addPs(String fn, String ln, String im, String stu, char g, int a, String sta) {
		//put adult into the person mapping	
		if(a > 16) {			
			Person.map_ps.put(fn+" "+ln, new Adult(fn, ln, im, stu, g, a, sta));
			System.out.println(fn+" "+ln+" has been put into the mapping as an adult.");
		}else {
			try {
			// Show error message of missing parents and cannot add to person mapping		
			if(prt1.equals("") || prt2.equals(""))
				throw new NoParentException(fn+" "+ln);
			// Put child into the person mapping
			else if(a > 2 && a <=16) {
				Person.map_ps.put(fn+" "+ln, new Child(fn, ln, im, stu, g, a, sta, prt1, prt2));
				System.out.println(fn+" "+ln+" has been put into the mapping as a child."); }
			// Put young child into the person mapping
			else if(a <= 2) {
				Person.map_ps.put(fn+" "+ln, new YoungChild(fn, ln, im, stu, g, a, sta, prt1, prt2));
				System.out.println(fn+" "+ln+" has been put into the mapping as a young child."); }
			}catch(NoParentException e) {
				System.out.println(e.getNoParentMessage());
			}
		}		 
	}
	
	public void checkForNewPerson() {
		MainStage.addPersonPane();		
		int approvedProfile = 1;
		// Check for input name
		String input_name = MainStage.tf1.getText().trim()+" "+MainStage.tf2.getText().trim();
		String a = MainStage.tf_prt1.getText(); String b = MainStage.tf_prt2.getText();
		prt1 = MainStage.tf_prt1.getText(); prt2 = MainStage.tf_prt2.getText();
		if(Person.map_ps.containsKey(input_name)) {
			Text n_result1 = new Text("duplicated name, try anothor");
			n_result1.setFill(Color.RED);
			MainStage.agrid.add(n_result1, 1, 2);	
			approvedProfile = 0; }
		else if(input_name.trim().equals("") || MainStage.tf1.getText().trim().equals("") 
				|| MainStage.tf2.getText().trim().equals("")) {
			Text n_result2 = new Text("please enter full name");
			MainStage.agrid.add(n_result2, 1, 2);
			n_result2.setFill(Color.RED);
			approvedProfile = 0; }
		// Check for gender
		char input_gender=' ';
		if(MainStage.m.isSelected()) {
			MainStage.m.setSelected(true);
			input_gender = 'M'; }
		else if(MainStage.f.isSelected()) {
			MainStage.f.setSelected(true);
			input_gender = 'F'; }
		else if(input_gender==' ') {	
			Text g_result = new Text("please select gender");
			MainStage.agrid.add(g_result, 1, 5);
			g_result.setFill(Color.RED);
			approvedProfile = 0; }	
		// Check for age
		int input_age = 0;
		try {
			input_age = Integer.parseInt(MainStage.tf3.getText().trim());
			if(input_age < 0 || input_age > 150) {		
				Text a_result = new Text("enter an integer from 0 to 150");
				a_result.setFill(Color.RED);
				MainStage.agrid.add(a_result, 1, 7);
				approvedProfile = 0;  // All the information entered for adding a new person is approved
			}else if(input_age > 0 && input_age <= 16) {
				MainStage.agrid.add(new Label("Parent 1:"), 0, 13);
				MainStage.agrid.add(MainStage.tf_prt1, 1, 13);
				MainStage.agrid.add(new Label("Parent 2:"), 0, 15);
				MainStage.agrid.add(MainStage.tf_prt2, 1, 15);
				// Check for parent 1						
				if(a.equals("")) {
					Text prt1_result = new Text("please enter parent's name");
					prt1_result.setFill(Color.RED);
					MainStage.agrid.add(prt1_result, 1, 14);
					MainStage.pane.setBottom(new Text(""));
					approvedProfile = 0;
				}else if(!Person.map_ps.containsKey(a)) {
					Text prt1_result = new Text("the person is not exist");
					prt1_result.setFill(Color.RED);
					MainStage.agrid.add(prt1_result, 1, 14);
					approvedProfile = 0; }
				// Check for parent 2
				if(b.equals("")) {
					Text prt2_result = new Text("please enter parent's name");
					prt2_result.setFill(Color.RED);
					MainStage.agrid.add(prt2_result, 1, 16);
					MainStage.pane.setBottom(new Text(""));
					approvedProfile = 0;
				}else if(!Person.map_ps.containsKey(b)) {
					Text prt2_result = new Text("the person is not exist");
					prt2_result.setFill(Color.RED);
					MainStage.agrid.add(prt2_result, 1, 16);
					approvedProfile = 0; }
				// Check for two parents
				if(Adult.couple.areCouple(a, b))
					approvedProfile = 1;
				else
					Adult.couple.becomeCouple(a, b);						
			}
		}catch(NumberFormatException e2) {
			System.out.println("Throws NumberFormatException");
			Text a_result = new Text("enter an integer from 0 to 150");
			a_result.setFill(Color.RED);
			MainStage.agrid.add(a_result, 1, 7);
			approvedProfile = 0;
		}catch(NoAvailableException e) {
			approvedProfile = 0;
			if(Adult.couple.areCouple(a,b)){				
				MainStage.pane.setBottom(new Bottom(new Text(e.getAlreadyCoupleMessage())));
			}else if((Adult.couple.hasSpouse(a) && Adult.couple.getSpouse(a)!=b)
					||(Adult.couple.hasSpouse(b) && Adult.couple.getSpouse(b)!=a)) {
				MainStage.pane.setBottom(new Bottom(new Text(e.getHasSpouseMessage()))); }	
		}catch (NotToBeCoupledException e) {
			approvedProfile = 0;
			MainStage.pane.setBottom(new Bottom(new Text(e.NotToBeCoupledMessage(a))));
		}		
		// Check for status
		String input_status = null;
		if(MainStage.tf4.getText().trim().equals("")) input_status = "";
		else input_status = MainStage.tf4.getText().trim();
		// Check for state
		String input_state = "";
		input_state = MainStage.cb.getValue();				
		if(MainStage.cb.getValue()==null) {		
			Text sta_result = new Text("please select state");
			sta_result.setFill(Color.RED);
			MainStage.agrid.add(sta_result, 1, 11);
			approvedProfile = 0; }
		// Check for image
		String upload_image = null;
		if(MainStage.checkBox.isSelected()) upload_image = " "+MainStage.tf1.getText().trim()+".jpg";
		else upload_image = "";
		// Show result if failed to save
		MainStage.pane.setCenter(MainStage.sp);	
		// Put the new person into the person mapping if can save
		if(approvedProfile==1) {				
			addPs(MainStage.tf1.getText().trim(), MainStage.tf2.getText().trim(), upload_image, 
					input_status, input_gender, input_age, input_state);
			// Writing to the database if is connecting to it
			if(con!=null) {
				try {
					Class.forName("org.hsqldb.jdbc.JDBCDriver");
					con = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/testdb", "SA", "");
					stmt = con.createStatement();						
					effect = stmt.executeUpdate("insert into people values ('"+MainStage.tf1.getText().trim()+"', '"+MainStage.tf2.getText().trim()+"', '"+upload_image+"', '"+input_status+"', '"+input_gender+"', "+input_age+", '"+input_state+"');");
					con.commit();
					System.out.println(effect + " rows effected");
				    System.out.println("Rows inserted successfully");					
				}  catch (Exception e) {
					e.printStackTrace(System.out);
				}
			}
			// Put new parent relation into the mapping 
			if(input_age <= 16)
				Person.family.addParent(input_name,a,b);
			if(!Adult.couple.areCouple(a, b))	
				Connections.map_r.put(new Connections(a, b), "couple"); 									
			MainStage.pane.setTop(new Top(new Text("Success!")));
			MainStage.pane.setBottom(new Bottom(new Text(""))); 
			Button newAdd = new Button("Add another");
			newAdd.setAlignment(Pos.CENTER);
			MainStage.pane.setCenter(newAdd);
			newAdd.setOnAction(e3 -> {
				MainStage.pane.setTop(new Top(new Text("- Enter the person's profile -")));
				MainStage.clearInput();
				MainStage.addPersonPane();
			});
		}
	}
	
	public void viewProfile(Person p) {	
		// Create a pane to display the profile
		GridPane profile = new GridPane();
		profile.getChildren().clear();
		profile.setAlignment(Pos.TOP_CENTER);
		profile.setPadding(new Insets(15, 15, 15, 15));
		profile.setHgap(5.5);
		profile.setVgap(5.5);
		// Add node to the profile pane
		Text tp = new Text("- " + p.getFirstName() + "'s Profile -");
		tp.setFont(Font.font("Arial", FontWeight.BOLD, 16));
		Rectangle r = new Rectangle();
		r.setWidth(100);
		r.setHeight(30);
		r.setFill(Color.WHITE);
		r.setStroke(Color.DARKGREY);
		profile.add(r, 1, 0);		
		String image = " "+p.getImage();	
		if(image.equals(" "))
			profile.add(new Text(" no image"), 1, 0);
		else
			profile.add(new Text(image), 1, 0); 		
		profile.add(new Label("Name:"), 0, 1);
		profile.add(new Text(p.getFirstName() + " " + p.getLastName()), 1, 1);
		profile.add(new Label("Gender:"), 0, 2);
		profile.add(new Text(new Character(p.getGender()).toString()), 1, 2);
		profile.add(new Label("Age:"), 0, 3);
		profile.add(new Text(new Integer(p.getAge()).toString()), 1, 3);
		profile.add(new Label("Status:"), 0, 4);
		profile.add(new Text(p.getStatus()), 1, 4);
		profile.add(new Label("State:"), 0, 5);
		profile.add(new Text(p.getState()), 1, 5);
		// Display the person's family members
		if(p instanceof Adult) {
			profile.add(new Label("Spouse:"), 0, 6);
			Adult.couple.hasSpouse(p.getFirstName()+" "+p.getLastName());
			profile.add(new Text(Adult.couple.getSpouse(p.getFirstName()+" "+p.getLastName())), 1, 6);
			profile.add(new Label("Children:"), 0, 7);
			String c = "";
			for(String child: Person.family.getChildren(p.getFirstName()+" "+p.getLastName()))
				c = c.concat(child+" | ");
			profile.add(new Text(c), 1, 7);
		}	
		if(p instanceof Child) {
			profile.add(new Label("Parents:"), 0, 6);
			profile.add(new Text(Person.family.getParent(p.getFirstName()+" "+p.getLastName())), 1, 6);	
			profile.add(new Label("Sibling:"), 0, 7);
			profile.add(new Text(Person.family.getSibling(p.getFirstName()+" "+p.getLastName())), 1, 7);
		}
		// Set profile pane to the border pane
		MainStage.pane.setTop(new Top(tp));
		MainStage.pane.setCenter(profile);		
		// Display options to do with the person
		HBox option = new HBox();
		option.setSpacing(0);
		option.setPadding(new Insets(0,0,0,0));
		option.setAlignment(Pos.CENTER);
		Button delete = new Button("Delete\nthe person");
		delete.setPrefSize(65,30);
		delete.setStyle("-fx-font: 10 Arial; -fx-base: #DC143C; -fx-background-radius: 0; -fx-border-radius: 0;");
		Button view = new Button("View\nProfile");
		view.setPrefSize(65,30);
		view.setStyle("-fx-font: 10 Arial; -fx-base: #000080; -fx-background-radius: 0; -fx-border-radius: 0;");
		Button update = new Button("Update\nProfile");
		update.setPrefSize(65,30);
		update.setStyle("-fx-font: 10 Arial; -fx-base: #000080; -fx-background-radius: 0; -fx-border-radius: 0;");
		Button connection = new Button("Social\nConnection");
		connection.setPrefSize(65,30);
		connection.setStyle("-fx-font: 10 Arial; -fx-base: #000080; -fx-background-radius: 0; -fx-border-radius: 0;");
		Button back =new Button("Go Back");
		back.setPrefSize(65,30);
		back.setStyle("-fx-font: 10 Arial; -fx-base: #000080; -fx-background-radius: 0; -fx-border-radius: 0;");
		option.getChildren().addAll(delete,view,update,connection,back);
		delete.setOnAction(e -> { delete(p); });
		view.setOnAction(e -> { viewProfile(p); });
		update.setOnAction(e -> { updateProfile(p); });
		connection.setOnAction(e -> { displayConnection(p); });
		back.setOnAction(e -> {
			MainStage.pane.setBottom(new Bottom(new Text("")));
			list();
		});
		// Place HBox on the bottom of the pane
		MainStage.pane.setBottom(option);
	}
	
	public void updateProfile(Person p) {
		MainStage.pane.setTop(new Top(new Text("- Update "+p.getFirstName()+" "+p.getLastName()+"'s profile -")));
		// Create a Grid pane to let user modify the profile
		GridPane update = new GridPane();
		update.getChildren().clear();
		update.setAlignment(Pos.TOP_CENTER);
		update.setPadding(new Insets(15, 15, 15, 15));
		update.setHgap(5.5); update.setVgap(5.5);	
		// Place nodes to the pane
		update.add(new Label("Name:"), 0, 1);
		update.add(new Text(p.getFirstName() + " " + p.getLastName()), 1, 1);
		update.add(new Label("Gender:"), 0, 2);
		update.add(new Text(new Character(p.getGender()).toString()), 1, 2);
		update.add(new Label("Age:"), 0, 3);
		update.add(new Text(new Integer(p.getAge()).toString()), 1, 3);
		update.add(new Label("Status:"), 0, 4);
		update.add(MainStage.tf4, 1, 4);
		MainStage.tf4.setText(p.getStatus());
		update.add(new Label("State:"), 0, 5);
		for(String sta: MainStage.st)
			if(sta.equals(p.getState().trim()))				
				MainStage.cb.setValue(sta);;
		update.add(MainStage.cb, 1, 5);		
		update.add(new Label("Image:"), 0, 6);
		TextField f = new TextField();
		f.setText(p.getImage());
		update.add(f, 1, 6);				
		// Create a button to save the updates
		Button sv = new Button("save");
		update.add(sv, 1, 8);
		// Set on button action
		sv.setOnAction(e -> {
			p.setStatus(MainStage.tf4.getText().trim());					
			p.setState(MainStage.cb.getValue());
			p.setImage(f.getText().trim());
			Text saved = new Text("New changes have been saved");
			saved.setFill(Color.RED);
			update.add(saved, 1, 9);
			System.out.println("Updated profiles of "+p.getFirstName()+" "+p.getLastName());
			// Updating the database
			if(con!=null) {
				try {	
					Class.forName("org.hsqldb.jdbc.JDBCDriver");
					con = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/testdb", "SA", "");
					stmt = con.createStatement();						
					effect = stmt.executeUpdate("update people set status='"+MainStage.tf4.getText().trim()+"where lastname='"+p.getLastName()+"';"
							+ "update people set state='"+MainStage.cb.getValue()+"where lasttname='"+p.getLastName()+"';"
							+ "update people set image='"+f.getText().trim()+"where lastname='"+p.getLastName()+"';");
					System.out.println(effect + " rows effected");
				    System.out.println("Rows updated successfully");			
				} catch (SQLException e1) {
					System.out.println("Errors in updating the database.");
				} catch (ClassNotFoundException e2) {
					System.out.println("Throw ClassNotFoundException");
				}
			}
		});
		// Place the grid pane to the border pane
		MainStage.pane.setCenter(update);
	}
	
	public void displayConnection(Person p) {
		MainStage.pane.setTop(new Top(new Text("- "+p.getFirstName()+" "+p.getLastName()+"'s Social Connections -")));
		// Create a Grid pane to display the person's connections
		GridPane cn = new GridPane();
		cn.getChildren().clear();
		cn.setAlignment(Pos.TOP_CENTER);
		cn.setPadding(new Insets(15, 15, 15, 15));
		cn.setHgap(20); cn.setVgap(5.5);
		// Place node into the pane	
		// Display friends, colleagues and classmates list
		String s = p.getFirstName()+" "+p.getLastName();		
		if(p instanceof Adult) {
			Text fr = new Text("Friends");
			fr.setFont(Font.font("Arial", FontWeight.BOLD, 12));	
			cn.add(fr, 0, 0);
			Text co = new Text("Colleagues");
			co.setFont(Font.font("Arial", FontWeight.BOLD, 12));	
			cn.add(co, 1, 0);
			Text cl = new Text("Classmates");
			cl.setFont(Font.font("Arial", FontWeight.BOLD, 12));	
			cn.add(cl, 2, 0);
			for(int i=0;i<Person.friend.getFriendList(s).size();i++)		
				cn.add(new Text(Person.friend.getFriendList(s).get(i)), 0, i+1); 
			for(int i=0;i<Adult.colleague.getColleagueList(s).size();i++)
				cn.add(new Text(Adult.colleague.getColleagueList(s).get(i)), 1, i+1); 
			for(int i=0;i<Person.classmate.getClassmateList(s).size();i++)
				cn.add(new Text(Person.classmate.getClassmateList(s).get(i)), 2, i+1); }	
		if(p instanceof YoungChild) {
			cn.add(new Text("Young child has no social connections"), 0, 0);
		}else if(p instanceof Child&&!(p instanceof YoungChild)) {
			Text fr = new Text("Friends");
			fr.setFont(Font.font("Arial", FontWeight.BOLD, 12));	
			cn.add(fr, 0, 0);
			Text cl = new Text("Classmates");
			cl.setFont(Font.font("Arial", FontWeight.BOLD, 12));	
			cn.add(cl, 1, 0);
			for(int i=0;i<Person.friend.getFriendList(s).size();i++)
				cn.add(new Text(Person.friend.getFriendList(s).get(i)), 0, i+1); 
			for(int i=0;i<Person.classmate.getClassmateList(s).size();i++)
				cn.add(new Text(Person.classmate.getClassmateList(s).get(i)), 1, i+1); }
		// Place node to the scroll pane
		MainStage.sp.setVbarPolicy(ScrollBarPolicy.ALWAYS);
		MainStage.sp.setPrefWidth(540); MainStage.sp.setPrefHeight(250);
		MainStage.sp.setContent(cn);
		MainStage.sp.setFitToWidth(true);
		// Place node to the center of the pane
		MainStage.pane.setCenter(MainStage.sp);
	}
	
	public void delete(Person p) {
		// Check whether the adult have children
		if(p instanceof Adult && !Person.family.getChildren(p.getFirstName()+" "+p.getLastName()).isEmpty()) {						
			Text fail = new Text("The person has children.\nFail to remove from the MiniNet!");
			fail.setFill(Color.RED);
			MainStage.pane.setTop(new Top(fail));	
			System.out.println("Fail to delete the person: "+p.getFirstName()+" "+p.getLastName());
		}else {
			// Remove the person and the relations			
			Person.map_ps.remove(p.getFirstName()+" "+p.getLastName());
			Person.friend.removeFriend(p.getFirstName()+" "+p.getLastName());
			Person.family.removeParent(p.getFirstName()+" "+p.getLastName());
			Person.classmate.removeClassmate(p.getFirstName()+" "+p.getLastName());
			Adult.couple.removeCouple(p.getFirstName()+" "+p.getLastName());
			Adult.colleague.removeColleague(p.getFirstName()+" "+p.getLastName());
			Button bk = new Button("Back to List");
			bk.setAlignment(Pos.CENTER);
			bk.setOnAction(e -> { list(); });
			MainStage.pane.setTop(new Top(new Text("The person has been removed.")));
			MainStage.pane.setCenter(bk);
			MainStage.pane.setBottom(new Bottom(new Text(""))); 
			System.out.println(p.getFirstName()+" "+p.getLastName()+" has been removed.");
			if(con!=null) {
				try {	
					Class.forName("org.hsqldb.jdbc.JDBCDriver");
					con = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/testdb", "SA", "");
					stmt = con.createStatement();						
					effect = stmt.executeUpdate("delete from people where lastname='"+p.getLastName()+"';");
					System.out.println(effect + " rows effected");			
				} catch (SQLException e1) {
					System.out.println("Errors in updating the database.");
					e1.printStackTrace(System.out);
				} catch (ClassNotFoundException e2) {
					System.out.println("Throw ClassNotFoundException");
				}
			}
		}		
	}
	
	public void manageRelation() {
		String a = MainStage.mtf1.getText().trim();
		String b = MainStage.mtf2.getText().trim();
		try {			
			// Names cannot be empty
			if(a.equals("") || b.equals(""))
				MainStage.pane.setBottom(new Bottom(new Text("Names cannot be empty!")));
			// Relation cannot be empty
			else if(MainStage.mcb.getValue()==null)
				MainStage.pane.setBottom(new Bottom(new Text("Please select relation!")));		
			// Names should be exist
			else if(!Person.map_ps.containsKey(a) || !Person.map_ps.containsKey(b))
				MainStage.pane.setBottom(new Bottom(new Text("The people should be exist in the MiniNet!")));
			// Start of managing relation
			else if(MainStage.mcb.getValue().equals("couple")) 			 				
				Adult.couple.becomeCouple(a, b); 
			else if(MainStage.mcb.getValue().equals("friends")) 
				Person.friend.becomeFriend(a, b); 
			else if(MainStage.mcb.getValue().equals("colleague")) 
				Adult.colleague.becomeColleague(a, b); 
			else if(MainStage.mcb.getValue().equals("classmate")) 
				Person.classmate.becomeClassmate(a, b); 	
		} catch(NoAvailableException e) {
			if(Adult.couple.areCouple(a, b)){
				MainStage.pane.setBottom(new Bottom(new Text(e.getAlreadyCoupleMessage())));
			}else if((Adult.couple.hasSpouse(a) && Adult.couple.getSpouse(a)!=b)
					||(Adult.couple.hasSpouse(b) && Adult.couple.getSpouse(b)!=a)) {
				MainStage.pane.setBottom(new Bottom(new Text(e.getHasSpouseMessage()))); }	
		} catch (NotToBeCoupledException e) {
			MainStage.pane.setBottom(new Bottom(new Text(e.NotToBeCoupledMessage(a))));
		} catch(NotToBeClassmatesException e) {
			System.out.println("Throw NotToBeClassmatesException");
			MainStage.pane.setBottom(new Bottom(new Text(e.NotToBeClassmatesMessage())));
		} catch(NotToBeColleaguesException e) {
			System.out.println("Throw NotToBeColleaguesException");
			MainStage.pane.setBottom(new Bottom(new Text(e.NotToBeColleaguesMessage())));
		} catch(TooYoungException e) {
			System.out.println("Throw TooYoungException");
			MainStage.pane.setBottom(new Bottom(new Text(e.getTooYoungMessage())));
		} catch (NotToBeFriendsException e) {
			System.out.println("Throw NotToBeFriendsException");	
			MainStage.pane.setBottom(new Bottom(new Text(e.getNotToBeFriendsMessage())));			
		}	
	}			

	public void checkRelation() {
		// Create a grid pane to allow user enter persons' profile
		GridPane cgrid = new GridPane();
		cgrid.setAlignment(Pos.CENTER);
		cgrid.setPadding(new Insets(15, 15, 15, 15));
		cgrid.setHgap(5.5);
		cgrid.setVgap(5.5);
		// Place nodes in the pane
		cgrid.add(new Label("1st Person:"), 0, 0);
		TextField tf1 = new TextField();
		cgrid.add(tf1, 1, 0);
		cgrid.add(new Label("2nd Person:"), 0, 1);
		TextField tf2 = new TextField();
		cgrid.add(tf2, 1, 1);
		Button btc = new Button("Check");			
		cgrid.add(btc, 1, 4);	
		// Place grid pane in the center
	    MainStage.pane.setCenter(cgrid);
		// Set on button action 
		btc.setOnAction(e -> {
			Person p1 = null;
			Person p2 = null;
			String a = tf1.getText().trim();
			String b = tf2.getText().trim();
			String c = null;
			if(a.compareTo(b) > 0) {
				c = a;
				a = b;
				b = c; }
			// Names cannot be empty
			if(a.equals("") || b.equals(""))
				MainStage.pane.setBottom(new Bottom(new Text("Names cannot be empty!")));	
			// Names should be exist
			if(!Person.map_ps.containsKey(a) || !Person.map_ps.containsKey(b))
				MainStage.pane.setBottom(new Bottom(new Text("The people should be exist in the MiniNet!")));
			// Find out the person's object
			Set<Entry<String,Person>> set_ps = Person.map_ps.entrySet();
			Iterator<Map.Entry<String,Person>> itera_ps_Entry = set_ps.iterator();
			while(itera_ps_Entry.hasNext()) {
				Map.Entry<String,Person> map_ps_Entry = itera_ps_Entry.next(); 
				if(map_ps_Entry.getKey().equals(a))
					p1 =  map_ps_Entry.getValue();
				else if(map_ps_Entry.getKey().equals(b))		
					p2 =  map_ps_Entry.getValue(); 
			}
			// Check for the relation of two people			
			if(Person.friend.areFriends(a, b))
				MainStage.pane.setBottom(new Bottom(new Text("They are friends.")));
			else if(Adult.couple.areCouple(a, b))
				MainStage.pane.setBottom(new Bottom(new Text("They are couple.")));
			else if(Person.classmate.areClassmate(a, b))
				MainStage.pane.setBottom(new Bottom(new Text("They are classmates.")));			
			else if(p1 instanceof Child && p2 instanceof Child) {
				if(Person.family.areSibling(a, b))
					MainStage.pane.setBottom(new Bottom(new Text("They are siblings.")));				
				else
					MainStage.pane.setBottom(new Bottom(new Text("They are not connected."))); }
			else if(p1 instanceof Child && p2 instanceof Adult) {
				if(((Child)p1).getParent1().equals(b)||((Child)p1).getParent2().equals(b))
					MainStage.pane.setBottom(new Bottom(new Text(b+" is the parent of "+a)));
				else
					MainStage.pane.setBottom(new Bottom(new Text("They are not connected."))); }
			else if(p2 instanceof Child && p1 instanceof Adult) {
				if(((Child)p2).getParent1().equals(a)||((Child)p2).getParent2().equals(a))
					MainStage.pane.setBottom(new Bottom(new Text(a+" is the parent of "+b)));
				else
					MainStage.pane.setBottom(new Bottom(new Text("They are not connected."))); }
			else if(p1 instanceof Adult && p2 instanceof Adult) {				
				if(Adult.colleague.areColleague(a, b))
					MainStage.pane.setBottom(new Bottom(new Text("They are colleagues.")));
				else
					MainStage.pane.setBottom(new Bottom(new Text("They are not connected."))); }
			else
				MainStage.pane.setBottom(new Bottom(new Text("They are not connected.")));				
		});		
	}
		
}

 
