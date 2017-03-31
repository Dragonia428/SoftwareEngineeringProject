import java.sql.*;
public class Connect {
	public static void main(String[] args)
	{
		Connect c = new Connect();
		c.CreateDatabase();
	}
	private void CreateDatabase()
	{

    try {
     
        //Class.forName("com.mysql.jdbc.Driver");
        //System.out.println("Driver loaded");
     
	    Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1", "root", "123456");    

	 //   System.out.println(" CONNECTED TO MySQL DB......");
	    Statement st = con.createStatement();
	    st.execute();
	    st.executeQuery("USE RMS;");
	    st.executeUpdate("");
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