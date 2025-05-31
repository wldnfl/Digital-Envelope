<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<html>
<head>
<title>신고 상태 조회</title>
<%@ include file="commonStyle.jsp"%>
</head>
<body>
	<h2>신고 상태 조회</h2>

	<form action="lookupStatus" method="post">
		<label for="uniqueCode">고유코드 입력:</label><br /> <input type="text"
			id="uniqueCode" name="uniqueCode" required /><br /> <br /> <input
			type="submit" value="조회" />
	</form>

</body>
</html>

<%--신고 상태 조회 폼--%>