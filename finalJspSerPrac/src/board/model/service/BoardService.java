package board.model.service;
import static common.JDBCTemplate.close;
import static common.JDBCTemplate.commit;
import static common.JDBCTemplate.getConnection;
import static common.JDBCTemplate.rollback;

import java.sql.Connection;
import java.util.ArrayList;

import board.model.dao.BoardDao;
import board.model.vo.Attachment;
import board.model.vo.Board;
import board.model.vo.Reply;

public class BoardService {

	/**
	 * 1_1. 게시판 리스트 갯수 조회용 서비스
	 * @return int
	 */
	public int getListCount() {
		Connection conn = getConnection();
		
		int listCount = new BoardDao().getListCount(conn);
		//BoardDao 클래스 만들고 getListCount 메소드 완성시키기
		
		close(conn);
		
		return listCount;
	}

	/**
	 * 1_2. 게시판 리스트 조회용 서비스
	 * @param currentPage
	 * @param limit
	 * @return ArrayList<Board>
	 */
	public ArrayList<Board> selectList(int currentPage, int limit) {
		Connection conn = getConnection();
		
		ArrayList<Board> list = new BoardDao().selectList(conn, currentPage, limit);
		//BoardDao 클래스로 가서 selectList 메소드 완성시키고 오자
		
		close(conn);
		
		return list;
	}

	/**
	 * 2. 게시판 글쓰기용 서비스
	 * @param board
	 * @return int
	 */
	public int insertBoard(Board board) {
		Connection conn = getConnection();
		
		int result = new BoardDao().insertBoard(conn,board);
		//BoardDao 클래스로 가서 insertBoard 메소드 완성시키기
		
		
		if(result>0) {
			commit(conn);
		}else {
			rollback(conn);
		}
		
		close(conn);
		return result;
	}

	/**
	 * 3. 게시판 상세보기용, 조회수 클릭 증가용 서비스
	 * @param bid
	 * @return Board
	 */
	public Board selectBoard(int bid) {
		Connection conn = getConnection();
		
		//BoardDao 메소드는 두개를 호출하기 때문에 그냥 참조변수로 선언하자.
		BoardDao bDao = new BoardDao();
		
		//게시판 상세 보기 클릭 시 조회수 또한 증가해야 된다.
		//따라서 게시판 상세보기용 서비스에서는 조회수 증가하는 기능 또한 구현해야 된다.
		int result = bDao.updateCount(conn,bid);
		//updateBoard 메소드 완성시키기
		
		Board b = null;
		if(result > 0) {
			commit(conn);
			b=bDao.selectBoard(conn,bid);
			//selectBoard 메소드 완성시키기
		}else {
			rollback(conn);
		}
		
		close(conn);
		
		return b;
	}

	
	/**
	 * 4. 사진 게시판 리스트에 보여질 게시판 리스트 조회용 서비스
	 * 		전달 받은 flag 값이 1인 경우 게시판 정보 리스트가 리턴
	 * 						2인 경우 메인 사진 리스트가 리턴
	 * @param flag
	 * @return ArrayList
	 */
	public ArrayList selectList(int flag) {
		Connection conn = getConnection();
		
		ArrayList list = null;
		
		//BoardDao 메소드 두개를 호출하기 때문에 그냥 참조변수로 선언하자
		BoardDao bDao = new BoardDao();
		
		if(flag == 1) {
			list = bDao.selectBList(conn);
			//BoardDao에서 selectBList 메소드 완성시키기
		}else {
			list = bDao.selectFList(conn);
			//BoardDao에서 selectFList 메소드 완성시키기
		}
		
		close(conn);
		return list;
	}

	
	/**
	 * 5. 사진 게시판 글 쓰기용 서비스
	 * @param b
	 * @param fileList
	 * @return int
	 */
	public int insertThumbnail(Board b, ArrayList<Attachment> fileList) {
		Connection conn = getConnection();
		
		//BoardDao 메소드 두개를 호출하기 때문에 그냥 참조변수로 선언
		BoardDao bDao = new BoardDao();
		
		int result1 = bDao.insertThBoard(conn,b);
		int result2 = bDao.insertAttachment(conn,fileList);
		//BoardDao 클래스로 가서 insertThBoard와 insertAttachment 메소드 완성시키기
		
		if(result1 > 0 && result2 > 0) {
			commit(conn);
		}else {
			rollback(conn);
		}
		
		close(conn);
		
		return result1;
	}

	/**
	 * 6. 사진들 리스트 조회용 서비스
	 * @param bid
	 * @return ArrayList<Attachment>
	 */
	public ArrayList<Attachment> selectThumbnail(int bid) {
		Connection conn = getConnection();
		
		ArrayList<Attachment> list = new BoardDao().selectThumbnail(conn, bid);
		//BoardDao 클래스로 가서 selectThumbnail 완성시키러 ㄱㄱ
		
		close(conn);
		return list;
		
	}

	/**
	 * 7. 첨부파일 다운로드 수 증가용, 파일 조회용 서비스
	 * @param fid
	 * @return Attachment
	 */
	public Attachment selectAttachment(int fid) {
		Connection conn = getConnection();
		//BoardDao 메소드 두개를 호출하기 위해 참조변수 선언
		BoardDao bDao = new BoardDao();
		
		int result = bDao.updateDownloadCount(conn, fid);
		//BoardDao로 가서 updateDownloadCount 메소드 완성시키기
	
		Attachment at = null;
		if(result > 0) {
			commit(conn);
			at = bDao.selectAttachment(conn, fid);
			// BoardDao로 가서 selectAttachment 완성시키기
		}else {
			rollback(conn);
		}
		
		close(conn);
		
		return at;
	}

	
	/**
	 * 이 부분부터는 ajax완료 후에 작성하는 내용임!!
	 * 8. 선택한 게시글의 댓글리스트 조회용 서비스
	 * @param bid
	 * @return ArrayList<Reply>
	 */
	public ArrayList<Reply> selectReplyList(int bid) {
		Connection conn = getConnection();
		
		ArrayList<Reply> rlist = new BoardDao().selectReplyList(conn, bid);
		//BoardDao로 가서 selectReplyList 메소드 완성시키기
		
		close(conn);
		
		return rlist;
	}
	
	
	/**
	 * 9. 댓글 추가 후 새로 갱신된 댓글 리스트 조회용 서비스
	 * @param r
	 * @return ArrayList<Reply>
	 */
	public ArrayList<Reply> insertReply(Reply r) {
		Connection conn = getConnection();
		
		//BoardDao 메소드 두개를 호출하기 때문에 그냥 참조변수로 선언
		BoardDao bDao = new BoardDao();
		
		int result = bDao.insertReply(conn, r);
		//BoardDao로 가서 insertReply 메소드 완성시키기
		
		ArrayList<Reply> rlist = null;
		
		if(result > 0) {
			commit(conn);
			rlist = bDao.selectReplyList(conn,  r.getRefBid());
		}else {
			rollback(conn);
		}
		close(conn);
		
		return rlist;
	}
}
