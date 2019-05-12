package board.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import board.model.service.BoardService;
import board.model.vo.Board;
import board.model.vo.PageInfo;

/**
 * Servlet implementation class BoardListServlet
 */
@WebServlet("/list.bo")
public class BoardListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BoardListServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//두 개의 서비스를 호출하기 때문에 참조변수로 생성해놓자
		BoardService bService = new BoardService();
		
		//1_1. 게시판 리스트 갯수 구하기
		int listCount = bService.getListCount();
		//boardService 클래스를 만들고 getListCount 메소드 완성시키기
		
		//-------------------페이징 처리 추가---------------------
		//페이지 수 처리용 변수 선언
		int currentPage;		//현재 페이지를 표시할 변수
		int limit;				//한 페이지에 게시글이 몇개가 보일 것인지 표시
		int maxPage;			//전체 페이지에서 가장 마지막 페이지
		int startPage;			//한번에 표시될 페이지가 시작할 페이지
		int endPage;			//한번에 표시될 페이지가 끝나는 페이지
		
		// *currentPage - 현재 페이지
		// 기본 게시판은 1페이지부터 시작함
		currentPage = 1;
		//하지만 페이지 전환시 전달받은 현재 페이지가 있을 시 해당 페이지를 currentPage로 적용
		if(request.getParameter("currentPage") !=null) {
			currentPage = Integer.parseInt(request.getParameter("currentPage"));
		}
		
		// * limit - 한 페이지에 보여질 목록 갯수
		limit = 10;
		
		// * maxPage - 총 페이지수
		// 목록 수가 123개이면 페이지 수는 13페이지임
		// 짜투리 목록이 최소 1개일 때, 1page로 처리하기 위해 0.9를 더함
		maxPage = (int)((double)listCount/limit+0.9);
		
		// * startPage - 현재 페이지에 보여질 시작 페이지 수
		// 아래쪽에 페이지 수가 10개씩 보여지게 할 경우
		// 1, 11, 21, 31, .....
		startPage = (((int)((double)currentPage/limit+0.9))-1)*limit+1;
		
		// * endPage - 현재 페이지에서 보여질 마지막 페이지 수
		// 아래쪽에 페이지 수가 10개씩 보여지게 할 경우
		// 10, 20, 30, 40, .....
		endPage = startPage + limit -1;
		//하지만 마지막 페이지 수가 총 페이지 수보다 클 경우
		//maxPage가 13페이지고 endPage가 20페이지일 경우
		if(maxPage<endPage) {
			endPage=maxPage;
		}
		
		
		//자 위에서 계산된 모든 페이지 관련 변수들을 request에 담아서 보내야 될텐데 너무 많다
		//그래서 페이지 정보를 공유할 vo 객체 PageInfo 클래스를 만들어주고 오자!
		PageInfo pi = new PageInfo(currentPage, listCount, limit, maxPage, startPage, endPage);
		
		//1_2. 게시판 리스트 조회해오기
		ArrayList<Board> list = bService.selectList(currentPage, limit);
		
		RequestDispatcher view = null;
		
		if(list != null) {
			view = request.getRequestDispatcher("views/board/boardListView.jsp");
			request.setAttribute("list", list);
			request.setAttribute("pi", pi); //위에서 페이지 정보를 저장한 PageInfo 객체를 담아 보내주자
		}else {
			view = request.getRequestDispatcher("views/common/errorPage.jsp");
			request.setAttribute("msg", "게시판 리스트 조회 실패!!");
		}
		
		view.forward(request, response);
		
		//boardListView.jsp 페이지 만들러 가자~~!
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
