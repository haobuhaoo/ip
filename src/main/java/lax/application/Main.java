package lax.application;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lax.Lax;

/**
 * Represents a GUI for Lax chatbot using FXML.
 */
public class Main extends Application {
    /**
     * An instance of the chatbot.
     */
    private final Lax lax = new Lax("./data/data.txt");

    /**
     * Starts the GUI of the application.
     */
    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);
            fxmlLoader.<MainWindow>getController().setLax(lax);
            stage.show();
        } catch (IOException e) {
            System.out.println("Error starting app: " + e.getMessage());
        }
    }
}
