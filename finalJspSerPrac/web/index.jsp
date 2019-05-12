<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

	<!-- 
		* 제일 먼저 셋팅 할 것들 
		1. Window - Preferences
			1) 인코딩 관련 한 것들 UTF-8로 바꿔주기
			2) Server 만들어주기
			3) 글자(Text Font) 포인트 바꿔주기
			
		2. Dynamic Web Project 생성
			1) Next > Next 버튼 누르면 
			   - Context root명 간단하게 해주고 
			   - Context directory web으로 바꿔주기 
			   - 아래 체크 박스 꼭 체크 해주기 (안그러면 web.xml이 안생김..)
			2) project 오른쪽 버튼 - Properties  
			   - Java Build Path > Source 탭 > Default output folder : Browse..
			   	  프로젝트명/web/WEB-INF/classes 로 바꿔주기
			   	  왜냐하면 ==> 소스파일이 컴파일 하고 저장되는 경로가 root폴더인 web폴더 밑에 저장되야되는게 통상 맞음 안그러면 404 에러가 날 수도 있다.
			   	  		      톰캣이 web.xml과 연결시켜준다. web.xml은 WEB-INF 폴더 아래에 있기 때문에 그래서 WEB-INF 안의 폴더에 해주는게 맞다.
			   	 ** 만약 output folder로 copy가 안된다면 폴더 다시만들어서 셋팅해줄것
			3) build 폴더 삭제하기
			4) project facets 설정 확인하기 -> sever 선택 안할 시 index.jsp의 상단 부분 오류날 수 있음
		
		3. new Server 서버 만들기
			1) 왼쪽 아래의 Serve modules without publishing 체크박스 꼭 체크해주기
			       왜냐하면 ==> 내가 지정한 output folder에 복사가 돼야되는데 안할 시 이상한 경로로 저장이 됨
			2) 포트 번호들 바꾸기
			3) Server에 프로젝트 올리기
		
		4. 내가 앞으로 작성할 jsp 파일이 java 파일로 볼 수 있도록 저장되는 폴더가 필요함
			1) Servers/프로젝트명-config/context.xml 파일
		   	   ==> 내가 만들 프로젝트의 META-INF 안으로 복사 
		    2) context.xml 파일에 workDir속성을 통해 경로 지정
		
		5. web.xml 파일 수정 ==> welcome file 수정
		
		6. index.jsp 파일 만들기
		
		7. Server 스타트 해서 실행 해보기
	 -->
	
	
	
	<!-- 
		* 부가 작업
		1. Window - Perspective - Customize Perspective.. - Shortcuts탭 을 통해 많이 사용되는 것들 지정하기
		   Window - Show view를 통해 Navigator, Project Explorer, Console, Problems, Progress 셋팅하기
		
		2. new Jsp file을 했을 때 불필요한 부분 제거하는 방법
			- Windows - Preferences - Web - JSP Files - Editor - Templates
			    들어가서 new JSP File(html) 부분 더블클릭해서 템플릿 수정하기
			    
		3. 이 프로젝트 관련 데이터베이스 스크립트 제공해주기 
			1) 계정 만들기 - username : server, password : server  
				driver.properties에서 본 수업에선 server로 둘다 할 것
			2) db script 읽기
		
		4. 큰 틀 먼저 잡고 시작하기
			1) web 아래에 폴더들 만들기(resources(css, images, js), views)
			2) views 폴더 아래에 각각의 폴더들 만들기(common, member, board, notice)
			3) 각각의 패키지들 만들기 (common, member, board, notice 까지만)
			4) common 패키지 안에 JDBCTemplate 클래스 만들기
	 -->

	<%@ include file="views/common/menubar.jsp" %>
	
	<!-- menubar.jsp 만들러 가자 -->
	
</body>
</html>