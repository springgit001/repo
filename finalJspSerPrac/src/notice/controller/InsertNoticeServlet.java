package notice.controller;

import java.io.IOException;
import java.util.GregorianCalendar;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import notice.model.service.NoticeService;
import notice.model.vo.Notice;

/**
 * Servlet implementation class InsertNoticeServlet
 */
@WebServlet("/insert.no")
public class InsertNoticeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public InsertNoticeServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("utf-8");
		
		String title = request.getParameter("title");
		String userId = request.getParameter("userId");
		String date = request.getParameter("date");
		String content = request.getParameter("content");
		
		java.sql.Date dat = null;
		
		if(date != "") {
			String[] dateArr = date.split("-");
			
			int year = Integer.parseInt(dateArr[0]);
			int month = Integer.parseInt(dateArr[1])-1;
			int day = Integer.parseInt(dateArr[2]);
			
			dat = new java.sql.Date(new GregorianCalendar(year, month, day).getTimeInMillis());
		}else {
			dat = new java.sql.Date(new GregorianCalendar().getTimeInMillis());
		}
		
		Notice n = new Notice(title, userId, dat, content);
		
		int result = new NoticeService().insertNotice(n);
		//NoticeService 클래스로 가서 insertNotice 메소드 완성시키기
		
		if(result > 0) {
			//완료 됐다고 해서 단지 noticeListView.jsp로 바로 포워딩 시키면 안되고
			//다시 list를 불러오는 Servlet을 실행 시켜야 되기 때문에 다음과 같이 작업하자
			response.sendRedirect("list.no");
		}else {
			RequestDispatcher view = request.getRequestDispatcher("views/common/errorPage.jsp");
			request.setAttribute("msg", "공지사항 등록 실패!!");
			
			view.forward(request, response);
		}
		
		//공지사항 글쓰기까지 완벽히 됐다면 다시 noticeListView.jsp로 돌아가서 공지사항 상세보기 작업
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
