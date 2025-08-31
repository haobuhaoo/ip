package lax.application;

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
 * Represents a dialog box consisting of an <code>ImageView</code> to represent the speaker and a
 * <code>Label</code> containing text from the speaker.
 */
public class DialogBox extends HBox {
    /**
     * Text from the speaker.
     */
    @FXML
    private Label dialog;

    /**
     * Image of the speaker.
     */
    @FXML
    private ImageView displayPicture;

    /**
     * Constructs the dialog box with the image and text from the speaker.
     */
    private DialogBox(String text, Image img) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("/view/DialogBox.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            System.out.println("Error creating dialog box: " + e.getMessage());
        }

        dialog.setText(text);
        displayPicture.setImage(img);
    }

    /**
     * Flips the dialog box such that the image is on the left and text on the right.
     */
    private void flip() {
        ObservableList<Node> tmp = FXCollections.observableArrayList(this.getChildren());
        Collections.reverse(tmp);
        getChildren().setAll(tmp);
        setAlignment(Pos.TOP_LEFT);
        dialog.getStyleClass().add("reply-label");
    }

    /**
     * Changes the style of the dialog base on the chatbot's reply from user's input.
     *
     * @param commandType The type of command that user inputs.
     */
    private void changeDialogStyle(String commandType) {
        switch (commandType) {
        case "AddCommand" -> dialog.getStyleClass().add("add-label");
        case "DeleteCommand" -> dialog.getStyleClass().add("delete-label");
        case "LabelCommand" -> dialog.getStyleClass().add("marked-label");
        case "InvalidCommand" -> dialog.getStyleClass().add("error-label");
        default -> { } // Do nothing
        }
    }

    /**
     * Creates an instance of DialogBox for the user.
     */
    public static DialogBox getUserDialog(String text, Image img) {
        return new DialogBox(text, img);
    }

    /**
     * Creates an instance of DialogBox for chatbot. It flips the image and text around and changes the style
     * of the dialog.
     */
    public static DialogBox getLaxDialog(String text, Image img, String commandType) {
        DialogBox db = new DialogBox(text, img);
        db.flip();
        db.changeDialogStyle(commandType);
        return db;
    }
}
