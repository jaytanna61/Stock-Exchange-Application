package login;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@ManagedBean(name="stock_history")
@ApplicationScoped
public class StockHistory {

	String symbol;

	
	public String getSymbol() {
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		Map<String, Object> sessionMap = externalContext.getSessionMap();
		symbol=sessionMap.get("symbol").toString();
		return symbol;
	}



	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}



	private ArrayList<WeekHistory> weekList;
	
	//private static ArrayList<String> orderList=null;
		
		public ArrayList<WeekHistory> getWeekList() {
			
			

			weekList = new ArrayList<WeekHistory>();
			try {
				ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
				Map<String, Object> sessionMap = externalContext.getSessionMap();
				String Symbol=sessionMap.get("symbol").toString();
				
			JsonNode root = AlphaVantage.getWeekData(Symbol);
			Iterator<String> dates = root.get("Weekly Time Series").fieldNames();
			JsonNode first=root.get("Weekly Time Series");
			int i=0;
			while(dates.hasNext() && i<6) {
				// Read the first date's open price
				String date=dates.next();
				JsonNode Second=first.get(date);
				
				String open=Second.get("1. open").asText();
				String high=Second.get("2. high").asText();
				String low=Second.get("3. low").asText();
				String close=Second.get("4. close").asText();
				String volume=Second.get("5. volume").asText();
				weekList.add(new WeekHistory(date,open,high,low,close,volume));
				i++;
				//price = root.at("/Weekly Time Series/" + dates.next() + "/1. open");
	
				// remove break if you wan't to print all the open prices.
			}
			
			
			}catch(Exception e)
			{
				e.printStackTrace();
			}
			
			return weekList;
			
		}
		
		private ArrayList<WeekHistory> min1List;
		
		//private static ArrayList<String> orderList=null;
			
			public ArrayList<WeekHistory> getMin1List() {
				
				

				min1List = new ArrayList<WeekHistory>();
				try {
					ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
					Map<String, Object> sessionMap = externalContext.getSessionMap();
					String Symbol=sessionMap.get("symbol").toString();
					
				JsonNode root = AlphaVantage.get1minData(Symbol);
				Iterator<String> dates = root.get("Time Series (1min)").fieldNames();
				JsonNode first=root.get("Time Series (1min)");
				int i=0;
				while(dates.hasNext() && i<10) {
					// Read the first date's open price
					String date=dates.next();
					JsonNode Second=first.get(date);
					
					String open=Second.get("1. open").asText();
					String high=Second.get("2. high").asText();
					String low=Second.get("3. low").asText();
					String close=Second.get("4. close").asText();
					String volume=Second.get("5. volume").asText();
					min1List.add(new WeekHistory(date,open,high,low,close,volume));
					i++;
					//price = root.at("/Weekly Time Series/" + dates.next() + "/1. open");
		
					// remove break if you wan't to print all the open prices.
				}
				
				
				}catch(Exception e)
				{
					e.printStackTrace();
				}
				
				return min1List;
				
			}
			
			private ArrayList<WeekHistory> min60List;
			
			//private static ArrayList<String> orderList=null;
		
				public ArrayList<WeekHistory> getMin60List() {
					
					

					min60List = new ArrayList<WeekHistory>();
					try {
						ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
						Map<String, Object> sessionMap = externalContext.getSessionMap();
						String Symbol=sessionMap.get("symbol").toString();
						
					JsonNode root = AlphaVantage.get60minData(Symbol);
					Iterator<String> dates = root.get("Time Series (60min)").fieldNames();
					JsonNode first=root.get("Time Series (60min)");
					int i=0;
					while(dates.hasNext() && i<6) {
						// Read the first date's open price
						String date=dates.next();
						JsonNode Second=first.get(date);
						
						String open=Second.get("1. open").asText();
						String high=Second.get("2. high").asText();
						String low=Second.get("3. low").asText();
						String close=Second.get("4. close").asText();
						String volume=Second.get("5. volume").asText();
						min60List.add(new WeekHistory(date,open,high,low,close,volume));
						i++;
						//price = root.at("/Weekly Time Series/" + dates.next() + "/1. open");
			
						// remove break if you wan't to print all the open prices.
					}
					
					
					}catch(Exception e)
					{
						e.printStackTrace();
					}
					
					return min60List;
					
				}
				
				
				private ArrayList<WeekHistory> daysList;
				
				//private static ArrayList<String> orderList=null;
			
					public ArrayList<WeekHistory> getDaysList() {
						
						

						daysList = new ArrayList<WeekHistory>();
						try {
							ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
							Map<String, Object> sessionMap = externalContext.getSessionMap();
							String Symbol=sessionMap.get("symbol").toString();
							
						JsonNode root = AlphaVantage.getDaysData(Symbol);
						Iterator<String> dates = root.get("Time Series (Daily)").fieldNames();
						JsonNode first=root.get("Time Series (Daily)");
						int i=0;
						while(dates.hasNext() && i<6) {
							// Read the first date's open price
							String date=dates.next();
							JsonNode Second=first.get(date);
							
							String open=Second.get("1. open").asText();
							String high=Second.get("2. high").asText();
							String low=Second.get("3. low").asText();
							String close=Second.get("4. close").asText();
							String volume=Second.get("5. volume").asText();
							daysList.add(new WeekHistory(date,open,high,low,close,volume));
							i++;
							//price = root.at("/Weekly Time Series/" + dates.next() + "/1. open");
				
							// remove break if you wan't to print all the open prices.
						}
						
						
						}catch(Exception e)
						{
							e.printStackTrace();
						}
						
						return daysList;
						
					}



		public static class WeekHistory{
			
			String date,open,high,low,close,volume;
			
			public WeekHistory(String date, String open, String high, String low, String close, String volume ) {
				this.date = date;
				this.open = open;
				this.high = high;
				this.low = low;
				this.close = close;
				this.volume = volume;
			}

			public String getDate() {
				return date;
			}

			public void setDate(String date) {
				this.date = date;
			}

			public String getOpen() {
				return open;
			}

			public void setOpen(String open) {
				this.open = open;
			}

			public String getHigh() {
				return high;
			}

			public void setHigh(String high) {
				this.high = high;
			}

			public String getLow() {
				return low;
			}

			public void setLow(String low) {
				this.low = low;
			}

			public String getClose() {
				return close;
			}

			public void setClose(String close) {
				this.close = close;
			}

			public String getVolume() {
				return volume;
			}

			public void setVolume(String volume) {
				this.volume = volume;
			}
			

		}
}
