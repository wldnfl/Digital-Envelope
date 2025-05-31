<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<html>
<head>
<title>전자봉투 열람</title>
<%@ include file="commonStyle.jsp"%>
</head>
<body>
	<h2>전자봉투 열람 및 검증</h2>

	<form action="verifyReport" method="post">
		<label for="uniqueCode">고유코드 입력:</label><br /> <input type="text"
			id="uniqueCode" name="uniqueCode" required /><br /> <br /> <input
			type="submit" value="전자봉투 열기 및 검증" />
	</form>

</body>
</html>

<%--고유코드 입력 및 전자봉투 열람--%>