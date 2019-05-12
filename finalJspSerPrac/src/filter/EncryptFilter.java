package filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import wrapper.EncryptWrapper;

/*
 * Servlet Filter implementation class IncryptFilter
 */

//이 필터는 암호화가 필요한 서블릿에서만 적용이 되기 때문에 직접 서블릿명을 통해서 필터적용을 해보자!!
//암호화가 필요한 서블릿은 InsertMemberServlet과 LoginServlet이다!

//하지만 서블릿명으로 필터를 적용할 때 배포명이 반드시 있어야 한다!!!
//--> InsertMemberServlet, LoginServlet으로 가서 @WebServlet 어노테이션에 name 속성값을 명시해주자!!
@WebFilter(filterName = "encrypt", servletNames = {"InsertMemberservlet", "LoginServlet"})
public class EncryptFilter implements Filter{

	/*
	 * Default constructor.
	 */
	public EncryptFilter() {
		System.out.println("EncryptFilter 작동!!");
	}

	/* 
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
		
	}


	/* 
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		
		// 매개변수로 받아온 Servletrequest를 HttpServletRequest로 캐스팅 해주는 작업
		HttpServletRequest h_request = (HttpServletRequest)request;
		
		// 암호화 래퍼 객체 생성
		EncryptWrapper encWrapper = new EncryptWrapper(h_request);
		
//		chain.doFilter(request, response);
		chain.doFilter(encWrapper, response);
		
		//이렇게 한다면 이제부터 InsertMemberServlet이나 LoginServlet의 request.getParameter(key)는
		//EncryptWrapper에서 오버라이딩된 getparameter 메소드로 실행될 것이다!!
		//즉 userPwd가 request에 담겨 들어온다면 암호화가 적용된다는 말씀!!
		
		//이걸로 jspProject는 끝이다!!
		//하지만 우리 못다한 기능이 한가지 있는데 뭘까??바로 댓글 달기이다!!
		//댓글달기는 AJAX라는 기술을용해서 한번 해보자
		
		//적용하기에 앞서 AJAX를 간단히 알아보기 위해 testAJAXProject 프로젝트를 만들어보자
		//만들 때 우리 항상 셋팅 먼저 한다!! --> 서버 만들어주고 프로젝트 설정들 하구!! 명심하고 만들어보자
		
	}


	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}
	


}
