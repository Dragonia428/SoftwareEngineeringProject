/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import static GUI.RmusersController.data;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

/**
 * FXML Controller class
 *
 * @author setti
 */
public class UserstatusController implements Initializable {
    @FXML Label name;
    @FXML Label email;
    @FXML Label currentmoney;
    
    /**
     * Initializes the controller class.
     */
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println(StatusCell.useremail);
        SetLabels();
    }  
    private void SetLabels()
    {
        try {
                Connection con = DriverManager.getConnection(Connect.databaselink, "root", "123456");
               
                PreparedStatement ps = con.prepareStatement("select first_name, last_name, email from customers where email=?");
                ps.setString(1,StatusCell.useremail);
                ResultSet rs = ps.executeQuery();
                rs.next();
                 String first = rs.getString("first_name");
                 String last = rs.getString("last_name");
                 String email = rs.getString("email");
                name.setText(email);
            
        }
        catch(SQLException ex)
        {
            
        }
    }
    
}
