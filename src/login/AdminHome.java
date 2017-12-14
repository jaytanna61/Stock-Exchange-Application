package login;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

@ManagedBean(name="admin_home")
@ApplicationScoped
public class AdminHome {
	
	private ArrayList<Transaction> transactionList = new ArrayList<Transaction>();
	
	private ArrayList<Request> requestList = new ArrayList<Request>();
	
	public ArrayList<Transaction> getTransactionList() {
		
		

		transactionList = new ArrayList<Transaction>();
		try {
			
		Connection con=DatabaseConnection.getConnection();
		// Get a prepared SQL statement
		String sql = "SELECT * from account_history";
		PreparedStatement st = con.prepareStatement(sql);
		// Execute the statement
		ResultSet rs = st.executeQuery();
		rs.beforeFirst();
		
		while(rs.next())
		{
			transactionList.add((new Transaction(rs.getString("symbol"),rs.getString("price"),rs.getString("count"),(Double.parseDouble(rs.getString("price"))*Integer.parseInt(rs.getString("count")))+"",rs.getString("manager_commission"),rs.getString("buy_or_sell"),rs.getString("userid"),rs.getString("managerid"))));
		}
		
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return transactionList;
		
	}
	
	public static class Transaction{
		
		String symbol,price,count,cost,commission,userid,managerid,buy_or_sell;
		
		public Transaction(String symbol, String price, String count, String cost, String commission, String buy_or_sell, String userid , String managerid ) {
			this.symbol = symbol;
			this.price = price;
			this.count = count;
			this.cost = cost;
			this.commission = commission;
			this.buy_or_sell = buy_or_sell;
			this.userid = userid;
			this.managerid = managerid;
		}

		public String getSymbol() {
			return symbol;
		}

		public void setSymbol(String symbol) {
			this.symbol = symbol;
		}

		public String getPrice() {
			return price;
		}

		public void setPrice(String price) {
			this.price = price;
		}

		public String getCount() {
			return count;
		}

		public void setCount(String count) {
			this.count = count;
		}

		public String getCost() {
			return cost;
		}

		public void setCost(String cost) {
			this.cost = cost;
		}

		public String getCommission() {
			return commission;
		}

		public void setCommission(String commission) {
			this.commission = commission;
		}

		public String getUserid() {
			return userid;
		}

		public void setUserid(String userid) {
			this.userid = userid;
		}

		public String getManagerid() {
			return managerid;
		}

		public void setManagerid(String managerid) {
			this.managerid = managerid;
		}

		public String getBuy_or_sell() {
			return buy_or_sell;
		}

		public void setBuy_or_sell(String buy_or_sell) {
			this.buy_or_sell = buy_or_sell;
		}
		
		

	}
	
	
	public ArrayList<Request> getRequestList() {
		
		requestList = new ArrayList<Request>();
		try {
		Connection con=DatabaseConnection.getConnection();
		// Get a prepared SQL statement
		String sql = "SELECT * from manager WHERE activated = ?";
		PreparedStatement st = con.prepareStatement(sql);
		st.setString(1, "false");
		// Execute the statement
		ResultSet rs = st.executeQuery();
		//rs.beforeFirst();
	
		while(rs.next())
		{	
			requestList.add((new Request(rs.getString("managerid"),rs.getString("name"), 
					rs.getString("email"),rs.getString("license"),rs.getString("commission")+"%")));
			//orderList.add(rs.getString(i));
		}
		
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return requestList;
		
	}
	
	public static class Request{
		
		String manager_id,manager_name,manager_email,manager_license,manager_commission;
		
		
		public String getManager_id() {
			return manager_id;
		}

		public void setManager_id(String manager_id) {
			this.manager_id = manager_id;
		}

		public String getManager_name() {
			return manager_name;
		}

		public void setManager_name(String manager_name) {
			this.manager_name = manager_name;
		}

		public String getManager_email() {
			return manager_email;
		}

		public void setManager_email(String manager_email) {
			this.manager_email = manager_email;
		}

		public String getManager_license() {
			return manager_license;
		}

		public void setManager_license(String manager_license) {
			this.manager_license = manager_license;
		}

		public String getManager_commission() {
			return manager_commission;
		}

		public void setManager_commission(String manager_commission) {
			this.manager_commission = manager_commission;
		}

		public Request(String manager_id, String manager_name, String manager_email, String manager_license, String manager_commission) {
			this.manager_id = manager_id;
			this.manager_name = manager_name;
			this.manager_email = manager_email;
			this.manager_license = manager_license;
			this.manager_commission = manager_commission;
			}

		public String approve() {

			DAO dao=DAO.getInstance();
			
			Connection con=DatabaseConnection.getConnection();
			// Get a prepared SQL statement
			String update_status = "UPDATE manager SET activated = ? WHERE managerid =  ?";
			PreparedStatement st;
			try {
				st = con.prepareStatement(update_status);
				st.setString(1,"true");
				st.setString(2, manager_id);
				
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
