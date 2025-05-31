<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<html>
<head>
<title>신고 작성</title>
</head>
<body>
	<h2>신고 작성하기</h2>

	<form action="reportWrite" method="post">
		<label for="reportContent">신고 내용:</label><br />
		<textarea id="reportContent" name="reportContent" rows="6" cols="50"
			required></textarea>
		<br /> <br /> <input type="submit" value="신고 접수 및 전자봉투 생성" />
	</form>

</body>
</html>

<%-- 신고 작성 및 전자봉투 생성 --%>