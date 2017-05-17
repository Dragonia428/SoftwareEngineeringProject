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
 * @author setti
 */
public class MenuController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML ListView<String> dishes; 
    @FXML ImageView img;
    @FXML Label Description; 
    @FXML Label Price;
    @FXML Label warning_label;
    @FXML TextField SearchBar;
    ObservableList<String> data = FXCollections.observableArrayList();
    static String currentitemselected; 
    static String currentprice;
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
            ResultSet rs = dbmanager.queryDatabase("select warnings from customers where email='"+UserInfo.email+"';");
            rs.next();
            warning_label.setText(Integer.toString(rs.getInt("warnings")));
            data.add("Penne alla vodka");
            data.add("Spaghetti");
            dishes.getItems().addAll(data);
            dishes.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
             @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                    currentitemselected = newValue; 
                    if(newValue.equals("Spaghetti"))
                    {
                        Description.setText("This is spaghetti");
                        Price.setText("12.95");
                        currentprice = Price.getText();
                        Image image = new Image("file:penneallavodka.png");
                        img.setImage(image);
                        
                    }
                    else if(newValue.equals("Penne alla vodka"))
                    {
                        Description.setText("This is penne");
                        Price.setText("11.95");
                        currentprice = Price.getText();
                        
                    }
                }
 
            });
 //           Image image = new Image("file:penneallavodka.png");
//            img.setImage(image);
        }
        catch(SQLException ex)
        {
            ex.printStackTrace();
        }
         
    }
    @FXML private void AddToCart()
    {
        try{
            Connection con = DriverManager.getConnection(Connect.databaselink, "root", "123456");
            PreparedStatement ps = con.prepareStatement("INSERT into shopping_cart(dish_name, dish_price) values(?, ?)");
            ps.setString(1, currentitemselected);
            ps.setFloat(2, Float.parseFloat(currentprice));
            ps.executeUpdate();
        }
        catch(SQLException ex)
        {
            ex.printStackTrace();
        }
               
    }
    
    @FXML private void search() throws IOException{
        try{
            DBManage dbmanage = new DBManage();
            String search = SearchBar.getText();
        
            ResultSet rs = dbmanage.queryDatabase("SELECT dish_name from dishes where dish_name ="+search+";");
            if(rs.next()){
                
            }
        
        }
        catch(SQLException ex){
            ex.printStackTrace();
        }
    }
    
    @FXML private void ShowCart() throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("OrderList.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            Scene scene = new Scene(root);
            Main.x.setScene(scene);
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
    
    @FXML private void LogOut() throws IOException
    {
        LoginController login = new LoginController();
        login.GoToLogin();
    }
    
}
