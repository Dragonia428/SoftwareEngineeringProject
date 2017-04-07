import java.sql.*;
public class Connect {
	public Connect(String databasename)
	{
		CreateDatabase("RMS");
		UseDatabase("RMS");
	}
	private void CreateDatabase(String DBName)
	{

    try {
     
        Class.forName("com.mysql.jdbc.Driver");
	    Connection con = DriverManager.getConnection("jdbc:mysql://localhost", "root", "123456");    
	    Statement st = con.createStatement();
	    st.execute("DROP DATABASE IF EXISTS RMS;");
	    st.execute("CREATE DATABASE RMS;");
    }
    catch (Exception ex) {
        System.out.println("ERROR OCCURED.");
        ex.printStackTrace();
    }
  }
  private void UseDatabase(String DBName)
  {

    try {
     
        Class.forName("com.mysql.jdbc.Driver");
	    Connection con = DriverManager.getConnection("jdbc:mysql://localhost", "root", "123456");    
	    Statement st = con.createStatement();
	   	st.execute("USE " + DBName + ";");
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