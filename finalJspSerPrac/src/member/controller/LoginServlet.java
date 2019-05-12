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
import member.model.vo.Member;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/login.me")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/*
		 * <request와 response>
		 * 
		 * request : 서버에 요청하는 모든 정보들에 대해 보관
		 * response : 서비스를 요청한 클라이언트의 ip나 url에 대해 보관
		 * 
		 * 쌍으로 서버로 왔다 갔다 함으로써 클라이언트에 정확한 정보 제공
		 */
		
		/*
		 * <post와 get 방식의 차이>
		 * 
		 * request와 response는 둘 다 head와 body로 나뉘어 있다.
		 * post : body에 기록되서 전달(url에 보이지 않음)
		 * get  : head에 기록되서 전달(url에 보임)
		 */
		
		//1. 전송 값에 한글이 있을 경우 인코딩 처리 해야 된다. (doPost에서는 무조건 해야됨 하지만 doGet에서는 안해도 됨)
		//		대소문자 상관 없음, 요청한 view 단의 charset과 일치해야됨
		// request.setCharacterEncoding("UTF-8"); //로그인에서는 필요 없음
		
		//2. 전송 값 꺼내서 변수에 기록하기 또는 객체에 저장하기
		String userId = request.getParameter("userId");
		String userPwd = request.getParameter("userPwd");
		Member member = new Member(userId, userPwd);
		
		//3. 비지니스 로직 처리하는 서비스 클래스의 해당 메소드를 실행하고, 그 처리 결과를 받음
		Member loginUser = new MemberService().loginMember(member);
		//MemberService 클래스 만들러 가자!!!!
		
		//4. 보낼 값에 한글이 있을 경우 인코딩 처리를 해야한다.
		//지금 내보내는 내용을 html문으로 해석해라 라는 뜻
		response.setContentType("text/html;charset=utf-8");
		
		//5. 서비스 요청에 해당하는 결과를 가지고 성공/실패에 대한 뷰 페이지(파일)을 선택해서 내보냄
		if(loginUser != null) {	//성공일 경우
			//해당 클라이언트에 대한 세션 객체를 생성함
			HttpSession session = request.getSession(); // 괄호에 true 써도 됨
			
			//session.setMaxInactiveInterval(600);		// 10분 뒤 자동 로그아웃
			
			session.setAttribute("loginUser",  loginUser);
			//세션에 담았기 때문에 따로 request에 담을 필요 없다.
			
			response.sendRedirect("index.jsp");
		}else {	//실패일 경우
			request.setAttribute("msg",  "로그인 실패");
			
			//Request Dispatcher 객체를 이용하여 내가 전달하고자 하는 view 지정
			//상대경로 방법 /root(web)에서 무조건 시작하기 때문에 다음과 같이 작성
			RequestDispatcher view = request.getRequestDispatcher("views/common/errorPage.jsp");
			view.forward(request,  response);
			
			//common - errorPage.jsp 만들러 가자!!!
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
