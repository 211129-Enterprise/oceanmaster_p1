package config;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Session {

	static Connection conn = ConnectionConfig.getConnection();
	private static int sessionId;
	private int sessid;

	public Session() {
		super();
		sessionId++;
		this.sessid = sessionId;
	}

	public int getSessid() {
		return sessid;
	}

	public DatabaseMetaData getMetaData() {
		try {
			DatabaseMetaData dbmd = conn.getMetaData();
			return dbmd;
		} catch (SQLException e) {
			System.out.println("Failed to make connection please check your application.properties "
					+ "and/or ensure correct filepath in ConnectionConfig.java by calling setFilePath() method");
			return null;
		}
	}

	public ArrayList<String> getTablesmethod() {
		ArrayList<String> arr = new ArrayList<String>();
		try {
			ResultSet rs = getMetaData().getTables(null, null, null, new String[] { "TABLE" });

			while (rs.next()) {
				arr.add(rs.getString("TABLE_NAME"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return arr;
	}

}
