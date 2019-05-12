package member.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import member.model.service.MemberService;
import member.model.vo.Member;

/**
 * Servlet implementation class UpdateMemberServlet
 */
@WebServlet("/update.me")
public class UpdateMemberServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateMemberServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		
		String userId = request.getParameter("userId");
		String userName = request.getParameter("userName");
		String phone = request.getParameter("phone");
		String email = request.getParameter("email");
		String address = request.getParameter("address");
		
		String[] irr = request.getParameterValues("interest");
		
		String interest="";
		
		//파라미터로 전달한 문자열 배열이 null이면, NullPointerException 유발 --> 조건식 써주자
		if(irr != null) {
			interest = String.join(",", irr);
		}
		
		int result = new MemberService().updateMember(new Member(userId, userName, phone, email, address,interest));
		//MemberService 클래스로 가서 updateMember 완성 시키고 오자
		
		RequestDispatcher view = null;
		
		if(result > 0) {
			view = request.getRequestDispatcher("views/common/successPage.jsp");
			request.setAttribute("msg", "성공적으로 회원정보를 수정했습니다.");
		}else {
			view = request.getRequestDispatcher("views/common/errorPage.jsp");
			request.setAttribute("msg", "회원정보 수정에 실패했습니다.");
		}
		
	    view.forward(request, response);
	    // successPage까지 잘 나오는거 확인하면 다시 memberView.jsp로 돌아가서 이제 회원 탈퇴 기능 마무리!
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
