<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>신고 문서 검증</title>
</head>
<body>
	<h2>✅ 전자봉투 검증 결과</h2>
	<p>
		<strong>제목:</strong> ${title}
	</p>
	<p>
		<strong>내용:</strong><br> ${content}
	</p>
	<p>
		<strong>전자서명 및 무결성 확인:</strong> <span
			style="color: ${verified ? 'green' : 'red'}"> ${verified ? '검증 성공' : '검증 실패'}
		</span>
	</p>
</body>
</html>