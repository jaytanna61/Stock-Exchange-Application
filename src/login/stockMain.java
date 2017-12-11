package login;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Iterator;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@ManagedBean(name="stock_main")
@ApplicationScoped
public class stockMain {

	static final String Key="L0BF024O7DI22RX3";
	
	private static final ArrayList<Order> orderList = 
			new ArrayList<Order>(
		);
	
	//private static ArrayList<String> orderList=null;
	
		 
		public ArrayList<Order> getOrderList() {
			
			try {
			Connection con=DatabaseConnection.getConnection();
			// Get a prepared SQL statement
			String sql = "SELECT * from symbols ";
			PreparedStatement st = con.prepareStatement(sql);
			// Execute the statement
			ResultSet rs = st.executeQuery();
			//rs.beforeFirst();
			int i=1;
			while(rs.next())
			{
				String price=null;
				
				Client client= ClientBuilder.newClient();

				// Core settings are here, put what ever API parameter you want to use
				WebTarget target= client.target("https://www.alphavantage.co/query")
				   .queryParam("function", "TIME_SERIES_WEEKLY")
				   .queryParam("symbol", rs.getString("symbol"))
				   .queryParam("apikey", Key);
				// Actually calling API here, Use HTTP GET method
				// data is the response JSON string
				String data = target.request(MediaType.APPLICATION_JSON).get(String.class);
				
				try {
					// Use Jackson to read the JSON into a tree like structure
					ObjectMapper mapper = new ObjectMapper();
					JsonNode root = mapper.readTree(data);
					
					// Make sure the JSON is an object, as said in their documents
					assert root.isObject();
					// Read the "Meta Data" property of JSON object
					JsonNode metadata = root.get("Meta Data");
					assert metadata.isObject();
					// Read "2. Symbol" property of "Meta Data" property
					if (metadata.get("2. Symbol").isValueNode()) {
						System.out.println(metadata.get("2. Symbol").asText());
						System.out.println(metadata);
					}
					// Print "4. Time Zone" property of "Meta Data" property of root JSON object
					//System.out.println(root.at("/Meta Data/4. Time Zone").asText());
					// Read "Weekly Time Series" property of root JSON object
					Iterator<String> dates = root.get("Weekly Time Series").fieldNames();
					JsonNode first=root.get("Weekly Time Series");
					JsonNode Second=first.get(dates.next());
					price=Second.get("1. open").asText();
					
					/*while(dates.hasNext()) {
						// Read the first date's open price
						JsonNode first=root.get("Weekly Time Series");
						JsonNode Second=first.get(dates.next());
						price=Second.get("1. open").asText();
						System.out.println(Second);
						//price = root.at("/Weekly Time Series/" + dates.next() + "/1. open");
						System.out.println(Double.parseDouble(price));
						// remove break if you wan't to print all the open prices.
						break;
					}*/
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				orderList.add((new Order(rs.getString("cname"),rs.getString("symbol"), 
						new BigDecimal(price),i)));
				//orderList.add(rs.getString(i));
				i++;
			}
			
			}catch(Exception e)
			{
				e.printStackTrace();
			}
			
			return orderList;
			
		}
		
		public String deleteAction(Order order) {
			orderList.remove(order);
			return null;
		}
	 
		public static class Order{
			
			String orderNo;
			String productName;
			BigDecimal price;
			int qty;

			public Order(String orderNo, String productName, 
					BigDecimal price, int qty) {
				this.orderNo = orderNo;
				this.productName = productName;
				this.price = price;
				this.qty = qty;
			}

			public String getOrderNo() {
				return orderNo;
			}

			public void setOrderNo(String orderNo) {
				this.orderNo = orderNo;
			}

			public String getProductName() {
				return productName;
			}

			public void setProductName(String productName) {
				this.productName = productName;
			}

			public BigDecimal getPrice() {
				return price;
			}

			public void setPrice(BigDecimal price) {
				this.price = price;
			}

			public int getQty() {
				return qty;
			}

			public void setQty(int qty) {
				this.qty = qty;
			}
		}
}
