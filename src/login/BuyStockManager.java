package login;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;

@ManagedBean(name="buystock_manager")
@ApplicationScoped
public class BuyStockManager {
	
	String symbol,price,numberOfStocks,user;
	
	
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




	public String getNumberOfStocks() {
		return numberOfStocks;
	}



	public void setNumberOfStocks(String numberOfStocks) {
		this.numberOfStocks = numberOfStocks;
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



	public String buyStock()
	{
		
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		Map<String, Object> sessionMap = externalContext.getSessionMap();
		String managerid=sessionMap.get("userid").toString();
		Double cost= Integer.parseInt(getNumberOfStocks()) * Double.parseDouble(getPrice());
		DAO dao=DAO.getInstance();
		Double balance=dao.getBalance(user);
		
		Double commission_per=Double.parseDouble(dao.getManagerCommission(managerid));
		
		Double commission=(cost*commission_per)/100;
		if((cost+commission) <= balance)
		{
			dao.buyStocks(user,managerid,commission,getSymbol(),getNumberOfStocks(),getPrice(),cost);
			FacesContext.getCurrentInstance().addMessage(
					"buy_stock_form:count",
					new FacesMessage(FacesMessage.SEVERITY_WARN,
							"Purchase done",
							"Purchase done"));
			Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
			flash.setKeepMessages(true);
		}
		else
		{
			FacesContext.getCurrentInstance().addMessage(
					"buy_stock_form:count",
					new FacesMessage(FacesMessage.SEVERITY_WARN,
							"Customer does not have enough funds",
							"Customer does not have enough funds"));
			Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
			flash.setKeepMessages(true);
		}
		
		return null;
	}
	
	public String buyRequest()
	{
		
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		Map<String, Object> sessionMap = externalContext.getSessionMap();
		String userid=sessionMap.get("userid").toString();
		Double cost= Integer.parseInt(getNumberOfStocks()) * Double.parseDouble(getPrice());
		DAO dao=DAO.getInstance();
		
		Double balance=dao.getBalance(userid);
		
		String managerid=dao.getManager(userid);
		
		if(managerid != null && cost <= balance)
		{
			dao.buyStockRequest(userid,managerid,getSymbol(),getNumberOfStocks());
			FacesContext.getCurrentInstance().addMessage(
					"buy_stock_form:count",
					new FacesMessage(FacesMessage.SEVERITY_WARN,
							"Request sent",
							"Request sent"));
			Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
			flash.setKeepMessages(true);
		}
		else
		{
			FacesContext.getCurrentInstance().addMessage(
					"buy_stock_form:count",
					new FacesMessage(FacesMessage.SEVERITY_WARN,
							"U do not have enough funds",
							"U do not have enough funds"));
			Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
			flash.setKeepMessages(true);
		}
		
		return null;
	}

}
