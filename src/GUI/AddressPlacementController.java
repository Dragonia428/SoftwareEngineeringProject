/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
/**
 * FXML Controller class
 *
 * @author setti
 */
public class AddressPlacementController implements Initializable {
    @FXML TextField address;
    @FXML TextField ZIP; 
    @FXML Label total; 
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }    
    public void write_total_price(String text)
    {
        total.setText(text);
    }
    private void show_total_price()
    {     
           // total_price.setText("$" + MenuController.currentprice);
    }
     @FXML private void OrderFood()
    {
      try{
        DBManage dbmanage = new DBManage();
        ArrayList<Integer> deliveryarray = new ArrayList<Integer>();
        ArrayList<Integer> disharray = new ArrayList<Integer>();
        int customer_id, dptoDeliver, total_price;
        Random rand = new Random();
        ResultSet customerid_rs = dbmanage.queryDatabase("select customer_id from customers where email="+"'" + UserInfo.email+ "'" +";");
        ResultSet delivery_rs = dbmanage.queryDatabase("select delivery_id from delivery;");
        customerid_rs.next();
        customer_id = customerid_rs.getInt("customer_id");
        java.sql.Date date = new java.sql.Date(Calendar.getInstance().getTimeInMillis());
        total_price = 0;
        

        while(delivery_rs.next()){
          deliveryarray.add(delivery_rs.getInt("delivery_id"));
        }

        dptoDeliver = deliveryarray.get(rand.nextInt(deliveryarray.size()-1));

       
          ResultSet dish_rs = dbmanage.queryDatabase("select dish_id from dishes WHERE dish_name ='"+MenuController.currentitemselected+"';");
          dish_rs.next();
          int dish_id = dish_rs.getInt("dish_id");
          dbmanage.addtoOrdersTable(customer_id, date, total_price, address.getText(), dish_id, dptoDeliver);
        
        
        UpdateCustomerTable(customer_id, total_price);
      }

      catch(SQLException sqlException){
        sqlException.printStackTrace();
      }
    }
      private void UpdateCustomerTable(int id, double total_price)
    {
     
        try {
            Connection con = DriverManager.getConnection(Connect.databaselink, "root", "123456");
            Statement st = con.createStatement();
            st.executeUpdate("update customers set funds = funds - " + total_price + " where customer_id =" + "'" + id + "'");

        }
        catch(SQLException ex)
        {

        }
    }
    
}
