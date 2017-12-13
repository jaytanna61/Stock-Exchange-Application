package login;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;



@ManagedBean(name="manager_details")
@ApplicationScoped
public class ManagersDetails {

	
	private ArrayList<Manager> managerList = new ArrayList<Manager>();
	
	//private static ArrayList<String> orderList=null;

		public ArrayList<Manager> getManagerList() {

			managerList = new ArrayList<Manager>();
			try {
			Connection con=DatabaseConnection.getConnection();
			// Get a prepared SQL statement
			String sql = "SELECT * from manager";
			PreparedStatement st = con.prepareStatement(sql);
			// Execute the statement
			ResultSet rs = st.executeQuery();
			//rs.beforeFirst();
		
			while(rs.next())
			{	
				managerList.add((new Manager(rs.getString("managerid"),rs.getString("name"), 
						rs.getString("email"),rs.getString("commission"))));
				//orderList.add(rs.getString(i));
			}
			
			}catch(Exception e)
			{
				e.printStackTrace();
			}
			
			return managerList;
			
		}
	
		
	 
		public static class Manager{
			
			String managerid,name,email,commission;
			
			
			public Manager(String managerid, String name, String email, String commission) {
				this.managerid = managerid;
				this.name = name;
				this.email = email;
				this.commission = commission +"%";
			}
			
			

			public String select() {
				//String bid=event.getComponent().getId();
				//System.out.println(symbol);
				ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
				Map<String, Object> sessionMap = externalContext.getSessionMap();
				String userid=sessionMap.get("userid").toString();
				DAO dao=DAO.getInstance();
				
				Connection con=DatabaseConnection.getConnection();
				// Get a prepared SQL statement
				String update_status = "UPDATE user_table SET managerid = ? WHERE userid =  ?";
				PreparedStatement st;
				try {
					st = con.prepareStatement(update_status);
					st.setString(1,managerid);
					st.setString(2,userid);

					st.executeUpdate();
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
				return null;
			}
			
			
			public String getManagerid() {
				return managerid;
			}



			public void setManagerid(String managerid) {
				this.managerid = managerid;
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



			public String getCommission() {
				return commission;
			}



			public void setCommission(String commission) {
				this.commission = commission;
			}



			

		}
}
