package board.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import board.model.service.BoardService;
import board.model.vo.Attachment;
import board.model.vo.Board;

/**
 * Servlet implementation class ThumbnailListServlet
 */
@WebServlet("/list.th")
public class ThumbnailListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ThumbnailListServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// 파일 관련 즉 사진게시판을 위해 Attachment라는 테이블을 만들었었다.
		// 따라서 Attachment vo 클래스를 만들어주고 오자!!
		
		// 두개의 서비스를 요청하기 위해 BoardService 참조변수를 미리 선언해놓자
		BoardService bService = new BoardService();
		
		// 사진 게시판은 일반게시판과 달리 페이징 처리를 하지 않고 해보자
		// 하고 싶다면 일반게시판에서 했던 페이징 처리를 참고해서 나중에 해볼것!
		// 또는 페이징 말고 스크롤링도 한번 구글링 해볼 것(얘는 AJAX 배우면 할 수 있을 것이다)
		
		// 1. 우선 사진게시판 리스트 정보를 불러오자
		ArrayList<Board> blist = bService.selectList(1);
		
		// 2. 사진리스트도 불러오자
		ArrayList<Attachment> flist = bService.selectList(2);
		
		if(blist != null && flist !=null) {
			request.setAttribute("blist", blist);
			request.setAttribute("flist", flist);
			request.getRequestDispatcher("views/thumbnail/thumbnailListView.jsp").forward(request, response);
		}else {
			request.setAttribute("msg", "사진 게시판 조회 실패!!");
			request.getRequestDispatcher("views/common/errorPage.jsp").forward(request, response);
		}
		//thumbnail 폴더 만들고 그 안에 thumbnailListView.jsp 만들러 ㄱㄱ
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
