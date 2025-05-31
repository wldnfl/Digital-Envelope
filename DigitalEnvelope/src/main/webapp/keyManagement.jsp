<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>키 관리</title>
</head>
<body>
	<h1>키 관리</h1>
	<p>${keyStatus}</p>

	<form method="post" action="keyManagement">
		<button type="submit" name="action" value="create">새 키 생성하기</button>
		<button type="submit" name="action" value="delete">키 삭제하기</button>
	</form>
	<br>
	<a href="index.jsp">메인 페이지로</a>

</body>
</html>
