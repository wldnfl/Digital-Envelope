<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>보고 내용 작성</title>
<%@ include file="commonStyle.jsp"%>
</head>
<body>
	<h2>보고 내용 작성하기</h2>

	<%
	String error = (String) request.getAttribute("error");
	if (error != null) {
	%>
	<p style="color: red;"><%=error%></p>
	<%
	}
	%>

	<form action="reportWrite" method="post">
		<label for="reportContent">보고 내용:</label><br />
		<textarea id="reportContent" name="reportContent" rows="6" cols="50"
			required></textarea>
		<br /> <br /> <input type="submit" value="보고 접수 및 전자봉투 생성" />
	</form>

</body>
</html>
