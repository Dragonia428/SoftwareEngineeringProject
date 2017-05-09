import java.sql.*;
import java.util.ArrayList;


public class DBManage
{
    Connection con;
    public String databasename = "RMS";
	public final String databaselink = "jdbc:mysql://localhost/" + databasename;

    private final int ERR_DUP_ENTRY = 1062;

    public DBManage() {
        try{
            con = DriverManager.getConnection(databaselink, "root", "123456");
        }
        catch(SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }
    
    private String insertIntoChefsQuery()
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

    private String deletePendingAccountQuery() {
        String str = "DELETE FROM pending_accounts WHERE id = ?";
        return str;
    }

    private String deleteUserAccountQuery() {
        String str = "DELETE FROM users WHERE id = ?";
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

    private Boolean usernameExists(String uname) {
        Boolean result = false;
        try{
            PreparedStatement ps = con.prepareStatement("SELECT id FROM users WHERE username=?");
            ps.setString(1, uname);
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
    
    public int addToPendingAccounts(String firstName, String lastName, String email, String username, String password) {
        int err_code = 0;
        try{
            if( !usernameExists(username) ) {
                PreparedStatement st = con.prepareStatement(insertIntoPendingQuery());
                st.setString(1, firstName);
                st.setString(2, lastName);
                st.setString(3, email);
                st.setString(4, username);
                st.setString(5, password);
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
        String select_query = "SELECT first_name, last_name, email, username, password FROM pending_accounts WHERE id=?";

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
            pst.setString(5, results.getObject(5).toString());
        }
        catch( SQLException sqlException ) {
            sqlException.printStackTrace();
        }
    } // end fillStatement

} // end class DBManage
