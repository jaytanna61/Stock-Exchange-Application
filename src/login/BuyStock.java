package login;

import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;

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
							"U do not have enough funds",
							"U do not have enough funds"));
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
		
		if(cost <= balance)
		{
			if(!managerid.equals("not_set"))
			{
			System.out.println(""+managerid);
			dao.buyStockRequest(userid,managerid,getSymbol(),getNumberOfStocks());
			FacesContext.getCurrentInstance().addMessage(
					"buy_stock_form:count",
					new FacesMessage(FacesMessage.SEVERITY_WARN,
							"Request sent",
							"Request sent"));
			Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
			flash.setKeepMessages(true);
			}
			else {
				FacesContext.getCurrentInstance().addMessage(
						"buy_stock_form:count",
						new FacesMessage(FacesMessage.SEVERITY_WARN,
								"You have not selecetd any manager yet",
								"You have not selected any manager yet"));
				Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
				flash.setKeepMessages(true);
			}
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
