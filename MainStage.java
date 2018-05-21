package MiniNet_2;

import java.sql.SQLException;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * @author s3647369_Yidian_He
 *
 */
public class MainStage {
	static String button_style = "-fx-font: 12 Arial; -fx-base: #B0C4DE; -fx-background-radius: 0; -fx-border-radius: 0;";
	static BorderPane pane = new BorderPane();  // Create a border pane as the body of the scene
	static ScrollPane sp = new ScrollPane();  // Create a scroll pane to show list of people	
	static GridPane agrid = new GridPane();  // Create a grid pane to add a new person
	static Button btAdd = new Button("Continue to save");
	static TextField tf1 = new TextField();  // Enter First name
	static TextField tf2 = new TextField();  // Enter Last name
	static TextField tf3 = new TextField();  // Enter Age
	static TextField tf4 = new TextField();  // Enter Status
	static RadioButton m = new RadioButton();  // Gender: male
	static RadioButton f = new RadioButton();  // Gender: female
	static ComboBox<String> cb = new ComboBox<String>();  // Select State
	static CheckBox checkBox = new CheckBox("upload image");  // Select to upload an image
	static TextField tf_prt1 = new TextField();  // Enter the first parent name
	static TextField tf_prt2 = new TextField();  // Enter the second parent name
	static TextField mtf1 = new TextField();  // Enter the first name to manage relation
	static TextField mtf2 = new TextField();  // Enter the second name to manage relation
	static ComboBox<String> mcb = new ComboBox<String>(); // Relation choice for managing relation
	static String[] st = { "ACT", "NSW", "NT", "QLD", "SA", "TAS", "VIC", "WA" }; 
	Driver Driver = new Driver();
	
