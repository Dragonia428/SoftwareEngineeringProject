/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import static GUI.LoginController.logged_in;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;

/**
 * FXML Controller class
 *
 * @author setti
 */
public class RmmenuController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    @FXML private void LogOut() throws IOException
    {
        LoginController login = new LoginController();
        login.GoToLogin();
    }
    @FXML private void CheckPending() throws IOException
    {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("rmpending.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            Scene scene = new Scene(root);
            Main.x.setScene(scene);
    }
    @FXML private void CheckUsers() throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("rmusers.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            Scene scene = new Scene(root);
            Main.x.setScene(scene);
    }
    @FXML private void CheckChefs() throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("rmchef.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            Scene scene = new Scene(root);
            Main.x.setScene(scene);
    }
    
}
