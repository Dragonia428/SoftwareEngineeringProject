import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.FXML;
import javafx.scene.Parent; 
import javafx.stage.Stage;
import javafx.scene.Scene; 
import java.io.IOException;
public class Main extends Application {
	public static Stage mainStage; 
	
	public static void main(String[] args) {
       new Connect("RMS");
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
     	mainStage = primaryStage;
     	GUIController gui = new GUIController();
        gui.GoToMainMenu(primaryStage);
    }
}