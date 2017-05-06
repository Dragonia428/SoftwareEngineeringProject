import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import java.io.IOException;

class gui extends Application {
	public static void main(String[] args)
	{
		Application.launch(gui.class, args);
	}
	@Override
    public void start(Stage primaryStage) throws IOException {
	    	try {
	        Parent root = FXMLLoader.load(gui.class.getResource("login.fxml"));
	        Scene scene = new Scene(root);
	        primaryStage.setTitle("Login");
	        primaryStage.setScene(scene);
	        primaryStage.show();
	    }
	    catch(Exception ex)
	    {
	    	ex.printStackTrace();
	    }

    }
    
}
