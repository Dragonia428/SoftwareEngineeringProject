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
public class Pending<T0, T1> {

    public Pending() {
        this("", "", "");
    }
    public SimpleStringProperty firstName = new SimpleStringProperty("");
    public SimpleStringProperty lastName = new SimpleStringProperty("");
    public SimpleStringProperty email = new SimpleStringProperty("");
    public SimpleBooleanProperty approve = new SimpleBooleanProperty(false);
 
    public Pending(String firstName, String lastName, String email) {
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
