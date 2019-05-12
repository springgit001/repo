package board.model.dao;

import static common.JDBCTemplate.close;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;

import board.model.vo.Attachment;
import board.model.vo.Board;
import board.model.vo.Reply;

public class BoardDao {
	
	Properties prop = new Properties();
	
	public BoardDao() {
		String fileName = BoardDao.class.getResource("/sql/board/board-query.properties").getPath();
		try {
			prop.load(new FileReader(fileName));
		}catch(IOException e) {
			e.printStackTrace();
		}
	}

	public int getListCount(Connection conn) {
		Statement stmt = null;
		ResultSet rs = null;
		
		int listCount = 0;
		
		String query = prop.getProperty("getListCount");
		
		try {
			stmt = conn.createStatement();
			rs=stmt.executeQuery(query);
			
			if(rs.next()) {
				listCount = rs.getInt(1);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(rs);
			close(stmt);
		}
		
		return listCount;
	}

	public ArrayList<Board> selectList(Connection conn, int currentPage, int limit) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		ArrayList<Board> list = null;
		
		//쿼리문 복잡한데 수업시간에 배포한 스크립트를 한번 봐봐라!
		String query = prop.getProperty("selectList");
		
		try {
			//쿼리문 실행시 조건절에 넣을 변수들 (rownum에 대한 조건 시 필요)
			int startRow = (currentPage -1)*limit +1;
			int endRow = startRow + limit -1;
			
			pstmt = conn.prepareStatement(query);
			
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, endRow);
			pstmt.setInt(3, 1); //1은 게시판 타입을 의미함 -->1=일반게시판, 2=사진게시판
			
			rs= pstmt.executeQuery();
			list = new ArrayList<Board>();
			while(rs.next()) {
				Board b = new Board(rs.getInt("bid"),
									rs.getInt("btype"),
									rs.getString("cname"),
									rs.getString("btitle"),
									rs.getString("bcontent"),
									rs.getString("user_name"),
									rs.getInt("bcount"),
									rs.getDate("create_date"),
									rs.getDate("modify_date"),
									rs.getString("status"));
				list.add(b);					
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(rs);
			close(pstmt);
		}
		
		return list;
	}

	public int insertBoard(Connection conn, Board board) {
		PreparedStatement pstmt = null;
		
		int result = 0;
		
		String query = prop.getProperty("insertBoard");
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1,  Integer.parseInt(board.getCategory()));
			pstmt.setString(2,  board.getbTitle());
			pstmt.setString(3,  board.getbContent());
			pstmt.setString(4, board.getbWriter());
			
