<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
	<title>Home</title>
</head>
<body>

<form method="post" action="/pro1/login">
 ID : <input type="text" name="c_id" /> <br>
 PASS : <input type="text" name="c_password" /><br>
	<input type="submit" value="go" />
</form>
<br>
SE : ${se }
</body>
</html>
