/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.*;
/**
 * FXML Controller class
 *
 * @author setti
 */
public class RegisterController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML TextField regemailfield;
    @FXML TextField firstnamefield;
    @FXML TextField lastnamefield;
    @FXML TextField regpasswordfield;
    @FXML TextField verifypasswordfield;
    @FXML Label notificationmessage;
    DBManage dbmanager = new DBManage();
    @FXML Button registerbutton; 
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
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
           // if(PasswordStrength.CheckPassword(password) == 5 || PasswordStrength.CheckPassword(password) == 4)
           // {
                notificationmessage.setText("");
               
                dbmanager.addToPendingAccountsTable(firstname, lastname, email, password);
                GoToLogin();
           // }
            //else if(PasswordStrength.CheckPassword(password) < 3) 
            //{
             //   notificationmessage.setText("Password is not very strong");
            //}

        }
    }
    private void GoToLogin() 
    {
        try {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("login.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            Scene scene = new Scene(root);
            Main.x.setScene(scene);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
}
