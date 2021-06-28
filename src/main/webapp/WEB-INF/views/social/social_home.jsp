<%@ page language="java" contentType="text/html; charset=UTF-8" 
pageEncoding="UTF-8"%>
<html>
<head>
	<title>Home</title>
</head>
<body>
<h1>
	카카오 프로파일 
</h1>

<P>  ${user.kakao_account.profile.nickname} 님, 환영합니다. </P>
<img alt="그림없음" src="${user.kakao_account.profile.profile_image_url}">
</body>
</html>
