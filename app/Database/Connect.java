import java.sql.*;
import java.lang.System.*;


public class Connect {
    
	public String databasename = "RMS";
	public final String databaselink = "jdbc:mysql://localhost/" + databasename;
	private Connection con;
    
	public Connect()
	{
		try{
			if (CanConnect()){
				System.out.println("Successfully connected");
				CreateDatabase(databasename);
				UseDatabase();
				InitializeTables(databasename);
			}
			else
				System.out.println("[-] Could not connect to DB!");
		}
		catch(Exception ex) {
			ex.printStackTrace();
        }
	}
    
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
	    	st.execute(DishesTable());
		}
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
			System.out.println("[+] Database created");
        }
        catch (Exception ex) {
            System.out.println("ERROR OCCURED.");
            ex.printStackTrace();
        }
    }
    
    private void UseDatabase()
    {
        try{
	    Statement st = con.createStatement();
	   	st.execute("USE RMS;");
	   }
        catch (Exception ex) {
            System.out.println("ERROR OCCURED.");
            ex.printStackTrace();
        }
  }
    
  private boolean CanConnect()
  {
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
  }

} // end class Connect
