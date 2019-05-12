package board.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import board.model.service.BoardService;
import board.model.vo.Board;
import board.model.vo.Reply;

/**
 * Servlet implementation class BoardDetailServlet
 */
@WebServlet("/detail.bo")
public class BoardDetailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BoardDetailServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int bid = Integer.parseInt(request.getParameter("bid"));
		
		Board board = new BoardService().selectBoard(bid);
		//BoardService 클래스로 가서 selectBoard 메소드 완성시키기
		
		//----------------------이부분은 ajax 기능할 때 하는 부분------------------------
		// 댓글달기 기능을 위해 Reply vo 클래스를 만들어 주고 오자
		ArrayList<Reply> rlist = new BoardService().selectReplyList(bid);
		//Boardservice로 가서 selectReplyList 메소드를 완성시켜 해당 게시글의 댓글리스트를 가져오자
		//-------------------------------------------------------------------------
		
		
		if(board!=null) {
			request.setAttribute("board", board);
			//-------------------------이 부분도 ajax 기능할 때 하는 부분------------------------
			request.setAttribute("rlist", rlist);
			//boardDetailView.jsp로 가서 댓글 리스트가 보여지도록 화면단 작성하자!!
			//-----------------------------------------------------------------------------
			request.getRequestDispatcher("views/board/boardDetailView.jsp").forward(request, response);
		}else {
			request.setAttribute("msg", "게시판 상세조회 실패!!");
			request.getRequestDispatcher("views/common/errorPage.jsp").forward(request,response);
		}
		//boardDetailView.jsp 만들러 ㄱㄱ
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
