/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import static GUI.Main.x;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import java.sql.*;
/**
 * FXML Controller class
 *
 * @author setti
 */
public class LoginController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML TextField textfield;
    @FXML PasswordField passwordfield; 
    Connection con;
    public static boolean logged_in = false;
    DBManage dbmanage = new DBManage();
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    public void GoToLogin() throws IOException
    {
        logged_in = false; 
        Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
            Scene scene = new Scene(root);
            Main.x.setScene(scene);
            Main.x.show();
    }
    @FXML private void OnLogin() throws IOException
    {
        String email = textfield.getText();
        String password = passwordfield.getText();
        if(isBlacklist(email)){
            //update label to say "Sorry your account is locked. Please consult with the manager."
        }
        else if(isManager(email, password)) GoToRM();
        else if(isChef(email, password)) GoToChefs();
        else if(isDP(email, password)) GoToDP();
        else if(isUser(email, password)) GoToMenu();
    }
    
    private boolean isBlacklist(String email) throws IOException{
        try{
            Connection con = DriverManager.getConnection(Connect.databaselink, "root", "123456");
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT email FROM blacklist");
            if(rs.next()){
                String em = rs.getString("email");
                return email.equals(em);
            }
            else{
                return false;
            }
        }
        catch(SQLException ex){
            ex.printStackTrace();
            return false;
        }
    }

    private boolean isManager(String email, String password)
    {
        try {
        Connection con = DriverManager.getConnection(Connect.databaselink, "root", "123456");
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery("SELECT email, password FROM manager");
        rs.next();
        String em = rs.getString("email");
        String pass = rs.getString("password");
        return email.equals(em) && password.equals(pass);
        }
        catch(SQLException ex)
        {
            ex.printStackTrace();
            return false;
        }
    }
     private boolean isChef(String email, String password)
    {
        try {
            Connection con = DriverManager.getConnection(Connect.databaselink, "root", "123456");
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT email, password, locked FROM chefs");
            while(rs.next())
            {
                String em = rs.getString("email");
                String pass = rs.getString("password");
                boolean locked = rs.getBoolean("locked");
                if(email.equals(em) && password.equals(pass) && locked == false) return true;
            }
            return false; 
        }
        catch(SQLException ex)
        {
            ex.printStackTrace();
            return false;
        }
    }
     private boolean isDP(String email, String password)
    {
        try {
        Connection con = DriverManager.getConnection(Connect.databaselink, "root", "123456");
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery("SELECT email, password, locked FROM delivery");
           while(rs.next())
            {
                String em = rs.getString("email");
                String pass = rs.getString("password");
                boolean locked = rs.getBoolean("locked");
                if(email.equals(em) && password.equals(pass) && locked == false) return true;
            }
            return false;
        }
        catch(SQLException ex)
        {
            ex.printStackTrace();
            return false;
        }
    }
     
     private boolean isUser(String email, String password)
    {
        try {
        Connection con = DriverManager.getConnection(Connect.databaselink, "root", "123456");
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery("SELECT email, password, locked FROM customers");
           while(rs.next())
            {
                String em = rs.getString("email");
                String pass = rs.getString("password");
                boolean locked = rs.getBoolean("locked");
                if(email.equals(em) && password.equals(pass) && locked == false) return true;
            }
            return false;
        }
        catch(SQLException ex)
        {
            ex.printStackTrace();
            return false;
        }
    }
     
    @FXML private void GoToRegistration() throws IOException
    {
          try {
            
            Parent root = FXMLLoader.load(getClass().getResource("register.fxml"));
            Scene scene = new Scene(root);
            Main.x.setScene(scene);
         }
         catch(IOException ex)
         {
            ex.printStackTrace();
         }
    } 
    private void GoToRM() throws IOException
    {
            logged_in=true;
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("RMTab.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            Scene scene = new Scene(root);
            Main.x.setScene(scene);
    }
    private void GoToChefs() throws IOException
    {
        logged_in = true; 
        ResultSet rs = dbmanage.queryDatabase("select chef_fname, chef_lname, email, title, salary, standing, locked from chefs where email="+textfield.getText()+";");
        try{
            rs.next();
            Chefs.chef_fname = rs.getString("chef_fname");
            Chefs.chef_lname = rs.getString("chef_lname");
            Chefs.email = rs.getString("email");
            Chefs.title = rs.getString("title");
            Chefs.salary = rs.getInt("salary");
            Chefs.standing = rs.getInt("standing");
            Chefs.locked = rs.getBoolean("locked");
        }
        catch(SQLException ex){
            ex.printStackTrace();
        }
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("rmchef.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            Scene scene = new Scene(root);
            Main.x.setScene(scene);
        
    }
    private void GoToDP() throws IOException
    {
        logged_in = true;
        ResultSet rs = dbmanage.queryDatabase("select fname, lname, email, standing, locked from delivery where email='"+textfield.getText()+"';");
        try{
            rs.next();
            DeliveryInfo.fname = rs.getString("fname");
            DeliveryInfo.lname = rs.getString("lname");
            DeliveryInfo.email = rs.getString("email");
            DeliveryInfo.standing = rs.getInt("standing");
            DeliveryInfo.locked = rs.getBoolean("locked");
        }
        catch(SQLException ex){
            ex.printStackTrace();
        }
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Delivery.fxml"));
        Parent root = (Parent) fxmlLoader.load();
        Scene scene = new Scene(root);
        Main.x.setScene(scene);
    }
    private void GoToMenu() throws IOException
    {
        logged_in = true;
        ResultSet rs = dbmanage.queryDatabase("select first_name, last_name, email from customers where email="+textfield.getText()+";");
        try{
            rs.next();
            UserInfo.first_name = rs.getString("first_name");
            UserInfo.last_name = rs.getString("lname");
            UserInfo.email = rs.getString("email");
        }
        catch(SQLException ex){
            ex.printStackTrace();
        }
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("menu.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            Scene scene = new Scene(root);
            Main.x.setScene(scene);
    }
    
}
