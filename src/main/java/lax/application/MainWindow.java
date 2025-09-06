package lax.application;

import java.util.Objects;

import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import lax.Lax;
import lax.ui.Ui;

/**
 * Represents the controller for the main GUI.
 */
public class MainWindow extends AnchorPane {
    /**
     * Pane where all the dialogs, images and scrollbar are shown.
     */
    @FXML
    private ScrollPane scrollPane;

    /**
     * Container holding all the dialogs.
     */
    @FXML
    private VBox dialogContainer;

    /**
     * User input text box.
     */
    @FXML
    private TextField userInput;

    /**
     * Button to send user input.
     */
    @FXML
    private Button sendButton;

    /**
     * An instance of the chatbot.
     */
    private Lax lax;

    /**
     * Image of user.
     */
    private final Image userImage =
            new Image(Objects.requireNonNull(this.getClass().getResourceAsStream("/images/munchlax.png")));

    /**
     * Image of Lax.
     */
    private final Image laxImage =
            new Image(Objects.requireNonNull(this.getClass().getResourceAsStream("/images/snorlax.png")));

    /**
     * Initializes the stage with default properties.
     */
    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /**
     * Adds variable number of dialogs into the dialog container.
     */
    private void addDialogs(DialogBox... dialogs) {
        dialogContainer.getChildren().addAll(dialogs);
    }

    /**
     * Injects the Lax instance and starts the conversation by displaying the welcome message to the user.
     *
     * @param l Instance of Lax.
     */
    public void setLax(Lax l) {
        lax = l;
        addDialogs(DialogBox.getLaxDialog(new Ui().showWelcome(), laxImage, "Start"));
    }

    /**
     * Exits the chatbot after 3 seconds, without the user doing it manually.
     */
    private void exit() {
        PauseTransition delay = new PauseTransition(Duration.seconds(3));
        delay.setOnFinished(e -> {
            Platform.exit();
            System.exit(0);
        });
        delay.play();
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Lax's reply and then appends
     * them to the dialog container. Clears the user input after processing.
     * <p>
     * If the user inputs the exit command, the application will exit by itself after 3 seconds.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText().trim().toLowerCase();
        if (input.isEmpty()) {
            addDialogs(DialogBox.getLaxDialog(new Ui().emptyCmd(), laxImage, "EmptyString"));
            return;
        }

        addDialogs(DialogBox.getUserDialog(input, userImage),
                DialogBox.getLaxDialog(lax.getResponse(input), laxImage, lax.getCommandType()));
        userInput.clear();

        if (input.trim().equalsIgnoreCase("bye")) {
            exit();
        }
    }
}
