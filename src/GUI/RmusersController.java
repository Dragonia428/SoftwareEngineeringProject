/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import static GUI.LoginController.logged_in;
import static GUI.RmPendingController.data;
import com.sun.prism.impl.Disposer.Record;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.sql.*;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author setti
 */
public class RmusersController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML TableColumn ufirst;
    @FXML TableColumn ulast;
    @FXML TableColumn uemail;
    @FXML TableColumn status;
    @FXML TableColumn remove; 
    @FXML TableView utable;
    
    static ObservableList<Users> data = FXCollections.observableArrayList();
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         ufirst.setCellValueFactory(new PropertyValueFactory<Users,String>("firstName"));
         ulast.setCellValueFactory(new PropertyValueFactory<Users,String>("lastName"));
         uemail.setCellValueFactory(new PropertyValueFactory<Users,String>("email"));
          //rmapprove.setCellValueFactory(new PropertyValueFactory<Pending, Boolean>("approve"));
          
          buildusertable();
    }    
     @FXML private void CheckPending() throws IOException
    {
      
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("rmpending.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            Scene scene = new Scene(root);
            Main.x.setScene(scene);
    }
    @FXML private void LogOut() throws IOException
    {
        LoginController login = new LoginController();
        login.GoToLogin();
    }
    @FXML private void GoToMenu() throws IOException
    {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("rmmenu.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            Scene scene = new Scene(root);
            Main.x.setScene(scene);
    }
    private void buildusertable()
    {
         try {
           
            Connection con = DriverManager.getConnection(Connect.databaselink, "root", "123456");
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select first_name, last_name, email from customers");
            while(rs.next())
            {
                 String first = rs.getString("first_name");
                 String last = rs.getString("last_name");
                 String email = rs.getString("email");
                
                 data.add(new Users(first, last, email));
                 
            }
            utable.setItems(data);
           status.setCellValueFactory(
           new Callback<TableColumn.CellDataFeatures<Record, Boolean>, 
           ObservableValue<Boolean>>() 
            {

            @Override
            public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<Record, Boolean> p) {
                return new SimpleBooleanProperty(p.getValue() != null);
            }
            });
            status.setCellFactory(
                new Callback<TableColumn<Record, Boolean>, TableCell<Record, Boolean>>() {

            @Override
            public TableCell<Record, Boolean> call(TableColumn<Record, Boolean> p) {
                return new StatusCell();
            }
        
        });
        
            
            
           remove.setCellValueFactory(
           new Callback<TableColumn.CellDataFeatures<Record, Boolean>, 
           ObservableValue<Boolean>>() 
            {

            @Override
            public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<Record, Boolean> p) {
                return new SimpleBooleanProperty(p.getValue() != null);
            }
            });
            remove.setCellFactory(
                new Callback<TableColumn<Record, Boolean>, TableCell<Record, Boolean>>() {

            @Override
            public TableCell<Record, Boolean> call(TableColumn<Record, Boolean> p) {
                return new RemoveCell();
            }
        
        });
            
            
            
             
         }
         catch(SQLException ex)
         {
             ex.printStackTrace();
         }

    }
}
class StatusCell extends TableCell<Record, Boolean> {
        final Button cellButton = new Button("Status");
        public static String userfirst;
        public static String userlast;
        public static String useremail; 
        StatusCell(){
            
        	//Action when the button is pressed
            cellButton.setOnAction(new EventHandler<ActionEvent>(){

                @Override
                public void handle(ActionEvent t) {
                    // get Selected Item
                        UserstatusController ucon = new UserstatusController();
                        Users users;
                        users = (Users) StatusCell.this.getTableView().getItems().get(StatusCell.this.getIndex());
                        userfirst = users.getFirstName();
                        userlast = users.getLastName();
                        useremail = users.getEmail();
                        DBManage dbmanager = new DBManage();
                        System.out.println(dbmanager.GetCustomerID(users.getEmail()));
                        
                }
            });
            
        }
        private void CheckStatus()
        {
            try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("userstatus.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            Scene scene = new Scene(root);
            Main.x.setScene(scene);
            Main.x.show();
            }
            catch(IOException ex)
            {
                ex.printStackTrace();
            }
            
        }
        @Override
        protected void updateItem(Boolean t, boolean empty) {
            super.updateItem(t, empty);
            if(!empty){
                setGraphic(cellButton);
                
            }
        }
}
class RemoveCell extends TableCell<Record, Boolean> {
        final Button cellButton = new Button("Remove");
        RemoveCell(){
            
        	//Action when the button is pressed
            cellButton.setOnAction(new EventHandler<ActionEvent>(){

                @Override
                public void handle(ActionEvent t) {
                    // get Selected Item
                       Remove();
                        
                }
            });
            
        }
        private void Remove()
        {
             Users curruser;
             curruser = (Users) RemoveCell.this.getTableView().getItems().get(RemoveCell.this.getIndex());
             DBManage dbmanager = new DBManage();
             RmusersController.data.remove(curruser);
             dbmanager.deleteFromCustomerTable(curruser.getEmail());
        }
        @Override
        protected void updateItem(Boolean t, boolean empty) {
            super.updateItem(t, empty);
            if(!empty){
                setGraphic(cellButton);
                
            }
        }
}
