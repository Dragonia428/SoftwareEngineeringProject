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
    @FXML TableColumn status,remove; 
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
        System.out.println(utable.getItems().size());
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
class StatCell extends TableCell<Disposer.Record, Boolean> {
        final Button cellButton = new Button("Status");
        public static String userfirst;
        public static String userlast;
        public static String useremail; 
        StatCell(){
            
        	//Action when the button is pressed
            cellButton.setOnAction(new EventHandler<ActionEvent>(){

                @Override
                public void handle(ActionEvent t) {
                    // get Selected Item
                        UserstatusController ucon = new UserstatusController();
                        Users users;
                        users = (Users) StatCell.this.getTableView().getItems().get(StatCell.this.getIndex());
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
        protected void updateItem(Boolean p, boolean empty) {
            super.updateItem(p, empty);
            if(!empty){
                setGraphic(cellButton);
                
            }
            
        }
}
class RemCell extends TableCell<Disposer.Record, Boolean> {
        final Button cellButton = new Button("Remove");
        RemCell(){
            
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
             curruser = (Users) RemCell.this.getTableView().getItems().get(RemCell.this.getIndex());
             int index = RemCell.this.getTableRow().getIndex();
             DBManage dbmanager = new DBManage();
             RMTabController.userdata.remove(index);
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
class Approve extends TableCell<Disposer.Record, Boolean> {
        final Button cellButton = new Button("Approve");
        Approve(){
            
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
                        currentPerson = (Pending) Approve.this.getTableView().getItems().get(Approve.this.getIndex());
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
                	RMTabController.pendingdata.remove(currentPerson);
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
class Deny extends TableCell<Record, Boolean>
{
     final Button cellButton2 = new Button("Deny");
    public Deny() {
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
            currentPerson = (Pending) Deny.this.getTableView().getItems().get(Deny.this.getIndex());
            RMTabController.pendingdata.remove(currentPerson);
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