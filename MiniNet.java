/**
 * 
 */
package MiniNet_2;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * @author s3647369_Yidian_He
 *
 */
public class MiniNet extends Application {

	/**
	 * @param args
	 */
	@Override // Override the start method in the Application class
	public void start(Stage primaryStage) {
		MainStage ms = new MainStage();
		ms.mainStage(primaryStage);
	}
		
	public static void main(String[] args) {
	    Application.launch(args);
	}

}
