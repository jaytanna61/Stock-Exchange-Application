package login;

import java.sql.Connection;

public class DatabaseConnection {
	
	public static com.mysql.jdbc.jdbc2.optional.MysqlDataSource ds;
	
	public static Connection getConnection() {
	
		if(ds==null)
		{
		try {
		ds = new com.mysql.jdbc.jdbc2.optional.MysqlDataSource();
		ds.setServerName("localhost");
		ds.setPortNumber(3306);
		ds.setDatabaseName("user");
		ds.setUser("root");
		ds.setPassword("admin");
		
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
