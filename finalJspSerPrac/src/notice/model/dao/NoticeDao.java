package notice.model.dao;
import static common.JDBCTemplate.*;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;

import notice.model.vo.Notice;

public class NoticeDao {
	
	Properties prop = new Properties();

	//MemberDao와 같이 NoticeDao도 마찬가지로 sql문들을 properties파일로 관리하기 때문에
	//properties 파일을 계속 불러들이는 작업을 기본 생성자에서 한다.
	
	public NoticeDao() {
		String fileName = NoticeDao.class.getResource("/sql/notice/notice-query.properties").getPath();
		
		try {
			prop.load(new FileReader(fileName));
			//load에서 오류나면 import가 util에 있는 Properties로 되어있는지 확인해 볼 것!
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public ArrayList<Notice> selectList(Connection conn) {
		Statement stmt = null;
		ResultSet rs = null;
		
		ArrayList<Notice> list = null;
		
		String query = prop.getProperty("selectList");
		
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);
			
			list = new ArrayList<Notice>();
			
			while(rs.next()) {
				Notice no = new Notice(rs.getInt("nno"),
									   rs.getString("ntitle"),
									   rs.getString("ncontent"),
									   rs.getString("nwriter"),
									   rs.getInt("ncount"),
									   rs.getDate("nDate"));
				list.add(no);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(rs);
			close(stmt);
		}
		return list;
	}


	public int insertNotice(Connection conn, Notice notice) {
		PreparedStatement pstmt = null;
		
		int result = 0;
		
		String query = prop.getProperty("insertNotice");
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, notice.getnTitle());
			pstmt.setString(2, notice.getnContent());
			pstmt.setString(3, notice.getnWriter());
			pstmt.setDate(4,  notice.getnDate());
			
			result=pstmt.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
		}
		
		return result;
	}

	public Notice selectNotice(Connection conn, int nno) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		Notice notice = null;
		
		String query = prop.getProperty("selectNotice");
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, nno);
			rs=pstmt.executeQuery();
			
			if(rs.next()) {
				notice = new Notice(rs.getInt("nno"),
									rs.getString("ntitle"),
									rs.getString("ncontent"),
									rs.getString("nwriter"),
									rs.getInt("ncount"),
									rs.getDate("ndate"));
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(rs);
			close(pstmt);
		}
		
		return notice;
	}


	public int updateNotice(Connection conn, Notice notice) {
		PreparedStatement pstmt = null;
		
		int result=0;
		
		String query = prop.getProperty("updateNotice");
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1,  notice.getnTitle());
			pstmt.setString(2,  notice.getnContent());
			pstmt.setInt(3,  notice.getnNo());
			
			result = pstmt.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
		}
		
		return result;
	}


	public int deleteNotice(Connection conn, int nno) {
		PreparedStatement pstmt = null;
		
		int result = 0;
		System.out.println("deleteNoticeDao에 값 넘어오니?"+nno);
		String query = prop.getProperty("deleteNotice");
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1,  nno);
			
			result = pstmt.executeUpdate();
			
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
		}
		
		return result;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
