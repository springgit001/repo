package member.model.dao;

import static common.JDBCTemplate.close;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import member.model.vo.Member;

public class MemberDao {

	// 앞으로 만들어질 메소드 마다 query문을 작성할 것이다.
	// 하지만 그 쿼리들은 properties 파일로 만들어 놓으면 유지보수 측면에서도 좋다!!
	// sql 폴더 안에 member 폴더를 만들고 그 안에 member-query.properties 파일을 만들자

	private Properties prop = new Properties();

	public MemberDao() {
		// 항상 member-query.properties에서 값을 가져 올 수 있도록
		// 기본 생성자 안에서 properties 파일을 불러오는 작업을 하자
		String fileName = MemberDao.class.getResource("/sql/member/member-query.properties").getPath();

		try {
			prop.load(new FileReader(fileName));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Member loginMember(Connection conn, Member member) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		Member loginUser = null;
		
		String query = prop.getProperty("loginMember");
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, member.getUserId());
			pstmt.setString(2, member.getUserPwd());
			
			rs=pstmt.executeQuery();
			
			if(rs.next()) {
				loginUser = new Member(rs.getInt("USER_NO"),
						rs.getString("USER_ID"),
						rs.getString("USER_PWD"),
						rs.getString("USER_Name"),
						rs.getString("PHONE"),
						rs.getString("EMAIL"),
						rs.getString("ADDRESS"),
						rs.getString("INTEREST"),
						rs.getDate("ENROLL_DATE"),
						rs.getDate("MODIFY_DATE"),
						rs.getString("STATUS"));		
	
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(rs);
			close(pstmt);
		}
		
		return loginUser;
		
		//다시 Service로 돌아가자
	}

	public int insertMember(Connection conn, Member member) {
		PreparedStatement pstmt = null;
		int result = 0;
		
		String query = prop.getProperty("insertMember");
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, member.getUserId());
			pstmt.setString(2, member.getUserPwd());
			pstmt.setString(3, member.getUserName());
			pstmt.setString(4, member.getPhone());
			pstmt.setString(5, member.getEmail());
			pstmt.setString(6, member.getAddress());
			pstmt.setString(7, member.getInterest());
			
			result = pstmt.executeUpdate();
			System.out.println("MemberDao에서 제대로 가입됐는지 int return값: "+result);
			
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
		}
		
		return result;
	}

	public Member selectMember(Connection conn, String userId) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		Member member = null;
		String query = prop.getProperty("selectMember");
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, userId);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				member = new Member(rs.getInt("user_no"),
									rs.getString("user_id"),
									rs.getString("user_pwd"),
									rs.getString("user_name"),
									rs.getString("phone"),
									rs.getString("email"),
									rs.getString("address"),
									rs.getString("interest"),
									rs.getDate("enroll_date"),
									rs.getDate("modify_date"),
									rs.getString("status"));
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(rs);
			close(pstmt);
		}
		
		return member;
	}

	public int updateMember(Connection conn, Member member) {
		PreparedStatement pstmt = null;
		
		int result = 0;
		
		String query = prop.getProperty("updateMember");
		
		try {
			pstmt = conn.prepareStatement(query);
			
			pstmt.setString(1,  member.getUserName());
			pstmt.setString(2, member.getPhone());
			pstmt.setString(3, member.getEmail());
			pstmt.setString(4, member.getAddress());
			pstmt.setString(5, member.getInterest());
			pstmt.setString(6, member.getUserId());
			
			result = pstmt.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
		}
		
		return result;
	}

	public int deleteMember(Connection conn, String userId) {
		PreparedStatement pstmt = null;
		
		int result = 0;

		String query = prop.getProperty("deleteMember");
		
		try {
			pstmt = conn.prepareStatement(query);
			
			pstmt.setString(1,userId);
			
			result=pstmt.executeUpdate();
			
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
		}
		
		return result;
		
		//회원 탈퇴를 한다고 해서 아예 db에서 회원을 삭제시키면 안된다.
		//따라서 회원 탈퇴를 한다하면 탈퇴용 회원 테이블에 따로 보관을 하거나
		//위와같이 status column을 통해 관리를 해야된다.
		//그래서 30일 정도 보관을 한 뒤에 테이블에서 삭제 시켜야 된다.
		//이는 트리거를 쓰거나 한번 시도해봐라!!(수업시간에는 안하지만 알아서 해봐라)
	}

	public int idCheck(Connection conn, String userId) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		int result = 0;
		
		String query = prop.getProperty("idCheck");
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1,  userId);
			
			rs=pstmt.executeQuery();
			
			if(rs.next()) {
				result = rs.getInt(1);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(rs);
			close(pstmt);
		}
		
		return result;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
