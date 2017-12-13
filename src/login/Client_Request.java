package login;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;



@ManagedBean(name="client_request")
@ApplicationScoped
public class Client_Request {

	
	private ArrayList<Request> requestList = new ArrayList<Request>();
	
	//private static ArrayList<String> orderList=null;

		public ArrayList<Request> getRequestList() {
			
			ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
			Map<String, Object> sessionMap = externalContext.getSessionMap();
			String managerid=sessionMap.get("userid").toString();

			requestList = new ArrayList<Request>();
			try {
			Connection con=DatabaseConnection.getConnection();
			// Get a prepared SQL statement
			String sql = "SELECT * from client_request WHERE managerid = ? and status = ?";
			PreparedStatement st = con.prepareStatement(sql);
			st.setString(1,managerid);
			st.setString(2, "Pending");
			// Execute the statement
			ResultSet rs = st.executeQuery();
			//rs.beforeFirst();
		
			while(rs.next())
			{	
				requestList.add((new Request(rs.getString("userid"),rs.getString("symbol"), 
						rs.getString("number"),rs.getString("buy_or_sell"))));
				//orderList.add(rs.getString(i));
			}
			
			}catch(Exception e)
			{
				e.printStackTrace();
			}
			
			return requestList;
			
		}
	
		
	 
		public static class Request{
			
			String userid,symbol,count,buy_or_sell;
			
			
			public Request(String userid, String symbol, String count, String buy_or_sell) {
				this.userid = userid;
				this.symbol = symbol;
				this.count = count;
				this.buy_or_sell = buy_or_sell;
			}
			
			public String getUserid() {
				return userid;
			}

			public void setUserid(String userid) {
				this.userid = userid;
			}

			public String getSymbol() {
				return symbol;
			}

			public void setSymbol(String symbol) {
				this.symbol = symbol;
			}

			public String getCount() {
				return count;
			}

			public void setCount(String count) {
				this.count = count;
			}

			public String getBuy_or_sell() {
				return buy_or_sell;
			}

			public void setBuy_or_sell(String buy_or_sell) {
				this.buy_or_sell = buy_or_sell;
			}

			public String done() {
				//String bid=event.getComponent().getId();
				//System.out.println(symbol);
				
				ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
				Map<String, Object> sessionMap = externalContext.getSessionMap();
				String managerid=sessionMap.get("userid").toString();
				DAO dao=DAO.getInstance();
				
				Connection con=DatabaseConnection.getConnection();
				// Get a prepared SQL statement
				String update_status = "UPDATE client_request SET status = ? WHERE userid =  ? and symbol =?";
				PreparedStatement st;
				try {
					st = con.prepareStatement(update_status);
					st.setString(1,"Done");
					st.setString(2, userid);
					st.setString(3, symbol);
/*					st.setString(3, managerid);
					st.setString(4, symbol);
					st.setString(5, count);
					st.setString(6, buy_or_sell);*/
					
					// Execute the statement
					st.executeUpdate();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
				return null;
			}

		}
}
