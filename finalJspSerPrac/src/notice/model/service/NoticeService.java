package notice.model.service;

import static common.JDBCTemplate.*;

import java.sql.Connection;
import java.util.ArrayList;

import notice.model.dao.NoticeDao;
import notice.model.vo.Notice;

public class NoticeService {

	/**
	 * 1. 공지사항 리스트 조회용 서비스
	 * @return ArrayList<Notice>
	 */
	public ArrayList<Notice> selectList() {
		Connection conn = getConnection();
		
		ArrayList<Notice> list = new NoticeDao().selectList(conn);
		//NoticeDao 클래스 만들고 selectList 메소드 완성시키기
		
		close(conn);
		
		return list;
	}

	/**
	 * 2. 공지사항 쓰기용 서비스
	 * @param notice
	 * @return int
	 */
	public int insertNotice(Notice notice) {
		Connection conn = getConnection();
		
		int result = new NoticeDao().insertNotice(conn, notice);
		//NoticeDao 클래스로 가서 insertNotice 메소드 완성시키기
		
		if(result > 0) {
			commit(conn);
		}else {
			rollback(conn);
		}
		
		close(conn);
		
		return result;
	}

	/**
	 * 3. 공지사항 상세보기용 서비스
	 * @param nno
	 * @return Notice
	 */
	public Notice selectNotice(int nno) {
		Connection conn = getConnection();
		
		Notice notice = new NoticeDao().selectNotice(conn, nno);
		//NoticeDao 클래스로 가서 selectNotice 메소드 완성
		
		close(conn);
		
		return notice;
	}

	/**
	 * 4. 공지사항 수정용 서비스
	 * @param notice
	 * @return int
	 */
	public int updateNotice(Notice notice) {
		Connection conn = getConnection();
		
		int result = new NoticeDao().updateNotice(conn,notice);
		//NoticeDoa 클래스 가서 updateNotice 메소드 완성시키기
	
		if(result > 0) {
			commit(conn);
		}else {
			rollback(conn);
		}
		
		close(conn);
		
		return result;
	}

	/**
	 * 5. 공지사항 삭제용 서비스
	 * @param nno
	 * @return int
	 */
	public int deleteNotice(int nno) {
		System.out.println("deleteService단에 값 넘어오니?"+nno);
		Connection conn = getConnection();
		
		int result = new NoticeDao().deleteNotice(conn, nno);
		//NoticeDao 클래스로 가서 deleteNotice 메소드 완성시키기
		
		if(result > 0) {
			commit(conn);
		}else {
			rollback(conn);
		}
		close(conn);
		
		return result;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
