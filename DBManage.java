import java.sql.*;
public class DBManage
{
	private String Users()
	{
		StringBuilder str = new StringBuilder();
		str.append("INSERT INTO users(first_name, last_name, email, password, status) VALUES(?,?,?,?,?)");
		return str.toString();
	}
	public void AddToUsersTable()
	{

	}
}