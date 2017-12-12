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
	
	public String getPrice(String Symbol) {
		
		con=DatabaseConnection.getConnection();
		String sql = "SELECT price FROM symbols WHERE symbol = ?";
		PreparedStatement st;
		try {
			st = con.prepareStatement(sql);
			st.setString(1,Symbol);
			ResultSet rs =st.executeQuery();
			rs.beforeFirst();
			rs.next();
			String price=rs.getString("price");
			return price;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}
	
	public String getCount(String userid, String Symbol) {
		
		con=DatabaseConnection.getConnection();
		String sql = "SELECT count FROM stock_count WHERE symbol = ? AND userid = ? " ;
		PreparedStatement st;
		try {
			st = con.prepareStatement(sql);
			st.setString(1,Symbol);
			st.setString(2, userid);
			ResultSet rs =st.executeQuery();
			rs.beforeFirst();
			if(rs.next())
			{
				String count=rs.getInt("count")+"";
				return count;
			}
			return 0+"";
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}
	
	public Double getBalance(String userid)
	{
		con=DatabaseConnection.getConnection();
		String sql = "SELECT account_balance FROM user_table WHERE userid = ? " ;
		PreparedStatement st;
		try {
			st = con.prepareStatement(sql);
			st.setString(1, userid);
			ResultSet rs =st.executeQuery();
			rs.beforeFirst();
			if(rs.next())
			{
				Double count=rs.getDouble("account_balance");
				return count;
			}
			return 0.00;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return 0.00;
	}
	
	public boolean buyStocks(String userid,String managerid,String Symbol,String count,String price,Double cost)
	{
		
		return false;
	}
}
