/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import java.sql.*;
import javafx.scene.control.*;
/**
 * FXML Controller class
 *
 * @author setti
 */

public class OrderListController implements Initializable {
    @FXML Label total_price;
    @FXML TableColumn pricecol;
    @FXML TableColumn foodcol;
    @FXML TableView cart; 
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }  
    @FXML private void OrderFood()
    {
        double price = Float.parseFloat(total_price.getText());
        UpdateCustomerTable(UserInfo.email, price);
        
    }
    private void UpdateCustomerTable(String email, double total_price)
    {
        java.sql.Date date = new java.sql.Date(System.currentTimeMillis());
        try {
            Connection con = DriverManager.getConnection(Connect.databaselink, "root", "123456");
            Statement st = con.createStatement();
            st.executeUpdate("update customers set funds = funds - " + total_price + " where email =" + "'" + email + "'");
            
        }
        catch(SQLException ex)
        {
            
        }
    }
    
}
