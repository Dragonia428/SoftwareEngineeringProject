/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 *
 * @author Moomookittyclam
 */
//For the current dish 
public class DishInfo {
    static int dish_id;
    static String dish_name;
    static Double dish_price; 
    public static double round(double value, int places) {
    if (places < 0) throw new IllegalArgumentException();

    BigDecimal bd = new BigDecimal(value);
    bd = bd.setScale(places, RoundingMode.HALF_UP);
    return bd.doubleValue();
}
}
