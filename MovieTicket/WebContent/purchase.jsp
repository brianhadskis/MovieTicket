<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="ISO-8859-1">
		<title>Ticket Purchase</title>
	</head>
	<body>
		<form action="BoxOffice" method="post">
			
			<p>Movie: ${MOVIE_SCHEDULE.movie.title}</p>
			<p>Theatre: ${MOVIE_SCHEDULE.theatre.name}</p>
			<p>Date: ${MOVIE_SCHEDULE.movieDate}</p>
			<p>Time: ${MOVIE_SCHEDULE.movieTime}</p>
			
			<input type="hidden" name="command" value="CONFIRM" />
			
			<table>
				<tbody>
				<c:choose>
					<c:when test="${ not empty sessionScope.email_id}">
						<tr>
							<td><label>Email:</label></td>
							<td><input type="text" name="email" value="${sessionScope.email_id}" /></td>
						</tr>
					</c:when>
					<c:otherwise>
						<tr>
							<td><label>Email:</label></td>
							<td><input type="text" name="email" /></td>
						</tr>
					</c:otherwise>
				</c:choose>
				<c:choose>
					<c:when test="${not empty IS_TUESDAY }">
						<tr>
							<td><label>Tuesday:</label></td>
							<td><input type="number" name="tuesday" /></td>
							<td>Price:</td>
							<td>${TUESDAY_TICKET_PRICE}</td>
						</tr>
					</c:when>
					<c:otherwise>
						<tr>
							<td><label>General:</label></td>
							<td><input type="number" name="general" /></td>
							<td>Price:</td>
							<td>${GENERAL_TICKET_PRICE}</td>
						</tr>
	
						<tr>
							<td><label>Child:</label></td>
							<td><input type="number" name="child" /></td>
							<td>Price:</td>
							<td>${CHILD_TICKET_PRICE}</td>
						</tr>
					</c:otherwise>
					</c:choose>
					
					<tr>
						<c:set var="SCHEDULE_ID" value="${MOVIE_SCHEDULE.id}" scope="session" />
						<td><label></label></td>
						<td><input type="submit" value="Continue" /></td>
					</tr>
				</tbody>
			</table>
		</form>
	</body>
</html>