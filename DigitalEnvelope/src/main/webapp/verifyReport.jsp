<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>전자봉투 검증</title>
</head>
<body>
	<h2>✅ 전자봉투 검증</h2>
	<form action="verifyReport" method="post">
		<label>전자봉투 파일 경로:</label><br> <input type="text"
			name="envelopePath" placeholder="/path/to/envelope.bin" required><br>
		<br>
		<button type="submit">검증 시작</button>
	</form>
</body>
</html>