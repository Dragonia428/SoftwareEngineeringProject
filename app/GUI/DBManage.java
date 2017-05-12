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

  public void addCheftoTable(String fname, String lname, String email, String password, String title,
  float pay, String managed_by){
    try{
      StringBuilder str = new StringBuilder();
  	  str.append("INSERT INTO chefs(chef_fname, chef_lname, email, password, title, pay, locked, ");
      str.append("standing, managed_by) VALUES(?,?,?,?,?,?,?,?,?);");
      PreparedStatement ps = con.prepareStatement(str.toString());
      ps.setString(1, fname);
      ps.setString(2, lname);
      ps.setString(3, email);
      ps.setString(4, password);
      ps.setString(5, title);
      ps.setFloat(6, pay);
      ps.setBoolean(7, false);
      ps.setInt(8, 0);
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
      str.append("INSERT INTO customers(first_name, last_name, funds, email, password, ");
      str.append("is_vip, warnings, num_ords_placed, dollars_spent, locked) VALUES(?,?,?,?,?,?,?,?,?,?);");
      PreparedStatement ps = con.prepareStatement(str.toString());
      ps.setString(1, fname);
      ps.setString(2, lname);
      ps.setFloat(3, funds);
      ps.setString(4, email);
      ps.setString(5, password);
      ps.setBoolean(6, false);
      ps.setInt(7, 0);
      ps.setInt(8, 0);
      ps.setFloat(9, 0);
      ps.setBoolean(10, false);
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
      str.append("INSERT INTO orders(customer_id, order_date, total_price, delivered, dish_id, delivery_id) VALUES(?,?,?,?,?,?);");
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

  public void addtoDishesTable(String dish_name, int chef_by, float price, String type, String description,
  String pic_location){
    try{
      StringBuilder str = new StringBuilder();
      str.append("INSERT INTO dishes(dish_name, chef_by, price, type, description, pic_location) VALUES(?,?,?,?,?,?);");
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

  public void deleteFromCustomerTable(int customer_id){
    try{
      StringBuilder str = new StringBuilder();
      str.append("DELETE FROM customer WHERE customer_id=\'"+customer_id+"\';");
      PreparedStatement ps = con.prepareStatement(str.toString());
      ps.executeUpdate(str.toString());
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
            PreparedStatement ps = con.prepareStatement(insertIntoUsersQuery());
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
            PreparedStatement insertST = con.prepareStatement(insertIntoUsersQuery());
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

  public void deleteFromPenAccTable(int pen_acc_id){
    try{
      StringBuilder str = new StringBuilder();
      str.append("DELETE FROM pending_accounts WHERE pen_acc_id=\'"+pen_acc_id+"\';");
      PreparedStatement ps = con.prepareStatement(str.toString());
      ps.executeUpdate(str.toString());
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

  public void closeDB(){
    con.close();
  }
} // end class DBManage
