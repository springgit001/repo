package board.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import board.model.service.BoardService;
import board.model.vo.Attachment;

/**
 * Servlet implementation class ThumbnailDownloadServlet
 */
@WebServlet("/download.th")
public class ThumbnailDownloadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ThumbnailDownloadServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int fid = Integer.parseInt(request.getParameter("fid"));
		
		Attachment file = new BoardService().selectAttachment(fid);
		//BoardService로 가서 selectAttachment 메소드 완성시키기
		
		//클라이언트로 내보낼 출력 스트림 생성
		ServletOutputStream downOut = response.getOutputStream();
		
		//스트림으로 전송할 파일 객체 생성
		File downFile = new File(file.getFilePath()+file.getChangeName());
		
		//한글 파일명에 대한 인코딩 처리함 --> 근데 안해도 되네??? 흠...
//		response.setContentType("text/plain; charset=utf-8");
		
		//강제적으로 다운로드 처리
		//파일명을 changeName이 아닌 originName으로 다운받을 수 있도록 처리
		response.setHeader("Content-Disposition",  "attachment; filename=\""+new String(file.getOriginName().getBytes("UTF-8"),"ISO-8859-1")+"\"");
		
		response.setContentLength((int)downFile.length());
		
		//폴더에서 파일을 읽을 스트림 생성
		FileInputStream fin = new FileInputStream(downFile);
		BufferedInputStream buf = new BufferedInputStream(fin);
		
		int readBytes = 0;
		
		while((readBytes = buf.read())!=-1) {
			downOut.write(readBytes);
		}
		
		downOut.close();
		buf.close();
		
		//다운로드가 잘되는지 확인!!! 이제 jspProject는 이걸로써 끝나긴 했는데!!!
		
		//우리 좀더 보완 작업 같은걸 해볼까??
		//1. 매 Servlet마다 한글에 대한 encoding작업을 했었었다.
		//		이를 하나의 filter로 만들어서 매번 거치도록 하자!!
		//		--> filter 패키지를 만들어서 CommonFilter라는 filter를 만들러 가자!!
		
		//2. 그리고 암호화에 대한 내용이다!
		//		보통 사이트를 만들때 사이트 관리자들 또한 회원에 대한 비밀번호를 알 수 없어야 된다.
		//		따라서 db에 회원 정보가 저장될 때 암호화가 된 비밀번호가 저장되어야 된다.
		//		--> wrapper 패키지를 만들어서 EncryptWrapper를 만들러 가자
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
