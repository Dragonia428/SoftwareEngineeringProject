/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import java.sql.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
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
    ObservableList<String> data = FXCollections.observableArrayList();
    static String currentitemselected; 
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
                    }
                    else if(newValue.equals("Penne alla vodka"))
                    {
                        Description.setText("This is penne");
                        Price.setText("11.95");
                        
                    }
                }
 
            });
 //           Image image = new Image("file:penneallavodka.png");
//            img.setImage(image);
            
        }
         
        catch(SQLException ex)
        {
            
        }
         
    }
    @FXML private void AddToCart()
         {
               
         }
    
    
}
