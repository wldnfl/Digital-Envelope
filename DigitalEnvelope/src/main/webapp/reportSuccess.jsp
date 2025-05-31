<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%
String uniqueCode = (String) request.getAttribute("uniqueCode");
%>
<html>
<head>
<title>신고 접수 완료</title>
</head>
<body>
	<h2>신고가 정상 접수되었습니다.</h2>

	<p>고유코드는 다음과 같습니다. 꼭 보관하세요:</p>
	<h3><%=uniqueCode != null ? uniqueCode : "코드가 없습니다."%></h3>

	<a href="statusLookup.jsp">신고 상태 조회하러 가기</a>
</body>
</html>

<%--신고 접수 완료 및 고유코드 안내--%>