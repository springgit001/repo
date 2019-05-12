package member.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import member.model.service.MemberService;

/**
 * Servlet implementation class DeleteMemberServlet
 */
@WebServlet("/delete.me")
public class DeleteMemberServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteMemberServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userId=request.getParameter("userId");
		
		int result = new MemberService().deleteMember(userId);
		//MemberService에 deleteMember 완성시키기
		
		RequestDispatcher view = null;
		
		if(result>0) {
			HttpSession session = request.getSession(false);
			if(session !=null) {
				session.invalidate();
			}
			view = request.getRequestDispatcher("views/common/successPage.jsp");
			request.setAttribute("msg", "성공적으로 회원 탈퇴를 했습니다.");
		}else {
			view = request.getRequestDispatcher("views/common/errorPage.jsp");
			request.setAttribute("msg", "회원 탈퇴에 실패했습니다.");
		}
		
		view.forward(request,response);
		
		//자 이제 기본적으로 갖춰야 할 회원 서비스들은 얼추 다 끝냈다!!
		
		//이제부터 공지사항 서비스를 해보자!!!
		//menubar.jsp로 돌아가서 메뉴 탭들을 완성시키자!!
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
