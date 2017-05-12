import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.input.InputEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent; 
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Node; 
import java.io.IOException;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import java.net.URL;
import java.util.ResourceBundle;
public class GUIController implements Initializable {
    private Parent root;
    public Stage curr_stage = new Stage();
     /*DB Manager -> Manages the database */
    DBManage dbmanager = new DBManage();
    /*--------------------------------------------------------------------Start of Main Menu Code--------------------------------------------
    This section will explain the main menu components that will be used in this GUIController. 
    The main menu consists of labels for the menuitems, their prices, a checkbox determining the order desired, searching, and ordering. 
    The main menu will also give the user the ability to go to Login/Registration if they are a surfer. 
    If a user is a surfer (i.e. logged_in == false) then many main menu features will not be accessible to the user. 
    I.e., a user won't be able to order food or post a review/look at review. 
    First I'll list the respective menuitems, which will be fetched by the DB. 
    Then I'll list the methods


    /* Menu item labels -- for all the menu items */
     @FXML Label menuitem12;
     @FXML Label menuitem11;
     @FXML Label menuitem10;
     @FXML Label menuitem9;
     @FXML Label menuitem8;
     @FXML Label menuitem7;
     @FXML Label menuitem6;
     @FXML Label menuitem5;
     @FXML Label menuitem4;
     @FXML Label menuitem3;
     @FXML Label menuitem2;
     @FXML Label menuitem1;
     Label[] menuitemlabels = {menuitem1, menuitem2, menuitem3, menuitem4, menuitem5, menuitem6, menuitem7, menuitem8, menuitem9, menuitem10, menuitem11, menuitem12};
     /* End menu item labels */
       @Override 
    public void initialize(URL url, ResourceBundle rb)
    {
    }
    public void GoToMainMenu(Stage primaryStage) throws IOException
    {
        curr_stage = primaryStage;
        curr_stage.setTitle("Welcome");
        Parent root = FXMLLoader.load(getClass().getResource("surfer.fxml"));
        Scene scene = new Scene(root);
      //  textfield = new TextField();
        curr_stage.setScene(scene);
        curr_stage.show();
    }
     @FXML private void Order() throws IOException
    {
        if(!logged_in) GoToLogin();
        System.out.println(menuitem1.getId());
    }
    /*---------------------End Main Menu--------------------------------------------------------*/
    /* 
    For the login page 
    These represent the values for the password and the username
    */
    @FXML PasswordField passwordfield;
    @FXML TextField textfield;
    public static boolean logged_in = false; //checks to see if we're logged in or not
    @FXML private void GoToLogin() throws IOException
    {
        try{
        Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
        Scene scene = new Scene(root);
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
        logged_in = true;
    }
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
    @FXML private void Register()
    {
        String email = regemailfield.getText();
        String password = regpasswordfield.getText();
        String firstname = firstnamefield.getText();
        String lastname = lastnamefield.getText();
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
              //  dbmanager.addToPendingAccounts(firstname, lastname, email, "test", password);
                //Node node = (Node) e.getSource(); 
                //Stage stage = (Stage) node.getScene().getWindow();
                //stage.close();
            }
            else if(PasswordStrength.CheckPassword(password) < 3) 
            {
                notificationmessage.setText("Password is not very strong");
            }

        }
    }
     /* End the registration stuff */

  


  
   

    
   
}
