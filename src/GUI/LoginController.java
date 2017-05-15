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
        if(isManager(email, password)) GoToRM();
        else if(isChef(email, password)) GoToChefs();
        else if(isDP(email, password)) GoToDP();
        else if(isUser(email, password)) GoToMenu();
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
            ResultSet rs = st.executeQuery("SELECT email, password FROM chefs");
            while(rs.next())
            {
                String em = rs.getString("email");
                String pass = rs.getString("password");
                if(email.equals(em) && password.equals(pass)) return true;
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
        ResultSet rs = st.executeQuery("SELECT email, password FROM delivery");
           while(rs.next())
            {
                String em = rs.getString("email");
                String pass = rs.getString("password");
                if(email.equals(em) && password.equals(pass)) return true;
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
        ResultSet rs = st.executeQuery("SELECT email, password FROM customers");
           while(rs.next())
            {
                String em = rs.getString("email");
                String pass = rs.getString("password");
                if(email.equals(em) && password.equals(pass)) return true;
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
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("rmmenu.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            Scene scene = new Scene(root);
            Main.x.setScene(scene);
    }
    private void GoToChefs()
    {
        logged_in = true; 
    }
    private void GoToDP() throws IOException
    {
        logged_in = true;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("DeliveryPerson.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            Scene scene = new Scene(root);
            Main.x.setScene(scene);
    }
    private void GoToMenu() throws IOException
    {
        logged_in = true;
        UserInfo.email = textfield.getText();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("menu.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            Scene scene = new Scene(root);
            Main.x.setScene(scene);
    }
}
