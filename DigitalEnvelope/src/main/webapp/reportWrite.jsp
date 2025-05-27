<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>내부고발 작성</title>
</head>
<body>
	<h2>📨 내부 고발 신고</h2>
	<form action="reportWrite" method="post">
		<label>제목: <input type="text" name="title" required></label><br>
		<br> <label>내용:<br> <textarea name="content"
				rows="10" cols="50" required></textarea>
		</label><br> <br>
		<button type="submit">신고 제출 (전자봉투 생성)</button>
	</form>
</body>
</html>