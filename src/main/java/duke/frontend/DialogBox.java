package duke.frontend;

import java.io.IOException;
import java.util.Collections;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

/**
 * An example of a custom control using FXML.
 * This control represents a dialog box consisting of an ImageView to represent the speaker's face and a label
 * containing text from the speaker.
 */
public class DialogBox extends HBox {
	@FXML
	private Label dialog;
	@FXML
	private ImageView displayPicture;
	
	/**
	 * Constructor to generate new dialog box for Duke chat bot.
	 */
	DialogBox(String text, Image img) {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("/view/DialogBox.fxml"));
			fxmlLoader.setController(this);
			fxmlLoader.setRoot(this);
			fxmlLoader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		dialog.setText(text);
		dialog.setWrapText(true);
		displayPicture.setImage(img);
	}
	
	/**
	 * Generate a GUI dialog box with duke response and image.
	 *
	 * @return Dialog box with duke response and image.
	 */
	static DialogBox getDukeDialog(String text, Image img) {
		return new DialogBox(text, img);
	}
	
	/**
	 * Generate a GUI dialog box with user response and image.
	 *
	 * @return Dialog box with user response and image.
	 */
	static DialogBox getUserDialog(String text, Image img) {
		var db = new DialogBox(text, img);
		db.flip();
		return db;
	}
	
	/**
	 * Flips the dialog box such that the ImageView is on the left and text on the right.
	 */
	private void flip() {
		ObservableList<Node> tmp = FXCollections.observableArrayList(this.getChildren());
		Collections.reverse(tmp);
		getChildren().setAll(tmp);
		setAlignment(Pos.TOP_RIGHT);
	}
}