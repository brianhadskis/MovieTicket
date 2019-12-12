<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="ISO-8859-1">
		<title>JavaPlex Theatre - Admin</title>
		<link type="text/css" rel="stylesheet" href="css/adminStyle.css" />
		<link type="text/css" rel="stylesheet" href="css/add-student-style.css">
		<script src="js/jquery.js"></script>
		<script src="js/adminScript.js"></script>
	</head>
	<body>
	
	</body id="wrapper">
		<header>
			<h2>JavaPlex Theatre - Update Record</h2>
		</header>
		<nav id="adminNav">
			<a href="AdminController">Go Back</a>
		</nav>
		<form action="AdminController" method="post">
			<c:choose>
				<c:when test="${not empty LOADED_MOVIE}">
					<input type="hidden" name="command" value="UPDATE_MOVIE" />
					<input type="hidden" id="movieId" name="movieId" value="${LOADED_MOVIE.id }" />
			
					<table>
						<tbody>
							<tr>
								<td><label>Title:</label></td>
								<td><input type="text" name="title" value="${LOADED_MOVIE.title}" /></td>
							</tr>
		
							<tr>
								<td><label>Description:</label></td>
								<td><input type="text" name="description" value="${LOADED_MOVIE.description}" /></td>
							</tr>
		
							<tr>
								<td><label>Rating:</label></td>
								<td>
									<select id="update_movie_rating" name="rating">
										
									</select>
								</td>
							</tr>
							
							<tr>
								<td><label>Length (minutes):</label></td>
								<td><input type="number" name="length" value="${LOADED_MOVIE.length}" /></td>
							</tr>
							
							<tr>
								<td><label>Release Date:</label></td>
								<td><input type="date" name="releaseDate" value="${LOADED_MOVIE.releaseDate}" /></td>
							</tr>
							
							<tr>
								<td><label>Poster Image URL:</label></td>
								<td><input type="text" name="posterImage" value="${LOADED_MOVIE.posterImage}" /></td>
							</tr>
							
							<tr>
								<td><label></label></td>
								<td><input type="submit" value="Save" class="save" /></td>
							</tr>
							
						</tbody>
					</table>
				</c:when>
				
				<c:when test="${not empty LOADED_ACCOUNT}">
				<input type="hidden" name="command" value="UPDATE_ACCOUNT" />
				<input type="hidden" id="oldEmail" name="oldEmail" value="${LOADED_ACCOUNT.email }" />
			
					<table>
						<tbody>
							<tr>
								<td><label>Email:</label></td>
								<td><input type="text" name="email" value="${LOADED_ACCOUNT.email}" /></td>
							</tr>
							
							<tr>
								<td><label>Password:</label></td>
								<td><input type="password" name="password"  /></td>
							</tr>
							
							<tr>
								<td><label>Verify password:</label></td>
								<td><input type="password" name="verify" /></td>
							</tr>
		
							<tr>
								<td><label>First name:</label></td>
								<td><input type="text" name="firstName" value="${LOADED_ACCOUNT.firstName}" /></td>
							</tr>
							
							<tr>
								<td><label>Last name:</label></td>
								<td><input type="text" name="lastName" value="${LOADED_ACCOUNT.lastName}" /></td>
							</tr>
							
							<tr>
								<td><label>Address:</label></td>
								<td><input type="text" name="address" value="${LOADED_ACCOUNT.address}" /></td>
							</tr>
							
							<tr>
								<td><label>City:</label></td>
								<td><input type="text" name="city" value="${LOADED_ACCOUNT.city}" /></td>
							</tr>
							
							<tr>
								<td><label>Postal Code:</label></td>
								<td><input type="text" name="postal" value="${LOADED_ACCOUNT.postal}" /></td>
							</tr>
							
							<tr>
								<td><label>Phone number:</label></td>
								<td><input type="text" name="phone" value="${LOADED_ACCOUNT.phone}" /></td>
							</tr>
							
							<tr>
								<td><label>Date of Birth:</label></td>
								<td><input type="date" name="dateOfBirth" value="${LOADED_ACCOUNT.dateOfBirth}" /></td>
							</tr>
		
							
							
							<tr>
								<td><label></label></td>
								<td><input type="submit" value="Save" class="save" /></td>
							</tr>
							
						</tbody>
					</table>
				</c:when>
				
				<c:when test="${not empty LOADED_SCHEDULE}">
				<input type="hidden" name="command" value="UPDATE_SCHEDULE" />
				<input type="hidden" id="scheduleId" name="scheduleId" value="${LOADED_SCHEDULE.id }" />
				<input type="hidden" id="theatreId" name="theatreId" value="${LOADED_SCHEDULE_THEATRE_ID }" />
				<input type="hidden" id="movieId" name="movieId" value="${LOADED_SCHEDULE_MOVIE_ID }" />
			
					<table>
						<tbody>
							<tr>
								<td><label>Theatre:</label></td>
								<td>
									<select id="update_schedule_theatre" name="theatre">
										
									</select>
								</td>
							</tr>
							
							<tr>
								<td><label>Movie:</label></td>
								<td>
									<select id="update_schedule_movie" name="movie">
										
									</select>
								</td>
							</tr>
							
							<tr>
								<td><label>Date:</label></td>
								<td><input type="date" name="date" value="${LOADED_SCHEDULE.movieDate}" /></td>
							</tr>
		
							<tr>
								<td><label>Time:</label></td>
								<td><input type="time" name="time" value="${LOADED_SCHEDULE.movieTime}" /></td>
							</tr>
							
							<tr>
								<td><label>Seats:</label></td>
								<td><input type="number" name="seats" value="${LOADED_SCHEDULE.seats}"/></td>
							</tr>
							
							
							<tr>
								<td><label></label></td>
								<td><input type="submit" value="Save" class="save" /></td>
							</tr>
							
						</tbody>
					</table>
				</c:when>
				
				<c:when test="${not empty LOADED_THEATRE}">
				<input type="hidden" name="command" value="UPDATE_THEATRE" />
				<input type="hidden" id="theatreId" name="theatreId" value="${LOADED_THEATRE.id }" />
			
					<table>
						<tbody>
							<tr>
								<td><label>Theatre name:</label></td>
								<td><input type="text" name="name" value="${LOADED_THEATRE.name}" /></td>
							</tr>
							
							<tr>
								<td><label></label></td>
								<td><input type="submit" value="Save" class="save" /></td>
							</tr>
							
						</tbody>
					</table>
				</c:when>
				
				<c:when test="${not empty LOADED_TICKET_TYPE}">
				<input type="hidden" name="command" value="UPDATE_TICKET_TYPE" />
				<input type="hidden" id="ticketTypeId" name="ticketTypeId" value="${LOADED_TICKET_TYPE.id }" />
			
					<table>
						<tbody>
							<tr>
								<td><label>Ticket Type ID:</label></td>
								<td><input type="text" name="typeID" value="${LOADED_TICKET_TYPE.id}" /></td>
							</tr>
							
							<tr>
								<td><label>Ticket Type Name:</label></td>
								<td><input type="text" name="typeName" value="${LOADED_TICKET_TYPE.name}" /></td>
							</tr>
							
							<tr>
								<td><label>Price:</label></td>
								<td><input type="number" name="price" value="${LOADED_TICKET_TYPE.price}" /></td>
							</tr>
		
							<tr>
								<td><label></label></td>
								<td><input type="submit" value="Save" class="save" /></td>
							</tr>
							
						</tbody>
					</table>
				</c:when>
				
				<c:when test="${not empty LOADED_TICKET}">
				<input type="hidden" name="command" value="UPDATE_TICKET" />
				<input type="hidden" id="ticketId" name="ticketId" value="${LOADED_TICKET.id }" />
			
					<table>
						<tbody>
							<tr>
								<td><label>Email:</label></td>
								<td><input type="text" name="email" value="${LOADED_TICKET.email}" /></td>
							</tr>
							
							<tr>
								<td><label>Schedule ID:</label></td>
								<td><input type="number" name="scheduleID" value="${LOADED_TICKET_SCHEDULE_ID}" /></td>
							</tr>
							
							<tr>
								<td><label>Ticket Type:</label></td>
								<td>
									<select id="update_ticket_ticketType" name="ticketType">
										
									</select>
								</td>
							</tr>
		
							<tr>
								<td><label></label></td>
								<td><input type="submit" value="Save" class="save" /></td>
							</tr>
							
						</tbody>
					</table>
				</c:when>
				
				<c:when test="${not empty LOADED_ADMIN}">
				<input type="hidden" name="command" value="UPDATE_ADMIN" />
				<input type="hidden" id="adminUsername" name="adminUsername" value="${LOADED_ADMIN.username }" />
			
					<table>
						<tbody>
							<tr>
								<td><label>Username:</label></td>
								<td><input type="text" name="username" value="${LOADED_ADMIN.username}" /></td>
							</tr>
							
							<tr>
								<td><label>Password:</label></td>
								<td><input type="password" name="password" /></td>
							</tr>
							
							<tr>
								<td><label>Verify password:</label></td>
								<td><input type="password" name="verify" /></td>
							</tr>
							
							<tr>
								<td><label>email:</label></td>
								<td><input type="text" name="email" value="${LOADED_TICKET.email}" /></td>
							</tr>
		
							<tr>
								<td><label></label></td>
								<td><input type="submit" value="Save" class="save" /></td>
							</tr>
							
						</tbody>
					</table>
				</c:when>
			
			
			
			
			</c:choose>
		</form>
</html>