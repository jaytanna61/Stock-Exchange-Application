package login;

import java.util.Map;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

@ManagedBean(name="buystock")
@ApplicationScoped
public class BuyStock {
	
	String symbol,price;
	
	
	
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
		return null;
	}

}
