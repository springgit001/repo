package common;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class JDBCTemplate {
	public static Connection getConnection() {
		Connection conn = null;
		Properties prop = new Properties();
		
		//driver 정보를 저장하여 제공할 수 있는 driver.properties 파일을 만들자
		//sql 패키지 만들고 그 안에 driver.properties 파일 생성 - 내용 작성
		
		//클래스 객체 위치 찾기 : 절대 경로를 반환한다.
		//"/" : 루트 디렉토리를 절대 경로로 URL 객체로 반환한다.
		//"./": 현재 디렉토리를 절대 경로로 URL 객체로 반환한다.
		//"./driver.properties" : 현재 경로의 driver.properties 파일의 경로를 URL 객체로 반환한다.
		//URL java.lang.Class.getResource(String name)
		String fileName = JDBCTemplate.class.getResource("/sql/driver.properties").getPath();
		
		try {
			prop.load(new FileReader(fileName));
			
			String driver = prop.getProperty("driver");
			String url = prop.getProperty("url");
			String user = prop.getProperty("user");
			String password = prop.getProperty("password");
		
			//1. 클래스 객체 등록, Driver 등록
			Class.forName(driver);
			
			//2. DBMS와 연결
			conn = DriverManager.getConnection(url, user, password);
			
			conn.setAutoCommit(false);
			
			//ojdbc6.jar 라이브러리 가져오자!!!
			
		}catch(IOException e) {
			e.printStackTrace();
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		return conn;
	}
	
	public static void close(Connection conn) {
		try {
			if(conn != null && !conn.isClosed())
				conn.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void close(Statement stmt) {
		try {
			if(stmt != null && !stmt.isClosed())
				stmt.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void close(ResultSet rs) {
		try {
			if(rs != null && !rs.isClosed())
				rs.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void commit(Connection conn) {
		try {
			if(conn != null && !conn.isClosed())
				conn.commit();
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void rollback(Connection conn) {
		try {
			if(conn !=null && !conn.isClosed())
				conn.rollback();
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
}
