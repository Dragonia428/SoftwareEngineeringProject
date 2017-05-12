import java.sql.*;
import java.lang.System.*;
<<<<<<< HEAD

=======
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
>>>>>>> master

public class Connect {
    
	public String databasename = "RMS";
	public final String databaselink = "jdbc:mysql://localhost/" + databasename;
	private Connection con;
<<<<<<< HEAD
    
	public Connect()
	{
		try{
=======

	public Connect(String databasename){
		try {
>>>>>>> master
			if (CanConnect()){
				System.out.println("Successfully connected");
				CreateDatabase(databasename);
				UseDatabase();
				InitializeTables(databasename);
			}
			else
				System.out.println("[-] Could not connect to DB!");
		}
<<<<<<< HEAD
		catch(Exception ex) {
=======
		catch(Exception ex){
>>>>>>> master
			ex.printStackTrace();
        }
	}
<<<<<<< HEAD
    
	private void InitializeTables(String databasename)
	{
		try {
	    	Statement st = con.createStatement();
	    	st.execute("DROP TABLE IF EXISTS chefs;");
	    	st.execute("DROP TABLE IF EXISTS users;");
	    	st.execute("DROP TABLE IF EXISTS dishes");
        	st.execute("DROP TABLE IF EXISTS pending_accounts");
	    	st.execute(UserTable());
	    	st.execute(ChefsTable());
        	st.execute(PendingAccountsTable());
	    	st.execute("CREATE INDEX cname ON chefs(chef_name)");
=======

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
>>>>>>> master
	    	st.execute(DishesTable());
				st.execute(OrderTable());
				st.execute(reviewsTable());
				st.execute("INSERT INTO manager(email, password, fname, lname) VALUES('manager@manager.com', '123456', 'Mana', 'Ger')");
		}
<<<<<<< HEAD
		catch(Exception ex) {
			System.out.println("ERROR OCCURED");
			ex.printStackTrace();
		}
	}
    
	private String ChefsTable()
	{
		StringBuilder str = new StringBuilder();
		str.append("CREATE TABLE chefs(chef_name VARCHAR(20), title VARCHAR(11), pay int(6));");
		return str.toString();
	}
    
	private String UserTable()
	{
		StringBuilder str = new StringBuilder();
		str.append("CREATE TABLE users(id int(11) NOT NULL AUTO_INCREMENT, first_name VARCHAR(20),");
		str.append("last_name VARCHAR(20), email VARCHAR(30), username VARCHAR(30), ");
        str.append("password VARCHAR(10), status BOOLEAN DEFAULT false, PRIMARY KEY(id), UNIQUE(username));");
		return str.toString();
	}
    
	private String DishesTable()
	{
		StringBuilder str = new StringBuilder();
		str.append("CREATE TABLE dishes(chef_name VARCHAR(20), dish_name VARCHAR(20),");
        str.append("price int(5), type VARCHAR(10), FOREIGN KEY(chef_name) REFERENCES chefs(chef_name));");
		return str.toString();
	}
    
    private String PendingAccountsTable() {
        StringBuilder str = new StringBuilder();
        str.append("CREATE TABLE pending_accounts(id int(11) NOT NULL AUTO_INCREMENT,");
        str.append("first_name VARCHAR(20), last_name VARCHAR(20), email VARCHAR(30), username VARCHAR(30),");
        str.append("password VARCHAR(20), PRIMARY KEY(id), UNIQUE(username));");
        return str.toString();
    }
    
	private void CreateDatabase(String DBName)
	{
        try {
            Statement st = con.createStatement();
            st.execute("DROP DATABASE IF EXISTS " + this.databasename + ";");
            st.execute("CREATE DATABASE " + this.databasename + ";");
=======
		catch(Exception ex){
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
		str.append("title varchar(20),");
		str.append("pay decimal(6),");
		str.append("locked tinyint(1),");
		str.append("standing int,");
		str.append("managed_by varchar(30),");
		str.append("PRIMARY KEY(chef_id),");
		str.append("FOREIGN KEY(managed_by) REFERENCES manager(email) ON DELETE CASCADE);");
		return str.toString();
	}

	private String CustomerTable(){
		StringBuilder str = new StringBuilder();
		str.append("CREATE TABLE customer(");
		str.append("customer_id int(11) NOT NULL AUTO_INCREMENT,");
		str.append("first_name varchar(20),");
		str.append("last_name varchar(20),");
		str.append("email varchar(30) NOT NULL,");
		str.append("password varchar(10) NOT NULL,");
		str.append("is_vip tinyint(1),");
		str.append("warnings int NOT NULL,");
		str.append("num_ords_placed int,");
		str.append("dollars_spent decimal,");
		str.append("locked tinyint(1),");
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
		str.append("locked tinyint(1) NOT NULL,");
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
			str.append("dish_id int,");
			str.append("delivery_id int,");
			str.append("PRIMARY KEY(order_id),");
			str.append("FOREIGN KEY(customer_id) REFERENCES customer(customer_id) ON DELETE CASCADE,");
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
		str.append("price decimal(5) NOT NULL,");
		str.append("type varchar(10) NOT NULL,");
		str.append("description varchar(100),");
		str.append("pic_location varchar(50) NOT NULL,");
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
			str.append("manager_email varchar(50),");
			str.append("PRIMARY KEY(pen_acc_id),");
			str.append("FOREIGN KEY(manager_email) REFERENCES manager(email) ON DELETE CASCADE,");
			str.append("UNIQUE(email));");
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

	private void CreateDatabase(String DBName){
    try {
	    Statement st = con.createStatement();
	    st.execute("DROP DATABASE IF EXISTS " + this.databasename + ";");
	    st.execute("CREATE DATABASE " + this.databasename + ";");
>>>>>>> master
			System.out.println("[+] Database created");
        }
        catch (Exception ex) {
            System.out.println("ERROR OCCURED.");
            ex.printStackTrace();
        }
    }
<<<<<<< HEAD
    
    private void UseDatabase()
    {
        try{
=======
    catch (Exception ex) {
        System.out.println("ERROR OCCURED.");
        ex.printStackTrace();
    }
  }

  private void UseDatabase(){
    try {
>>>>>>> master
	    Statement st = con.createStatement();
	   	st.execute("USE RMS;");
	   }
        catch (Exception ex) {
            System.out.println("ERROR OCCURED.");
            ex.printStackTrace();
        }
  }
<<<<<<< HEAD
    
  private boolean CanConnect()
  {
=======

  private boolean CanConnect(){
>>>>>>> master
    try {
      try {
        Class.forName("com.mysql.jdbc.Driver");
        System.out.println("Driver loaded");
      }
      catch (Exception ex) {
        System.out.println(" CLASS NOT FOUND EXCEPTION .");
      }
      con = DriverManager.getConnection(databaselink, "root", "123456");
      return true;
    }
    catch (Exception ex) {
        return false;
    }
<<<<<<< HEAD
  }

} // end class Connect
=======
	}
}
>>>>>>> master
