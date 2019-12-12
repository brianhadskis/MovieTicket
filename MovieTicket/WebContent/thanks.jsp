<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="ISO-8859-1">
		<title>Enjoy the movie!</title>
	</head>
	<body>
		<form action="BoxOffice" method="post">
			
			<p>Movie: ${MOVIE_SCHEDULE.movie.title}</p>
			<p>Theatre: ${MOVIE_SCHEDULE.theatre.name}</p>
			<p>Date: ${MOVIE_SCHEDULE.movieDate}</p>
			<p>Time: ${MOVIE_SCHEDULE.movieTime}</p>
			
			<table>
				<tbody>
				
					<c:if test="${NUM_TUESDAY > 0 }">
						<tr>
							<td><label>Tuesday:</label></td>
							<td>${NUM_TUESDAY }</td>
							<td>Price:</td>
							<td>${TOTAL_TUESDAY}</td>
						</tr>
					</c:if>
					<c:if test="${NUM_GENERAL > 0 }">
						<tr>
							<td><label>General:</label></td>
							<td>${NUM_GENERAL }</td>
							<td>Price:</td>
							<td>${TOTAL_GENERAL}</td>
						</tr>
					</c:if>
					<c:if test="${NUM_CHILD > 0 }">
						<tr>
							<td><label>Child:</label></td>
							<td>${NUM_CHILD }</td>
							<td>Price:</td>
							<td>${TOTAL_CHILD}</td>
						</tr>
					</c:if>
					<tr>
						<td>Ticket Total:</td>
						<td>${TOTAL_PRICE }</td>
					</tr>
					
					<tr>
						<td><label></label></td>
						<td></td>
					</tr>
				</tbody>
			</table>
		</form>
	</body>
</html>