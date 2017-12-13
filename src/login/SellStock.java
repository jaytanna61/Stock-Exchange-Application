package login;

import java.util.Map;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

@ManagedBean(name="sellstock")
@ApplicationScoped
public class SellStock {
	
	String symbol,price,count,requestedCount;
	
	
	
	public String getRequestedCount() {
		return requestedCount;
	}



	public void setRequestedCount(String requestedCount) {
		this.requestedCount = requestedCount;
	}



	public String getCount() {
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		Map<String, Object> sessionMap = externalContext.getSessionMap();
		String userid=sessionMap.get("userid").toString();
		DAO dao=DAO.getInstance();
		String count=dao.getCount(userid,getSymbol());
		return count;
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
		String userid=sessionMap.get("userid").toString();
		
		DAO dao=DAO.getInstance();
		
		if( Integer.parseInt(requestedCount) <= Integer.parseInt(getCount()))
		{
			Double cost=Integer.parseInt(requestedCount) * Double.parseDouble(getPrice());
			dao.sellStocks(userid,null,null,getSymbol(),requestedCount,getPrice(),cost);
		}
		else
		{
			
		}
		
		return null;
	}
	
	public String sellRequest()
	{
		
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		Map<String, Object> sessionMap = externalContext.getSessionMap();
		String userid=sessionMap.get("userid").toString();
		DAO dao=DAO.getInstance();
		String managerid=dao.getManager(userid);
		
		if(managerid != null && Integer.parseInt(requestedCount) <= Integer.parseInt(getCount()))
		{
			dao.sellStockRequest(userid,managerid,getSymbol(),getCount());
		}
		else
		{
			System.out.println("IN");
		}
		
		return null;
	}
	

}
