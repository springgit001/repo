package member.model.service;

import static common.JDBCTemplate.*;

import java.sql.Connection;

import member.model.dao.MemberDao;
import member.model.vo.Member;

public class MemberService {
	//로그인 처리를 위한 상수 선언
	public static int LOGIN_OK = 1;
	public static int WRONG_PASSWORD = 0;
	public static int ID_NOT_EXIST = -1;
	
	//기본 생성자 생략
	
	/**
	 * 1. 로그인용 서비스
	 * @param member
	 * @return member
	 */
	public Member loginMember(Member member) {
		Connection conn = getConnection();
		//getConnection()에 오류가 생기면 JDBCTemplate을 작성하러 ㄱㄱ
		
		Member loginUser = new MemberDao().loginMember(conn,member);
		
		//MemberDao 클래스를 만들고 login 메소드를 완성시키자!!
		close(conn);
		
		return loginUser;
		
		//다시 LoginServlet으로 돌아가자
	}

	
	/**
	 * 3. 회원가입용 서비스
	 * @param member
	 * @return int
	 */
	public int insertMember(Member member) {
		Connection conn = getConnection();
		
		int result = new MemberDao().insertMember(conn,member);
		//MemberDao로 가서 insertMember 메소드를 완성시키자!!
		
		if(result>0) {
			commit(conn);
		}else {
			rollback(conn);
		}
		close(conn);
		return result;
	}


	/**
	 * 4. 회원 정보 조회용 서비스
	 * @param userId
	 * @return Member
	 */
	public Member selectMember(String userId) {
		Connection conn = getConnection();
		
		Member member = new MemberDao().selectMember(conn, userId);
		//MemberDao로 가서 selectMember 메소드를 완성시키자!!
		
		close(conn);
		
		return member;
		//다시 MyPageServlet으로 가자
		
	}


	/**
	 * 5. 회원 정보 수정용 서비스
	 * @param member
	 * @return int
	 */
	public int updateMember(Member member) {
		Connection conn = getConnection();
		 int result = new MemberDao().updateMember(conn,member);
		 //MemberDao로 가서  updateMember 메소드 완성시키기
		 
		 if(result > 0) {
			 commit(conn);
		 }else {
			 rollback(conn);
		 }
		 
		 close(conn);
		 
		 return result;
	}


	/**
	 * 6. 회원 탈퇴용 서비스
	 * @param userId
	 * @return int
	 */
	public int deleteMember(String userId) {
		Connection conn = getConnection();
		
		int result = new MemberDao().deleteMember(conn,userId);
		//MemberDao로 가서 deleteMember 메소드 완성시키기
		
		if(result> 0) {
			commit(conn);
		}else {
			rollback(conn);
		}
		
		close(conn);
		
		return result;
	}


	/**
	 * 이 부분은 프로젝트 마지막쯤에 ajax 배우고 하는 부분!
	 * 7. 아이디 중복 체크용 서비스
	 * @param userId
	 * @return int
	 */
	public int idCheck(String userId) {
		Connection conn = getConnection();
		int result = new MemberDao().idCheck(conn,userId);
		//MemberDao로 가서 idCheck 메소드 완성
		
		close(conn);
		return result;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
