package login;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

@ManagedBean
@ApplicationScoped
public class Login {
	
	private String userid="jaytanna61",password="33637371",name;
	
	public Login(){	
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
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String submit(){
		
		Connection con = null;
		try {

			con=DatabaseConnection.getConnection();
			// Get a prepared SQL statement
			String sql = "SELECT name from user_table where userid = ? and password = ?";
			PreparedStatement st = con.prepareStatement(sql);
			st.setString(1,this.userid);
			st.setString(2, this.password);

			// Execute the statement
			ResultSet rs = st.executeQuery();

			// Iterate through results
			if (rs.next()) {
				
				//AlphaVantage.getData();
				
				ExternalContext externalContext = FacesContext.getCurrentInstance()
				        .getExternalContext();
				this.setName(rs.getString("name"));
				/*HttpSession session = SessionUtils.getSession();
				session.setAttribute("username", this.userid);*/
				FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("userid",this.userid);
				try {
					    externalContext.redirect(externalContext.getRequestContextPath()
					            + "/faces/response.xhtml");
					    return "true";
			
				} catch (IOException e) {
				    // TODO Auto-generated catch block
				    e.printStackTrace();
				    return "false";
				}
				
			}
			else
			{
				con=DatabaseConnection.getConnection();
				// Get a prepared SQL statement
				sql = "SELECT name,activated from manager where managerid = ? and password = ?";
				st = con.prepareStatement(sql);
				st.setString(1,this.userid);
				st.setString(2, this.password);

				// Execute the statement
				rs = st.executeQuery();
				rs.beforeFirst();

				// Iterate through results
				
				if (rs.next()) {
					
					if(rs.getString("activated").equals("true"))
					{
					//AlphaVantage.getData();
					
					ExternalContext externalContext = FacesContext.getCurrentInstance()
					        .getExternalContext();
					this.setName(rs.getString("name"));
					/*HttpSession session = SessionUtils.getSession();
					session.setAttribute("username", this.userid);*/
					FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("userid",this.userid);
					try {
						    externalContext.redirect(externalContext.getRequestContextPath()
						            + "/faces/response_manager.xhtml");
						    return "true";
				
					} catch (IOException e) {
					    // TODO Auto-generated catch block
					    e.printStackTrace();
					    return "false";
					}
					}
					else
					{
						FacesContext.getCurrentInstance().addMessage(
								"login_form:password",
								new FacesMessage(FacesMessage.SEVERITY_WARN,
										"Not Approved yet",
										"Not a Approved Manager yet"));
					}
				}
				else
				{
					con=DatabaseConnection.getConnection();
					// Get a prepared SQL statement
					sql = "SELECT adminid from admin where adminid = ? and password = ?";
					st = con.prepareStatement(sql);
					st.setString(1,this.userid);
					st.setString(2, this.password);

					// Execute the statement
					rs = st.executeQuery();
					rs.beforeFirst();

					
					
					
				if(rs.next()) {

					ExternalContext externalContext = FacesContext.getCurrentInstance()
					        .getExternalContext();
					this.setName(rs.getString("adminid"));
					/*HttpSession session = SessionUtils.getSession();
					session.setAttribute("username", this.userid);*/
					FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("userid",this.userid);
					try {
						    externalContext.redirect(externalContext.getRequestContextPath()
						            + "/faces/response_admin.xhtml");
						    return "true";
				
					} catch (IOException e) {
					    // TODO Auto-generated catch block
					    e.printStackTrace();
					    return "false";
					}
					
				}
				else {
				FacesContext.getCurrentInstance().addMessage(
						"login_form:password",
						new FacesMessage(FacesMessage.SEVERITY_WARN,
								"Incorrect Username and Passowrd",
								"Username or password is incorrect"));
				}
				}
				}
		}catch (Exception e) {
			System.err.println("Exception: " + e.getMessage());
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
			}
		}
		return "false";

	}

	public String logout() {
		/*HttpSession session = SessionUtils.getSession();
		session.invalidate();*/
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		ExternalContext externalContext = FacesContext.getCurrentInstance()
		        .getExternalContext();
		try {
		externalContext.redirect(externalContext.getRequestContextPath()
	            + "/faces/login.xhtml");
		}catch(IOException e) {
			e.printStackTrace();
		}
		return "true";
	}
	
	public String update_profile() {
		ExternalContext externalContext = FacesContext.getCurrentInstance()
		        .getExternalContext();
		try {
		externalContext.redirect(externalContext.getRequestContextPath()
	            + "/faces/update_profile.xhtml");
		}catch(IOException e) {
			e.printStackTrace();
		}
		return "true";
	}
	
	public String update_profile_manager() {
		ExternalContext externalContext = FacesContext.getCurrentInstance()
		        .getExternalContext();
		try {
		externalContext.redirect(externalContext.getRequestContextPath()
	            + "/faces/update_profile_manager.xhtml");
		}catch(IOException e) {
			e.printStackTrace();
		}
		return "true";
	}
	
	public String watchlist() {
		ExternalContext externalContext = FacesContext.getCurrentInstance()
		        .getExternalContext();
		try {
		externalContext.redirect(externalContext.getRequestContextPath()
	            + "/faces/watchlist.xhtml");
		}catch(IOException e) {
			e.printStackTrace();
		}
		return "true";
	}
	
	public String account() {
		ExternalContext externalContext = FacesContext.getCurrentInstance()
		        .getExternalContext();
		try {
		externalContext.redirect(externalContext.getRequestContextPath()
	            + "/faces/account.xhtml");
		}catch(IOException e) {
			e.printStackTrace();
		}
		return "true";
	}
	
	public String managers() {
		ExternalContext externalContext = FacesContext.getCurrentInstance()
		        .getExternalContext();
		try {
		externalContext.redirect(externalContext.getRequestContextPath()
	            + "/faces/manager_details.xhtml");
		}catch(IOException e) {
			e.printStackTrace();
		}
		return "true";
	}
	
	public String home() {
		ExternalContext externalContext = FacesContext.getCurrentInstance()
		        .getExternalContext();
		try {
		externalContext.redirect(externalContext.getRequestContextPath()
	            + "/faces/response.xhtml");
		}catch(IOException e) {
			e.printStackTrace();
		}
		return "true";
	}
	
	public String home_manager() {
		ExternalContext externalContext = FacesContext.getCurrentInstance()
		        .getExternalContext();
		try {
		externalContext.redirect(externalContext.getRequestContextPath()
	            + "/faces/response_manager.xhtml");
		}catch(IOException e) {
			e.printStackTrace();
		}
		return "true";
	}
	
	public String client_request() {
		ExternalContext externalContext = FacesContext.getCurrentInstance()
		        .getExternalContext();
		try {
		externalContext.redirect(externalContext.getRequestContextPath()
	            + "/faces/client_requests.xhtml");
		}catch(IOException e) {
			e.printStackTrace();
		}
		return "true";
	}
	
	public String watchlist_manager() {
		ExternalContext externalContext = FacesContext.getCurrentInstance()
		        .getExternalContext();
		try {
		externalContext.redirect(externalContext.getRequestContextPath()
	            + "/faces/watchlist_manager.xhtml");
		}catch(IOException e) {
			e.printStackTrace();
		}
		return "true";
	}
	
	public String account_manager() {
		ExternalContext externalContext = FacesContext.getCurrentInstance()
		        .getExternalContext();
		try {
		externalContext.redirect(externalContext.getRequestContextPath()
	            + "/faces/account_manager.xhtml");
		}catch(IOException e) {
			e.printStackTrace();
		}
		return "true";
	}
	
}
