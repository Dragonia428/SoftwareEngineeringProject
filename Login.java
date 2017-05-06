import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.FXML;
import javafx.scene.Parent; 
import javafx.stage.Stage;
import javafx.scene.Scene; 
import java.io.IOException;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
public class Login extends Application {
    private Parent root;
    @FXML PasswordField passwordfield; 
    @FXML TextField textfield; 
    Stage primaryStage = new Stage();
    public DBManage dbmanager; 
    public static void main(String[] args) {
        new Connect("RMS");
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
     
        try{
        LoadLoginFile();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    @FXML
    private void SetPassword()
    {

    }
    private void LoadLoginFile() throws IOException
    {
        try{
        
        //primaryStage = new Stage();
        primaryStage.setTitle("Welcome");
         root = FXMLLoader.load(getClass().getResource("login.fxml"));
        Scene scene = new Scene(root);
      //  textfield = new TextField();
        primaryStage.setScene(scene);
        primaryStage.show();
        }
        catch(IOException ex)
        {
            ex.printStackTrace();
        }
    }
    @FXML
    private void GoToRegistration() throws IOException
    {   
        root = FXMLLoader.load(getClass().getResource("register.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();   
    }
    @FXML 
    private void OnLogin()
    {
        dbmanager = new DBManage(Connect.databaselink);
        if(dbmanager.SearchForUserInDatabase(textfield.getText(), passwordfield.getText()))
            System.out.println("Found!");
        else System.out.println("Not Found!");
    }
}
