<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<title>신고 목록</title>
</head>
<body>
	<h2>📋 신고 목록</h2>
	<table border="1">
		<thead>
			<tr>
				<th>전자봉투 ID</th>
				<th>제목</th>
				<th>작성일자</th>
				<th>검증</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="report" items="${reportList}">
				<tr>
					<td>${report.id}</td>
					<td>${report.title}</td>
					<td>${report.date}</td>
					<td>
						<form action="reportVerify" method="get">
							<input type="hidden" name="envelopeId" value="${report.id}">
							<button type="submit">검증</button>
						</form>
					</td>
				</tr>
			</c:forEach>

		</tbody>

	</table>
</body>
</html>