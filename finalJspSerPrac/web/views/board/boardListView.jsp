<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="board.model.vo.*, java.util.ArrayList"%>
    
    <%
    	ArrayList<Board> list = (ArrayList<Board>)request.getAttribute("list");
    	PageInfo pi = (PageInfo)request.getAttribute("pi");
    	
    	int listCount = pi.getListCount();
    	int currentPage = pi.getCurrentPage();
    	int maxPage = pi.getMaxPage();
    	int startPage = pi.getStartPage();
    	int endPage = pi.getEndPage();
    %>
<!DOCTYPE>
<html>
<head>
<meta charset=UTF-8>
<style>
	.outer{
		width:900px;
		height:500px;
		background:black;
		color:white;
		margin-left:auto;
		margin-right:auto;
		margin-top:50px;
	}
	table {
		border:1px solid white;
		text-align:center;
		
	}
	.tableArea {
		width:650px;
		height:350px;
		margin-left:auto;
		margin-right:auto;
	
	}
	.searchArea {
		width:650px;
		margin-left:auto;
		margin-right:auto;
	}
	
	#listArea{
		color:white;
	}
</style>
<title>Insert title here</title>
</head>
<body>
	<%@ include file="../common/menubar.jsp" %>
	<div class ="outer">
		<br>
		<h2 align="center">게시판</h2>
		<div class="tableArea">
			<table align="center" id="listArea">
				<tr>
					<th width="100px">글번호</th>
					<th width="100px">카테고리</th>
					<th width="300px">글제목</th>
					<th width="100px">작성자</th>
					<th width="100px">조회수</th>
					<th width="150px">작성일</th>
				</tr>
				<% if(list.isEmpty()) {%>
				<tr>
					<td colspan="6">조회된 리스트가 없습니다.</td>
				</tr>
				<%}else{ %>
					<%for(Board b : list){ %>
					<tr>
						<input type="hidden" value="<%=b.getbId() %>">
						<td><%=b.getbId() %></td>
						<td><%=b.getCategory() %></td>
						<td><%=b.getbTitle() %></td>
						<td><%=b.getbWriter() %></td>
						<td><%=b.getbCount() %></td>
						<td><%=b.getModifyDate()%></td>
					</tr>
					<%} %>
				<%} %>
			</table>
		</div>
		
		<!-- 페이징 처리 시작! -->
		<div class="pagingArea" align="center">
			<!-- 맨 처음으로(<<) -->
			<button onclick="location.href='<%=request.getContextPath() %>/list.bo?currentPage=1'"> << </button>
			
			<!-- 이전 페이지로(<) -->
			<% if(currentPage <= 1){ %>
				<button disabled> < </button>
			<%} else{ %>
				<button onclick="location.href='<%=request.getContextPath() %>/list.bo?currentPage=<%=currentPage-1 %>'"> < </button>
			<%} %>
			
			<!-- 10개의 페이지 목록 -->
			<% for(int p=startPage;p<=endPage;p++){ %>
				<% if(p==currentPage){ %>
					<button disabled> <%= p %></button>
				<%} else{ %>
					<button onclick="location.href='<%=request.getContextPath() %>/list.bo?currentPage=<%=p %>'"><%= p %></button>
				<% } %>
			<%} %>
			
			<!-- 다음 페이지로(>) -->
			<% if(currentPage >= maxPage){%>
				<button disabled> > </button>
			<%}else{ %>
				<button onclick="location.href='<%=request.getContextPath() %>/list.bo?currentPage=<%=currentPage+1 %>'"> > </button>
			<%} %>
			
			<!-- 맨 끝으로(>>) -->
			<button onclick="location.href='<%=request.getContextPath() %>/list.bo?currentPage=<%=maxPage %>'"> >> </button>
		</div>
		
		<!-- 공지사항 때와 마찬가지로 검색 부분은 있다! 하지만 기능 구현은 우선 안하겠다. -->
				<div class="searchArea" align="center">
			<select id="searchCondition" name="searchCondition">
				<option>-----</option>
				<option value="category">카테고리</option>
				<option value="writer">작성자</option>
				<option value="title">제목</option>
				<option value="content">내용</option>
			</select>
			<input type="search">
			<button type="submit">검색하기</button>
			
			<% if(loginUser != null){ %>
				<button onclick="location.href='views/board/boardInsertForm.jsp'">작성하기</button>
			<% } %>
			<!-- boardInsertForm.jsp 만들러 가자!!! -->
		</div>
	</div>
	<script>
		//3. 게시판 상세보기 기능 구현하자!
		$(function(){
			$("#listArea td").mouseenter(function(){
				$(this).parent().css({"background":"darkgray", "cursor":"pointer"});
			}).mouseout(function(){
				$(this).parent().css({"background":"black"});
			}).click(function(){
				var bid = $(this).parent().children("input").val();
				
				//로그인 한 사람만 이용하도록 하자!
				<% if(loginUser != null){ %>
					location.href="<%= request.getContextPath()%>/detail.bo?bid="+bid;
				<% }else{ %>
					alert("로그인해야만 상세보기가 가능합니다!");
				<% } %>
				//BoardDetailServlet 만들러 ㄱㄱ
			})
			
		})
	</script>
	
</body>
















</html>