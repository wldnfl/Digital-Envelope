<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="java.util.Collection"%>
<%@ page import="model.Report"%>

<html>
<head>
<title>관리자 상태 테스트 페이지</title>
<style>
table {
	border-collapse: collapse;
	width: 100%;
}

th, td {
	border: 1px solid #999;
	padding: 8px;
	text-align: left;
}

th {
	background-color: #eee;
}
</style>
</head>
<body>
	<h2>관리자 상태 테스트</h2>

	<%
	Collection<Report> reports = (Collection<Report>) request.getAttribute("reports");
	if (reports != null && !reports.isEmpty()) {
	%>
	<p>
		총 신고 건수:
		<%=reports.size()%></p>
	<hr>
	<table>
		<thead>
			<tr>
				<th>고유 코드</th>
				<th>상태</th>
				<th>신고 내용 요약</th>
			</tr>
		</thead>
		<tbody>
			<%
			for (Report report : reports) {
				String summary = (report.getReportContent() != null && report.getReportContent().length() > 20)
				? report.getReportContent().substring(0, 20) + "..."
				: report.getReportContent();

				// boolean isVerified 로 상태 표시
				String displayStatus = report.isVerified() ? "검증 완료" : "검증 전";
			%>
			<tr>
				<td><%=report.getUniqueCode()%></td>
				<td><%=displayStatus%></td>
				<td><%=summary != null ? summary : "내용 없음"%></td>
			</tr>
			<%
			} // for end
			%>
		</tbody>
	</table>
	<%
	} else {
	%>
	<p>신고가 없습니다.</p>
	<%
	}
	%>
</body>
</html>
