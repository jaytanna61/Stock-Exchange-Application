package login;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Map;



@ManagedBean(name="stock_main")
@ApplicationScoped
public class stockMain {

	
	private ArrayList<Stock> stockList = new ArrayList<Stock>();
	
	//private static ArrayList<String> orderList=null;

		public ArrayList<Stock> getStockList() {

			stockList = new ArrayList<Stock>();
			try {
			Connection con=DatabaseConnection.getConnection();
			// Get a prepared SQL statement
			String sql = "SELECT * from symbols ";
			PreparedStatement st = con.prepareStatement(sql);
			// Execute the statement
			ResultSet rs = st.executeQuery();
			//rs.beforeFirst();
		
			while(rs.next())
			{	
				stockList.add((new Stock(rs.getString("cname"),rs.getString("symbol"), 
						new BigDecimal(rs.getString("price")),Integer.parseInt(rs.getString("volume")))));
				//orderList.add(rs.getString(i));
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
			
			
			public String addToWatchlist() {
				//String bid=event.getComponent().getId();
				//System.out.println(symbol);
				
				ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
				Map<String, Object> sessionMap = externalContext.getSessionMap();
				String userid=sessionMap.get("userid").toString();
				DAO dao=DAO.getInstance();
				
				dao.addToWatchlist(userid, this.symbol);
				
				return null;
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
