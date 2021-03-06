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
import java.util.Set;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
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
   
    @FXML TableView ptable, utable, ctable, dtable; 
    @FXML TableColumn warn,remove; 
    @FXML TableColumn rmfirst, rmlast, rmemail, rmapprove, deny;
    @FXML TableColumn ufirst, ulast, uemail;
    @FXML TableColumn cfirst, clast, cemail, cstatus, cremove; 
    @FXML TableColumn dfirst, dlast, demail, dstatus, dremove; 
    static ObservableList<Users> userdata = FXCollections.observableArrayList();
    static ObservableList<Pending> pendingdata = FXCollections.observableArrayList(); 
    static ObservableSet<Chefs> test3 = FXCollections.observableSet();
    ObservableSet<Pending> test = FXCollections.observableSet(); 
    ObservableSet<Users> test2 = FXCollections.observableSet();
    ObservableSet<DeliveryInfo> test4 = FXCollections.observableSet();
    @Override
    public void initialize(URL url, ResourceBundle rb) {
          rmfirst.setCellValueFactory(new PropertyValueFactory<Pending,String>("firstName"));
          rmlast.setCellValueFactory(new PropertyValueFactory<Pending,String>("lastName"));
          rmemail.setCellValueFactory(new PropertyValueFactory<Pending,String>("email"));
          ufirst.setCellValueFactory(new PropertyValueFactory<Users,String>("firstName"));
          ulast.setCellValueFactory(new PropertyValueFactory<Users,String>("lastName"));
          uemail.setCellValueFactory(new PropertyValueFactory<Users,String>("email"));
          cfirst.setCellValueFactory(new PropertyValueFactory<Users,String>("firstName"));
          clast.setCellValueFactory(new PropertyValueFactory<Users,String>("lastName"));
          cemail.setCellValueFactory(new PropertyValueFactory<Users,String>("email"));
          dfirst.setCellValueFactory(new PropertyValueFactory<Users,String>("firstName"));
          dlast.setCellValueFactory(new PropertyValueFactory<Users,String>("lastName"));
          demail.setCellValueFactory(new PropertyValueFactory<Users,String>("email"));
          ptable.getColumns().removeAll(test);
          utable.getColumns().removeAll(test2);
    }   

    @FXML private void buildpendingtable()
    {
        
        try {
            if(test.isEmpty())
            {
            Connection con = DriverManager.getConnection(Connect.databaselink, "root", "123456");
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select first_name, last_name, email from pending_accounts");
            while(rs.next())
            {
                 String first = rs.getString("first_name");
                 String last = rs.getString("last_name");
                 String email = rs.getString("email");
                
                test.add(new Pending(first, last, email));
            }
               
               ptable.getItems().addAll(test); //add user to table
               
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
            }
      
           
        
        
        
        catch(SQLException ex)
        {
            ex.printStackTrace();
        }
        }
    
     @FXML private void buildusertable()
    {
  
        
         try {
            if(test2.isEmpty())
            {
            Connection con = DriverManager.getConnection(Connect.databaselink, "root", "123456");
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select first_name, last_name, email from customers where warnings >= 3");
            while(rs.next())
            {
                 String first = rs.getString("first_name");
                 String last = rs.getString("last_name");
                 String email = rs.getString("email");
                
                 test2.add(new Users(first, last, email));  
            }


           utable.getItems().addAll(test2);
          

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
           private final Button warnButton = new Button("Resolve");
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
                                 st.executeUpdate("update customers set warnings = 0 where email="+person.getEmail());
                                }
                                catch(SQLException ex)
                                {
                                    
                                }
                            }
                            
                        });
                    }
                });
           
           
            }
           
           
         }
         catch(SQLException ex)
         {
             ex.printStackTrace();
         }
        
    }
    @FXML private void buildcheftable()
    {
  
        
         try {
            if(test3.isEmpty())
            {
            Connection con = DriverManager.getConnection(Connect.databaselink, "root", "123456");
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select chef_fname, chef_lname, email from chefs where standing = -3");
            while(rs.next())
            {
                 String first = rs.getString("chef_fname");
                 String last = rs.getString("chef_lname");
                 String email = rs.getString("email");
                
                 test3.add(new Chefs(first, last, email));  
            }


           ctable.getItems().addAll(test3);
          

           TableColumn<Chefs, Chefs> rem = cremove;
           rem.setCellValueFactory(
                    param -> new ReadOnlyObjectWrapper<>(param.getValue())
            );
           rem.setCellFactory(param -> new TableCell<Chefs, Chefs>() {
           private final Button deleteButton = new Button("Remove");
              @Override
                protected void updateItem(Chefs person, boolean empty) {
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
                                 dbmanager.deleteFromChefTable(person.getEmail());
                                 dbmanager.addtoBlacklistTable(person.getFirstName(), person.getLastName(), person.getEmail());
                            }
                            
                        });
                    }
                });
            TableColumn<Users, Users> warning = warn;
           warning.setCellValueFactory(
                    param -> new ReadOnlyObjectWrapper<>(param.getValue())
            );
           warning.setCellFactory(param -> new TableCell<Users, Users>() {
           private final Button warnButton = new Button("Resolve");
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
                                 st.executeUpdate("update customers set warnings = 0 where email="+person.getEmail());
                                }
                                catch(SQLException ex)
                                {
                                    
                                }
                            }
                            
                        });
                    }
                });
           
           
            }
           
           
         }
         catch(SQLException ex)
         {
             ex.printStackTrace();
         }
        
    }
    @FXML private void builddeliverytable()
    {
  
        
         try {
            if(test4.isEmpty())
            {
            Connection con = DriverManager.getConnection(Connect.databaselink, "root", "123456");
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select fname, lname, email from delivery where standing =< -3");
            while(rs.next())
            {
                 String first = rs.getString("fname");
                 String last = rs.getString("lname");
                 String email = rs.getString("email");
                
                 test4.add(new DeliveryInfo(first, last, email));  
            }


           dtable.getItems().addAll(test4);
          

           TableColumn<DeliveryInfo, DeliveryInfo> drem = dremove;
           drem.setCellValueFactory(
                    param -> new ReadOnlyObjectWrapper<>(param.getValue())
            );
           drem.setCellFactory(param -> new TableCell<DeliveryInfo, DeliveryInfo>() {
           private final Button deleteButton = new Button("Remove");
              @Override
                protected void updateItem(DeliveryInfo person, boolean empty) {
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
                                 dbmanager.deleteFromDeliverTable(person.getEmail());
                            }
                            
                        });
                    }
                });
            TableColumn<DeliveryInfo, DeliveryInfo> dresolve = dstatus;
           dresolve.setCellValueFactory(
                    param -> new ReadOnlyObjectWrapper<>(param.getValue())
            );
           dresolve.setCellFactory(param -> new TableCell<DeliveryInfo, DeliveryInfo>() {
           private final Button warnButton = new Button("Resolve");
              @Override
                protected void updateItem(DeliveryInfo person, boolean empty) {
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
                                 st.executeUpdate("update delivery set standing = 0 where email="+person.getEmail());
                                }
                                catch(SQLException ex)
                                {
                                    
                                }
                            }
                            
                        });
                    }
                });
           
           
            }
           
           
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
}