			result = pstmt.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
		}
		
		return result;
	}

	public int updateCount(Connection conn, int bid) {
		PreparedStatement pstmt = null;
		
		int result = 0;
		
		String query = prop.getProperty("updateCount");
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, bid);
			
			result = pstmt.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
		}
		
		return result;
	}

	public Board selectBoard(Connection conn, int bid) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		Board b = null;
		String query = prop.getProperty("selectBoard");
		
		try {
			pstmt = conn.prepareStatement(query);
			
			pstmt.setInt(1, bid);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				b=new Board(rs.getInt("bid"),
						    rs.getInt("btype"),
						    rs.getString("cname"),
						    rs.getString("btitle"),
						    rs.getString("bcontent"),
						    rs.getString("user_name"),
						    rs.getInt("bcount"),
						    rs.getDate("create_date"),
						    rs.getDate("modify_date"),
						    rs.getString("status"));
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(rs);
			close(pstmt);
		}
		
		return b;
	}

	public ArrayList selectBList(Connection conn) {
		Statement stmt = null;
		ResultSet rs = null;
		
		ArrayList<Board> list = null;
		
		String query = prop.getProperty("selectBList");
		
		try {
			stmt = conn.createStatement();
			
			rs = stmt.executeQuery(query);
			
			list = new ArrayList<Board>();
			
			while(rs.next()) {
				list.add(new Board(rs.getInt("bid"),
									rs.getInt("btype"),
									rs.getString("cname"),
									rs.getString("btitle"),
									rs.getString("bcontent"),
									rs.getString("user_name"),
									rs.getInt("bcount"),
									rs.getDate("create_date"),
									rs.getDate("modify_date"),
									rs.getString("status")));
				}
			}catch(SQLException e) {
				e.printStackTrace();
			}finally {
				close(rs);
				close(stmt);
			}
			
			return list;
		
	}

	public ArrayList selectFList(Connection conn) {
		Statement stmt = null;
		ResultSet rs = null;
		
		ArrayList<Attachment> list = null;
		
		String query = prop.getProperty("selectFList");
		
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);
			
			list = new ArrayList<Attachment>();
			
			while(rs.next()) {
				list.add(new Attachment(rs.getInt("bid"),
										rs.getString("change_name")));
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(rs);
			close(stmt);
		}
		
		return list;
	}

	public int insertThBoard(Connection conn, Board b) {
		PreparedStatement pstmt = null;
		
		int result = 0;
		String query = prop.getProperty("insertThBoard");
		
		try {
			pstmt = conn.prepareStatement(query);
			
			pstmt.setString(1,  b.getbTitle());
			pstmt.setString(2,  b.getbContent());
			pstmt.setInt(3,  Integer.parseInt(b.getbWriter()));
			
			result = pstmt.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
		}
		
		return result;
	}

	public int insertAttachment(Connection conn, ArrayList<Attachment> fileList) {
		PreparedStatement pstmt = null;
		
		int result = 0;
		
		String query = prop.getProperty("insertAttachment");
		
		try {
			for(int i=0; i<fileList.size(); i++) {
				Attachment at = fileList.get(i);
				
				pstmt = conn.prepareStatement(query);
				pstmt.setString(1, at.getOriginName());
				pstmt.setString(2, at.getChangeName());
				pstmt.setString(3, at.getFilePath());
				pstmt.setInt(4, at.getFileLevel());
				
				result += pstmt.executeUpdate();
				
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
		}
		
		return result;
	}

	public ArrayList<Attachment> selectThumbnail(Connection conn, int bid) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		ArrayList<Attachment> list = null;
		
		String query = prop.getProperty("selectThumbnail");
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, bid);
			
			rs = pstmt.executeQuery();
			
			list = new ArrayList<Attachment>();
			
			while(rs.next()) {
				Attachment at = new Attachment();
				at.setfId(rs.getInt("fid"));
				at.setOriginName(rs.getString("origin_name"));
				at.setChangeName(rs.getString("change_name"));
				at.setFilePath(rs.getString("file_path"));
				at.setUploadDate(rs.getDate("upload_date"));
				
				list.add(at);
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(rs);
			close(pstmt);
		}
		
		return list;
		
	}

	public int updateDownloadCount(Connection conn, int fid) {
		PreparedStatement pstmt = null;
		
		int result = 0;
		
		String query = prop.getProperty("updateDownloadCount");
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, fid);
			
			result = pstmt.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
		}
		
		return result;
	}

	public Attachment selectAttachment(Connection conn, int fid) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		Attachment at = null;
		
		String query = prop.getProperty("selectAttachment");
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1,  fid);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				at = new Attachment();
				
				at.setOriginName(rs.getString("origin_name"));
				at.setChangeName(rs.getString("change_name"));
				at.setFilePath(rs.getString("file_path"));
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(rs);
			close(pstmt);
		}
		
		return at;
	}

	public ArrayList<Reply> selectReplyList(Connection conn, int bid) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		ArrayList<Reply> rlist = null;
		
		String query = prop.getProperty("selectReplyList");
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, bid);
			
			rs = pstmt.executeQuery();
			
			rlist = new ArrayList<Reply>();
			
			while(rs.next()) {
				rlist.add(new Reply(rs.getInt("rid"),
									rs.getString("rcontent"),
									rs.getInt("ref_bid"),
									rs.getString("user_name"),
									rs.getDate("create_date"),
									rs.getDate("modify_date"),
									rs.getString("status")));
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(rs);
			close(pstmt);
		}
		return rlist;
	}

	public int insertReply(Connection conn, Reply r) {
		PreparedStatement pstmt = null;
		
		int result = 0;
		
		String query = prop.getProperty("insertReply");
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1,  r.getrContent());
			pstmt.setInt(2,  r.getRefBid());
			pstmt.setInt(3,  Integer.parseInt(r.getrWriter()));
			
			result = pstmt.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
		}
		return result;
	}
}
