package duke;

import java.io.IOException;

import duke.frontend.MainWindow;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * A GUI for Duke using FXML.
 */
public class Main extends Application {
	private Duke duke = new Duke();
	
	/**
	 * Start the main application for Duke GUI.
	 */
	@Override
	public void start(Stage stage) {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
			AnchorPane ap = fxmlLoader.load();
			Scene scene = new Scene(ap);
			stage.setScene(scene);
			
			Image icon = new Image(getClass().getResourceAsStream("/images/robot.png"));
			stage.getIcons().add(icon);
			stage.setTitle("Duke Helpbot");
			
			fxmlLoader.<MainWindow>getController().setDuke(duke);
			stage.show();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}