	public void mainStage(Stage primaryStage) {	
		Driver.importData();
		// Place initial nodes in the border pane
		Text t = new Text("Click on the above buttons.");
		pane.setTop(new Top(t));
		t.setFont(Font.font("Arial", 12));
		// Create a HBox
		HBox hbox = new HBox();
		hbox.setSpacing(0);
		hbox.setPadding(new Insets(0,0,0,0));								
		// Place nodes in the HBox
		Button guide = new Button("Guide");	
		guide.setPrefSize(60,40);	
		guide.setStyle(button_style);
		Button list = new Button("List\nAll People");
		list.setPrefSize(80,40);
		list.setStyle(button_style);
		Button addPerson = new Button("Add\nNew People");
		addPerson.setPrefSize(90,40);
		addPerson.setStyle(button_style);
		Button relationship = new Button("Define\nRelationships");
		relationship.setPrefSize(100,40);
		relationship.setStyle(button_style);
		Button check = new Button("Find out\nRelationships");
		check.setPrefSize(100,40);
		check.setStyle(button_style);
		Button exit = new Button("Exit");
		exit.setPrefSize(60,40);
		exit.setStyle("-fx-font: 12 Arial; -fx-base: #FFE4E1; -fx-background-radius: 0; -fx-border-radius: 0;");
		hbox.getChildren().add(guide);
		hbox.getChildren().add(list);
		hbox.getChildren().add(addPerson);
		hbox.getChildren().add(relationship);
		hbox.getChildren().add(check);
		hbox.getChildren().add(exit);								       
		// Create a VBox
		VBox vbox = new VBox();
		vbox.setSpacing(5);
		vbox.setPadding(new Insets(0,0,0,0));	
		vbox.getChildren().add(hbox);
		vbox.getChildren().add(pane);					
		// Create a scene and place it in the stage		
		Scene scene = new Scene(vbox, 490, 520);
		primaryStage.setTitle("MiniNet"); // Set the stage title
		primaryStage.setScene(scene); // Place the scene in the stage
		primaryStage.setMaxWidth(490); // Set the Maximum width of stage
		primaryStage.setMinWidth(200); // Set the Minimum width of stage
		primaryStage.setMinHeight(200); // Set the Minimum height of stage
		primaryStage.show(); // Display the stage
		
		// Create <Guide> button handlers
		guide.setOnAction(e -> {	
			pane.setBottom(new Bottom(new Text("")));
            System.out.println("Click on <Guide> button");
            Text welcome = new Text("- Welcome -");
            Text guideText = new Text("\n   Here are some tips for using the Mininet application:"
            		+ "\n\n - SELECT a person in <List All Persons> to see the person's"
            		+ "\n   PROFILE / FAMILY MEMBERS / SOCIAL CONNECTIONS."
            		+ "\n\n - Click on <Add new Persons> to ADD a new person into the network."
            		+ "\n\n - You can DELETE / UPDATE a person in the person's profile interface."
            		+ "\n\n - You can CONNECT two persons in a certain way in <Define Relationships>."
            		+ "\n\n - Also, you can Find OUT the RELATIONS between the two persons\n   in <Find out Relationships>.");
            guideText.setFont(Font.font("Arial", 12));
            pane.setTop(new Top(welcome));
            pane.setCenter(guideText);
        });
		// Create <List All Persons> button handlers
		list.setOnAction(e -> {
			pane.setBottom(new Bottom(new Text("")));
			System.out.println("Click on <List All Persons> button");			
			Driver.list();						
		});		
		// Define states for user to choose			
		cb.getItems().addAll(st);
		// Create <Add new Persons> button handlers
		addPerson.setOnAction(e -> {
			System.out.println("Click on <Add new Persons> button");
			pane.setTop(new Top(new Text("- Enter the person's profile -")));	
			MainStage.clearInput();
			MainStage.addPersonPane();					
			// Create <save> button handlers
			MainStage.btAdd.setOnAction(e1 ->{	
				Driver.checkForNewPerson();
			});
		});
		// Create <Manage Relationships> button handlers
		String[] rl = { "friends", "couple", "colleague", "classmate" };
		mcb.getItems().addAll(rl);	
		relationship.setOnAction(e -> {
			pane.setBottom(new Bottom(new Text("")));
			System.out.println("Click on <Define Relationships> button");
			pane.setTop(new Top(new Text("- Connect two people -")));
			// Create a grid pane to allow user enter persons' profile
			GridPane rgrid = new GridPane();
			rgrid.setAlignment(Pos.CENTER);
			rgrid.setPadding(new Insets(15, 15, 15, 15));
			rgrid.setHgap(5.5);
			rgrid.setVgap(5.5);
			// Place nodes in the pane
			rgrid.add(new Label("1st Person:"), 0, 0);
			rgrid.add(mtf1, 1, 0);
			rgrid.add(new Label("2nd Person:"), 0, 1);			
			rgrid.add(mtf2, 1, 1);
			rgrid.add(new Label("Connect them as:"), 0, 2);														
			rgrid.add(mcb, 1, 2);
			Button btm = new Button("Save");
			rgrid.add(btm, 1, 4);
			// Place grid pane in the center
			MainStage.pane.setCenter(rgrid);	
			btm.setOnAction(e1 -> {	
				Driver.manageRelation();
			});			
			
		});
		// Create <Find out Relationships> button handlers
		check.setOnAction(e -> {
			pane.setBottom(new Bottom(new Text("")));
			System.out.println("Click on <Find out Relationships> button");	
			pane.setTop(new Top(new Text("- Enter names of two people -")));
			Driver.checkRelation();			
		});
		// Create <Exit> button handlers
		exit.setOnAction(e -> {
			pane.setBottom(new Bottom(new Text("")));
			System.out.println("Click on <Exit> button");
			System.out.println("Process finished.");
			if(Driver.con!=null) {
				try {
					System.out.println("Disconnect to the database.");
					Driver.stmt.close();
					Driver.result.close();
					Driver.con.close();
					Driver.hsqlServer.stop();
				} catch (SQLException e1) {
					e1.printStackTrace(System.out);
				}				
			}
			primaryStage.close();
		});
		// Listen for close window request
		primaryStage.setOnCloseRequest(WindowEvent -> {
			System.out.println("Window closed request.");
			System.out.println("Process finished.");
			if(Driver.con!=null) {
				try {
					System.out.println("Disconnect to the database.");
					Driver.stmt.close();
					Driver.result.close();
					Driver.con.close();
					Driver.hsqlServer.stop();
				} catch (SQLException e1) {
					e1.printStackTrace(System.out);
				}
			}
			primaryStage.close();
		});
		
	}
	
