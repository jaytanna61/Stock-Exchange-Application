package login;

import java.sql.Connection;

public class DatabaseConnection {
	
	public static com.mysql.jdbc.jdbc2.optional.MysqlDataSource ds;
	
	public static Connection getConnection() {
	
		if(ds==null)
		{
		try {
			
		/*ds = new com.mysql.jdbc.jdbc2.optional.MysqlDataSource();
		ds.setServerName("localhost");
		ds.setPortNumber(3306);
		ds.setDatabaseName("user");
		ds.setUser("root");
		ds.setPassword("admin");*/
		
		ds = new com.mysql.jdbc.jdbc2.optional.MysqlDataSource();
		ds.setServerName(System.getenv("ICSI518_SERVER"));
		ds.setPortNumber(Integer.valueOf(System.getenv("ICSI518_PORT")));
		ds.setDatabaseName(System.getenv("ICSI518_DB"));
		ds.setUser(System.getenv("ICSI518_USER"));
		ds.setPassword(System.getenv("ICSI518_PASSWORD"));
		return ds.getConnection();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}
		}
		else
		{
			try {
			return ds.getConnection();
			}
			catch(Exception e)
			{
				e.printStackTrace();
				return null;
			}
		}
	}
	
	public static void close(Connection con) {
        try {
            con.close();
        } catch (Exception ex) {
        }
    }

}
