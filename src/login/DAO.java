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
				Double balance=rs.getDouble("account_balance");
				return balance;
			}
			return 0.00;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return 0.00;
	}
	
	public boolean buyStocks(String userid,String managerid,String commission,String Symbol,String count,String price,Double cost)
	{
		con=DatabaseConnection.getConnection();
		String update_balance_sql = "UPDATE user_table SET account_balance = ? WHERE USERID = ?";;
		
		try {
			PreparedStatement update_balance_st = con.prepareStatement(update_balance_sql);
			update_balance_st.setDouble(1,getBalance(userid)-cost);
			update_balance_st.setString(2,userid);
			// Execute the statement
			update_balance_st.executeUpdate();
			
			String check_count_sql = "SELECT count FROM stock_count WHERE userid = ? and symbol = ? " ;
			PreparedStatement check_count_st = con.prepareStatement(check_count_sql);
			check_count_st.setString(1,userid);
			check_count_st.setString(2, Symbol);
			ResultSet rs =check_count_st.executeQuery();
			rs.beforeFirst();
			if(rs.next())
			{
				// Get a prepared SQL statement
				String update_count = "INSERT into stock_count (userid,symbol,count) VALUES (?,?,?)";
				PreparedStatement uodate_count_st = con.prepareStatement(update_count);
				//st.setInt(1, 1);
				uodate_count_st.setString(1,userid);
				uodate_count_st.setString(2, Symbol);
				uodate_count_st.setString(3,Integer.parseInt(count)+rs.getInt("count")+"");
				uodate_count_st.executeUpdate();
			}
			else 
			{
				// Get a prepared SQL statement
				String update_count = "INSERT into stock_count (userid,symbol,count) VALUES (?,?,?)";
				PreparedStatement uodate_count_st = con.prepareStatement(update_count);
				//st.setInt(1, 1);
				uodate_count_st.setString(1,userid);
				uodate_count_st.setString(2, Symbol);
				uodate_count_st.setString(3,count);
				uodate_count_st.executeUpdate();
			}
			
			
			
			String insert_history = "INSERT into account_history (userid,symbol,price,count,buy_or_sell) VALUES (?,?,?,?,?)";
			PreparedStatement insert_history_st = con.prepareStatement(insert_history);
			//st.setInt(1, 1);
			insert_history_st.setString(1,userid);
			insert_history_st.setString(2, Symbol);
			insert_history_st.setString(3,price);
			insert_history_st.setString(4,count);
			insert_history_st.setString(5,"Buy");
			insert_history_st.executeUpdate();
			
			return true;
			
								
			
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return false;
	}
	
	public boolean sellStocks(String userid,String managerid,String commission,String Symbol,String count,String price,Double cost)
	{
		con=DatabaseConnection.getConnection();
		String update_balance_sql = "UPDATE user_table SET account_balance = ? WHERE USERID = ?";;
		
		try {
			PreparedStatement update_balance_st = con.prepareStatement(update_balance_sql);
			update_balance_st.setDouble(1,getBalance(userid)+cost);
			update_balance_st.setString(2,userid);
			// Execute the statement
			update_balance_st.executeUpdate();
			
			String check_count_sql = "SELECT count FROM stock_count WHERE userid = ? and symbol = ? " ;
			PreparedStatement check_count_st = con.prepareStatement(check_count_sql);
			check_count_st.setString(1,userid);
			check_count_st.setString(2, Symbol);
			ResultSet rs =check_count_st.executeQuery();
			rs.beforeFirst();
			if(rs.next())
			{
				// Get a prepared SQL statement
				String update_count = "UPDATE stock_count SET count = ? WHERE userid = ? AND symbol = ?";
				PreparedStatement uodate_count_st = con.prepareStatement(update_count);
				//st.setInt(1, 1);
				uodate_count_st.setString(1,(rs.getInt("count")-Integer.parseInt(count))+"");
				uodate_count_st.setString(2,userid);
				uodate_count_st.setString(3, Symbol);
				uodate_count_st.executeUpdate();
			}
			else 
			{
				return false;
			}
			
			
			
			String insert_history = "INSERT into account_history (userid,symbol,price,count,buy_or_sell) VALUES (?,?,?,?,?)";
			PreparedStatement insert_history_st = con.prepareStatement(insert_history);
			//st.setInt(1, 1);
			insert_history_st.setString(1,userid);
			insert_history_st.setString(2, Symbol);
			insert_history_st.setString(3,price);
			insert_history_st.setString(4,count);
			insert_history_st.setString(5,"Sell");
			insert_history_st.executeUpdate();
			
			return true;
			
								
			
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return false;
	}
}
