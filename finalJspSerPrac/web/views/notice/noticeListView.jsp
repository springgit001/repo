<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"
	import="notice.model.vo.Notice, java.util.ArrayList"%>
<%
    	ArrayList<Notice> list = (ArrayList<Notice>)request.getAttribute("list");
%>
<!DOCTYPE>
<html>
<head>
<meta charset=UTF-8>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<style>
.outer {
	width: 800px;
	height: 500px;
	background: black;
	color: white;
	margin-left: auto;
	margin-right: auto;
	margin-top: 50px;
}

table {
	border: 1px solid white;
	text-align: center;
}

.tableArea {
	width: 650px;
	height: 350px;
	margin-left: auto;
	margin-right: auto;
}

#listArea{
	color:white;
}

.searchArea {
	width: 650px;
	margin-left: auto;
	margin-right: auto;
}
</style>
<title>Insert title here</title>
</head>
<body>
	<%@ include file="../common/menubar.jsp"%>

	<div class="outer">
		<br>
		<h2 align="center">공지사항</h2>
		<div class="tableArea">
			<!-- 1. 조회가 잘 되는지 확인하기!! -->
			<table align="center" id="listArea" >
				<tr>
					<th>글번호</th>
					<th width="300px">글제목</th>
					<th width="100px">작성자</th>
					<th>조회수</th>
					<th width="100px">작성일</th>
				</tr>
				<!-- 공지사항 글이 존재하지 않을 경우도 생각하기!
					list는 dao에서 무조건 생성된다.
					따라서 null이냐 아니냐의 조건이 아니라 비었냐 안비었냐의 조건으로 할 것 -->
				<% if(list.isEmpty()){ %>
					<tr>
						<td colspan="5">존재하는 공지사항이 없습니다.</td>
					</tr>
				<% }else{%>
					<% for(Notice n : list){ %>
						<tr>
							<td><%=n.getnNo() %></td>
							<td><%=n.getnTitle() %></td>
							<td><%=n.getnWriter() %></td>
							<td><%=n.getnCount() %></td>
							<td><%=n.getnDate() %></td>
						</tr>
					<%} %>
				<%} %>
			</table>
		</div>

<!-- 		보통 사이트들 공지사항이나 게시판 보면 아래에 검색하기 기능들이 있다. 만들어보자.
		기능은 아직 구현 안함. -->
		
		<div class="searchArea" align="center">
			<select id="searchCondition" name="searchCondition">
				<option>----</option>
				<option value="writer">작성자</option>
				<option value="title">제목</option>
				<option value="content">내용</option>
			</select>
			<input type="search">
			<button type="submit">검색하기</button>
			
<!-- 			2. 공지사항 글쓰기 기능!! -->
<!-- 				공지사항은 보통 관리자만 쓸 수 있다. 따라서 관리자만 쓸 수 있도록 조건을 걸자 -->
			<% if(loginUser != null && loginUser.getUserId().equals("admin")){ %>
			<button onclick="location.href='views/notice/noticeInsertForm.jsp'">작성하기</button>
			<%} %>
			<!-- noticeInsertForm.jsp 페이지 만들러 가자! -->
		</div>
	</div>
	<script>
		//3. 공지사항 상세보기 기능(jquery를 통해 작업)
		$(function(){
			//하나의 element에 여러개의 이벤트를 걸 수 있다.
			$("#listArea td").mouseenter(function(){
				$(this).parent().css({"background":"darkgrey","cursor":"pointer"});
			}).mouseout(function(){
				$(this).parent().css({"background":"black"});
			}).click(function(){
				var num = $(this).parent().children().eq(0).text();
				location.href="<%=request.getContextPath()%>/detail.no?no="+num;
			});
		})
		//이제 NoticeDetailServlet 만들러 ㄱㄱ
	</script>
</body>




























</html>