package login;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DAO {

	public static DAO dao;
	public Connection con;
	private DAO() {
		
	}
	
	public static DAO getInstance() {
		if(dao==null) {
			dao=new DAO();
		}
		return dao;
	}
	
	public boolean addToWatchlist(String userid,String symbol)  {
		
		con=DatabaseConnection.getConnection();
		
		// Get a prepared SQL statement
		String symbols_sql = "SELECT * FROM user_watchlist WHERE userid = ? and symbol = ?";
		ResultSet symbols_rs = null;
		
		try {
			PreparedStatement symbols_st = con.prepareStatement(symbols_sql);
			symbols_st.setString(1,userid);
			symbols_st.setString(2, symbol);
			symbols_rs = symbols_st.executeQuery();
			if(symbols_rs.next())
			{
				DatabaseConnection.close(con);
				return false;
			}
			
			String insert_sql = "INSERT into user_watchlist (symbol,userid) VALUES (?,?)";
			PreparedStatement st = con.prepareStatement(insert_sql);
			st.setString(1,symbol);
			st.setString(2, userid);
			st.executeUpdate();
			
			
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		// Execute the statement
		
		
		DatabaseConnection.close(con);
		
		
		return false;
	}
}
