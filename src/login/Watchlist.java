package login;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;



@ManagedBean(name="watchlist")
@ApplicationScoped
public class Watchlist {
	
	private ArrayList<Stock> stockList = new ArrayList<Stock>();
	
	//private static ArrayList<String> orderList=null;

		public ArrayList<Stock> getStockList() {
			
			

			stockList = new ArrayList<Stock>();
			try {
			
			ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
			Map<String, Object> sessionMap = externalContext.getSessionMap();
			String userid=sessionMap.get("userid").toString();
				
			Connection con=DatabaseConnection.getConnection();
			// Get a prepared SQL statement
			String symbols_sql = "SELECT symbol from user_watchlist WHERE userid = ?";
			PreparedStatement symbols_st = con.prepareStatement(symbols_sql);
			symbols_st.setString(1,userid);
			// Execute the statement
			ResultSet symbols_rs = symbols_st.executeQuery();
			
			
			//rs.beforeFirst();
			ArrayList<String> symbolList = new ArrayList<String>();
			
			while(symbols_rs.next())
			{
				symbolList.add(symbols_rs.getString("symbol"));
			}
			
			DatabaseConnection.close(con);
			
			Iterator<String> iterator = symbolList.listIterator();
			
			while(iterator.hasNext())
			{
			// Get a prepared SQL statement
			
			Connection con2=DatabaseConnection.getConnection();
			String sql = "SELECT * from symbols WHERE symbol = ? ";
			PreparedStatement st = con2.prepareStatement(sql);
			st.setString(1,iterator.next());
			// Execute the statement
			ResultSet rs = st.executeQuery();
			rs.beforeFirst();
			rs.next();
			
			
			stockList.add((new Stock(rs.getString("cname"),rs.getString("symbol"), 
					new BigDecimal(rs.getString("price")),Integer.parseInt(rs.getString("volume")))));
			
			}
			}catch(Exception e)
			{
				e.printStackTrace();
			}
			
			return stockList;
			
		}
		
		public String deleteAction(Stock stock) {
			stockList.remove(stock);
			return null;
		}
		
		




		public static class Stock{
			
			String companyName;
			String symbol;
			BigDecimal price;
			int volume;
			

			
			public String buy() {
				ExternalContext externalContext = FacesContext.getCurrentInstance()
				        .getExternalContext();
				try {
				externalContext.redirect(externalContext.getRequestContextPath()
			            + "/faces/buystock.xhtml");
				FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("symbol",this.symbol);
				}catch(IOException e) {
					e.printStackTrace();
				}
				return "true";
				
			}
			
			public String viewHistory() {
				ExternalContext externalContext = FacesContext.getCurrentInstance()
				        .getExternalContext();
				try {
				externalContext.redirect(externalContext.getRequestContextPath()
			            + "/faces/stock_history.xhtml");
				FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("symbol",this.symbol);
				}catch(IOException e) {
					e.printStackTrace();
				}
				return "true";
				
			}
			
			public String sell() {
				ExternalContext externalContext = FacesContext.getCurrentInstance()
				        .getExternalContext();
				try {
				externalContext.redirect(externalContext.getRequestContextPath()
			            + "/faces/sellstock.xhtml");
				FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("symbol",this.symbol);
				}catch(IOException e) {
					e.printStackTrace();
				}
				return "true";
			}
			
			public Stock(String companyName, String symbol, 
					BigDecimal price, int volume) {
				this.companyName = companyName;
				this.symbol = symbol;
				this.price = price;
				this.volume = volume;
			}

			public String getCompanyName() {
				return companyName;
			}

			public void setCompanyName(String companyName) {
				this.companyName = companyName;
			}

			public String getSymbol() {
				return symbol;
			}

			public void setSymbol(String symbol) {
				this.symbol = symbol;
			}

			public BigDecimal getPrice() {
				return price;
			}

			public void setPrice(BigDecimal price) {
				this.price = price;
			}

			public int getVolume() {
				return volume;
			}

			public void setVolume(int volume) {
				this.volume = volume;
			}
		}
}
