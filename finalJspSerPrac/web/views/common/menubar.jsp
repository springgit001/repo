<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="member.model.vo.Member"%>
<%
	Member loginUser = (Member)session.getAttribute("loginUser");
%>
<!DOCTYPE>
<html>
<head>
<meta charset=UTF-8>
<title>Insert title here</title>
<!-- css 가져오기 -->
<%-- <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/resources/css/menubar.css"> --%>
<style>
	body{
		background:url('<%=request.getContextPath() %>/resources/images/city1.PNG') no-repeat;
		background-size:cover;
	}
	
	
	#loginBtn input, #memberJoinBtn, #logoutBtn, #myPage{
		display:inline-block;
		verticla-align:middle;
		text-align:center;
		background:orangered;
		color:white;
		height:25px;
		width:100px;
		border-radius:5px;
	}
	#memberJoinBtn {
		background:yellowgreen;
	}
	#loginBtn:hover, #changeInfo:hover, #logoutBtn:hover, #memberJoinBtn:hover, #myPage:hover{
		cursor:pointer;
	}
	.loginArea > form, #userInfo {
		float:right;
	}
	#logout, #myPage {
		background:orangered;
		color:white;
		text-decoration:none;
		border-radius:5px;
	}
	#myPage {
		background:yellowgreen;
	}
	
	.wrap {
		background:black;
		width:100%;
		height:50px;
	}
	.menu {
		background:black;
		color:white;
		text-align:center;
		vertical-align:middle;
		width:150px;
		height:50px;
		display:table-cell;
	}
	.nav {
		width:600px;
		margin-left:auto;
		margin-right:auto;
	}
	.menu:hover {
		background:darkgray;
		color:orangered;
		font-weight:bold;
		cursor:pointer;
	}
</style>
</head>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>

<body>

 
	
	<h1 align="center">Welcome JSP World!</h1>
	<!---------------------------- 1. 회원 관련 서비스 ---------------------------------->
	<div class="loginArea">
	<!-- 2_1. 로그인이 안되어 있는 경우와 되어 있는 경우가 다르게 보여야 된다. 따라서 if문을 추가하자
	위에서 loginUser를 session 객체에서 받아 놓자 -->
	<% if(loginUser == null){ %>
		<!-- 1_1. 로그인 관련 폼 만들기 -->
		<form id="loginForm" action="<%=request.getContextPath()%>/login.me"
			method="post" onsubmit="return validate();">
			
	
			
			<table>
				<tr>
					<td><label>ID : </label></td>
					<td><input type="text" name="userId" id="userId"></td>
				</tr>
				<tr>
					<td><label>PWD : </label></td>
					<td><input type="password" name="userPwd" id="userPwd"></td>
				</tr>
				<tr>
					<td colspan="2"><input type="checkbox" name="saveId"
						id="saveId">&nbsp; <label for="saveId">아이디 저장</label></td>
				</tr>
			</table>

			<div class="btns" align="center">
				<div id="memberJoinBtn" onclick="memberJoin();">회원가입</div>
				<div id="loginBtn">
					<input type="submit" value="로그인">
				</div>

			</div>
		</form>
		


		<!-- 2_2. 로그인이 성공적으로 됐을 경우 -->
		<% }else{ %>
			<div id = "userInfo">
				<label><%=loginUser.getUserName() %>님의 방문을 환영합니다.</label>
				<div class="btns" align="right">
					<!-- 4. 이제부터 마이페이지 작업 시작! -->
					<div id="myPage" onclick="location.href='/jsp/myPage.me?userId=<%= loginUser.getUserId() %>';">정보수정</div>
					<div id="logoutBtn" onclick="logout();">로그아웃</div>
				</div>
			</div>
		<% } %>
		
		<script>
		// 1_2. validate() 함수 작성하기
		function validate() {
			if ($("#userId").val().trim().length == 0) {
				alert("아이디를 입력하세요");
				$("#userId").focus();

				return false;
			}

			if ($("#userPwd").val().trim().length == 0) {
				alert("비밀번호를 입력하세요");
				$("#userPwd").focus();

				return false;
			}

			return true;

			// 여기까지 작성했으면 LoginServlet 만들러 가자
		}

		// 2_3. logout() 함수 작성하기
		function logout(){
			location.href='<%= request.getContextPath() %>/logout.me';
			
			// LogoutServlet 만들러 가기!
		}
		
		// 3. 지금부터 회원가입하자!! 회원가입으로 넘어가는 함수
		function memberJoin(){
			//원래는 페이지로 바로 전환이 안되서 항상 단순 페이지 전환시에도 서블릿을 거쳐서 가야된다.
			//하지만 우리가 가고자 하는 페이지가 WEB-INF안에 없고 밖에 있다면 WAS 서버를 거칠 필요가 없다!!
			location.href="<%=request.getContextPath()%>/views/member/memberJoinForm.jsp";
			
			//memberJoinForm.jsp 파일 만들어서 폼 작성하기!
		}
		</script>
			
	<!-- ---------------------------------------------------------------- -->
	
	<!-- 2. 회원 관련 서비스들을 모두 마치면 이제부터 공지사항 및 게시판들 작업 시작!! -->
	<br clear = "both">
	<br>
	
	<div class="wrap">
		<div class="nav">
			<div class="menu" onclick="goHome();">HOME</div>
			<div class="menu" onclick="goNotice();">공지사항</div>
			<div class="menu" onclick="goBoard();">게시판</div>
			<div class="menu" onclick="goThumbnail();">사진게시판</div>
		</div>
	</div>
	<script>
		function goHome(){
			location.href="<%=request.getContextPath()%>/index.jsp";
		}
		
		//1. 공지사항 먼저 작업해보자!!
		function goNotice(){
			location.href="<%=request.getContextPath()%>/list.no";
			//NoticeListServlet 만들러 ㄱㄱ
		}
		
		//2. 게시판 작업하자!!
		function goBoard(){
			location.href="<%=request.getContextPath() %>/list.bo";
			//BoardListServlet 만들러 ㄱㄱ
		}
		
		//3. 사진게시판 작업하자!!
		function goThumbnail(){
			location.href="<%=request.getContextPath()%>/list.th";
			// ThumbnailListServlet 만들러 가자!!
		}
	</script>
		
</body>
</html>