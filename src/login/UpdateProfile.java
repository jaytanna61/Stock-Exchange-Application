package login;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;

@ManagedBean(name="update_profile")
@ApplicationScoped
public class UpdateProfile {

	private String name,email,userid,password;	

	Connection con;
	
	public UpdateProfile() {
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		Map<String, Object> sessionMap = externalContext.getSessionMap();
		String userid=sessionMap.get("userid").toString();
		con=DatabaseConnection.getConnection();
		String sql = "SELECT * FROM user_table WHERE userid = ?";
		ResultSet rs = null;
		
		try {
			PreparedStatement symbols_st = con.prepareStatement(sql);
			symbols_st.setString(1,userid);
			rs = symbols_st.executeQuery();
			rs.beforeFirst();
			rs.next();
			this.userid=rs.getString("userid");
			this.name=rs.getString("name");
			this.email=rs.getString("email");
			this.password=rs.getString("password");
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
		
		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	
	public String updateUserDetails() {
		
		con=DatabaseConnection.getConnection();
		String update_balance_sql = "UPDATE user_table SET name = ? , email = ? , password = ?  WHERE USERID = ?";
		
		try {
			PreparedStatement update_balance_st = con.prepareStatement(update_balance_sql);
			update_balance_st.setString(1,name);
			update_balance_st.setString(2,email);
			update_balance_st.setString(3,password);
			update_balance_st.setString(4,userid);
			// Execute the statement
			update_balance_st.executeUpdate();
			ExternalContext externalContext = FacesContext.getCurrentInstance()
			        .getExternalContext();
			try {
			externalContext.redirect(externalContext.getRequestContextPath()
		            + "/faces/response.xhtml");
			}catch(IOException e) {
				e.printStackTrace();
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	
	
	return null;
		}
	
}
