<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="java.util.Collection"%>
<%@ page import="model.Report"%>

<html>
<head>
<title>관리자 보고 내역 페이지</title>
<%@ include file="commonStyle.jsp"%>
</head>
<body>
	<h2>보고 내역</h2>

	<%
	Collection<Report> reports = (Collection<Report>) request.getAttribute("reports");
	if (reports != null && !reports.isEmpty()) {
	%>
	<p>
		총 보고 건수:
		<%=reports.size()%></p>
	<hr>
	<table>
		<thead>
			<tr>
				<th>고유 코드</th>
				<th>상태</th>
				<th>보고 내용 요약</th>
			</tr>
		</thead>
		<tbody>
			<%
			for (Report report : reports) {
				String summary = (report.getReportContent() != null && report.getReportContent().length() > 20)
				? report.getReportContent().substring(0, 20) + "..."
				: report.getReportContent();

				String displayStatus = report.isVerified() ? "검증 완료" : "검증 전";
			%>
			<tr>
				<td><%=report.getUniqueCode()%></td>
				<td
					class="<%=report.isVerified() ? "status-verified" : "status-unverified"%>">
					<%=displayStatus%>
				</td>
				<td><%=summary != null ? summary : "내용 없음"%></td>
			</tr>
			<%
			}
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
