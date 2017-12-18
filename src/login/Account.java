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



@ManagedBean(name="account")
@ApplicationScoped
public class Account {
	
	String balance,manager_name,manager_id,manager_email,manager_commission;
	
	
	
	public String getManager_id() {
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		Map<String, Object> sessionMap = externalContext.getSessionMap();
		String userid=sessionMap.get("userid").toString();
		
		DAO dao=DAO.getInstance();
		manager_id=dao.getManager(userid);
		return manager_id;
	}

	public void setManager_id(String manager_id) {
		this.manager_id = manager_id;
	}

	public String getManager_name() {
		
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		Map<String, Object> sessionMap = externalContext.getSessionMap();
		String userid=sessionMap.get("userid").toString();
		
		DAO dao=DAO.getInstance();
		String managerid=dao.getManager(userid);
		manager_name=dao.getManagerName(managerid);
		
		return manager_name;
	}

	public void setManager_name(String manager_name) {
		this.manager_name = manager_name;
	}
	

	public String getManager_email() {
		
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		Map<String, Object> sessionMap = externalContext.getSessionMap();
		String userid=sessionMap.get("userid").toString();
		
		DAO dao=DAO.getInstance();
		String managerid=dao.getManager(userid);
		manager_email=dao.getManagerEmail(managerid);
		
		return manager_email;
	}

	public void setManager_email(String manager_email) {
		this.manager_email = manager_email;
	}

	public String getManager_commission() {
		
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		Map<String, Object> sessionMap = externalContext.getSessionMap();
		String userid=sessionMap.get("userid").toString();
		
		DAO dao=DAO.getInstance();
		String managerid=dao.getManager(userid);
		manager_commission=dao.getManagerCommission(managerid);
		
		return manager_commission + "%";
	}

	public void setManager_commission(String manager_commission) {
		this.manager_commission = manager_commission;
	}

	public void setBalance(String balance) {
		this.balance = balance;
	}
	
	public String getBalance() {
		
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		Map<String, Object> sessionMap = externalContext.getSessionMap();
		String userid=sessionMap.get("userid").toString();
		
		DAO dao=DAO.getInstance();
		balance=dao.getBalance(userid)+"";
		
		return balance;
	}

	private ArrayList<Count> countList = new ArrayList<Count>();
	
	private ArrayList<Transaction> transactionList = new ArrayList<Transaction>();
	
	//private static ArrayList<String> orderList=null;

		public ArrayList<Count> getCountList() {
			
			

			countList = new ArrayList<Count>();
			try {
			
			ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
			Map<String, Object> sessionMap = externalContext.getSessionMap();
			String userid=sessionMap.get("userid").toString();
				
			Connection con=DatabaseConnection.getConnection();
			// Get a prepared SQL statement
			String sql = "SELECT * from stock_count WHERE userid = ?";
			PreparedStatement st = con.prepareStatement(sql);
			st.setString(1,userid);
			// Execute the statement
			ResultSet rs = st.executeQuery();
			rs.beforeFirst();
			
			while(rs.next())
			{
				countList.add((new Count(rs.getString("symbol"),rs.getString("count"))));
			}
			
			
			
			
			}catch(Exception e)
			{
				e.printStackTrace();
			}
			
			return countList;
			
		}
		
		public ArrayList<Transaction> getTransactionList() {
			
			

			transactionList = new ArrayList<Transaction>();
			try {
				
			Connection con=DatabaseConnection.getConnection();
			// Get a prepared SQL statement
			String sql = "SELECT * from account_history where userid = ?";
			
			ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
			Map<String, Object> sessionMap = externalContext.getSessionMap();
			String userid=sessionMap.get("userid").toString();
			
			PreparedStatement st = con.prepareStatement(sql);
			st.setString(1,userid);
			// Execute the statement
			ResultSet rs = st.executeQuery();
			rs.beforeFirst();
			
			while(rs.next())
			{
				transactionList.add((new Transaction(rs.getString("symbol"),rs.getString("price"),rs.getString("count"),(Double.parseDouble(rs.getString("price"))*Integer.parseInt(rs.getString("count")))+"",rs.getString("manager_commission"),rs.getString("buy_or_sell"))));
			}
			
			}catch(Exception e)
			{
				e.printStackTrace();
			}
			
			return transactionList;
			
		}


		public static class Count{
			
			String symbol,count;
			public Count(String symbol, String count ) {
				this.symbol = symbol;
				this.count = count;
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

		}
		
		public static class Transaction{
			
			String symbol,price,count,cost,commission,buy_or_sell;
			
			public Transaction(String symbol, String price, String count, String cost, String commission, String buy_or_sell ) {
				this.symbol = symbol;
				this.price = price;
				this.count = count;
				this.cost = cost;
				this.commission = commission;
				this.buy_or_sell = buy_or_sell;
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
