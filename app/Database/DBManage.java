import java.sql.*;
import java.util.ArrayList;


public class DBManage
{
    public String databasename = "RMS";
	public final String databaselink = "jdbc:mysql://localhost/" + databasename;
    

    public DBManage() {}
    
	    
    private String Chefs()
    {
    	StringBuilder str = new StringBuilder();
    	str.append("INSERT INTO chefs(chef_name, chef_email, title, pay) VALUES(?,?,?,?)");
        return str.toString();
    }

    private String insertIntoUsersQuery()
	{
		StringBuilder str = new StringBuilder();
		str.append("INSERT INTO users(first_name, last_name, email, username, password) VALUES(?,?,?,?,?)");
		return str.toString();
	}
    
    private String insertIntoPendingQuery() {
        String str = "INSERT INTO pending_accounts(first_name, last_name, email, username, password) VALUES(?,?,?,?,?)";
        return str;
    }

    private String removeFromPendingQuery() {
        String str = "DELETE FROM pending_accounts WHERE id = ?";
        return str;
    }
        
	public void AddUser(String firstname, String lastName, String email, String username, String password)
	{
        try{
            Connection con = DriverManager.getConnection(databaselink, "root", "123456");
            PreparedStatement ps = con.prepareStatement(insertIntoUsersQuery());
            ps.setString(1, firstname);
            ps.setString(2, lastName);
            ps.setString(3, email);
            ps.setString(4, username);            
            ps.setString(5, password);
            ps.executeUpdate();
        }
        catch( SQLException sqlException ) {
            sqlException.printStackTrace();
        }
	}
    
    public void addToPendingAccounts(String firstName, String lastName, String email, String username, String password) {
        try{
            Connection con = DriverManager.getConnection(databaselink, "root", "123456");
            PreparedStatement st = con.prepareStatement(insertIntoPendingQuery());
            st.setString(1, firstName);
            st.setString(2, lastName);
            st.setString(3, email);
            st.setString(4, username);
            st.setString(5, password);
            st.executeUpdate();
        }
        catch( SQLException sqlException ) {
            sqlException.printStackTrace();
        }
    }

    public void removeFromPendingAccounts(ArrayList<Integer> id_list) {
		
    }

    public void authorizeAccounts(ArrayList<Integer> authorizeList) {
        String select_query = "SELECT first_name, last_name, email, username, password FROM pending_accounts WHERE id=?";
        String insert = "INSERT INTO users(first_name, last_name, email, username, password) VALUES(?,?,?,?,?)";
        String remove = "DELETE FROM pending_accounts WHERE id=?";
        
        try{
            Connection con = DriverManager.getConnection(databaselink, "root", "123456");
            PreparedStatement queryST = con.prepareStatement(select_query);
            PreparedStatement insertST = con.prepareStatement(insertIntoUsersQuery());
            PreparedStatement deleteST = con.prepareStatement(remove);
            ResultSet results;
            
            for(int i = authorizeList.size()-1; i >= 0; i--) {
                queryST.setInt(1, authorizeList.get(i));
                ResultSet resultSet = queryST.executeQuery();
                if( resultSet.next() ) {
                    setInsertStatementFields(resultSet, insertST);		// set the fields of the insert statement with the results of the query
                    insertST.executeUpdate();
                    deleteST.setInt(1, authorizeList.get(i));
                    deleteST.executeUpdate();
                }
            }
        }
        catch( SQLException sqlException ) {
            sqlException.printStackTrace();
        }   
    } // end authorizeAcounts

    
    private void setInsertStatementFields(ResultSet results, PreparedStatement pst) {
        try{
            pst.setString(1, results.getObject(1).toString());
            pst.setString(2, results.getObject(2).toString());
            pst.setString(3, results.getObject(3).toString());
            pst.setString(4, results.getObject(4).toString());
            pst.setString(5, results.getObject(5).toString());
        }
        catch( SQLException sqlException ) {
            sqlException.printStackTrace();
        }
    } // end fillStatement

} // end class DBManage
