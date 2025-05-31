<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<html>
<head>
<title>내부 보고 시스템</title>
<%@ include file="commonStyle.jsp"%>
</head>
<body>
	<h1>내부 보고 시스템</h1>

	<h2>사용자 메뉴</h2>
	<ul>
		<li><a href="reportWrite.jsp">보고 작성 및 전자봉투 생성</a></li>
		<li><a href="statusLookup.jsp">보고 상태 조회</a></li>
	</ul>

	<h2>관리자 메뉴</h2>
	<ul>
		<li><a href="envelopeInput.jsp">전자봉투 열람 및 검증</a></li>
		<li><a href="keyManagement">키 관리 페이지</a></li>
	</ul>
</body>
</html>
