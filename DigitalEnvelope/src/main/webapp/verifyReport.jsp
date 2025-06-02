<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%
String verificationResult = (String) request.getAttribute("verificationResult");
String reportContent = (String) request.getAttribute("reportContent");
String reportStatus = (String) request.getAttribute("reportStatus");
%>
<html>
<head>
<title>전자봉투 상세</title>
<%@ include file="commonStyle.jsp"%>
</head>
<body>
	<h2>전자봉투 상세 및 검증 결과</h2>

	<p>
		<strong>검증 결과:</strong>
		<%=verificationResult != null ? verificationResult : "결과 없음"%>
	</p>

	<p>
		<strong>보고 내용:</strong><br>
		<%=reportContent != null ? reportContent : "내용 없음"%>
	<p>
		<strong>보고 상태:</strong> <span
			class="<%="검증 완료".equals(reportStatus) ? "status-verified" : "status-unverified"%>">
			<%=reportStatus != null ? reportStatus : "상태 정보 없음"%>
		</span>
	</p>

	<a href="envelopeInput.jsp">다른 전자봉투 검증하기</a> |
	<a href="adminReportList">관리자 보고 내역 페이지</a> |
	<a href="index.jsp">메인 페이지로</a>
</body>
</html>


<%--전자봉투 상세 및 검증 결과--%>