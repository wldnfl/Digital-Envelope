<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%
String verificationResult = (String) request.getAttribute("verificationResult");
String reportContent = (String) request.getAttribute("reportContent");
String reportStatus = (String) request.getAttribute("reportStatus");
%>
<html>
<head>
<title>전자봉투 상세</title>
</head>
<body>
	<h2>전자봉투 상세 및 검증 결과</h2>

	<p>
		<strong>검증 결과:</strong>
		<%=verificationResult != null ? verificationResult : "결과 없음"%></p>

	<p>
		<strong>신고 내용:</strong>
	</p>
	<p style="white-space: pre-wrap;"><%=reportContent != null ? reportContent : "내용 없음"%></p>

	<p>
		<strong>신고 상태:</strong>
		<%=reportStatus != null ? reportStatus : "상태 정보 없음"%></p>

	<a href="envelopeInput.jsp">다른 전자봉투 검증하기</a> |
	<a href="adminStatusTest">관리자 신고 상태 테스트</a> |
	<a href="index.jsp">메인 페이지로</a>

</body>
</html>

<%--전자봉투 상세 및 검증 결과--%>