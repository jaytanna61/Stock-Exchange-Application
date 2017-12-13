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
	
	private ArrayList<Count> countList = new ArrayList<Count>();
	
	private ArrayList<Count> transactionList = new ArrayList<Count>();
	
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
		
		public ArrayList<Count> getTransactionList() {
			
			

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
