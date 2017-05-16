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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
/**
 * FXML Controller class
 *
 * @author setti
 */

public class OrderListController implements Initializable {
    @FXML Label total_price;
    @FXML TableColumn<Shopping, String> pricecol;
    @FXML TableColumn<Shopping, String> foodcol;
    @FXML TableView<Shopping> cart; 
    /**
     * Initializes the controller class.
     */
        static ObservableList<Shopping> data = FXCollections.observableArrayList();
        
    @Override
    public void initialize(URL url, ResourceBundle rb) {
          pricecol.setCellValueFactory(new PropertyValueFactory<Shopping,String>("price"));
          foodcol.setCellValueFactory(new PropertyValueFactory<Shopping,String>("item"));
          initializeshoppingcart();
    }  
    private void initializeshoppingcart()
    {
        
        try {
           
            Connection con = DriverManager.getConnection(Connect.databaselink, "root", "123456");
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select dish_name, dish_price from shopping_cart");
            while(rs.next())
            {
                 String dish = rs.getString("dish_name");
                 System.out.println(dish);
                 Double price = rs.getDouble("dish_price");
                 System.out.println(price.toString());
                 data.add(new Shopping(dish, price.toString()));
                 
            }
            cart.setItems(data);
        }
        catch(SQLException ex)
        {
            
        }
    }
    @FXML private void OrderFood()
    {
        double price = Float.parseFloat(total_price.getText());
        UpdateCustomerTable(UserInfo.email, price);
        
    }
    private void UpdateCustomerTable(String email, double total_price)
    {
     
        try {
            Connection con = DriverManager.getConnection(Connect.databaselink, "root", "123456");
            Statement st = con.createStatement();
            st.executeUpdate("update customers set funds = funds - " + total_price + " where email =" + "'" + email + "'");
            
            
        }
        catch(SQLException ex)
        {
            
        }
    }
    private void SendToOrders()
    {
        java.sql.Date date = new java.sql.Date(System.currentTimeMillis());
        try {
            Connection con = DriverManager.getConnection(Connect.databaselink, "root", "123456");
            
        }
        catch(SQLException ex)
        {
            
        }
        
    }
    
}
