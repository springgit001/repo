package board.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import board.model.service.BoardService;
import board.model.vo.Board;
import member.model.vo.Member;

/**
 * Servlet implementation class InsertBoardServlet
 */
@WebServlet("/insert.bo")
public class InsertBoardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public InsertBoardServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("utf-8");
		
		String category = request.getParameter("category");
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		
		HttpSession session = request.getSession();
		
		Member loginUser = (Member)session.getAttribute("loginUser");
		//BOARD 테이블에 BWRITER 컬럼에 추가할 회원 번호를 알아내기 위해 다음과 같이 작업
		//int 값이지만 Board라는 vo클래스의 bWriter 필드에 담기 위해 다음과 같이 함.
		String writer = String.valueOf(loginUser.getUserNo());
		
		Board b = new Board();
		b.setCategory(category);
		b.setbTitle(title);
		b.setbContent(content);
		b.setbWriter(writer);
		
		int result = new BoardService().insertBoard(b);
		//BoardService 클래스로 가서 insertBoard 완성시키고 오자
		
		if(result>0) {
			response.sendRedirect("list.bo?currentPage=1");
		}else {
			//이때까지 RequestDispatcher 변수 view를 선언해서 했지만
			//다음과 같이 바로 포워딩 시켜버리자
			request.setAttribute("msg", "게시판 작성 실패!!");
			request.getRequestDispatcher("views/common/errorPage.jsp").forward(request, response);
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
