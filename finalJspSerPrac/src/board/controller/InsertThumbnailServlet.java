package board.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;

import com.oreilly.servlet.MultipartRequest;

import board.model.service.BoardService;
import board.model.vo.Attachment;
import board.model.vo.Board;
import common.MyFileRenamePolicy;
import member.model.vo.Member;

/**
 * Servlet implementation class InsertThumbnailServlet
 */
@WebServlet("/insert.th")
public class InsertThumbnailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public InsertThumbnailServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		
		//String title= request.getParameter("title");
		
		//폼 전송을 multipart/form-data로 전송하는 경우
		//기존처럼 request.getParameter로 값을 받을 수 없다.
		//cos.jar가 파일도 받고 폼의 다른 값들도 받아주는 역할을 한다.
		//com.orelilly.servlet의 약자이다.
		
		//http://www.servlets.com/cos/에서 다운로드!
		//cos.jar 파일을 lib 폴더에 복사하자!! 라이브러리를 가져와야 사용 가능!
		
		//enctype이  multipart/form-data로 전송 되었는지 확인!!
		if(ServletFileUpload.isMultipartContent(request)) {
			
			//1_1. 전송 파일 용량 제한 : 10Mbyte로 제한한 경우
			int maxSize = 1024 * 1024 * 10;
			//1_2. 웹 서버 컨테이너 경로 추출함
			String root = request.getSession().getServletContext().getRealPath("/");
			//1_3. 파일들 저장 경로(ex : web/thumbnail_uploadFiles/) 정함
			String savePath = root + "thumbnail_uploadFiles/";
		
			/*
			 * 2. 파일 명 변환 및 저장 작업
			 * 
			 * 객체 생성 시 파일을 저장하고 그에 대한 정보를 가져오는 형태이다.
			 * 즉 파일의 정보를 검사하여 저장하는 형태가 아닌, 저장한 다음 검사 후 삭제를 해야 된다.
			 * 
			 * 사용자가 올린 파일명을 그대로 저장하지 않는 것이 일반적이다.
			 * - 같은 파일명이 있는 경우 이전 파일을 덮어쓸 수 있다.
			 * - 한글로 된 파일명, 특수 기호나 띄어쓰기 등은 서버에 따라 문제가 생길 수 있다.
			 * 
			 * DefaultFileRenamePolicy는 cos.jar 안에 존재하는 클래스이고
			 * 같은 파일명이 존재하는 지를 검사하고 있을 경우에는 파일명 뒤에 숫자를 붙여준다.
			 * ex: aaa.zip, aaa1.zip, aaa2.zip
			 * 
			 * DefaultFileRenamePolicy 사용시 다음과 같이...
			 * MultipartRequest multiRequest = new MultipartRequest(request, savePath, maxSize, "UTF-8", new DefaultFileRenamePolicy());
			 * 
			 * 하지만 우리는 DefaultFileRenamePolicy를 사용하지 않고
			 * 직접 우리 방식대로 rename작업을 하기 위한 클래스를 만들 것이다.
			 * common 패키지 안에 MyFileRenamePolicy 클래스를 FileRenamePolicy를 상속받아 만들어주자!
			 * 
			 */
			
			//2_1. 1번 작업에서 작업한 내용(저장경로, 용량제한), 인코딩, 파일명 변환 기능이 있는 클래스 들을 지정하여
			//		MultipartRequest의 참조변수 multiRequest 선언
			//		--> 선언하는 순간에 MyFileRenamePolicy의 rename 메소드가 실행되면서 rename된 파일이 폴더에 저장
			MultipartRequest multiRequest = new MultipartRequest(request, savePath, maxSize, "UTF-8", new MyFileRenamePolicy());
			
			//2-2. DB에 저장하기 위해 change_name과 origin_name 각각의 리스트들을 만들어 주는 작업
			
			//다중 파일을 묶어서 업로드 하기에 컬렉션을 사용
			//저장한 파일의 이름을 저장할 ArrayList를 생성한다.
			ArrayList<String> saveFiles = new ArrayList<String>();
			//원본 파일의 이름을 저장할 ArrayList를 생성한다.
			ArrayList<String> originFiles = new ArrayList<String>();
			
			//getFileNames() - 폼에서 전송된 파일 리스트들의 name들을 반환한다.
			Enumeration<String> files = multiRequest.getFileNames();
			while(files.hasMoreElements()) {
				//전송 순서 역순으로 파일을 가져온다.
				String name = files.nextElement();
				
				if(multiRequest.getFilesystemName(name) != null) {
					//getFilesystemName() - MyRenamePolicy의 rename 메소드에서 작성한대로 rename 된 파일 명
					saveFiles.add(multiRequest.getFilesystemName(name));
					//getOriginalFileName() - 실제 사용자가 업로드 할 때의 파일 명
					originFiles.add(multiRequest.getOriginalFileName(name));
				}
			}
			
			System.out.println(originFiles.size());
			
			//3_1. 파일 외에 게시판 제목, 내용, 작성자 회원 번호 받아오기
			String title = multiRequest.getParameter("title");
			String content = multiRequest.getParameter("content");
			String bwriter = String.valueOf(((Member)request.getSession().getAttribute("loginUser")).getUserNo());
			
			//3_2. DB에 보낼 Board 객체와 Attachment리스트 생성
			Board b = new Board();
			b.setbTitle(title);
			b.setbContent(content);
			b.setbWriter(bwriter);
			
			
			ArrayList<Attachment> fileList = new ArrayList<Attachment>();
			//전송 순서 역순으로 파일이 list에 저장 되었기 때문에 반복문을 역으로 수행함
			for(int i=originFiles.size() -1;i>=0;i--) {
				Attachment at = new Attachment();
				at.setFilePath(savePath);
				at.setOriginName(originFiles.get(i));
				at.setChangeName(saveFiles.get(i));
				
				//타이틀 이미지가 originFiles에서의 마지막 인덱스이기 때문에 다음과 같이 조건을 준 다음에 level을 0으로 지정
				if(i==originFiles.size()-1) {
					at.setFileLevel(0);
				}else {
					at.setFileLevel(1);
				}
				fileList.add(at);
			}
			
			int result = new BoardService().insertThumbnail(b, fileList);
			//BoardService로 가서 insertThumbnail메소드 완성 시키기
			
			if(result > 0) {
				response.sendRedirect("list.th");
			}else {
				//실패시 저장된 사진 삭제
				for(int i=0;i<saveFiles.size();i++) {
					//파일 시스템에 저장된 이름으로 파일 객체 생성함
					File failedFile = new File(savePath + saveFiles.get(i));
					failedFile.delete();
				}
				
				request.setAttribute("msg", "사진 게시판 등록 실패!!");
				request.getRequestDispatcher("views/common/errorPage.jsp").forward(request, response);
			}
		}
		
		// 여기까지 테스트 해보고 잘 되면 이제 사진게시판 상세보기를 하자!! thumbnailListView.jsp로 다시 돌아감
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
