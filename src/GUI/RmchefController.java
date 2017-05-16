/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chef;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.FileChooser;
import javafx.collections.ObservableList;
import java.sql.*;
import java.text.NumberFormat;
import java.io.File;
import java.util.ListIterator;

/**
 *
 * @author gurbos
 */
public class FXMLDocumentController implements Initializable {
    
    private DBManage dbm;
    @FXML
    private Label resultLabel;
    @FXML
    private Button addDishButton, clearFieldsButton, removeDishesButton;
    @FXML
    private Label chefName, chefTitle, chefEmail, chefSalary;
    @FXML
    private TextField dishNameField, dishTypeField, dishPriceField, dishImageFileField;
    @FXML
    private TextArea dishDescriptionArea;
    @FXML
    private Tab removeMenuItemsTab;
    @FXML
    private ListView menuItemsListView;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        menuItemsListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        try{
            dbm = new DBManage();
            ResultSet resultSet = dbm.getChefStatus();
            if( resultSet.next() ) {
                chefName.setText(resultSet.getNString("fname") + " "  + resultSet.getNString("lname"));
                chefTitle.setText(resultSet.getNString("title"));
                chefEmail.setText(resultSet.getNString("email"));
                chefSalary.setText( NumberFormat.getCurrencyInstance().format(resultSet.getInt("salary")));
            }
        }
        catch( SQLException ex ){
            ex.printStackTrace();
            System.out.println(ex.getMessage());
        }
    }
    
    @FXML
    private void handleAddDishButton(ActionEvent event) {
        try{
            dbm = new DBManage();
            String dishName, dishType, dishImageFile, dishDescription;
            float dishPrice;
            dishName = dishNameField.getText();
            dishType = dishTypeField.getText();
            dishImageFile = dishImageFileField.getText();
            dishPrice = Float.parseFloat(dishPriceField.getText());
            dishDescription = dishDescriptionArea.getText();
            boolean result = dbm.addtoDishesTable(dishName, 1, dishPrice, dishType, dishDescription, dishImageFile);
            if( result )
                resultLabel.setText("Dish successfully added to menu");
            else
                resultLabel.setText("Error: Dish not added to menu");
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
    @FXML
    private void handleClearFieldsButton(ActionEvent event) {
        dishNameField.setText("");
        dishTypeField.setText("");
        dishPriceField.setText("");
        dishImageFileField.setText("");
        dishDescriptionArea.setText("");
    }
    
    @FXML
    private void handleBrowseButton(ActionEvent event) {
        try{
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Select Dish Image File");
            File file = fileChooser.showOpenDialog(new Stage());
            dishImageFileField.setText(file.getAbsolutePath());
        }
        catch(Exception ex) {
            ex.printStackTrace();
            System.exit(1);
        }
    }
    
    @FXML
    private void handleRemoveDishesButton(ActionEvent event) {
        ObservableList<String> dishNames = menuItemsListView.getSelectionModel().getSelectedItems();
        ListIterator<String> lit = dishNames.listIterator();
        while( lit.hasNext() ) {
            dbm.deleteFromDishesTable(lit.next());
            //lit.remove();
        }
    }
    
    
    @FXML
    private void onSelectRemoveMenuItemTab(Event event) {
        if( removeMenuItemsTab.isSelected() ) {
            
            menuItemsListView.setItems(dbm.getMenuItemNames());
        }
    }
    
    
    public void createChefObject(String email, String password) {
        
    }
    
} // end FXMLDocumentController