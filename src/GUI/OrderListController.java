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

    @FXML TableColumn<Shopping, String> pricecol;
    @FXML TableColumn<Shopping, String> foodcol;
    @FXML TableView<Shopping> cart; 
  
    /**
     * Initializes the controller class.
     */
     ObservableList<Shopping> data = FXCollections.observableArrayList();
        
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
            ResultSet rs = st.executeQuery("select * from shopping_cart");
            while(rs.next())
            {
                 String dish = rs.getString("dish_name");
               //  System.out.println(dish);
                 Double price = rs.getDouble("dish_price");
                 
                // System.out.println(price.toString());
                 data.add(new Shopping(dish, price.toString()));
                 
            }
                cart.getItems().setAll(data);
            ResultSet rs2 = st.executeQuery("select sum(dish_price) from shopping_cart");
            rs2.next();
            Double value = rs2.getDouble(1);
            total_price.setText(value.toString());
//            System.out.println(data.get(0).getPrice());
        
        }
        catch(SQLException ex)
        {
            
        }

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
        ResultSet shopping_rs = dbmanage.queryDatabase("select dish_name as dn from shopping_cart;");
        customerid_rs.next();
        customer_id = customerid_rs.getInt("customer_id");
        java.sql.Date date = new java.sql.Date(Calendar.getInstance().getTimeInMillis());
        total_price = 0;
        String address = "Insert address here";

        while(delivery_rs.next()){
          deliveryarray.add(delivery_rs.getInt("delivery_id"));
        }

        dptoDeliver = deliveryarray.get(rand.nextInt(deliveryarray.size()-1));

        while(shopping_rs.next()){
          ResultSet dish_rs = dbmanage.queryDatabase("select dish_id from dishes WHERE dish_name ="+shopping_rs.getString("dn")+";");
          dish_rs.next();
          disharray.add(dish_rs.getInt("dish_id"));
        }

        for(int i = 0; i < disharray.size(); i++){
          dbmanage.addtoOrdersTable(customer_id, date, total_price, address, disharray.get(i), dptoDeliver);
        }
        
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
