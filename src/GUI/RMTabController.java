/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import static GUI.RmPendingController.data;
import static GUI.RmusersController.data;
import com.sun.prism.impl.Disposer;
import com.sun.prism.impl.Disposer.Record;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author setti
 */
public class RMTabController implements Initializable {
    
    /**
     * Initializes the controller class.
     */
    @FXML Tab pending_accounts;
    @FXML Tab users; 
    @FXML Tab chefstable; 
    @FXML TableColumn deny, rmapprove;
    @FXML TableView ptable, utable; 
    @FXML TableColumn warn,remove; 
    @FXML TableColumn rmfirst, rmlast, rmemail;
    @FXML TableColumn ufirst, ulast, uemail;
    static ObservableList<Users> userdata = FXCollections.observableArrayList();
    static ObservableList<Pending> pendingdata = FXCollections.observableArrayList(); 
    @Override
    public void initialize(URL url, ResourceBundle rb) {
          rmfirst.setCellValueFactory(new PropertyValueFactory<Pending,String>("firstName"));
          rmlast.setCellValueFactory(new PropertyValueFactory<Pending,String>("lastName"));
          rmemail.setCellValueFactory(new PropertyValueFactory<Pending,String>("email"));
          ufirst.setCellValueFactory(new PropertyValueFactory<Users,String>("firstName"));
          ulast.setCellValueFactory(new PropertyValueFactory<Users,String>("lastName"));
          uemail.setCellValueFactory(new PropertyValueFactory<Users,String>("email"));
    }   
    
    @FXML private void buildpendingtable()
    {
        if(ptable.getItems().isEmpty())
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
                
                pendingdata.add(new Pending(first, last, email));
            }
               ptable.setItems(pendingdata); //add user to table
               
               //approve a customer (allow him to customers DB)
                   TableColumn<Pending, Pending> appr = rmapprove;
                    appr.setCellValueFactory(
                             param -> new ReadOnlyObjectWrapper<>(param.getValue())
                     );
                    appr.setCellFactory(param -> new TableCell<Pending, Pending>() {
                    private final Button deleteButton = new Button("Approve");
                       @Override
                         protected void updateItem(Pending person, boolean empty) {
                         super.updateItem(person, empty);

                                 if (person == null) {
                                     setGraphic(null);
                                     return;
                                 }

                                 setGraphic(deleteButton);


                                 deleteButton.setOnAction(new EventHandler<ActionEvent>() {
                                     @Override public void handle(ActionEvent e) {
                                         try{
                                          getTableView().getItems().remove(person);
                                          DBManage dbmanager = new DBManage();
                                          Connection con = DriverManager.getConnection(Connect.databaselink, "root", "123456");
                                          PreparedStatement st = con.prepareStatement("select password from pending_accounts where email =?");
                                          st.setString(1, person.getEmail());
                                          ResultSet rs = st.executeQuery();
                                          rs.next();
                                          String password = rs.getString("password");
                                          dbmanager.addtoCustomerTable(person.getFirstName(), person.getLastName(), person.getEmail(), password);
                                          dbmanager.deleteFromPenAccTable(person.getEmail());
                                         }
                                         catch(SQLException ex)
                                         {
                                             
                                         }
                                     }

                                 });
                             }
                         });
                //button for handling denying a customer. 
                
                TableColumn<Pending, Pending> den = deny;
                den.setCellValueFactory(
                         param -> new ReadOnlyObjectWrapper<>(param.getValue())
                 );
                den.setCellFactory(param -> new TableCell<Pending, Pending>() {
                private final Button deleteButton = new Button("Deny");
                   @Override
                     protected void updateItem(Pending person, boolean empty) {
                     super.updateItem(person, empty);

                             if (person == null) {
                                 setGraphic(null);
                                 return;
                             }

                             setGraphic(deleteButton);


                             deleteButton.setOnAction(new EventHandler<ActionEvent>() {
                                 @Override public void handle(ActionEvent e) {
                                      getTableView().getItems().remove(person);
                                      DBManage dbmanager = new DBManage();
                                      dbmanager.deleteFromPenAccTable(person.getEmail());
                                 }

                             });
                         }
                     });

            }
      
           
        
        
        
        catch(SQLException ex)
        {
            ex.printStackTrace();
        }
        }
    }
     @FXML private void buildusertable()
    {
  
        if(utable.getItems().isEmpty()) 
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
                
                 userdata.add(new Users(first, last, email));  
            }
            
            
                utable.setItems(userdata);
           TableColumn<Users, Users> rem = remove;
           rem.setCellValueFactory(
                    param -> new ReadOnlyObjectWrapper<>(param.getValue())
            );
           rem.setCellFactory(param -> new TableCell<Users, Users>() {
           private final Button deleteButton = new Button("Remove");
              @Override
                protected void updateItem(Users person, boolean empty) {
                super.updateItem(person, empty);

                        if (person == null) {
                            setGraphic(null);
                            return;
                        }

                        setGraphic(deleteButton);
                        
                       
                        deleteButton.setOnAction(new EventHandler<ActionEvent>() {
                            @Override public void handle(ActionEvent e) {
                                 getTableView().getItems().remove(person);
                                 DBManage dbmanager = new DBManage();
                                 dbmanager.deleteFromCustomerTable(person.getEmail());
                            }
                            
                        });
                    }
                });
            TableColumn<Users, Users> warning = warn;
           warning.setCellValueFactory(
                    param -> new ReadOnlyObjectWrapper<>(param.getValue())
            );
           warning.setCellFactory(param -> new TableCell<Users, Users>() {
           private final Button warnButton = new Button("Warn");
              @Override
                protected void updateItem(Users person, boolean empty) {
                super.updateItem(person, empty);

                        if (person == null) {
                            setGraphic(null);
                            return;
                        }

                        setGraphic(warnButton);
                        
                       
                        warnButton.setOnAction(new EventHandler<ActionEvent>() {
                            @Override public void handle(ActionEvent e) {
                                try {
                                 DBManage dbmanager = new DBManage();
                                 Connection con = DriverManager.getConnection(Connect.databaselink, "root", "123456");
                                 Statement st = con.createStatement();
                                 st.executeUpdate("update customers set warnings = warnings + 1 where email=" + "'" + person.getEmail() + "'");
                                }
                                catch(SQLException ex)
                                {
                                    
                                }
                            }
                            
                        });
                    }
                });
           
           
           
           
           
         }
         catch(SQLException ex)
         {
             ex.printStackTrace();
         }
        }
    }

    @FXML private void LogOut() throws IOException
    {
        LoginController login = new LoginController();
        login.GoToLogin();
    }
}

