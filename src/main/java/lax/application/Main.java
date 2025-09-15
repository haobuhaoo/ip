package lax.application;

import java.io.IOException;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lax.Lax;

/**
 * Represents a GUI for Lax chatbot using FXML.
 */
public class Main extends Application {
    /**
     * The file path to store the tasks.
     */
    private final String taskPath = "./data/task.txt";

    /**
     * The file path to store the notes.
     */
    private final String notesPath = "./data/notes.txt";

    /**
     * An instance of the chatbot.
     */
    private Lax lax;

    /**
     * Starts the GUI of the application.
     */
    @Override
    public void start(Stage stage) {
        try {
            lax = new Lax(taskPath, notesPath);
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setTitle("Lax");
            stage.setScene(scene);
            fxmlLoader.<MainWindow>getController().setLax(lax);
            stage.show();
        } catch (IOException e) {
            showAlert("Failed to load app: " + e.getMessage());
            Platform.exit();
        }
    }

    /**
     * Displays an alert with the title "Error" and message.
     *
     * @param message The message to be displayed in the alert.
     */
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