	public static void addPersonPane() {	
		MainStage.pane.setBottom(new Bottom(new Text("")));
		agrid.getChildren().clear();
		// Create a grid pane to allow user enter persons' profile		
		agrid.setAlignment(Pos.CENTER);
		agrid.setPadding(new Insets(15, 15, 15, 15));
		agrid.setHgap(5.5);
		agrid.setVgap(5.5);
		// Place nodes in the pane
		// Name result row:3
		agrid.add(new Label("First Name:"), 0, 0);						
		agrid.add(tf1, 1, 0);	
		agrid.add(new Label("Last Name:"), 0, 1);					
		agrid.add(tf2, 1, 1);
		// Gender result row:5
		agrid.add(new Label("Gender:"), 0, 3);
		ToggleGroup group = new ToggleGroup();		
		m.setText("Male");
		m.setToggleGroup(group);		
		f.setText("Female");
		f.setToggleGroup(group);			
		agrid.add(m, 1, 3);
		agrid.add(f, 1, 4);
		// Age result row:7
		agrid.add(new Label("Age:"), 0, 6);		
		tf3.setMaxWidth(50);			
		agrid.add(tf3, 1, 6);
		// Status result row:9
		agrid.add(new Label("Status:"), 0, 8);		
		agrid.add(tf4, 1, 8);
		// State result row:11
		agrid.add(new Label("State:"), 0, 10);						
		// Create the combo box							
		agrid.add(cb, 1, 10);
		// Choice for image
		agrid.add(new Label("Image:"), 0, 12);
		agrid.add(checkBox, 1, 12);		
		//  Create save button		
		agrid.add(btAdd, 1, 17);
		// Set of scroll pane
		MainStage.sp.setVbarPolicy(ScrollBarPolicy.ALWAYS);
		MainStage.sp.setPrefWidth(540);
		MainStage.sp.setPrefHeight(395);
		MainStage.sp.setContent(agrid);
		MainStage.sp.setFitToWidth(true);
		// Place grid pane in the center
		MainStage.pane.setCenter(MainStage.sp);
		// Save user input
		MainStage.tf1.setText(MainStage.tf1.getText().trim());
		MainStage.tf2.setText(MainStage.tf2.getText().trim());				
		MainStage.tf3.setText(MainStage.tf3.getText());
		MainStage.tf4.setText(MainStage.tf4.getText());	
		MainStage.tf_prt1.setText(MainStage.tf_prt1.getText().trim());
		MainStage.tf_prt2.setText(MainStage.tf_prt2.getText().trim());
	}
	
	public static void clearInput() {
		MainStage.tf1.setText("");
		MainStage.tf2.setText("");				
		MainStage.tf3.setText("");
		MainStage.tf4.setText("");	
		MainStage.tf_prt1.setText("");
		MainStage.tf_prt2.setText("");
		m.setSelected(false);
		f.setSelected(false);
		cb.setValue(null);
		checkBox.setSelected(false);
	}
		
}

//Define a custom pane to hold text on the top of the pane
class Top extends StackPane {
	public Top(Text title) {
		getChildren().add(title);
		setPadding(new Insets(5,0,5,0));
		title.setFont(Font.font("Arial", FontWeight.BOLD, 16));
	}
}

//Define a custom pane to hold text on the bottom of the pane
class Bottom extends StackPane {
	public Bottom(Text text) {
		getChildren().add(text);
		setPadding(new Insets(5,0,0,0));
		setAlignment(Pos.CENTER);
		text.setFill(Color.RED);	
	}
}



