package login;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Map;



@ManagedBean(name="account_manager")
@ApplicationScoped
public class AccountManager {
	
	String balance,manager_name,manager_id,manager_email,manager_commission;
	

	public void setBalance(String balance) {
		this.balance = balance;
	}
	
	public String getBalance() {
		
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		Map<String, Object> sessionMap = externalContext.getSessionMap();
		String userid=sessionMap.get("userid").toString();
		
		DAO dao=DAO.getInstance();
		balance=dao.getManagerBalance(userid)+"";
		
		return balance;
	}

	private ArrayList<Client> clientList = new ArrayList<Client>();
	
	private ArrayList<Transaction> transactionList = new ArrayList<Transaction>();
	
	//private static ArrayList<String> orderList=null;

		public ArrayList<Client> getClientList() {
			
			

			clientList = new ArrayList<Client>();
			try {
			
			ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
			Map<String, Object> sessionMap = externalContext.getSessionMap();
			String managerid=sessionMap.get("userid").toString();
				
			Connection con=DatabaseConnection.getConnection();
			// Get a prepared SQL statement
			String sql = "SELECT * from user_table WHERE managerid = ?";
			PreparedStatement st = con.prepareStatement(sql);
			st.setString(1,managerid);
			// Execute the statement
			ResultSet rs = st.executeQuery();
			rs.beforeFirst();
			
			while(rs.next())
			{
				clientList.add((new Client(rs.getString("userid"),rs.getString("name"),rs.getString("email"))));
			}
			
			
			
			
			}catch(Exception e)
			{
				e.printStackTrace();
			}
			
			return clientList;
			
		}
		
		public ArrayList<Transaction> getTransactionList() {
			
			ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
			Map<String, Object> sessionMap = externalContext.getSessionMap();
			String managerid=sessionMap.get("userid").toString();

			transactionList = new ArrayList<Transaction>();
			try {
				
			Connection con=DatabaseConnection.getConnection();
			// Get a prepared SQL statement
			String sql = "SELECT * from account_history where managerid = ?";
			PreparedStatement st = con.prepareStatement(sql);
			st.setString(1, managerid);
			// Execute the statement
			ResultSet rs = st.executeQuery();
			rs.beforeFirst();
			
			while(rs.next())
			{
				transactionList.add((new Transaction(rs.getString("userid"),rs.getString("symbol"),rs.getString("price"),rs.getString("count"),(Double.parseDouble(rs.getString("price"))*Integer.parseInt(rs.getString("count")))+"",rs.getString("manager_commission"),rs.getString("buy_or_sell"))));
			}
			
			}catch(Exception e)
			{
				e.printStackTrace();
			}
			
			return transactionList;
			
		}


		public static class Client{
			
			String userid,name,email;
			public Client(String userid, String name , String email ) {
				this.userid = userid;
				this.name = name;
				this.email = email;
			}
			public String getUserid() {
				return userid;
			}
			public void setUserid(String userid) {
				this.userid = userid;
			}
			public String getName() {
				return name;
			}
			public void setName(String name) {
				this.name = name;
			}
			public String getEmail() {
				return email;
			}
			public void setEmail(String email) {
				this.email = email;
			}
			
			

		}
		
		public static class Transaction{
			
			String client,symbol,price,count,cost,commission,buy_or_sell;
			
			public Transaction(String client,String symbol, String price, String count, String cost, String commission, String buy_or_sell ) {
				this.client=client;
				this.symbol = symbol;
				this.price = price;
				this.count = count;
				this.cost = cost;
				this.commission = commission;
				this.buy_or_sell = buy_or_sell;
			}
			
			public String getClient() {
				return client;
			}

			public void setClient(String client) {
				this.client = client;
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

			public String getBuy_or_sell() {
				return buy_or_sell;
			}

			public void setBuy_or_sell(String buy_or_sell) {
				this.buy_or_sell = buy_or_sell;
			}

			
			
			

		}
}
