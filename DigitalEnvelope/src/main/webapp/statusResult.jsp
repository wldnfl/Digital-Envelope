<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%
String reportStatus = (String) request.getAttribute("reportStatus");
String message = (String) request.getAttribute("message");
%>
<html>
<head>
<title>신고 상태 결과</title>
</head>
<body>
	<h2>신고 상태 결과</h2>

	<%
	if (reportStatus != null) {
	%>
	<p>
		신고 상태: <strong><%=reportStatus%></strong>
	</p>
	<%
	} else {
	%>
	<p><%=message != null ? message : "조회 결과가 없습니다."%></p>
	<%
	}
	%>

	<a href="statusLookup.jsp">다시 조회하기</a>
</body>
</html>

<%--신고 상태 결과 표시--%>