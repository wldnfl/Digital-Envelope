<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%
String uniqueCode = (String) request.getAttribute("uniqueCode");
String currentStatus = (String) request.getAttribute("currentStatus");
%>
<html>
<head>
<title>신고 상태 변경</title>
</head>
<body>
	<h2>신고 상태 변경</h2>

	<form action="updateStatus" method="post">
		<input type="hidden" name="uniqueCode"
			value="<%=uniqueCode != null ? uniqueCode : ""%>" /> <label
			for="newStatus">새 상태 선택:</label><br /> <select id="newStatus"
			name="newStatus" required>
			<option value="">-- 상태 선택 --</option>
			<option value="처리중"
				<%="처리중".equals(currentStatus) ? "selected" : ""%>>처리중</option>
			<option value="완료"
				<%="완료".equals(currentStatus) ? "selected" : ""%>>완료</option>
			<option value="보류"
				<%="보류".equals(currentStatus) ? "selected" : ""%>>보류</option>
		</select><br />
		<br /> <input type="submit" value="상태 변경" />
	</form>

	<a href="envelope_detail.jsp?uniqueCode=<%=uniqueCode%>">상세보기로
		돌아가기</a>
</body>
</html>

<%--신고 상태 변경 --%>
