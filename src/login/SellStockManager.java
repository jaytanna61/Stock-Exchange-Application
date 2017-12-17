package login;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;


@ManagedBean(name="sellstock_manager")
@ApplicationScoped
public class SellStockManager {
	
	String symbol,price,requestedCount,user;
	
	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;

	}
	
	private static Map<String,Object> userList;

	

	
	public Map<String,Object> getUserList() {
		
		userList = new LinkedHashMap<String,Object>();
		Connection con=DatabaseConnection.getConnection();
		
		// Get a prepared SQL statement
		String sql = "SELECT * FROM user_table WHERE managerid = ?";
		ResultSet rs = null;
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		Map<String, Object> sessionMap = externalContext.getSessionMap();
		String userid=sessionMap.get("userid").toString();
		try {
			
			PreparedStatement st = con.prepareStatement(sql);
			st.setString(1,userid);

			rs = st.executeQuery();
			
			if(rs.next())
			{
				userList.put(rs.getString("userid"), rs.getString("userid") );
		
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return userList;
	}
	
	public String getRequestedCount() {
		return requestedCount;
	}



	public void setRequestedCount(String requestedCount) {
		this.requestedCount = requestedCount;
	}

	public String getSymbol() {
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		Map<String, Object> sessionMap = externalContext.getSessionMap();
		String Symbol=sessionMap.get("symbol").toString();
		return Symbol;
	}



	public String getPrice() {
		DAO dao=DAO.getInstance();
		String price=dao.getPrice(getSymbol());
		return price;
	}

	


	public String sellStock()
	{
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		Map<String, Object> sessionMap = externalContext.getSessionMap();
		String managerid=sessionMap.get("userid").toString();
		
		DAO dao=DAO.getInstance();
		Double cost=Integer.parseInt(requestedCount) * Double.parseDouble(getPrice());
		Double commission_per=Double.parseDouble(dao.getManagerCommission(managerid));
		Double commission=(cost*commission_per)/100;
		
		if( Integer.parseInt(requestedCount) <= Integer.parseInt(dao.getCount(user,getSymbol())))
		{
			
			dao.sellStocks(user,managerid,commission,getSymbol(),requestedCount,getPrice(),cost);
			FacesContext.getCurrentInstance().addMessage(
					"sell_stock_form:count",
					new FacesMessage(FacesMessage.SEVERITY_WARN,
							"Sell done",
							"Sell done"));
			Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
			flash.setKeepMessages(true);
		}
		else
		{
			FacesContext.getCurrentInstance().addMessage(
					"sell_stock_form:count",
					new FacesMessage(FacesMessage.SEVERITY_WARN,
							"Customer does not have enough number of stocks to sell",
							"Customer does not have enough number of stocks to sell"));
			Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
			flash.setKeepMessages(true);
		}
		
		return null;
	}
	
	

}
