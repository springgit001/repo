package board.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import board.model.service.BoardService;
import board.model.vo.Reply;

/**
 * Servlet implementation class InsertReplyServlet
 */
@WebServlet("/insertReply.bo")
public class InsertReplyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public InsertReplyServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 인코딩에 대한 필터 적용 했으니깐 인코딩 내용 생략하겠음
		
		String writer = request.getParameter("writer");
		int bid = Integer.parseInt(request.getParameter("bid"));
		String content = request.getParameter("content");
		
		Reply r = new Reply();
		r.setrWriter(writer);
		r.setRefBid(bid);
		r.setrContent(content);		
		
		ArrayList<Reply> rlist = new BoardService().insertReply(r);
		// 뭔가 이상하다... insert 작업을 하는데 int 결과값이 아니라 ArrayList??
		// 왜냐면!! 댓글을 insert 해주고 갱신된 댓글 리스트들을 다시 불러올것이기 때문!! 
		// BoardService로 가서 insertReply 메소드를 완성시키고 오자
		
		
		
		// 리스트를 다시 클라이언트로 보내줄건데! 그 때 편한 Gson을 이용해서 보내주자
		// Gson라이브러리를 testAjaxProject에서 복사해서 추가하고 다음과 같이 보내주자
		response.setContentType("application/json;"); // charset=utf-8은 필터에서 적용해줫으니 생략!!
		// Gson으로 값을 보내게 되면 Date형식이 바껴서 출력되는걸 볼 수 있다.
	      // 따라서 Gson 생성시 Date포맷을 원하고자 하는 포맷으로 바꿔서 생성해보자
	      Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create(); 
	      gson.toJson(rlist, response.getWriter());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
