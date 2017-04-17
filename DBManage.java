import java.sql.*;


public class DBManage
{
    
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
    }
	public void AddToUsersTable(String firstname, String lastName, String email, String password, String status)
	{
        try{
            Connection con = DriverManager.getConnection( Connect.databaselink, "root", "123456" );
            ps = con.prepareStatement(Users());
            ps.setString(1, firstname);
            ps.setString(2, lastName);
            ps.setString(3, email);
            ps.setString(4, password);
            ps.setString(5, status);
            ps.executeUpdate();
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
