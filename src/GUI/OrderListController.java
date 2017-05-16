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
import java.util.Random;
import java.util.ArrayList;
import java.util.*;
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
      try{
        DBManage dbmanage = new DBManage();
        ArrayList<Integer> deliveryarray = new ArrayList<Integer>();
        ArrayList<Integer> disharray = new ArrayList<Integer>();
        int customer_id, dptoDeliver, total_price;
        Random rand = new Random();
        ResultSet customerid_rs = dbmanage.queryDatabase("select customer_id from customers where email="+UserInfo.email+";");
        ResultSet delivery_rs = dbmanage.queryDatabase("select delivery_id from delivery;");
        ResultSet shopping_rs = dbmanage.queryDatabase("select dish_name from shopping_cart;");
        customerid_rs.next();
        customer_id = customerid_rs.getInt("customer_id");
        java.sql.Date date = new java.sql.Date(Calendar.getInstance().getTimeInMillis());
        total_price = 0;
        String address = "Insert address here";

        while(delivery_rs.next()){
          deliveryarray.add(delivery_rs.getInt("delivery_id"));
        }

        dptoDeliver = deliveryarray.get(rand.nextInt());

        while(shopping_rs.next()){
          ResultSet dish_rs = dbmanage.queryDatabase("select dish_id from dishes WHERE dish_name ="+shopping_rs.getString("dish_name")+";");
          dish_rs.next();
          disharray.add(dish_rs.getInt("dish_id"));
        }

        for(int i = 0; i < disharray.size(); i++){
          dbmanage.addtoOrdersTable(customer_id, date, total_price, address, disharray.get(i), dptoDeliver);
        }
      }
      catch(SQLException sqlException){
        sqlException.printStackTrace();
      }
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
