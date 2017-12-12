package login;

import java.sql.Connection;
import java.sql.PreparedStatement;
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
	
	public boolean addToWatchlist(String userid,String symbol) {
		
		con=DatabaseConnection.getConnection();
		
		String sql = "INSERT into user_watchlist (symbol,userid) VALUES (?,?)";
		
		//st.setInt(1, 1);
		try {
			PreparedStatement st = con.prepareStatement(sql);
			st.setString(1,symbol);
			st.setString(2, userid);
			st.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		DatabaseConnection.close(con);
		
		
		return false;
	}
}
