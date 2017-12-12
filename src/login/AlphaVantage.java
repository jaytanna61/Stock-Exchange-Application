package login;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Iterator;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AlphaVantage {
	
	static final String Key="L0BF024O7DI22RX3";
	
	
	
	private AlphaVantage() {
		
	}
	
	public static void getData() {
		String price=null;
		int volume=0;
		
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
					volume=Second.get("5. volume").asInt();
					
					String inner_sql = "UPDATE SYMBOLS SET PRICE = ? , VOLUME = ? WHERE SYMBOL = ?";;
					PreparedStatement inner_st = con.prepareStatement(inner_sql);
					inner_st.setString(1,price);
					inner_st.setString(2,volume+"");
					inner_st.setString(3,rs.getString("symbol"));
					// Execute the statement
					inner_st.executeUpdate();
					
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
			}
			
		}catch(Exception e) {
			e.printStackTrace();
			}
		}
		
	}

