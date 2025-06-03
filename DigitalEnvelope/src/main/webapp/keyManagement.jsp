<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>키 관리</title>
<%@ include file="commonStyle.jsp"%>
</head>
<body>
	<h2>키 관리</h2>

	<p>${keyStatus}</p>

	<form action="keyManagement" method="post">
		<label for="type">사용자 유형 선택:</label> <select name="type" id="type"
			required>
			<option value="admin">관리자 (ADMIN)</option>
			<option value="reporter">보고자 (REPORTER)</option>
		</select> <br> <br>

		<button type="submit" name="action" value="create">키 생성</button>
		<button type="submit" name="action" value="delete">키 삭제</button>
		<br>
		<br>
		<a href="index.jsp">메인 페이지로</a>
	</form>

</body>
</html>
