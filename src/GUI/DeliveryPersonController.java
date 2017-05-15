/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
/**
 * FXML Controller class
 *
 * @author setti
 */
public class DeliveryPersonController implements Initializable {

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
    @FXML private void CheckRecentOrders() throws IOException
    {
         FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("recentorders.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            Scene scene = new Scene(root);
            Main.x.setScene(scene);
            Main.x.show();
    }
    
}
