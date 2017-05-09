import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent; 
import javafx.stage.Stage;
import javafx.scene.Scene; 
import java.io.IOException;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import java.net.URL;
import java.util.ResourceBundle;
public class GUIController implements Initializable {
    private Parent root;
    public Stage curr_stage = new Stage();
    /* 
    For the login page 
    These represent the values for the password and the username
    */
    @FXML PasswordField passwordfield;
    @FXML TextField textfield;
    /* ----------------------------------------------------------
    For the registration page 
    These represent the passwordfield for registration, 
    its verification field, the email field, 
    and the first name and last name fields.
    The label is for a notification message, which fires whenever we get an error 
    i.e., unverified passwords, blacklisted user, or a password whose strength is weak. 
    -------------------------------------------------------------
    */
     @FXML PasswordField regpasswordfield;
     @FXML PasswordField verifypasswordfield; 
     @FXML TextField regemailfield; 
     @FXML TextField firstnamefield;
     @FXML TextField lastnamefield; 
     @FXML Label notificationmessage; 
     /* End the registration stuff */

     /*DB Manager -> Manages the database */
     DBManage dbmanager = new DBManage(Connect.databaselink);

    @Override 
    public void initialize(URL url, ResourceBundle rb)
    {

    }
    public void GoToLogin(Stage primaryStage) throws IOException
    {
        try{
        
        curr_stage = primaryStage;
        curr_stage.setTitle("Welcome");
        Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
        Scene scene = new Scene(root);
      //  textfield = new TextField();
        curr_stage.setScene(scene);
        curr_stage.show();
        }
        catch(IOException ex)
        {
            ex.printStackTrace();
        }
    }
    @FXML 
    private void OnLogin()
    {
        System.out.println(textfield.getText());
    }
    @FXML private void GoToRegistration() throws IOException
    {   
        try {
            
            Parent root = FXMLLoader.load(getClass().getResource("register.fxml"));
            Scene scene = new Scene(root);
            curr_stage.setScene(scene);
            curr_stage.show();
         }
         catch(IOException ex)
         {
            System.out.println("ERROR");
         }
      
    }
    @FXML private void Register(ActionEvent e)
    {
        String email = regemailfield.getText();
        String password = regpasswordfield.getText();
        String verify = verifypasswordfield.getText();
        if(!password.equals(verify))
        {
            notificationmessage.setText("Passwords are not the same.");
        }
        else {
            if(PasswordStrength.CheckPassword(password) == 5 || PasswordStrength.CheckPassword(password) == 4)
            {
                notificationmessage.setText("");
                System.out.println("Adding to pending accounts");
                dbmanager.addToPendingAccountsTable(email, firstnamefield.getText(), lastnamefield.getText(), password);
                curr_stage.close();
            }
            else if(PasswordStrength.CheckPassword(password) < 3) 
            {
                notificationmessage.setText("Password is not very strong");
            }

        }
    }
}
