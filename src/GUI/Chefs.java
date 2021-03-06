/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author setti
 */
public class Chefs {
    public static int chef_id;
    public static String chef_email;
    public static String chef_fname;
    public static String chef_lname;
    public static String title;
    public static int salary; 
    public static int standing; 
    public static boolean locked;
    private SimpleStringProperty firstName = new SimpleStringProperty("");
    private SimpleStringProperty lastName = new SimpleStringProperty("");
    private SimpleStringProperty email = new SimpleStringProperty("");
    private SimpleBooleanProperty approve = new SimpleBooleanProperty(false);
 
    public Chefs(String firstName, String lastName, String email) {
        setFirstName(firstName);
        setLastName(lastName);
        setEmail(email);
    }

    public String getFirstName() {
        return firstName.get();
    }
 
    public void setFirstName(String fName) {
        firstName.set(fName);
    }
        
    public String getLastName() {
        return lastName.get();
    }
    
    public void setLastName(String fName) {
        lastName.set(fName);
    }
    
    public String getEmail() {
        return email.get();
    }
    
    public void setEmail(String fName) {
        email.set(fName);
    }
    public boolean getApprove()
    {
        return approve.get();
    }
    public void setApprove(boolean val)
    {
        approve.set(val);
    }
    
}


