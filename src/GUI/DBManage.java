/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

/**
 *
 * @author setti
 */
import java.sql.*;
import java.lang.System.*;
import java.lang.StringBuilder;
import java.util.ArrayList;



public class DBManage
{
  private Connection con;
  public String databasename = "RMS";
  public final String databaselink = "jdbc:mysql://localhost/" + databasename;
  private final int ERR_DUP_ENTRY = 1062;

  public DBManage(){
    try{
      if(canConnect()){
        useDatabase();
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
  public int GetCustomerID(String email)
  {
      try
      {
        PreparedStatement ps = con.prepareStatement("select customer_id from customers where email=?");
        ps.setString(1, email);
        ResultSet rs = ps.executeQuery();
        rs.next();
        int id = rs.getInt("customer_id");
        return id;
      }
      catch(SQLException ex)
      {
          return 0;
      }
  }
  public int GetOrderID(String email)
  {
      try
      {
        PreparedStatement ps = con.prepareStatement("select order_id from customers where order=?");
        ps.setString(1, email);
        ResultSet rs = ps.executeQuery();
        rs.next();
        int id = rs.getInt("order_id");
        return id;
      }
      catch(SQLException ex)
      {
          return 0;
      }
  }
  public void addCheftoTable(String fname, String lname, String email, String password, String title,
  float pay, String managed_by){
    try{
      StringBuilder str = new StringBuilder();
  	  str.append("INSERT INTO chefs(chef_fname, chef_lname, email, password) VALUES(?,?,?,?);");
      PreparedStatement ps = con.prepareStatement(str.toString());
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

  public void addtoCustomerTable(String fname, String lname, String email, String password){
    try{
      StringBuilder str = new StringBuilder();
      str.append("INSERT INTO customers(first_name, last_name, email, password) VALUES(?,?,?,?);");
      PreparedStatement ps = con.prepareStatement(str.toString());
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

  public void addtoManagerTable(String email, String password, String fname, String lname){
    try{
      StringBuilder str = new StringBuilder();
      str.append("INSERT INTO manager(email, password, fname, lname) VALUES(?,?,?,?);");
      PreparedStatement ps = con.prepareStatement(str.toString());
      ps.setString(1, email);
      ps.setString(2, password);
      ps.setString(3, fname);
      ps.setString(4, lname);
      ps.executeUpdate();
    }
    catch(SQLException sqlException){
      sqlException.printStackTrace();
    }
  }

  public void addtoDeliveryTable(String email, String password, String fname, String lname){
    try{
      StringBuilder str = new StringBuilder();
      str.append("INSERT INTO delivery(email, password, fname, lname, locked) VALUES(?,?,?,?,?);");
      PreparedStatement ps = con.prepareStatement(str.toString());
      ps.setString(1, email);
      ps.setString(2, password);
      ps.setString(3, fname);
      ps.setString(4, lname);
      ps.setBoolean(5, false);
      ps.executeUpdate();
    }
    catch(SQLException sqlException){
      sqlException.printStackTrace();
    }
  }

  public void addtoOrdersTable(int customer_id, String order_date, float total_price, int dish_id, int delivery_id){
    try{
      StringBuilder str = new StringBuilder();
      str.append("INSERT INTO orders(customer_id, order_date, total_price, delivered, dish_id, delivery_id) VALUES((select customer_id from customers where email=?),?,?,?,?,?);");
      PreparedStatement ps = con.prepareStatement(str.toString());
      ps.setInt(1, customer_id);
      ps.setDate(2, java.sql.Date.valueOf(order_date));
      ps.setFloat(3, total_price);
      ps.setBoolean(4, false);
      ps.setInt(5, dish_id);
      ps.setInt(6, delivery_id);
      ps.executeUpdate();
    }
    catch(SQLException sqlException){
      sqlException.printStackTrace();
    }
  }

  public void addtoDishesTable(String dish_name, String chef_email, float price, String type, String description,
  String pic_location){
    try{
      StringBuilder str = new StringBuilder();
      str.append("INSERT INTO dishes(dish_name, chef_by, price, type, description, pic_location) VALUES(?,?,?,?,?,?);");
      Statement st = con.createStatement();
      ResultSet rs = st.executeQuery("select chef_id from chefs where email="+chef_email);
      rs.next(); 
      int chef_by = rs.getInt("chef_id");
      PreparedStatement ps = con.prepareStatement(str.toString());
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
      str.append("INSERT INTO reviews(stars, review, dish_id, chef_id) VALUES(?,?,?,?);");
      PreparedStatement ps = con.prepareStatement(str.toString());
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

  public void deleteFromChefTable(int chef_id){
    try{
      StringBuilder str = new StringBuilder();
      str.append("DELETE FROM chefs WHERE chef_id=\'"+chef_id+"\';");
      PreparedStatement ps = con.prepareStatement(str.toString());
      ps.executeUpdate(str.toString());
    }
    catch(SQLException sqlException){
      sqlException.printStackTrace();
    }
  }

  public void deleteFromCustomerTable(String email){
    try{
      StringBuilder str = new StringBuilder();
      str.append("DELETE FROM customers WHERE email=?");
      PreparedStatement ps = con.prepareStatement(str.toString());
      ps.setString(1, email);
      ps.executeUpdate();
    }
    catch(SQLException sqlException){
      sqlException.printStackTrace();
    }
  }

  public void deleteFromMangerTable(String email){
    try{
      StringBuilder str = new StringBuilder();
      str.append("DELETE FROM manager WHERE email=\'"+email+"\';");
      PreparedStatement ps = con.prepareStatement(str.toString());
      ps.executeUpdate(str.toString());
    }
    catch(SQLException sqlException){
      sqlException.printStackTrace();
    }
  }

  public void deleteFromDeliverTable(int delivery_id){
    try{
      StringBuilder str = new StringBuilder();
      str.append("DELETE FROM delivery WHERE delivery_id=\'"+delivery_id+"\';");
      PreparedStatement ps = con.prepareStatement(str.toString());
      ps.executeUpdate(str.toString());
    }
    catch(SQLException sqlException){
      sqlException.printStackTrace();
    }
  }



    private String insertIntoChefsQuery()
    {
    	StringBuilder str = new StringBuilder();
    	str.append("INSERT INTO chefs(chef_name, chef_email, title, pay) VALUES(?,?,?,?)");
        return str.toString();
    }

    private String insertIntoCustomersQuery()
	{
		StringBuilder str = new StringBuilder();
		str.append("INSERT INTO customers(first_name, last_name, email, password) VALUES(?,?,?,?)");
		return str.toString();
	}

    private String insertIntoPendingQuery() {
        String str = "INSERT INTO pending_accounts(first_name, last_name, email, password) VALUES(?,?,?,?)";
        return str;
    }

    private String deletePendingAccountQuery() {
        String str = "DELETE FROM pending_accounts WHERE pen_acc_id = ?";
        return str;
    }

    private String deleteCustomersAccountQuery() {
        String str = "DELETE FROM customers WHERE customer_id = ?";
        return str;
    }

	public void AddUser(String firstname, String lastName, String email, String username, String password)
	{
        try{
            //Connection con = DriverManager.getConnection(databaselink, "root", "123456");
            PreparedStatement ps = con.prepareStatement(insertIntoCustomersQuery());
            ps.setString(1, firstname);
            ps.setString(2, lastName);
            ps.setString(3, email);
            ps.setString(4, username);
            ps.setString(5, password);
            ps.executeUpdate();
        }
        catch( SQLException sqlException ) {
            switch( sqlException.getErrorCode() ) {
            	case ERR_DUP_ENTRY:
                    System.out.printf("%s\n", sqlException.getMessage());
                    break;
            }
        }
	}

    private Boolean emailExists(String email) {
        Boolean result = false;
        try{
            PreparedStatement ps = con.prepareStatement("SELECT customer_id FROM customers WHERE email=?");
            ps.setString(1, email);
            ResultSet resultSet = ps.executeQuery();
            if( resultSet.next() )
                result = true;
            else
                result = false;
        }
        catch(SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return result;
    }

    public int addToPendingAccountsTable(String firstName, String lastName, String email, String password) {
        int err_code = 0;
        try{
            if( !emailExists(email) ) {
                PreparedStatement st = con.prepareStatement(insertIntoPendingQuery());
                st.setString(1, firstName);
                st.setString(2, lastName);
                st.setString(3, email);
                st.setString(4, password);
                st.executeUpdate();
            }
            else
                err_code = ERR_DUP_ENTRY;
        }
        catch( SQLException sqlException ) {
            err_code = sqlException.getErrorCode();
        }
        return err_code;
    }

    public void removeFromPendingAccounts(ArrayList<Integer> idList) {
         try{
            PreparedStatement ps = con.prepareStatement(deletePendingAccountQuery());
            for(int i = idList.size()-1; i >= 0; i--) {
                ps.setInt(1, idList.get(i));
                ps.executeUpdate();
            }
         }
         catch(SQLException sqlException) {
             sqlException.printStackTrace();
         }
    }

    public void authorizeAccounts(ArrayList<Integer> authorizeList) {
        String select_query = "SELECT first_name, last_name, email, password FROM pending_accounts WHERE pen_acc_id=?";

        try{
            PreparedStatement selectST = con.prepareStatement(select_query);
            PreparedStatement insertST = con.prepareStatement(insertIntoCustomersQuery());
            PreparedStatement deleteST = con.prepareStatement(deletePendingAccountQuery());
            ResultSet results;
            for(int i = authorizeList.size()-1; i >= 0; i--) {
                try{
                    selectST.setInt(1, authorizeList.get(i));
                    ResultSet resultSet = selectST.executeQuery();
                    if( resultSet.next() ) {
                        setInsertStatementFields(resultSet, insertST);		// set the fields of the insert statement with the results of the query
                        insertST.executeUpdate();
                        deleteST.setInt(1, authorizeList.get(i));
                        deleteST.executeUpdate();
                    }
                }
                catch( SQLException sqlException ) {
                    switch( sqlException.getErrorCode() ) {
                    	case ERR_DUP_ENTRY:
                            System.out.printf("%s -- ERROR CODE: %d\n", sqlException.getMessage(), sqlException.getErrorCode());
                            break;
                    }
                }
            }
        }
        catch(SQLException sqlException){
            System.out.printf("%s\n", sqlException.toString());
        }

    } // end authorizeAcounts


    private void setInsertStatementFields(ResultSet results, PreparedStatement pst) {
        try{
            pst.setString(1, results.getObject(1).toString());
            pst.setString(2, results.getObject(2).toString());
            pst.setString(3, results.getObject(3).toString());
            pst.setString(4, results.getObject(4).toString());
        }
        catch( SQLException sqlException ) {
            sqlException.printStackTrace();
        }
    } // end fillStatement
  public void deleteFromOrdersTable(int order_id){
    try{
      StringBuilder str = new StringBuilder();
      str.append("DELETE FROM orders WHERE order_id=\'"+order_id+"\';");
      PreparedStatement ps = con.prepareStatement(str.toString());
      ps.executeUpdate(str.toString());
    }
    catch(SQLException sqlException){
      sqlException.printStackTrace();
    }
  }

  public void deleteFromDishesTable(int dish_id){
    try{
      StringBuilder str = new StringBuilder();
      str.append("DELETE FROM dishes WHERE dish_id=\'"+dish_id+"\';");
      PreparedStatement ps = con.prepareStatement(str.toString());
      ps.executeUpdate(str.toString());
    }
    catch(SQLException sqlException){
      sqlException.printStackTrace();
    }
  }

  public void deleteFromPenAccTable(String email){
    try{
      StringBuilder str = new StringBuilder();
      str.append("DELETE FROM pending_accounts WHERE email=?");
      PreparedStatement ps = con.prepareStatement(str.toString());
      ps.setString(1, email);
      ps.executeUpdate();
    }
    catch(SQLException sqlException){
      sqlException.printStackTrace();
    }
  }

  public void deleteFromReviewsTable(int review_id){
    try{
      StringBuilder str = new StringBuilder();
      str.append("DELETE FROM reviews WHERE review_id=\'"+review_id+"\';");
      PreparedStatement ps = con.prepareStatement(str.toString());
      ps.executeUpdate(str.toString());
    }
    catch(SQLException sqlException){
      sqlException.printStackTrace();
    }
  }

  public ResultSet queryDatabase(String query){
    try{
      if(query.substring(0,6).equals("select") || query.substring(0,6).equals("SELECT")){
        Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
        ResultSet rs = stmt.executeQuery(query);
        return rs;
      }
      else{
        System.out.println("[-] That SQL statement does not query");
        return null;
      }
    }
    catch(SQLException sqlException){
      sqlException.printStackTrace();
      return null;
    }
  }

  public void closeDB() throws SQLException {
    con.close();
  }
  public ArrayList<String> gatherPendingData(String type)
  {
    ArrayList<String> temp = new ArrayList<String>();
    if(type == "firstname")
    {
      try {
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery("SELECT first_name FROM pending_accounts");
        while(rs.next())
        {
            temp.add(rs.getString("first_name"));
        }
        return temp;
      }
      catch(SQLException ex)
      {
        ex.printStackTrace();
      }
    }
    if(type == "lastname")
    {
      try {
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery("SELECT last_name FROM pending_accounts");
        while(rs.next())
        {
            temp.add(rs.getString("last_name"));
        }
        return temp;
      }
      catch(SQLException ex)
      {
        ex.printStackTrace();
      }
      return temp;
    }
    if(type == "email")
    {
      try {
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery("SELECT email FROM pending_accounts");
        while(rs.next())
        {
            temp.add(rs.getString("email"));
        }
        return temp;
      }
      catch(SQLException ex)
      {
        ex.printStackTrace();
      }
      return temp;  
    }
    return temp;
  }
} // end class DBManage

