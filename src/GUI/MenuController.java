/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import java.sql.*;
import java.text.DecimalFormat;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableSet;
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
 * @author setti
 */
public class MenuController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML ListView<String> dishes; 
    @FXML ImageView Menu_img;
    @FXML Label Description; 
    @FXML Label Price;
    @FXML Label warning_label;
    @FXML TextField SearchBar;
    @FXML MenuItem warning_tab;
    ObservableSet<String> data = FXCollections.observableSet();
    static String currentitemselected; 
    static String currentprice;
    @FXML Label notification;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        gather_menu_items();
    }    
    private void gather_menu_items()
    {
         try{
            Connection con = DriverManager.getConnection(Connect.databaselink, "root", "123456");
            Statement st = con.createStatement();
            DBManage dbmanager = new DBManage();
            if(LoginController.logged_in)
            {
            ResultSet rs = dbmanager.queryDatabase("select warnings from customers where email='"+UserInfo.email+"';");
            rs.next();
            
                warning_label.setText(Integer.toString(rs.getInt("warnings")));
            //warning_tab.setText("Warnings :" + Integer.toString(rs.getInt("warnings")));
            }
            ResultSet rs2 = st.executeQuery("select dish_name from dishes");
            while(rs2.next())
            {
                String dish = rs2.getString("dish_name");
                data.add(dish);
            }
            dishes.getItems().addAll(data);
            dishes.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
             @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                    ObservableList<String> temp = FXCollections.observableArrayList();
                    temp.addAll(data);
                    currentitemselected = newValue;
                    for(int i = 0; i < temp.size(); i++)
                    {
                        if(newValue.equals(temp.get(i)))
                        {
                            
                                try {
                                    ResultSet rs3 = st.executeQuery("select price, description from dishes where dish_name=" + "'" + newValue + "'");
                                    rs3.next();
                                    String descr = rs3.getString("description");
                                    Double price = rs3.getDouble("price");
                                    currentprice = price.toString();
                                    
                                    //System.out.println(String.format("%.2f", price.toString()));
                                    Description.setText(descr);
                                    Price.setText("$" + currentprice);
                                    
                               
                                }
                                catch(Exception ex)
                                {
                                    
                                }
                        }
                    }
                    
                    }
            });
                    }
        catch(SQLException ex)
        {
            ex.printStackTrace();
        }
         
    }
    @FXML private void AddToCart()
    {
       DBManage dbmanager = new DBManage();
       dbmanager.AddToCart();
               
    }
    
    @FXML private void search(){
        
            DBManage dbmanage = new DBManage();
            String search = SearchBar.getText();
            try {
                  Connection con = DriverManager.getConnection(Connect.databaselink, "root", "123456");
            data.clear();
            if(search.isEmpty())
            {
              
                Statement st = con.createStatement();
                ResultSet regular = st.executeQuery("select dish_name from dishes");
                while(regular.next())
                {
                    String reg = regular.getString("dish_name");
                    data.add(reg);
                }
                dishes.getItems().setAll(data);
            }
            else {
                
                PreparedStatement ps = con.prepareStatement("select dish_name from dishes where type =? or dish_name=?");
                ps.setString(1, search);
                ps.setString(2, search);
                ResultSet rs = ps.executeQuery();
                while(rs.next())
                {
                    String dish = rs.getString("dish_name");
                    data.add(dish);
                }
                dishes.getItems().setAll(data);
            }
           
        }
        catch(SQLException ex){
            ex.printStackTrace();
        }
    }
    
    @FXML private void ShowCart() throws IOException
    {
        if(LoginController.logged_in)
        {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("OrderList.fxml"));
                Parent root = (Parent) fxmlLoader.load();
                Scene scene = new Scene(root);
                Main.x.setScene(scene);
        }
    }
    
    @FXML private void Order() throws IOException{
        if(!LoginController.logged_in) LogOut();
        else if(currentitemselected.isEmpty()) notification.setText("Please select an item first");
        else {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AddressPlacement.fxml"));
        Parent root = (Parent) fxmlLoader.load();
        AddressPlacementController control = fxmlLoader.getController();
        control.write_total_price(currentprice);
        Scene scene = new Scene(root); 
        Main.x.setScene(scene);
        }
    }
    
    @FXML private void ShowReviews() throws IOException{
        
        //FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("showreviews.fxml"));
        //Parent root = (Parent) fxmlLoader.load();
        //Scene scene = new Scene(root);
        //Main.x.setScene(scene);
    }
    
    @FXML private void WriteReview() throws IOException{
        //FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("writereviews.fxml"));
        //Parent root = (Parent) fxmlLoader.load();
        //Scene scene = new Scene(root);
        //Main.x.setScene(scene);
    }
    
    @FXML private void complain() throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("complain.fxml"));
        Parent root = (Parent) fxmlLoader.load();
        Scene scene = new Scene(root);
        Main.x.setScene(scene);
    }
    
    @FXML private void compliment() throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("complement.fxml"));
        Parent root = (Parent) fxmlLoader.load();
        Scene scene = new Scene(root);
        Main.x.setScene(scene);
    }
    
    @FXML private void LogOut() throws IOException
    {
        LoginController login = new LoginController();
        login.GoToLogin();
    }
    
}
