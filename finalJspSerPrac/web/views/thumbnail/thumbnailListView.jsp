<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import = "java.util.*, board.model.vo.*"%>
    <%
    	ArrayList<Board> blist = (ArrayList<Board>)request.getAttribute("blist");
    	ArrayList<Attachment> flist = (ArrayList<Attachment>)request.getAttribute("flist");
    %>
<!DOCTYPE>
<html>
<head>
<style>
	.outer{
		width:1000px;
		height:700px;
		background:black;
		color:white;
		margin-left:auto;
		margin-right:auto;
		margin-top:50px;
	}
	.thumbnailArea {
		width:760px;
		height:550px;
		margin-left:auto;
		margin-right:auto;
	}
	.searchArea {
		width:420px;
		margin-left:auto;
		margin-right:auto;
	}
	.thumb-list {
		width:220px;
		border:1px solid white;
		display:inline-block;
		margin:10px;
		align:center;
	}
	.thumb-list:hover {
		opacity:0.8;
		cursor:pointer;
	}
</style>
<meta charset=UTF-8>
<title>Insert title here</title>
</head>
<body>
	<%@ include file="../common/menubar.jsp" %>
	<div class = "outer">
		<br>
		<h2 align="center">사진 게시판</h2>
		<div class = "thumbnailArea">
			<% for(int i=0;i<blist.size();i++){
				Board b = blist.get(i);	%>
		
				<div class="thumb-list" align="center">
					<div>
						<input type="hidden" value="<%= b.getbId() %>">
						<% for(int j=0;j<flist.size();j++){ 
							Attachment a = flist.get(j);%>
							
							<% if(b.getbId() == a.getbId()){ %>
								<img src="<%=request.getContextPath() %>/thumbnail_uploadFiles/<%=a.getChangeName() %>"
								width="200px" height="150px">
							<%} %>
						<%} %>
					</div>
					<p>No. <%= b.getbId() %><%=b.getbTitle() %><br>
							조회수 : <%= b.getbCount() %>
					</p>
				</div>
			<%} %>
		</div>
		
		<!-- 마찬 가지로 검색 창을 만들어주자! 역시나 기능 구현은 안할 꺼다 -->
		<div class="searchArea">
			<select id="searchCondition" name="searchCondition">
				<option>------</option>
				<option value="writer">작성자</option>
				<option value="title">제목</option>
				<option value="content">내용</option>
			</select>
			<input type="search">
			<button type="submit">검색하기</button>
			
			
			<% if(loginUser != null) { %>
				<button onclick="location.href='views/thumbnail/thumbnailInsertForm.jsp'">작성하기</button>
			<% } %>
			<!-- thumbnailInsertForm.jsp 만들러 가자!! -->
		</div>
	</div>

	<script>
		$(function(){
			$(".thumb-list").click(function(){
				var bid = $(this).children().children().eq(0).val();
				location.href="<%=request.getContextPath()%>/detail.th?bid="+bid;
				//ThumbnailDetailServlet 만들러 ㄱㄱ
			});
		});
	</script>
</body>
</html>