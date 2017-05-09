import java.sql.*;
import java.lang.System.*;
import java.lang.StringBuilder;


public class DBManage
{
  private Connection con;

  public DBManage(){
    try{
      if(canConnect()){
        System.out.println("[+] Successfully logged into Database");
        useDatabate();
      }
      else{
        System.out.println("[-] Could not connect to DB!");
      }
    }
    catch(Exception ex){
      ex.printStackTrace();
    }
  }

  private boolean canConnect(){
    try {
      try {
        Class.forName("org.mariadb.jdbc.Driver");
        System.out.println("Driver loaded");
      }
      catch (Exception ex) {
        System.out.println(" CLASS NOT FOUND EXCEPTION .");
      }
      con = DriverManager.getConnection("jdbc:mariadb://localhost/?user=root&password=123456");
      return true;
    }
    catch (Exception ex) {
        return false;
    }
	}

  private void useDatabase(){
    try {
	    Statement st = con.createStatement();
	   	st.execute("USE RMS;");
      System.out.println("[+] Successfully logged into Database RMS");
	   }
    catch (Exception ex) {
        System.out.println("ERROR OCCURED.");
        ex.printStackTrace();
    }
  }


	private String Users(){
		StringBuilder str = new StringBuilder();
		str.append("INSERT INTO users(first_name, last_name, email, password, status) VALUES(?,?,?,?,?)");
		return str.toString();
	}
  private String Chefs(){
  	StringBuilder str = new StringBuilder();
  	str.append("INSERT INTO chefs(chef_name, chef_email, title, pay) VALUES(?,?,?,?)");
  }

  public void addCheftoTable(String fname, String lname, String email, String password, String title,
  float pay, String managed_by){
    try{
      StringBuilder str = new StringBuilder();
  	  str.append("INSERT INTO chefs(chef_fname, chef_lname, email, password, title, pay, locked, ");
      str.append("standing, managed_by) VALUES(?,?,?,?,?,?,?,?,?)");
      ps = con.prepareStatement(str);
      ps.setString(1, fname);
      ps.setString(2, lname);
      ps.setString(3, email);
      ps.setString(4, password);
      ps.setString(5, title);
      ps.setFloat(6, pay);
      ps.setBoolean(7, 0);
      ps.setString(8, 0);
      ps.setString(9, managed_by);
      ps.executeUpdate();
    }
    catch(SQLException sqlException){
      sqlException.printStackTrace();
    }
  }

  public void addtoCustomerTable(String fname, String lname, String email, String password){
    try{
      StringBuilder str = new StringBuilder();
      str.append("INSERT INTO customer(first_name, last_name, email, password, ");
      str.append("is_vip, warnings, num_ords_placed, dollars_spent, locked) VALUES(?,?,?,?,?,?,?,?,?)");
      ps = con.prepareStatement(str);
      ps.setString(1, first_name);
      ps.setString(2, last_name);
      ps.setString(3, email);
      ps.setString(4, password);
      ps.setBoolean(5, 0);
      ps.setInt(6, 0);
      ps.setInt(7, 0);
      ps.setFloat(8, 0);
      ps.setBoolean(9, 0);
      ps.executeUpdate();
    }
    catch(SQLException sqlException){
      sqlException.printStackTrace()
    }
  }

  public void addtoManagerTable(String email, String password, String fname, String lname){
    try{
      StringBuilder str = new StringBuilder();
      str.append("INSERT INTO manager(email, password, fname, lname) VALUES(?,?,?,?)");
      ps = con.prepareStatement(str);
      ps.setString(3, email);
      ps.setString(4, password);
      ps.setString(1, fname);
      ps.setString(2, lname);
      ps.executeUpdate();
    }
    catch(SQLException sqlException){
      sqlException.printStackTrace();
    }
  }

  public void addtoDeliveryTable(String email, String password, String fname, String lname){
    try{
      StringBuilder str = new StringBuilder();
      str.append("INSERT INTO delivery(email, password, fname, lname) VALUES(?,?,?,?)");
      ps = con.prepareStatement(str);
      ps.setString(1, fname);
      ps.setString(2, lname);
      ps.setString(3, email);
      ps.setString(4, password);
      ps.executeUpdate();
    }
    catch(SQLException sqlException){
      sqlException.printStackTrace();
    }
  }

  public void addtoOrdersTable(int customer_id, Date order_date, int dish_id, int delivery_id){
    try{
      StringBuilder str = new StringBuilder();
      str.append("INSERT INTO delivery(email, password, fname, lname) VALUES(?,?,?,?)");
      ps = con.prepareStatement(str);
      ps.setInt(1, customer_id);
      ps.setDate(2, order_date);
      ps.setString(3, 0)
      ps.setInt(4, dish_id);
      ps.setInt(5, delivery_id);
      ps.executeUpdate();
    }
    catch(SQLException sqlException){
      sqlException.printStackTrace();
    }
  }

  public void addtoDishesTable(String dish_name, int chef_by, float price, String type, String description,
  String pic_location){
    try{
      StringBuilder str = new StringBuilder();
      str.append("INSERT INTO dishes(dish_name, chef_by, price, type, description, pic_location) VALUES(?,?,?,?,?,?)");
      ps = con.prepareStatement(str);
      ps.setString(1, dish_name);
      ps.setInt(2, chef_by);
      ps.setFloat(3, price);
      ps.setString(4, type);
      ps.setString(5, description);
      ps.setString(6, pic_location);
      ps.executeUpdate();
    }
    catch(SQLException sqlException){
      sqlException.printStackTrace();
    }
  }

  public void addtoReviewsTable(int stars, String review, int dish_id, int chef_id){
    try{
      StringBuilder str = new StringBuilder();
      str.append("INSERT INTO reviews(stars, review, dish_id, chef_id) VALUES(?,?,?,?)");
      ps = con.prepareStatement(str);
      ps.setInt(1, stars);
      ps.setString(2, review);
      ps.setInt(3, dish_id);
      ps.setInt(4, chef_id);
      ps.executeUpdate();
    }
    catch(SQLException sqlException){
      sqlException.printStackTrace();
    }
  }

  public void addToPendingAccountsTable( String email, String firstName, String lastName, String password,
  String manager_email){
      String stmnt += "INSERT INTO pending_accounts(email, firstName, lastName, password)"+
          "VALUES("+email+","+fName+","+lName+","+password+","+manager_email+")";
        try{
          Statement statement = con.createStatement();
          statement.executeUpdate(stmnt);
      }
      catch( SQLExcepation sqlException ) {
          sqlException.printStackTrace();
      }
  }

} // end class DBManage
