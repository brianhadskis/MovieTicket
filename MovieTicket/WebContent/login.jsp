<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<form action="BoxOffice" method="post">
		<input type="hidden" name="command" value="LOGIN" />
		<label>Email:</label>
		<input name="email" type="text" /><br>
		<label>Password:</label>
		<input name="password" type="password" /><br>
		<input type="submit" value="Login" />
	</form>
</body>
</html>