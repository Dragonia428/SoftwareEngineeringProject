/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import java.sql.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * FXML Controller class
 *
 * @author Moomookittyclam
 */
public class deliveryController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    @FXML Label fname_label;
    @FXML Label lname_label;
    @FXML Label email_label;
    @FXML Label standing_label;
    @FXML Button logout_button;
    @FXML Tab routemap_tab;
    @FXML TabPane tabPane;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        DBManage dbmanage = new DBManage();
        ResultSet rs = dbmanage.getDeliveryPersonStatus();
        
        try{
            int standing;
            rs.next();
            email_label.setText(rs.getString("email"));
            fname_label.setText(rs.getString("fname"));
            lname_label.setText(rs.getString("lname"));
            standing = rs.getInt("standing");
            standing_label.setText(dbmanage.getStanding(rs.getInt("standing")));
        }
        catch(SQLException ex){
            ex.printStackTrace();
        }
    }
    
    @FXML private void deliverTo() throws IOException{
        tabPane.getSelectionModel().select(routemap_tab);
    }

    @FXML private void LogOut() throws IOException
    {
        LoginController login = new LoginController();
        login.GoToLogin();
    }
    
}
