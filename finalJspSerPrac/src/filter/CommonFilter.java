package filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

/*
 * Servlet Filter implementation class CommonFilter
 */

//filterName을 통해서 어떤 역할을 하는 필터인지 지정해주고
//urlPatterns를 통해서 어떠한 서블릿을 가기전에 거칠 것인지를 지정해준다. --> /*로 지정하게 되면 모든 서블릿을 뜻한다.
@WebFilter(filterName = "encoding", urlPatterns="/*")
public class CommonFilter implements Filter{
	/*
	 * 필터는 Servlet 2.3 이상에서 사용 가능하다!!
	 * 서블릿 필터는 request, response가 서블릿이나 JSP 등 리소스에 도달하기 전 필요한 전/후 처리 작업을 맡는다.
	 * 필터는 FilterChain을 통해 여러개 혹은 연쇄적으로 사용가능하다.
	 * 
	 * 필터클래스 등록하는 방법
	 * 1. WEB-INF/web.xml 파일에 필터를 등록해야만 사용가능하다.
	 * 2.하지만 최근에는 web.xml에 등록하지 않고, @WebFilter라는 어노테이션으로 대체해서 사용하는 추세이다.
	 * 
	 * 
	 * Filter 생명주기
	 * - init()		: 컨테이너가 필터를 인스턴스화 할 때 호출한다.
	 * 					필터 설정 관련 코드 작성가능
	 * 					FilterConfig 객체를 인자로 받아 필드화 할 수 있음
	 * - doFilter() : 컨테이너가 현재 요청에 필터 적용을 하겠다 판단하면 호출한다.
	 * 					ServletRequest, ServletResponse, FilterChain 객체
	 * - destroy()  : 컨테이너가 필터 인스턴스를 제거할 때 호출한다.
	 * 
	 * Filter(인터페이스)의 doFilter(request, response, chain)과
	 * FilterChain의 doFilter(request, response)는 다르다.
	 * --> Filter의 doFilter()는 진짜 필터링 작업을 함
	 * --> FilterChain의 doFilter()는 다음 필터를 호출하거나, 마지막이라면 servlet, jsp를 호출함
	 * 
	 * 
	 */
	
	/*
	 * Default constructor. 
	 */
	
	public CommonFilter() {
		System.out.println("CommonFilter 작동!");
	}
	
	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub		
	}


	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// 뷰에서 전달 받은 값에 한글이 있을 경우
		// 1_1. request 파라미터를 이용해서 요청의 필터 작업 수행
		request.setCharacterEncoding("utf-8");
		// 뷰로 전달 할 값에 한글이 있을 경우
		// 1_2. response 파라미터를 이용해서 응답의 필터 작업 수행
		response.setContentType("text/html;charset=utf-8");
		
		chain.doFilter(request,  response);
		// 2. 체인의 다음 필터 처리
		// 3. response를 이용하여 응답의 필터링 작업 수행
		
		// 필터 메서드 내용부의 마지막 코드는 현재까지 작업한 내용을 적용하고 연결된 페이지로 이동하도록 만들어준다. 이런역할을 하는 메서드가 chain 객체의 doFilter()이다.
		// 세번째 매개변수인 FilterChain 클래스의 객체인 chain을 이용해서 다른 필터나 서블릿과 연결하는 코드를 반드시 작성해야 한다.
		
//		이렇게 필터 작업을 끝내면 모든 서블릿에 위의 1번과 2번을 생략할 수 있다. --> 테스트 해보라.
		
//		다시 ThumbnailDownloadServlet으로 가보자
		
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
		
	}
	
	
	
	
	
	
	
	
}
