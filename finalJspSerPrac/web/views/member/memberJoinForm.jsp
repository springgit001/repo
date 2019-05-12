<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE>
<html>
<head>
<meta charset=UTF-8>
<!-- <link rel="stylesheet" type="text/css" href="../../resources/css/memberJoin.css"> -->
<link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/resources/css/memberJoin.css">
<title>Insert title here</title>
</head>
<body>
	<%@ include file="../common/menubar.jsp" %>
	
	<div class="outer">
		<br>
		<h2 align="center">회원가입</h2>
		
		<form id="joinForm" action="<%= request.getContextPath() %>/insert.me" method="post">
			<table>
				<tr>
					<td width="200px">* 아이디</td>
					<td><input type="text" maxlength="13" name="userId" required></td>
					<td width="200px"><div id="idCheck">중복확인</div></td>
				</tr>
				<tr>
					<td>* 비밀번호</td>
					<td><input type="password" maxlength="13" name="userPwd" required></td>
				</tr>
				<tr>
					<td>* 비밀번호 확인</td>
					<td><input type="password" maxlength="13" name="userPwd2" required></td>
					<td><label id="pwdResult"></label></td>
				</tr>
				<tr>
					<td>* 이름</td>
					<td><input type="text" maxlength="5" name="userName" required></td>
				</tr>
				<tr>
					<td>연락처</td>
					<td>
						<input type="tel" maxlength="11" name="phone" placeholder="(-없이)01012345678">
					</td>
				</tr>
				<tr>
					<td>이메일</td>
					<td><input type="email" name="email"></td>
					<td></td>
				</tr>
				<tr>
					<td>주소</td>
					<td><input type="text" name="address"></td>
					<td></td>
				</tr>
				<tr>
					<td>관심분야</td>
					<td>
						<input type="checkbox" id="sports" name="interest" value="운동">
						<label for="sports">운동</label>
						<input type="checkbox" id="climbing" name="interest" value="등산">
						<label for="climbing">등산</label>
						<input type="checkbox" id="fishing" name="interest" value="낚시">
						<label for="fishing">낚시</label>
						<input type="checkbox" id="cooking" name="interest" value="요리">
						<label for="cooking">요리</label>
						<input type="checkbox" id="game" name="interest" value="게임">
						<label for="game">게임</label>
						<input type="checkbox" id="etc" name="interest" value="기타">
						<label for="etc">기타</label>
					</td>
				</tr>
			</table>
			<br>
<!-- <<<<<<<<<<<<<<<<<<<<<<<<< a >>>>>>>>>>>>>>>>>>>>>>>>>>> -->
			<div class="btns" align="center">
				<div id="goMain" onclick="goMain();">메인으로</div>
<!-- 				회원가입하기 -->
<!-- 				<div id="joinBtn" onclick="insertMember();">가입하기</div> -->
<!-- 				위와 같이 하면 required가 작동하지 않는다. -->
				<input id="joinBtn" type=submit value="가입하기">
			</div>
		</form>
	</div>
<!-- <<<<<<<<<<<<<<<<<<<<<<<<< b >>>>>>>>>>>>>>>>>>>>>>>>>>> -->
	<script>
		function goMain(){
			location.href="<%=request.getContextPath()%>/index.jsp";
		}
		
/* 		function insertMember(){
			$("#joinForm").submit();
			//InsertMemberServlet 만들자!!
		} */
		//위 함수는 input submit 버튼으로 바꾸면서 해결
		
		//이 부분은 ajax 배우고 실행!!
		//자 ajax를 잘 배웠으니 이제 적용시켜보자! 저번에 만들어 둔 중복체크라는 버튼을 활용해보자
		
		$(function(){
			//아이디 중복시 false, 아이디 사용가능시 true --> 나중에 유효성 검사에 쓰인다.
			var isUsable = false;
			
			$("#idCheck").click(function(){
				//회원가입 폼의 userId input 부분에 id=userId를 부여할 수 없는 이유는
				//menubar.jsp를 include하고 있는데 이 menubar.jsp에서 id를 userId로 쓰고 있기 때문이다.
				//따라서 다음과 같이 작성하여 회원가입 폼에 작성된 아이디를 받아오자
				var userId = $("#joinForm input[name='userId']");
				
				if(!userId||userId.val().length<4){
					alert("아이디는 최소 4자리 이상이어야 합니다.");
					userId.focus();
				}else{
					$.ajax({
						url:"<%=request.getContextPath()%>/idCheck.me",
						type:"post",
						data:{userId:userId.val()},
						success:function(data){
							if(data=="fail"){
								alert("아이디가 중복됩니다.");
								userId.focus();
							}else{
								alert("사용가능합니다.");
								userId.attr("readonly","true");	//더이상 바꿀 수 없도록
								isUsable=true;	//사용가능하다는 flag값
							}
						},
						error:function(data){
							console.log("서버 통신 안됨");
						}
					});
					//IdCheckServlet 만들러 ㄱㄱ
					
				}
				
				
			});
		});
		
	</script>
</body>
















</html>