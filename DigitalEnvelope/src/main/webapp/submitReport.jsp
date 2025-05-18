<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>고발 내용 제출</title>
</head>
<body>
	<h2>✉️ 고발 내용 작성 및 제출</h2>
	<form action="submitReport" method="post">
		제목: <input type="text" name="title" required><br>
		<br> 내용:
		<textarea name="content" rows="10" cols="50" required></textarea>
		<br>
		<br>
		<button type="submit">전자봉투 생성 및 저장</button>
	</form>
</body>
</html>