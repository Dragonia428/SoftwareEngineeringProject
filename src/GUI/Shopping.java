/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleDoubleProperty;

/**
 *
 * @author setti
 */

public class Shopping {
    final SimpleStringProperty item = new SimpleStringProperty();
    final SimpleStringProperty price = new SimpleStringProperty();
    public Shopping()
    {
        new Shopping("", "");
    }
    public Shopping(String itm, String food)
    {
        setItem(itm);
        setPrice(food);
    }
    String getPrice() {return price.get(); }
    String getItem() {return item.get(); }
    void setPrice(String pr) {price.set(pr);}
    void setItem(String i) {item.set(i);}
}
