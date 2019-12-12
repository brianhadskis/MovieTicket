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
		<input type="hidden" name="command" value="REGISTER" />
		<label>First name:</label>
		<input name="firstName" type="text" /><br>
		<label>Last name:</label>
		<input name="lastName" type="text" /><br>
		<label>Address:</label>
		<input name="address" type="text" /><br>
		<label>City:</label>
		<input name="city" type="text" /><br>
		<label>Postal:</label>
		<input name="postal" type="text" /><br>
		<label>Phone:</label>
		<input name="phone" type="text" /><br>
		<label>Email:</label>
		<input name="email" type="text" /><br>
		<label>Password:</label>
		<input name="password" type="text" /><br>
		<label>Verify password:</label>
		<input name="verify" type="text" /><br>
		<label>Date of Birth:</label>
		<input name="dateOfBirth" type="date" /><br>
		<input type="submit" value="Register" />
	</form>
</body>
</html>