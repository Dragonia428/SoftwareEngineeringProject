/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import static GUI.LoginController.logged_in;
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

public class RmPendingController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML TableView ptable; 
    @FXML TableColumn rmfirst;
    @FXML TableColumn rmlast; 
    @FXML TableColumn rmemail;
    @FXML TableColumn rmapprove;
    @FXML TableColumn deny; 
    static ObservableList<Pending> data = FXCollections.observableArrayList();
    @Override

    public void initialize(URL url, ResourceBundle rb) {
          rmfirst.setCellValueFactory(new PropertyValueFactory<Pending,String>("firstName"));
          rmlast.setCellValueFactory(new PropertyValueFactory<Pending,String>("lastName"));
          rmemail.setCellValueFactory(new PropertyValueFactory<Pending,String>("email"));
          //rmapprove.setCellValueFactory(new PropertyValueFactory<Pending, Boolean>("approve"));
          buildpendingtable();
          
    }
    @FXML private void CheckUsers() throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("rmusers.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            Scene scene = new Scene(root);
            Main.x.setScene(scene);
    }
    private void buildpendingtable()
    {
        try {
           
            Connection con = DriverManager.getConnection(Connect.databaselink, "root", "123456");
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select first_name, last_name, email from pending_accounts");
            while(rs.next())
            {
                 String first = rs.getString("first_name");
                 String last = rs.getString("last_name");
                 String email = rs.getString("email");
                
                 data.add(new Pending(first, last, email));
                rmapprove.setCellValueFactory(
                        new Callback<TableColumn.CellDataFeatures<Record, Boolean>, 
                        ObservableValue<Boolean>>() {

                    @Override
                    public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<Record, Boolean> p) {
                        return new SimpleBooleanProperty(p.getValue() != null);
                    }
                });

                //Adding the Button to the cell
                rmapprove.setCellFactory
                (
                  new Callback<TableColumn<Record, Boolean>, TableCell<Record, Boolean>>() {

                    @Override
                    public TableCell<Record, Boolean> call(TableColumn<Record, Boolean> p) {
                        return new ButtonCell();
                    }

                });
                deny.setCellValueFactory(
                        new Callback<TableColumn.CellDataFeatures<Record, Boolean>, 
                        ObservableValue<Boolean>>() {

                    @Override
                    public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<Record, Boolean> p) {
                        return new SimpleBooleanProperty(p.getValue() != null);
                    }
                });

                //Adding the Button to the cell
                deny.setCellFactory(
                    new Callback<TableColumn<Record, Boolean>, TableCell<Record, Boolean>>() {

                    @Override
                    public TableCell<Record, Boolean> call(TableColumn<Record, Boolean> p) {
                        return new ButtonCell2();
                    }

                });

            }
            ptable.setItems(data);
           
        
        
        }
        catch(SQLException ex)
        {
            ex.printStackTrace();
        }
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
}
class ButtonCell extends TableCell<Record, Boolean> {
        final Button cellButton = new Button("Approve");
        ButtonCell(){
            
        	//Action when the button is pressed
            cellButton.setOnAction(new EventHandler<ActionEvent>(){

                @Override
                public void handle(ActionEvent t) {
                    // get Selected Item
                        ApproveCustomer();
                        
                }
            });
            
        }
        private void ApproveCustomer()
        {
            try
            {
                        Pending currentPerson;
                        currentPerson = (Pending) ButtonCell.this.getTableView().getItems().get(ButtonCell.this.getIndex());
                	//remove selected item from the table list
                        DBManage dbmanager = new DBManage();
                        Connection con = DriverManager.getConnection(Connect.databaselink, "root", "123456");
                        PreparedStatement st = con.prepareStatement("select password from pending_accounts where email =?");
                        st.setString(1, currentPerson.getEmail());
                        ResultSet rs = st.executeQuery();
                        rs.next();
                        String password = rs.getString("password");
                        dbmanager.addtoCustomerTable(currentPerson.getFirstName(), currentPerson.getLastName(), currentPerson.getEmail(), password);
                        dbmanager.deleteFromPenAccTable(currentPerson.getEmail());
                	RmPendingController.data.remove(currentPerson);
                }
               catch(SQLException ex)
                    {
                        ex.printStackTrace();
                    }
        }
        //Display button if the row is not empty
        @Override
        protected void updateItem(Boolean t, boolean empty) {
            super.updateItem(t, empty);
            if(!empty){
                setGraphic(cellButton);
                
            }
        }
    }
class ButtonCell2 extends TableCell<Record, Boolean>
{
     final Button cellButton2 = new Button("Deny");
    public ButtonCell2() {
     cellButton2.setOnAction(new EventHandler<ActionEvent>(){

                @Override
                public void handle(ActionEvent t) {
                    // get Selected Item
                        DenyCustomer();
                        
                }
            });
    }
    private void DenyCustomer()
        {
            Pending currentPerson;
            currentPerson = (Pending) ButtonCell2.this.getTableView().getItems().get(ButtonCell2.this.getIndex());
            RmPendingController.data.remove(currentPerson);
            DBManage dbmanager = new DBManage();
            dbmanager.deleteFromPenAccTable(currentPerson.getEmail());
        }
      @Override
        protected void updateItem(Boolean t, boolean empty) {
            super.updateItem(t, empty);
            if(!empty){
                setGraphic(cellButton2);
                
            }
        }
}
