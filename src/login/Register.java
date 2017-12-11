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
import javax.faces.context.Flash;

@ManagedBean(name="register")
@ApplicationScoped
public class Register {

	private String name,email,userid,password,usertype,license,commission;	

	public Register() {
		
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
	
	public String getUsertype() {
		return usertype;
	}

	public void setUsertype(String usertype) {
		this.usertype = usertype;
	}
	
	public void setLicense(String license) {
		this.license = license;
	}

	public String getLicense() {
		return license;
	}

	public String getCommission() {
		return commission;
	}

	public void setCommission(String commission) {
		this.commission = commission;
	}
	
	public String registerUser() {
		
		boolean valid=validateUserID(this.userid);
		
		if(!valid)
		{
		Connection con = null;
		try {
			// Setup the DataSource object
			com.mysql.jdbc.jdbc2.optional.MysqlDataSource ds = new com.mysql.jdbc.jdbc2.optional.MysqlDataSource();
			ds.setServerName("localhost");
			ds.setPortNumber(3306);
			ds.setDatabaseName("user");
			ds.setUser("root");
			ds.setPassword("admin");

			// Get a connection object
			con = ds.getConnection();

			// Get a prepared SQL statement
			String sql = "INSERT into user_table (name,email,userid,password) VALUES (?,?,?,?)";
			PreparedStatement st = con.prepareStatement(sql);
			//st.setInt(1, 1);
			st.setString(1,this.name);
			st.setString(2, this.email);
			st.setString(3,this.userid);
			st.setString(4, this.password);
			
			// Execute the statement
			st.executeUpdate();
			//JOptionPane.showMessageDialog(null, "User Registered");
			
			FacesContext.getCurrentInstance().addMessage(
					"login_form:password",
					new FacesMessage(FacesMessage.SEVERITY_WARN,
							"Registration Successful",
							"User Registered"));
			Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
			flash.setKeepMessages(true);
			
			
			signinUser();

			// Iterate through results
			/*if (rs.next()) {
				System.out.println("First Name is: " + rs.getString("first_name"));
				this.name = rs.getString("first_name");
			}*/
		} catch (Exception e) {
			System.err.println("Exception: " + e.getMessage());
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
			}
		}
		}
		else
		{
			FacesContext.getCurrentInstance().addMessage(
					"register_form:userid",
					new FacesMessage(FacesMessage.SEVERITY_WARN,
							"Username Exists",
							"Username ALready exits"));
		}
		
		return "true";
	}
	
	public String signinUser() {
		
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
	
	public boolean validateUserID(String userid)
	{
		
		Connection con = null;
		try {
			// Setup the DataSource object
			com.mysql.jdbc.jdbc2.optional.MysqlDataSource ds = new com.mysql.jdbc.jdbc2.optional.MysqlDataSource();
			ds.setServerName("localhost");
			ds.setPortNumber(3306);
			ds.setDatabaseName("user");
			ds.setUser("root");
			ds.setPassword("admin");

			// Get a connection object
			con = ds.getConnection();

			// Get a prepared SQL statement
			String sql = "SELECT name from user_table where userid = ?";
			PreparedStatement st = con.prepareStatement(sql);
			st.setString(1,userid);

			// Execute the statement
			ResultSet rs = st.executeQuery();

			// Iterate through results
			if (rs.next()) {
				return true;
			}
			return false;
		} catch (Exception e) {
			System.err.println("Exception: " + e.getMessage());
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
			}
		}
		return false;
		
	}
}
