import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.FXML;
import javafx.scene.Parent; 
import javafx.stage.Stage;
import javafx.scene.Scene; 
import java.io.IOException;
class Registration {
	private Parent root;
	//Login login;  
	private Stage primaryStage;
	public Registration() throws IOException
	{
		LoadRegistration();
	}
	private void LoadRegistration() throws IOException
	{
		try 
		{
			//login = new Login();
			primaryStage = new Stage();
			primaryStage.setTitle("Register for an Account");
			root = FXMLLoader.load(getClass().getResource("registration.fxml"));
			Scene scene = new Scene(this.root);
			primaryStage.setScene(scene);
			primaryStage.show();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}

	}

}