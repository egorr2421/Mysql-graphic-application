package src.Lab2;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.mysql.cj.api.jdbc.Statement;
import com.mysql.cj.api.mysqla.result.Resultset;

public class view {
public static Connection getConnection () throws Exception{
		
		try {
			//String driver = "com.sql.jdbc.Driver";
			//String url ="jdbc:mysql://localhost:3306/mydbtest";
			String url="jdbc:mysql://localhost:3306/mydbtest?serverTimezone=UTC";
			String username= "root";
			String password = "root";
			//Class.forName(driver);
			Connection conn = DriverManager.getConnection(url,username,password);
			System.out.println("Connected");
			return conn;
		}catch (Exception e) {
			System.out.println(e);
		}
		
		return null;
	}	
	
	public static void main(String[] args) throws Exception {
		Statement myStat= (Statement) getConnection().createStatement();
		//myStat.execute("insert into new_table (name,age,email) values ('jeka',45,'lolka@wad.ru');");
//	 top 	ResultSet myRs = myStat.executeQuery("SELECT COUNT(*) AS TABLE_COUNT FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = 'mydbtest'");
		ResultSet myRs = myStat.executeQuery("SELECT TABLE_NAME, COUNT(*) AS COLUMNS_COUNT FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = 'mydbtest' GROUP BY TABLE_NAME ");
//		while(myRs.next()) {
//		System.out.println(myRs.getString("name")+" "+myRs.getString("id")+" "+myRs.getString("age"));
//	}
		while(myRs.next()) {
			System.out.println(myRs.getString(1));
		}
	}


}
