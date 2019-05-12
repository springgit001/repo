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
 * Servlet implementation class InsertMemberServlet
 */
@WebServlet("/insert.me")
public class InsertMemberServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public InsertMemberServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//1. 한글이 있을 경우 인코딩 처리
		request.setCharacterEncoding("UTF-8");
		
		//2. 전송 값 꺼내서 변수에 저장 및 객체 생성
		String userId = request.getParameter("userId");
		String userPwd = request.getParameter("userPwd");
		String userName = request.getParameter("userName");
		String phone = request.getParameter("phone");
		String email = request.getParameter("email");
		String address = request.getParameter("address");
		
		//checkbox와 같은 경우 배열로 받게 된다. getParameterValues메소드를 이용해야 된다.
		String irr[] = request.getParameterValues("interest");
		//위에서 받은 배열을 vo의 Member에서의 String interest 자료형과 같게 하기 위해 아래와 같은 작업을 하자
		String interest="";
		
		if(irr != null) {
			for(int i=0;i<irr.length;i++) {
				if(i==irr.length -1)
					interest += irr[i];
				else
					interest += irr[i] + ",";
			}
		}
		
		Member member = new Member(userId, userPwd, userName, phone, email,address,interest);
		
		//3. 비지니스 로직을 수행할 서비스 메소드로 전달하고 그 결과 값 받기
		int result = new MemberService().insertMember(member);
		// MemberService로 가서 insertMember메소드 작성하고 옴
		
		//4. 받은 결과에 따라 성공 / 실패에 따른 페이지 내보내기
		String page = "";
		if(result>0) {
			page="views/common/successPage.jsp";
			request.setAttribute("msg", "회원 가입 성공!!");
		}else {
			page="views/common/errorPage.jsp";
			request.setAttribute("msg", "회원 가입 실패!!");
		}
		
		RequestDispatcher view = request.getRequestDispatcher(page);
		view.forward(request,response);
		
		//successPage.jsp 만들러 가기!
		
		//다시 menubar로 돌아가서 myPage 작업하자.
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
