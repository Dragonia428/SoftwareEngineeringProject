import java.sql.*;


public class DBManage
{
    
	private String Users()
	{
		StringBuilder str = new StringBuilder();
		str.append("INSERT INTO users(first_name, last_name, email, password, status) VALUES(?,?,?,?,?)");
		return str.toString();
	}
    
	public void AddToUsersTable(String firstname, String lastName, String email, String password, String status)
	{
        String stmnt += "INSERT INTO users(first_name, lastName, email, password, status)"+
            "VALUES("+firstName+","+lastName+","+email+","+password+","+status")";
        
        try{
            Connection con = DriverManager.getConnection( Connect.databaselink, "root", "123456" );
            Statement statement = con.createStatement();
            statement.executeUpdate(stmnt);
        }
        catch( SQLExcepation sqlException ) {
            sqlException.printStackTrace();
        }
	}
    
    public void addToPendingAccountsTable( String email, String firstName, String lastName, String password )
    {
        String stmnt += "INSERT INTO pending_accounts(email, firstName, lastName, password)"+
            "VALUES("+email+","+fName+","+lName+","+password+")";
        
        try{
            Connection con = DriverManager.getConnection( Connect.databaselink, "root", "123456" );
            Statement statement = con.createStatement();
            statement.executeUpdate(stmnt);
        }
        catch( SQLExcepation sqlException ) {
            sqlException.printStackTrace();
        }
    }

} // end class DBManage
