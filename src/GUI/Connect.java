/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.sql.*;
import java.lang.System.*;

import java.lang.StringBuilder;

// This program plus main creates the mariaDB database on your localhost machine.
// This program must be run first before the Resturant system can be run.
// Database name RMS
// Tables
// * Customers
// * Delivery
// * Manager
// * Chef
// * Pending Accounts
// * Dishes
// * Orders
// * Review

// Command to run in quotes
// "javac Connect.java Main.java"
// "java Main Connect"


public class Connect {

	public static String databasename = "RMS";
	public static final String databaselink = "jdbc:mariadb://localhost/" + databasename;
	private Connection con;




	public Connect(String databasename){
		try {

			if (CanConnect()){
				//System.out.println("Successfully connected");
				CreateDatabase(databasename);
				UseDatabase();
				InitializeTables(databasename);
			}
			else
				System.out.println("[-] Could not connect to DB!");
		}

		catch(Exception ex){

			ex.printStackTrace();
        }
	}


	private void InitializeTables(String databasename){
		try {
	    	Statement st = con.createStatement();
	    	st.execute("DROP TABLE IF EXISTS customer;");
	    	st.execute("DROP TABLE IF EXISTS manager;");
	    	st.execute("DROP TABLE IF EXISTS delivery");
				st.execute("DROP TABLE IF EXISTS chef");
				st.execute("DROP TABLE IF EXISTS dishes");
				st.execute("DROP TABLE IF EXISTS orders");
				st.execute("DROP TABLE IF EXISTS review");
        st.execute("DROP TABLE IF EXISTS pending_accounts");
	    	st.execute(CustomerTable());
	    	st.execute(DeliveryTable());
			  st.execute(ManagerTable());
				st.execute(ChefsTable());
        st.execute(PendingAccountsTable());
	    	st.execute(DishesTable());
				st.execute(OrderTable());
				st.execute(reviewsTable());
				st.execute(blacklistTable());
				st.execute("INSERT INTO manager(email, password, fname, lname) VALUES('manager@thinmint.com', '123456', 'Mana', 'Ger');");
        st.execute("INSERT INTO chefs(chef_fname, chef_lname, email, password) VALUES('Miguel', 'Rodriguez','Miguelrodriguez@thinmint.com', '123456');");
        st.execute("INSERT INTO chefs(chef_fname, chef_lname, email, password) VALUES('Galvin', 'Burgos', 'Galvinburgos@thinmint.com', '123456');");
        st.execute("INSERT INTO delivery(email, password, fname, lname) VALUES('Bryandepena@thinmint.com', '123456', 'Bryan', 'De Pena');");
        st.execute("INSERT INTO delivery(email, password, fname, lname) VALUES('Lawrancemedina@thinmint.com', '123456', 'Lawrance', 'Medina');");
				st.execute("INSERT INTO customers(first_name, last_name, email, password) VALUES('Garody', 'Jean Charles', 'garody@customer.com', '1234');");
        st.execute(ShoppingCart());
		}

		catch(Exception ex) {
			System.out.println("ERROR OCCURED");
			ex.printStackTrace();
		}
	}


	private String ChefsTable(){
		StringBuilder str = new StringBuilder();
		str.append("CREATE TABLE chefs(");
		str.append("chef_id int(11) NOT NULL AUTO_INCREMENT,");
		str.append("chef_fname varchar(20),");
		str.append("chef_lname varchar(20),");
		str.append("email varchar(50) NOT NULL,");
		str.append("password varchar(45) NOT NULL,");
		str.append("title varchar(20) DEFAULT 'Entry Level Chef',");
		str.append("salary int DEFAULT 500000,");
		str.append("locked tinyint(1),");
		str.append("standing int DEFAULT 0,");
		str.append("managed_by varchar(30) DEFAULT 'manager@thinmint.com',");
		str.append("PRIMARY KEY(chef_id),");
		str.append("FOREIGN KEY(managed_by) REFERENCES manager(email) ON DELETE CASCADE);");
		return str.toString();
	}

	private String CustomerTable(){
		StringBuilder str = new StringBuilder();
		str.append("CREATE TABLE customers(");
		str.append("customer_id int(11) NOT NULL AUTO_INCREMENT,");
		str.append("first_name varchar(20),");
		str.append("last_name varchar(20),");
    str.append("funds float DEFAULT 500.0,");
		str.append("email varchar(30) NOT NULL,");
		str.append("password varchar(10) NOT NULL,");
		str.append("is_vip tinyint(1) DEFAULT false,");
		str.append("warnings int NOT NULL DEFAULT 0,");
		str.append("num_ords_placed int DEFAULT 0,");
		str.append("dollars_spent decimal DEFAULT 0,");
		str.append("locked tinyint(1) DEFAULT false,");
		str.append("PRIMARY KEY(customer_id),");
		str.append("UNIQUE(email));");
		return str.toString();
	}

	private String ManagerTable(){
		StringBuilder str = new StringBuilder();
		str.append("CREATE TABLE manager(");
		str.append("email varchar(50) NOT NULL,");
		str.append("password varchar(30) NOT NULL,");
		str.append("fname varchar(10),");
		str.append("lname varchar(20),");
		str.append("PRIMARY KEY(email),");
		str.append("UNIQUE(email));");
		return str.toString();
	}

