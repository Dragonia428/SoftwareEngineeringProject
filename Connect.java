import java.sql.*;
public class Connect {
	public String databasename = "RMS";
	public final String databaselink = "jdbc:mysql://localhost/" + databasename;
	public Connect(String databasename)
	{
		try {
			InitializeTables(databasename);
		}
		catch(Exception ex)
		{
			ex.printStackTrace(); 
		}
	}
	private void InitializeTables(String databasename)
	{
		try {
			 Class.forName("com.mysql.jdbc.Driver");
	    	Connection con = DriverManager.getConnection(databaselink, "root", "123456");    
	    	Statement st = con.createStatement();
	    	st.execute("DROP TABLE IF EXISTS chefs;");
	    	st.execute("DROP TABLE IF EXISTS users;");
	    	st.execute("DROP TABLE IF EXISTS dishes");
	    	st.execute(UserTable());
	    	st.execute(ChefsTable());
	    	st.execute("CREATE INDEX cname ON chefs(chef_name)");
	    	st.execute(DishesTable());
		}
		catch(Exception ex)
		{
			System.out.println("ERROR OCCURED");
			ex.printStackTrace();
		}
		

	}
	private String ChefsTable()
	{
		StringBuilder str = new StringBuilder();
		str.append("CREATE TABLE chefs(chef_name varchar(20), title varchar(11), pay int(6));");
		return str.toString();
	}
	private String UserTable()
	{
		StringBuilder str = new StringBuilder();
		str.append("CREATE TABLE users(id int(11) NOT NULL AUTO_INCREMENT,");
		str.append("first_name varchar(20), last_name varchar(20), email varchar(26), password varchar(10), status varchar(3), PRIMARY KEY(id));");
		return str.toString();	
	}
	private String DishesTable()
	{
		StringBuilder str = new StringBuilder();
		str.append("CREATE TABLE dishes(chef_name varchar(20), dish_name varchar(20), price int(5), type varchar(10), FOREIGN KEY(chef_name) REFERENCES chefs(chef_name));");
		return str.toString();
	}
	private void CreateDatabase(String DBName)
	{

    try {
     
        Class.forName("com.mysql.jdbc.Driver");
	    Connection con = DriverManager.getConnection("jdbc:mysql://localhost", "root", "123456");    
	    Statement st = con.createStatement();
	    this.databasename = DBName;
	    st.execute("DROP DATABASE IF EXISTS " + DBName + ";");
	    st.execute("CREATE DATABASE " + DBName + ";");
    }
    catch (Exception ex) {
        System.out.println("ERROR OCCURED.");
        ex.printStackTrace();
    }
  }
  private void UseDatabase()
  {

    try {
     
        Class.forName("com.mysql.jdbc.Driver");
	    Connection con = DriverManager.getConnection("jdbc:mysql://localhost", "root", "123456");    
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
      Connection con = DriverManager.getConnection("jdbc:mysql://localhost/", "root", "123456");    
      return true;
    }
    catch (Exception ex) {
        return false;
    }
  	}
  

	
}
