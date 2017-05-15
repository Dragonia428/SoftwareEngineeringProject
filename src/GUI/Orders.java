/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author setti
 */
public class Orders {
    public SimpleStringProperty ZIP = new SimpleStringProperty("");
    public SimpleStringProperty address = new SimpleStringProperty("");
    public SimpleStringProperty email = new SimpleStringProperty("");
    public Orders()
    {
        new Orders("", "", "");
    }
    public Orders(String ZIP, String address, String email)
    {
        SetEmail(email);
        SetZIP(ZIP);
        SetAddress(address);
    }
    public void SetEmail(String newValue)
    {
        email.set(newValue);
    }
    public void SetZIP(String newValue)
    {
        ZIP.set(newValue);
    }
    public void SetAddress(String newValue)
    {
        address.set(newValue);
    }
    public String GetEmail()
    {
        return email.get();
    }
    public String GetZIP()
    {
        return ZIP.get();
    }
    public String GetAdress()
    {
        return address.get();
    }
    
}
