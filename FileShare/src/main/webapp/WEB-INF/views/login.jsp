<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Login</title>

</head>
<body>
State: ${state }<br>
SE : ${se }<br>
ID : ${ID }<br>
<br>
<!-- file -->
<form id="files-upload" enctype="multipart/form-data" action="/pro1/login/fileadd" method="post">
	<input type="file" name="file">
	<input type="submit" value="send">
</form>
<br>

<c:forEach var="list" items="${fList}">
fileName : <a href="<c:url value='/download/${list }/file/' />" > ${list }</a><br>
</c:forEach>
<br>



<!-- share -->
<form method="post" action="/pro1/login/shared/">
	<input type="submit" value="share" >
</form>
<form method="post" action="/pro1/login/cancel/">
	<input type="submit" value="cancel" >
</form>


<!-- logout -->
<form action="/pro1/logout" method="post">
	<input type="submit" value="logout">
</form>
<br>
-----------------------------------------------------------------------
<form action="/pro1/login/receive" method="post">
	<input type="text" name="receiveId">
	<input type="submit" value="receive">
</form>
<form action="/pro1/login/cutoff" method="post">
	<input type="submit" value="cutoff">
</form>
<br>
shared ID : ${receiveid }
<br>
<c:forEach var="list" items="${receiveFile}">
fileName : <a href="<c:url value='/download/${receiveid }/${list }/file/' />" > ${list }</a><br>
</c:forEach>
<br>

</body>
</html>