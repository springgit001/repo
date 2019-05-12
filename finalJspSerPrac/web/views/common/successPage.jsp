<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String msg = (String)request.getAttribute("msg");
%>
    
<!DOCTYPE>
<html>
<head>
<meta charset=UTF-8>
<title>Insert title here</title>
<style>
	#return {
		height:50px;
		width:100px;
		background:orange;
		font:20pt black;
		margin:auto;
	}
	#return:hover{
		cursor:pointer;
	}
</style>
</head>
<body>
	<h1 align="center"><%=msg %></h1>
	<div id="return" onclick="goHome()" align="center">고홈</div>
</body>
<script>
	function goHome(){
		location.href="<%= request.getContextPath() %>/index.jsp";
	}
</script>
</html>