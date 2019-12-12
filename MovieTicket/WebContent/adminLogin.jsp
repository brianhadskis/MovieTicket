<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>JavaPlex Theatre Admin Login</title>
</head>
<body>
<form action="AdminController" method="post">
		<input type="hidden" name="command" value="LOGIN" />
		<label>Username:</label>
		<input name="username" type="text" /><br>
		<label>Password:</label>
		<input name="password" type="password" /><br>
		<input type="submit" value="Login" />
	</form>
</body>
</html>