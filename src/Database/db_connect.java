package Database;
import java.sql.*;

/*
 * README
 * 
 * The purpose of this class is to provide functions for any interaction our
 * web app could have with the database. Review the example.java class for examples 
 * on how to use these functions (they throw exceptions that you might need to
 * handle!).
 * 
 * NOTE: At the beginning of db_connect, check your database username, password, 
 * and pathway and make the appropriate changes if your database has any differences.
 * 
 */

public class db_connect {
	
	// Database settings
	// NOTE: Change this if your DB has different settings
	private static String db = "jdbc:mysql://localhost/uno";
	private static String db_user = "root";
	private static String db_pwd = "root";
	
	// Adds a new user to the database
	public static void addUser(String user, String pwd) throws SQLException
	{
		String sql = "INSERT INTO Users (Username, Password, Wins, Losses) VALUES (?,?,0,0)";
		
		try(Connection conn = DriverManager.getConnection(db, db_user, db_pwd);
				PreparedStatement ps = conn.prepareStatement(sql);) {
			
			// Add "user" and "pwd" to SQL statement
			ps.setString(1, user);
			ps.setString(2, pwd);
			
			// Execute DB update
			ps.executeUpdate();
			
		}
		catch (SQLException e) {
			throw new SQLException(e.getMessage());
		}
	}
	
	// Increments the total number of a user's wins by one
	public static void addWin(String user) throws SQLException
	{
		String sql = "UPDATE Users SET Wins = Wins + 1 WHERE Username = ?";
		addHelper(user, sql);
	}
	
	// Increments the total number of a user's losses by one
	public static void addLoss(String user) throws SQLException
	{
		String sql = "UPDATE Users SET Losses = Losses + 1 WHERE Username = ?";
		addHelper(user, sql);
	}
	
	// Helper for addWin and addLoss to reduce redundant code
	public static void addHelper(String user, String sql) throws SQLException
	{
		try(Connection conn = DriverManager.getConnection(db, db_user, db_pwd);
				PreparedStatement ps = conn.prepareStatement(sql);) {
			
			// Add "user" to SQL statement
			ps.setString(1, user);
			
			// Execute DB update
			ps.executeUpdate();
			
		}
		catch (SQLException e) {
			throw new SQLException(e.getMessage());
		}
	}
	
	// Output the number of wins a user has
	public static int getWins(String user) throws SQLException
	{
		String sql = "SELECT Wins FROM Users WHERE Username = ?;";
		return getHelper(user, sql);
	}
	
	// Output the number of losses a user has
	public static int getLosses(String user) throws SQLException
	{
		String sql = "SELECT Losses FROM Users WHERE Username = ?;";
		return getHelper(user, sql);
	}
	
	// Helper for getWins and getLosses to reduce redundant code
	public static int getHelper(String user, String sql) throws SQLException
	{
		int answer = 0;
		
		try(Connection conn = DriverManager.getConnection(db, db_user, db_pwd);
				PreparedStatement ps = conn.prepareStatement(sql);) {
			
			// Add "user" to SQL statement
			ps.setString(1, user);
			
			// Get the result set
			ResultSet rs = ps.executeQuery();
			
			// Either get the number of wins/losses or throw an exception
			if(rs.next()) answer = rs.getInt(1);
			else throw new SQLException("User does not exist");
			
		}
		catch (SQLException e) {
			throw new SQLException(e.getMessage());
		}
		
		// Return the number of wins/losses
		return answer;
	}
	
	// returns true if the username and password combination exist, false if otherwise
	public static boolean validateLogin(String user, String pwd) throws SQLException
	{
		// MySQL statement
		String sql = "SELECT Username, Password FROM Users WHERE Username = ?;";
		boolean answer = false;
		
		try(Connection conn = DriverManager.getConnection(db, db_user, db_pwd);
				PreparedStatement ps = conn.prepareStatement(sql);) {
			
			// Add "user" to SQL statement
			ps.setString(1, user);
			
			// Get the result set
			ResultSet rs = ps.executeQuery();
			if(rs.next())
			{
				// the user is found
				if(user.compareTo(rs.getString("Username")) == 0 && pwd.compareTo(rs.getString("Password")) == 0)
				{
					// the password is valid
					answer = true;
				}
			}
			else throw new SQLException("User does not exist");
			
		}
		catch (SQLException e) {
			throw new SQLException(e.getMessage());
		}
		
		// return whether the user is valid or not
		return answer;
	}
	
	
	// returns true if user exists, false otherwise
	public static boolean userExists(String user) throws SQLException
	{
		// MySQL statement
		String sql = "SELECT COUNT(*) as count FROM Users WHERE Username = ?;";
		boolean answer = false;
		
		try(Connection conn = DriverManager.getConnection(db, db_user, db_pwd);
				PreparedStatement ps = conn.prepareStatement(sql);) {
			
			// Add "user" to SQL statement
			ps.setString(1, user);
			
			// Get the result set
			ResultSet rs = ps.executeQuery();
			rs.next();
			if (rs.getInt("count") == 1) answer = true;
			
		}
		catch (SQLException e) {
			throw new SQLException(e.getMessage());
		}
		
		// return whether the user is valid or not
		return answer;
	}
	
	public static Boolean checkAlphaNum(String s)
    {
        for(int i = 0; i < s.length(); ++i)
        {
            if(!Character.isLetterOrDigit(s.charAt(i)))
            {
                return false;
            }
        }
        return true;
    }
}
