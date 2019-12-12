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
			<h2>JavaPlex Theatre - Add Record</h2>
		</header>
		<nav id="adminNav">
			<a href="AdminController">Go Back</a>
		</nav>
		<form action="AdminController" method="post">
			<c:choose>
				<c:when test="${not empty param.ADD_MOVIE}">
					<input type="hidden" name="command" value="ADD_MOVIE" />
			
					<table>
						<tbody>
							<tr>
								<td><label>Title:</label></td>
								<td><input type="text" name="title" /></td>
							</tr>
		
							<tr>
								<td><label>Description:</label></td>
								<td><input type="text" name="description" /></td>
							</tr>
		
							<tr>
								<td><label>Rating:</label></td>
								<td>
									<select id="add_movie_rating" name="rating">
										
									</select>
								</td>
							</tr>
							
							<tr>
								<td><label>Length (minutes):</label></td>
								<td><input type="number" name="length" /></td>
							</tr>
							
							<tr>
								<td><label>Release Date:</label></td>
								<td><input type="date" name="releaseDate" /></td>
							</tr>
							
							<tr>
								<td><label>Poster Image URL:</label></td>
								<td><input type="text" name="posterImage" /></td>
							</tr>
							
							<tr>
								<td><label></label></td>
								<td><input type="submit" value="Save" class="save" /></td>
							</tr>
							
						</tbody>
					</table>
				</c:when>
				
				<c:when test="${not empty param.ADD_ACCOUNT}">
				<input type="hidden" name="command" value="ADD_ACCOUNT" />
			
					<table>
						<tbody>
							<tr>
								<td><label>Email:</label></td>
								<td><input type="text" name="email" /></td>
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
								<td><label>First name:</label></td>
								<td><input type="text" name="firstName" /></td>
							</tr>
							
							<tr>
								<td><label>Last name:</label></td>
								<td><input type="text" name="lastName" /></td>
							</tr>
							
							<tr>
								<td><label>Address:</label></td>
								<td><input type="text" name="address" /></td>
							</tr>
							
							<tr>
								<td><label>City:</label></td>
								<td><input type="text" name="city" /></td>
							</tr>
							
							<tr>
								<td><label>Postal Code:</label></td>
								<td><input type="text" name="postal" /></td>
							</tr>
							
							<tr>
								<td><label>Phone number:</label></td>
								<td><input type="text" name="phone" /></td>
							</tr>
							
							<tr>
								<td><label>Date of Birth:</label></td>
								<td><input type="date" name="dateOfBirth" /></td>
							</tr>
		
							
							
							<tr>
								<td><label></label></td>
								<td><input type="submit" value="Save" class="save" /></td>
							</tr>
							
						</tbody>
					</table>
				</c:when>
				
				<c:when test="${not empty param.ADD_SCHEDULE}">
				<input type="hidden" name="command" value="ADD_SCHEDULE" />
			
					<table>
						<tbody>
							<tr>
								<td><label>Theatre:</label></td>
								<td>
									<select id="add_schedule_theatre" name="theatre">
										<c:forEach var="theatre" items="${THEATRE_LIST }">
											<option value="${theatre.getId()}">${theatre.getName() }</option>
										</c:forEach>
									</select>
								</td>
							</tr>
							
							<tr>
								<td><label>Movie:</label></td>
								<td>
									<select id="add_schedule_movie" name="movie">
										<c:forEach var="movie" items="${MOVIE_LIST }">
											<option value="${movie.getId()}">${movie.getName() }</option>
										</c:forEach>
									</select>
								</td>
							</tr>
							
							<tr>
								<td><label>Date:</label></td>
								<td><input type="date" name="date" /></td>
							</tr>
		
							<tr>
								<td><label>Time:</label></td>
								<td><input type="time" name="time" /></td>
							</tr>
							
							<tr>
								<td><label>Seats:</label></td>
								<td><input type="number" name="seats" /></td>
							</tr>
							
							
							<tr>
								<td><label></label></td>
								<td><input type="submit" value="Save" class="save" /></td>
							</tr>
							
						</tbody>
					</table>
				</c:when>
				
				<c:when test="${not empty param.ADD_THEATRE}">
				<input type="hidden" name="command" value="ADD_THEATRE" />
			
					<table>
						<tbody>
							<tr>
								<td><label>Theatre name:</label></td>
								<td><input type="text" name="name" /></td>
							</tr>
							
							<tr>
								<td><label></label></td>
								<td><input type="submit" value="Save" class="save" /></td>
							</tr>
							
						</tbody>
					</table>
				</c:when>
				
				<c:when test="${not empty ADD_TICKET_TYPE}">
				<input type="hidden" name="command" value="ADD_TICKET_TYPE" />
			
					<table>
						<tbody>
							<tr>
								<td><label>Ticket Type ID:</label></td>
								<td><input type="text" name="typeID" /></td>
							</tr>
							
							<tr>
								<td><label>Ticket Type Name:</label></td>
								<td><input type="text" name="typeName" /></td>
							</tr>
							
							<tr>
								<td><label>Price:</label></td>
								<td><input type="number" name="price" /></td>
							</tr>
		
							<tr>
								<td><label></label></td>
								<td><input type="submit" value="Save" class="save" /></td>
							</tr>
							
						</tbody>
					</table>
				</c:when>
				
				<c:when test="${not empty param.ADD_TICKET}">
				<input type="hidden" name="command" value="ADD_TICKET" />
			
					<table>
						<tbody>
							<tr>
								<td><label>Email:</label></td>
								<td><input type="text" name="email" /></td>
							</tr>
							
							<tr>
								<td><label>Schedule ID:</label></td>
								<td><input type="number" name="scheduleID" /></td>
							</tr>
							
							<tr>
								<td><label>Ticket Type:</label></td>
								<td>
									<select id="add_ticket_ticketType" name="ticketType">
										
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
				
				<c:when test="${not empty param.ADD_ADMIN}">
				<input type="hidden" name="command" value="ADD_ADMIN" />
			
					<table>
						<tbody>
							<tr>
								<td><label>Username:</label></td>
								<td><input type="text" name="username" /></td>
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
								<td><input type="text" name="email" /></td>
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