	private String DeliveryTable(){
		StringBuilder str = new StringBuilder();
		str.append("CREATE TABLE delivery(");
		str.append("delivery_id int NOT NULL AUTO_INCREMENT,");
		str.append("fname varchar(15),");
		str.append("lname varchar(20),");
		str.append("email varchar(30) NOT NULL,");
		str.append("password varchar(30) NOT NULL,");
		str.append("locked tinyint(1) NOT NULL DEFAULT false,");
		str.append("standing int(11) DEFAULT 0,");
		str.append("PRIMARY KEY(delivery_id),");
		str.append("UNIQUE(email));");
		return str.toString();
	}

	private String OrderTable(){
			StringBuilder str = new StringBuilder();
			str.append("CREATE TABLE orders(");
			str.append("order_id int NOT NULL AUTO_INCREMENT,");
			str.append("customer_id int,");
			str.append("order_date datetime NOT NULL,");
			str.append("total_price float NOT NULL,");
			str.append("delivered tinyint(1),");
      str.append("address varchar(500),");
			str.append("dish_id int,");
			str.append("delivery_id int,");
			str.append("PRIMARY KEY(order_id),");
			str.append("FOREIGN KEY(customer_id) REFERENCES customers(customer_id) ON DELETE CASCADE,");
			str.append("FOREIGN KEY(dish_id) REFERENCES dishes(dish_id) ON DELETE SET NULL,");
			str.append("FOREIGN KEY(delivery_id) REFERENCES delivery(delivery_id) ON DELETE SET NULL);");
			return str.toString();
	}

	private String DishesTable(){
		StringBuilder str = new StringBuilder();
		str.append("CREATE TABLE dishes(");
		str.append("dish_id int NOT NULL AUTO_INCREMENT,");
		str.append("dish_name varchar(20) NOT NULL,");
		str.append("chef_by int,");
		str.append("price decimal(5,2) NOT NULL,");
		str.append("type varchar(10) NOT NULL,");
		str.append("description varchar(500),");
		str.append("pic_location varchar(250) NOT NULL,");
		str.append("FOREIGN KEY(chef_by) REFERENCES chefs(chef_id) ON DELETE CASCADE,");
		str.append("PRIMARY KEY(dish_id),");
		str.append("UNIQUE(dish_name));");
		return str.toString();
	}

  private String PendingAccountsTable(){
      StringBuilder str = new StringBuilder();
      str.append("CREATE TABLE pending_accounts(");
			str.append("pen_acc_id int(11) NOT NULL AUTO_INCREMENT,");
			str.append("email varchar(30),");
			str.append("first_name varchar(20),");
			str.append("last_name varchar(20),");
			str.append("password varchar(20),");
			str.append("PRIMARY KEY(pen_acc_id),");
			str.append("UNIQUE(email));");
      return str.toString();
  }

    private String ShoppingCart()
    {
        StringBuilder str = new StringBuilder();
        str.append("CREATE TABLE shopping_cart(");
        str.append("dish_name varchar(20),");
        str.append("dish_price decimal(5));");
        return str.toString();
    }

	private String reviewsTable(){
		StringBuilder str = new StringBuilder();
		str.append("CREATE TABLE reviews(");
		str.append("review_id int(11) NOT NULL AUTO_INCREMENT,");
		str.append("stars tinyint(5),");
		str.append("review varchar(100),");
		str.append("dish_id int,");
		str.append("chef_id int,");
		str.append("PRIMARY KEY(review_id),");
		str.append("FOREIGN KEY(dish_id) REFERENCES dishes(dish_id) ON DELETE CASCADE,");
		str.append("FOREIGN KEY(chef_id) REFERENCES chefs(chef_id) ON DELETE CASCADE);");
		return str.toString();
	}

	private String blacklistTable()
	{
			StringBuilder str = new StringBuilder();
			str.append("CREATE TABLE blacklist(");
			str.append("blacklist_id int NOT NULL AUTO_INCREMENT,");
			str.append("fname varchar(15),");
			str.append("lname varchar(20),");
			str.append("email varchar(50),");
			str.append("PRIMARY KEY(blacklist_id));");
			return str.toString();
	}

	private void CreateDatabase(String DBName){
    try {
	    Statement st = con.createStatement();
	    st.execute("DROP DATABASE IF EXISTS " + this.databasename + ";");
	    st.execute("CREATE DATABASE " + this.databasename + ";");

			System.out.println("[+] Database created");
        }
        catch (Exception ex) {
            System.out.println("ERROR OCCURED.");
            ex.printStackTrace();
        }
    }

  private void UseDatabase(){
    try {

	    Statement st = con.createStatement();
	   	st.execute("USE RMS;");
	   }
        catch (Exception ex) {
            System.out.println("ERROR OCCURED.");
            ex.printStackTrace();
        }
  }

  private boolean CanConnect(){

    try {
      try {
        Class.forName("org.mariadb.jdbc.Driver");
        System.out.println("Driver loaded");
      }
      catch (Exception ex) {
        //System.out.println(" CLASS NOT FOUND EXCEPTION .");
      }
      con = DriverManager.getConnection("jdbc:mariadb://localhost/", "root", "123456");
      return true;
    }
    catch (Exception ex) {
        return false;
    }

  }

  public void closeDB() throws SQLException {
    con.close();
  }

} // end class Connect
