import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
 import javafx.scene.Parent;
 import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import javafx.fxml.FXML;
public class Login extends Application {
	Stage primaryStage, stage; 
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
    	try {
    	Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    catch(Exception ex)
    {
    	System.out.println("ERROR");
    }
    }

    @FXML 
    private void handleRegistration(ActionEvent e)
    {
    	try{
    	showRegistration();
    	}
    	catch(Exception ex)
    	{
    	System.out.println("ERROR");
    	}
    }
    private void showRegistration() throws IOException
    {
    	try {
    		Parent root = FXMLLoader.load(getClass().getResource("register.fxml"));
    		Scene scene = new Scene(root);
    		stage.setScene(scene);
    		stage.show();
    	}
    	catch(Exception ex)
    	{
    		ex.printStackTrace();
    	}
    }
}
