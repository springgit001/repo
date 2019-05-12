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
 * Servlet implementation class MyPageServlet
 */
@WebServlet("/myPage.me")
public class MyPageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MyPageServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//회원정보 조회용 컨트롤러
		
		//쿼리스트링 값 가져오기(아이디는 무조건 영어이기 때문에 인코딩 처리를 안해도 된)
		String userId = request.getParameter("userId");
		
		Member member = new MemberService().selectMember(userId);
		//MemberService로 가서 selectMember 메소드 작업하고 오자!
		
		RequestDispatcher view = null;
		if(member !=null) {
			//member를 보내서 view에 띄어 주기 위해 다음과 같이 사용,
			//이때 java 소스를 쓸 수 있어야 되기 때문에 무조건 jsp 파일이어야 될 것
			//또한 경로는 "상대 경로"만 사용해야 됨
			view = request.getRequestDispatcher("views/member/memberView.jsp");
			
			//이젠 member를 어딘가에 담아서 보내줘야 되는데 다음과 같은 방식들이 있다.
			//1. application : jsp, servlet, java 다 접근해서 쓸 수 있다.  	--> 공유 범위가 큼
			//2. session 	 : 로그인 한 그 사용자만 사용할 수 있다. jsp에서 사용 가능하다.   --> 공유 범위가 모든 jsp에서만 사용 가능
			//3. request	 : 전달 받은 그 대상 jsp만 꺼내 쓸 수 있다.		--> 공유 범위가 제한적임
			//4. page(==this): 자기 자신만 쓸 수 있다.(그 해당 jsp 파일 안에서만 사용 가능) 	--> 공유 범위가 제일 작다.
			
			//위의 4개의 객체 모두 setAttribute("이름",객체)를 이용해 객체를 저장할 수 있다.
			//꺼낼 때는 getAttribute("이름")을 이용
			//삭제 할 때는 removeAttribute("이름")을 이용
			
			//통상 request를 많이 사용함 request에 담아두자.
			request.setAttribute("member", member);
		}else {
			view = request.getRequestDispatcher("view/common/errorPage.jsp");
			request.setAttribute("msg", "조회에 실패 했습니다.");
		}
		//forward를 통해 request와 response를 보내준다.
		view.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
