<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE>
<html>
<head>
<meta charset=UTF-8>
<style>
	.outer {
		width:1000px;
		height:650px;
		background:black;
		color:white;
		margin-left:auto;
		margin-right:auto;
		margin-top:50px;
	}
	table {
		border:1px solid white;
	}
	.insertArea {
		width:500px;
		height:450px;
		margin-left:auto;
		margin-right:auto;
	}
	.btnArea {
		width:150px;
		margin-left:auto;
		margin-right:auto;
	}
	#titleImgArea {
		width:350px;
		height:200px;
		border:2px dashed darkgray;
		text-align:center;
		display:table-cell;
		vertical-align:middle;
	}
	#titleImgArea:hover, #contentImgArea1:hover, 
	#contentImgArea2:hover, #contentImgArea3:hover {
		cursor:pointer;
	}
	#contentImgArea1, #contentImgArea2, #contentImgArea3 {
		width:150px;
		height:100px;
		border:2px dashed darkgray;
		text-align:center;
		display:table-cell;
		vertical-align:middle;
	}
</style>
<title>Insert title here</title>
</head>
<body>
	<%@ include file = "../common/menubar.jsp" %>
	<div class="outer">
		<br>
		<h2 align="center">사진 게시판 작성</h2>
		
		<!-- 파일업로드를 위해 enctype을 지정해줘야 된다. -->
		<form action="<%=request.getContextPath() %>/insert.th" method="post" encType="multipart/form-data">
			<div class="insertArea">
				<table align="center">
					<tr>
						<td width="100px">제목</td>
						<td colspan="3"><input type="text" size="45" name="title"></td>
					</tr>
					<tr>
						<td>대표 이미지</td>
						<td colspan="3">
							<div id="titleImgArea">
								<img id="titleImg" width="350" height="200">
							</div>
						</td>
					</tr>
					<tr>
						<td>내용 사진</td>
						<td>
							<div id="contentImgArea1">
								<img id="contentImg1" width="120" height="100"> 
							</div>
						</td>
						<td>
							<div id="contentImgArea2">
								<img id="contentImg2" width="120" height="100"> 
							</div>
						</td>
						<td>
							<div id="contentImgArea3">
								<img id="contentImg3" width="120" height="100"> 
							</div>
						</td>
					</tr>
					<tr>
						<td width="100px">사진 메모</td>
						<td colspan="3"><textarea name="content" rows="5" cols="50" size="resize:none;"></textarea>
					</tr>
				</table>
				
				<!-- 파일 업로드 하는 부분 -->
				<div id="fileArea">
					<input type="file" id="thumbnailImg1" multiple="multiple" name="thumbnailImg1" onchange ="LoadImg(this,1)">
					<input type="file" id="thumbnailImg2" multiple="multiple" name="thumbnailImg2" onchange ="LoadImg(this,2)">
					<input type="file" id="thumbnailImg3" multiple="multiple" name="thumbnailImg3" onchange ="LoadImg(this,3)">
					<input type="file" id="thumbnailImg4" multiple="multiple" name="thumbnailImg4" onchange ="LoadImg(this,4)">
				</div>
				<script>
					//내용 작성 부분의 공간을 클릭할 때 파일 첨부 창이 뜨도록 설정하는 함수
					$(function(){
						$("#fileArea").hide();
						$("#titleImgArea").click(function(){
							$("#thumbnailImg1").click();
						});
						$("#contentImgArea1").click(function(){
							$("#thumbnailImg2").click();
						});
						$("#contentImgArea2").click(function(){
							$("#thumbnailImg3").click();
						});
						$("#contentImgArea3").click(function(){
							$("#thumbnailImg4").click();
						});
					});
					
					//각각의 영역에 파일을 첨부 했을 경우 미리 보기가 가능하도록 하는 함수
					function LoadImg(value,num){
						if(value.files && value.files[0]){
							var reader = new FileReader();
							
							reader.onload = function(e){
								switch(num){
								case 1:
									$("#titleImg").attr("src", e.target.result);
									break;
								case 2:
									$("#contentImg1").attr("src",e.target.result);
									break;
								case 3:
									$("#contentImg2").attr("src",e.target.result);
									break;
								case 4:
									$("#contentImg3").attr("src",e.target.result);
									break;
								}
							}
							
							reader.readAsDataURL(value.files[0]);
						}
					}
				</script>
			</div>
			<br>
			<div class="btnArea">
				<button type="reset">취소하기</button>
				<button type="submit">작성완료</button>
				<!-- InsertThumbnailServlet 만들러 ㄱㄱ -->
			</div>
		</form>
	</div>
</body>
</html>