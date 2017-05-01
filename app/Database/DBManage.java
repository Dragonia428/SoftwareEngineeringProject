import java.sql.*;
import java.util.ArrayList;


public class DBManage
{

    public String database_url;

    public DBManage(String dblink) { database_url = dblink; }
    
	private String Users()
	{
		StringBuilder str = new StringBuilder();
		str.append("INSERT INTO users(first_name, last_name, email, password, status) VALUES(?,?,?,?,?)");
		return str.toString();
	}
    
    private String Chefs()
    {
    	StringBuilder str = new StringBuilder();
    	str.append("INSERT INTO chefs(chef_name, chef_email, title, pay) VALUES(?,?,?,?)");
        return str.toString();
    }
    
    private String Pending() {
        String str = "INSERT INTO pending_accounts(email, firstName, lastName, password) VALUES(?,?,?,?)";
        return str;
    }
        
	public void AddToUsersTable(String firstname, String lastName, String email, String password, String status)
	{
        try{
            Connection con = DriverManager.getConnection(database_url, "root", "123456");
            PreparedStatement ps = con.prepareStatement(Users());
            ps.setString(1, firstname);
            ps.setString(2, lastName);
            ps.setString(3, email);
            ps.setString(4, password);
            ps.setString(5, status);
            ps.executeUpdate();
        }
        catch( SQLException sqlException ) {
            sqlException.printStackTrace();
        }
	}
    
    public void addToPendingAccountsTable(String email, String firstName, String lastName, String password) {
        try{
            Connection con = DriverManager.getConnection(database_url, "root", "123456");
            PreparedStatement st = con.prepareStatement(Pending());
            st.setString(1, email);
            st.setString(2, firstName);
            st.setString(3, lastName);
            st.setString(4, password);
            st.executeUpdate();
        }
        catch( SQLException sqlException ) {
            sqlException.printStackTrace();
        }
    }

    public void authorizeAccounts(ArrayList<Integer> authorizeList) {
        String select_query = "SELECT email, first_name, last_name, password FROM pending_accounts WHERE id=?";
        String insert = "INSERT INTO users(email, first_name, last_name, password, status) VALUES(?,?,?,?,?)";
        String remove = "DELETE FROM pending_accounts WHERE id=?";
        
        try{
            Connection con = DriverManager.getConnection(database_url, "root", "123456");
            PreparedStatement queryST = con.prepareStatement(select_query);
            PreparedStatement insertST = con.prepareStatement(insert);
            PreparedStatement deleteST = con.prepareStatement(remove);
            ResultSet results;
            
            for(int i = authorizeList.size()-1; i >= 0; i--) {
                queryST.setInt(1, authorizeList.get(i));
                ResultSet resultSet = queryST.executeQuery();
                setInsertStatementFields(resultSet, insertST);		// set the fields of the insert statement with the results of the query
                insertST.executeUpdate();
                deleteST.setInt(1, authorizeList.get(i));
                deleteST.executeUpdate();
            }
        }
        catch( SQLException sqlException ) {
            sqlException.printStackTrace();
        }   
    } // end authorizeAcounts

    
    private void setInsertStatementFields(ResultSet results, PreparedStatement pst) {
        try{
            results.next();
            pst.setString(1, results.getObject(1).toString());
            pst.setString(2, results.getObject(2).toString());
            pst.setString(3, results.getObject(3).toString());
            pst.setString(4, results.getObject(4).toString());
            pst.setString(5, "std");
        }
        catch( SQLException sqlException ) {
            sqlException.printStackTrace();
        }
    } // end fillStatement

} // end class DBManage
