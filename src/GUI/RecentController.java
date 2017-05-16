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
//import com.lynden.gmapsfx.GoogleMapView;
/**
 * FXML Controller class
 *
 * @author setti
 */

public class RecentController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML TableColumn<Orders,String> orderzip;
    @FXML TableColumn<Orders,String> orderemail;
    @FXML TableColumn<Orders, String> orderaddress;
    @FXML TableColumn getdirections;
    @FXML TableColumn Done; 
    @FXML TableView<Orders> ordertable; 
    static ObservableList<Orders> data = FXCollections.observableArrayList();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         orderzip.setCellValueFactory(new PropertyValueFactory<Orders,String>("ZIP"));
         orderemail.setCellValueFactory(new PropertyValueFactory<Orders,String>("email"));
         orderaddress.setCellValueFactory(new PropertyValueFactory<Orders,String>("address"));
          
         // buildorderstable();
          
    }

    private void buildpendingtable()
    {
        try {
           
            Connection con = DriverManager.getConnection(Connect.databaselink, "root", "123456");
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select  from pending_accounts");
            while(rs.next())
            {
                 String first = rs.getString("first_name");
                 String last = rs.getString("last_name");
                 String email = rs.getString("email");
                
                 data.add(new Orders(first, last, email));
                Done.setCellValueFactory(
                        new Callback<TableColumn.CellDataFeatures<Record, Boolean>, 
                        ObservableValue<Boolean>>() {

                    @Override
                    public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<Record, Boolean> p) {
                        return new SimpleBooleanProperty(p.getValue() != null);
                    }
                });

                //Adding the Button to the cell
                Done.setCellFactory
                (
                  new Callback<TableColumn<Record, Boolean>, TableCell<Record, Boolean>>() {

                    @Override
                    public TableCell<Record, Boolean> call(TableColumn<Record, Boolean> p) {
                        return new DeleteCell();
                    }

                });
                getdirections.setCellValueFactory(
                        new Callback<TableColumn.CellDataFeatures<Record, Boolean>, 
                        ObservableValue<Boolean>>() {

                    @Override
                    public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<Record, Boolean> p) {
                        return new SimpleBooleanProperty(p.getValue() != null);
                    }
                });

                //Adding the Button to the cell
                getdirections.setCellFactory(
                    new Callback<TableColumn<Record, Boolean>, TableCell<Record, Boolean>>() {

                    @Override
                    public TableCell<Record, Boolean> call(TableColumn<Record, Boolean> p) {
                        return new ButtonCell2();
                    }

                });

            }
            ordertable.setItems(data);
           
        
        
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
class DeleteCell extends TableCell<Record, Boolean> {
        final Button deleteButton = new Button("Delete");
        DeleteCell(){
            
        	//Action when the button is pressed
            deleteButton.setOnAction(new EventHandler<ActionEvent>(){

                @Override
                public void handle(ActionEvent t) {
                    // get Selected Item
                         DBManage dbmanager = new DBManage();
                Orders currentPerson; 
                currentPerson = (Orders) DeleteCell.this.getTableView().getItems().get(DeleteCell.this.getIndex());
                
                dbmanager.deleteFromOrdersTable(dbmanager.GetOrderID(currentPerson.GetEmail()));
                RecentController.data.remove(currentPerson);
                        
                }
            });
            
        }
        //Display button if the row is not empty
        @Override
        protected void updateItem(Boolean t, boolean empty) {
            super.updateItem(t, empty);
            if(!empty){
                setGraphic(deleteButton);
                
            }
        }
    }
class Direction extends TableCell<Record, Boolean>
{
     final Button cellButton2 = new Button("Direction");
    public Direction() {
     cellButton2.setOnAction(new EventHandler<ActionEvent>(){

                @Override
                public void handle(ActionEvent t) {
                    // get Selected Item
                        
                        
                }
            });
    }
    private void GetDirections()
    {
        
    }
      @Override
        protected void updateItem(Boolean t, boolean empty) {
            super.updateItem(t, empty);
            if(!empty){
                setGraphic(cellButton2);
                
            }
        }
}
