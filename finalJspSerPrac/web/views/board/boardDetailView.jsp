
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="board.model.vo.*,java.util.ArrayList"%>
   <%
    	Board b = (Board)request.getAttribute("board");
    	ArrayList<Reply> rlist = (ArrayList<Reply>)request.getAttribute("rlist");
    %> 
<!DOCTYPE>
<html>
<head>
<meta charset=UTF-8>
<style>
	.outer{
		width:800px;
		height:500px;
		background:black;
		color:white;
		margin-left:auto;
		margin-right:auto;
		margin-top:50px;
	}
	td {
		border:1px solid white;
	}

	.tableArea {
		border:1px solid white;
		width:800px;
		height:350px;
		margin-left:auto;
		margin-right:auto;
		
	}
	
	table{
		color:white;
		text-align:center;
	}
	#content {
		height:230px;
	}
	.replyArea {
		width:800px;
		/* height:300px; */
		color:white;
		background:black;
		margin-left:auto;
		margin-right:auto;
	}
</style>
<title>Insert title here</title>
</head>
<body>
	<%@ include file="../common/menubar.jsp" %>
	
	<div class="outer">
		<br>
		<h2 align="center">게시판 상세보기</h2>
		<div class="tableArea">
			<table align="center" width="800px">
			
				<tr>
					<td>분야</td>
					<td><span><%=b.getCategory() %></span></td>
					<td>제목</td>
					<td colspan="3"><span><%=b.getbTitle() %></span></td>					
				</tr>
				<tr>
					<td>작성자</td>
					<td><span><%=b.getbWriter() %></span></td>
					<td>조회수</td>
					<td><span><%=b.getbCount() %></span></td>
					<td>작성일</td>
					<td><span><%=b.getModifyDate() %></span></td>
				</tr>
				<tr>
					<td colspan="6">내용</td>
				</tr>
				<tr>
					<td colspan="6"><p id="content"><%=b.getbContent() %></p></td>
				</tr>
		
			</table>
		</div>
		
		<div align="center">
			<button onclick="location.href='<%= request.getContextPath() %>/list.bo'">메뉴로 돌아가기</button>

			<button>수정하기</button>
			<!-- 수정하기 기능은 공지사항 때 해봤으니 각자 알아서 해보도록 하고 지금은 생략하도록 한다. -->
			<!-- 이제 사진 게시판을 해보자! menubar.jsp로 돌아가자	 -->		
		</div>
	</div>
	
		<!-- 여기 ajax 부분임  -->
	<div class="replyArea">
		<!-- 2_1. 댓글 작성하는 부분  -->
		<div class="replyWriterArea">
			<table align="center">
				<tr>
					<td>댓글작성</td>
					<td><textArea rows="3" cols="80" id="replyContent"></textArea></td>
					<td>
						<button id="addReply">댓글등록</button>
					</td>
				</tr>
			</table>
		</div>
		
		<!-- 1. 불러온 댓글 리스트 보여주기 -->
		<div id="replySelectArea">
			<table id="replySelectTable" border="1" align="center">
				<% if(rlist.isEmpty()) { %>
					<tr><td colspan="3">댓글이 없습니다.</td></tr>
					
				<% }else { %>
					<% for(int i=0; i<rlist.size(); i++){ %>
						<tr>
							<td width="100px"><%= rlist.get(i).getrWriter() %></td>
							<td width="400px"><%= rlist.get(i).getrContent() %></td>
							<td width="200px"><%= rlist.get(i).getCreateDate() %></td>
						</tr>
					<% } %>
				<% } %>
			</table>
		</div>
	</div>
	<script>
		$(function(){
			//2_2. addReply 버튼 클릭 시 댓글 달기 기능을 실행했을 때 비동기적으로 새로 갱신된 리스트들을 테이블에 적용시키는 ajax
			$("#addReply").click(function(){
				var writer = '<%= loginUser.getUserNo()%>';	
				var bid = '<%= b.getbId()%>';
				var content = $("#replyContent").val();
				
				$.ajax({
					url:"insertReply.bo",
					type:"post",
					data:{writer:writer, content:content, bid:bid},
					//InsertReplyServlet 만들러 ㄱㄱ
					success:function(data){
						$replyTable = $("#replySelectTable");		//$변수는 jQuery용 내장 함수들을 모두 사용할 수 있다.
						$replyTable.html("");	//기존 테이블 정보 초기화
						
						//새로 받아온 갱신된 댓글리스트들을 for문을 통해 다시 table에 추가
						for(var key in data){
							var $tr = $("<tr>");
							var $writerTd = $("<td>").text(data[key].rWriter).css("width","100px");
							var $contentTd = $("<td>").text(data[key].rContent).css("width","400px");
							var $dateTd = $("<td>").text(data[key].createDate).css("width","200px");
							
							$tr.append($writerTd);
							$tr.append($contentTd);
							$tr.append($dateTd);
							$replyTable.append($tr);
						}
						
						//댓글 작성 부분 리셋
						$("#replyContent").val("");
					}
				});
				
			});
		});
		
	</script>
	<!-- 이제!!! 끝났다 드디어! -->
</body>
</html>