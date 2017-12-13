package login;

import java.util.Map;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

@ManagedBean(name="buystock")
@ApplicationScoped
public class BuyStock {
	
	String symbol,price,numberOfStocks;
	
	
	
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
		String userid=sessionMap.get("userid").toString();
		Double cost= Integer.parseInt(getNumberOfStocks()) * Double.parseDouble(getPrice());
		DAO dao=DAO.getInstance();
		Double balance=dao.getBalance(userid);
		
		if(cost <= balance)
		{
			dao.buyStocks(userid,null,null,getSymbol(),getNumberOfStocks(),getPrice(),cost);
		}
		else
		{
			
		}
		
		return null;
	}

}
