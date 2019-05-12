<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="notice.model.vo.Notice"%>
<%
	Notice n = (Notice)request.getAttribute("notice");
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
	table {
		border:1px solid white;
		color:white;
	}

	.tableArea {
		width:450px;
		height:350px;
		margin-left:auto;
		margin-right:auto;
	}
</style>
<title>Insert title here</title>
</head>
<body>
		<%@ include file = "../common/menubar.jsp" %>
	<div class="outer">
		<br>
		<h2 align="center">공지사항 수정 페이지</h2>
		<div class="tableArea">
			<!-- noticeDetailView.jsp에서 table 부분 복사해 와서 readonly 없애기 -->
			<!-- form태그 추가하기 -->
			<form id="updateForm" method="post">
			<table>
					<tr>
						<td>제목</td>
						<td colspan="3">
							<input type="text" size="50" name="title" value="<%=n.getnTitle() %>" >
							<!-- hidden으로 nno값 심어주자 -->
							<input type="hidden" name="nno" value="<%=n.getnNo() %>">
						</td>
					</tr>
					<tr>
						<td>작성자</td>
						<td>
							<input type="text" value="<%=n.getnWriter() %>" name="writer" readonly>
						</td>
						<td>작성일</td>
						<td>
							<input type="date" name="date" value="<%=n.getnDate() %>" >
						</td>
					</tr>
					<tr>
						<td>내용</td>
					</tr>
					<tr>
						<td colspan="4">
							<textarea name="content" cols="60" rows="15" style="resize:none;"><%=n.getnContent() %></textarea>
						</td>
					</tr>
				</table>
				<br>
				<div align="center">
					<button onclick="update();"> 수정하기</button>
					<button onclick="delete1();"> 삭제하기</button>	<!-- delete로 함수명을 정할 수 없음(delete 연산자는 기존 javascript에 있으므로 쓰면 X) -->
					
				</div>
			</form>
			<script>
				// 4. 공지사항 수정하기 작업부터
				function update(){
					$("#updateForm").attr("action","<%=request.getContextPath()%>/update.no");
					//$("#updateForm").submit();//필요없다.
					
					//NoticeUpdateServlet 만들러 가기
				}
				
				// 5. 공지사항 삭제하기
				function delete1(){
					$("#updateForm").attr("action","<%=request.getContextPath()%>/delete.no");
					//NoticeDeleteServlet 만들러 가기
				} 
			</script>
		</div>
	</div>
</body>


















</html>