<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="ISO-8859-1">
		<title>JavaPlex Theatre - Admin</title>
		<script src="js/jquery.js"></script>
		<link type="text/css" rel="stylesheet" href="css/adminStyle.css" />
		<script src="js/adminScript.js"></script>
	</head>
	<body id="wrapper">
		<header>
			<h2>JavaPlex Theatre - Admin</h2>
		</header>
		<nav id="adminNav">
			<c:url var="accountLink" value="AdminController">
				<c:param name="command" value="LIST_ACCOUNTS" />
			</c:url>
			<c:url var="movieLink" value="AdminController">
				<c:param name="command" value="LIST_MOVIES" />
			</c:url>
			<c:url var="scheduleLink" value="AdminController">
				<c:param name="command" value="LIST_SCHEDULES" />
			</c:url>
			<c:url var="theatreLink" value="AdminController">
				<c:param name="command" value="LIST_THEATRES" />
			</c:url>
			<c:url var="ticketTypeLink" value="AdminController">
				<c:param name="command" value="LIST_TICKET_TYPES" />
			</c:url>
			<c:url var="ticketLink" value="AdminController">
				<c:param name="command" value="LIST_TICKETS" />
			</c:url>
			<c:url var="adminTableLink" value="AdminController">
				<c:param name="command" value="LIST_ADMINS" />
			</c:url>
			<a href="${accountLink}">Accounts</a> | 
			<a href="${movieLink}">Movies</a> | 
			<a href="${scheduleLink}">Schedules</a> | 
			<a href="${theatreLink}">Theatres</a> | 
			<a href="${ticketTypeLink}">Ticket Types</a> | 
			<a href="${ticketLink}">Tickets</a> | 
			<a href="${adminTableLink}">Admins</a> | 
			<form action="BoxOffice" method="post">
				<input type="hidden" name="command" value="LOGOUT" />
				<input type="submit" value="Logout" />
			</form>
		</nav>
		<main id="adminMain">
		
			<table>
			<c:choose>
				<c:when test="${not empty ADMIN_MOVIES}">
					<tr>
						<th>Movie ID</th>
						<th>Title</th>
						<th>Description</th>
						<th>Rating</th>
						<th>Length</th>
						<th>Release Date</th>
						<th>Poster Image Location</th>
						<th>Action</th>
					</tr>
					<c:choose>
						<c:when test="${not empty MOVIE_LIST}">
							<c:forEach var="tempMovie" items="${MOVIE_LIST}">
								
								<!-- set up a link for each student -->
								<c:url var="tempLink" value="AdminController">
									<c:param name="command" value="LOAD_MOVIE" />
									<c:param name="movie_id" value="${tempMovie.getId()}" />
								</c:url>
			
								<!--  set up a link to delete a student -->
								<c:url var="deleteLink" value="AdminController">
									<c:param name="command" value="DELETE_MOVIE" />
									<c:param name="movie_id" value="${tempMovie.getId()}" />
								</c:url>
																					
								<tr>
									<td> ${tempMovie.getId()}</td>
									<td> ${tempMovie.getTitle()} </td>
									<td> ${tempMovie.getDescription()} </td>
									<td> ${tempMovie.getRating()} </td>
									<td> ${tempMovie.getLength()} </td>
									<td> ${tempMovie.getReleaseDate()} </td>
									<td> ${tempMovie.getPosterImage()} </td>
									<td> 
										<a href="${tempLink}">Update</a> 
										 | 
										<a href="${deleteLink}"
										onclick="if (!(confirm('Are you sure you want to delete this movie?'))) return false">
										Delete</a>	
									</td>
								</tr>
							
							</c:forEach>
						</c:when>
						<c:otherwise>
							<tr>
								<td colspan="8">No movies found.</td>
							</tr>
						</c:otherwise>
					</c:choose>
					<tr>
						<td>
							<c:url var="nextLink" value="AdminController">
								<c:param name="command" value="LIST_MOVIES" />
								<c:param name="page" value="${page + 1}" />
							</c:url>
		
							<c:url var="prevLink" value="AdminController">
								<c:param name="command" value="LIST_MOVIES" />
								<c:param name="page" value="${page - 1}" />
							</c:url>
							
							<a href="${prevLink}">Prev</a> 
							 | 
							<a href="${nextLink}">Next</a>
							
							<form action="adminAdd.jsp" method="post">
								<input type="hidden" name="ADD_MOVIE" value="stuff" />
								<input type="submit" value="Add Movie" />
							</form>
						</td>
					</tr>
				</c:when>
				<c:when test="${not empty ADMIN_ACCOUNTS}">

					<tr>
						<th>Account ID (email)</th>
						<th>First name</th>
						<th>Last name</th>
						<th>Address</th>
						<th>City</th>
						<th>Postal</th>
						<th>Phone</th>
						<th>Secure Password</th>
						<th>Password Salt</th>
						<th>Date of Birth</th>
						<th>Action</th>
					</tr>
				
					<c:choose>
						<c:when test="${not empty ACCOUNT_LIST}">
							<c:forEach var="tempAccount" items="${ACCOUNT_LIST}">
								
								<!-- set up a link for each student -->
								<c:url var="tempLink" value="AdminController">
									<c:param name="command" value="LOAD_ACCOUNT" />
									<c:param name="account_email" value="${tempAccount.getEmail()}" />
								</c:url>
			
								<!--  set up a link to delete a student -->
								<c:url var="deleteLink" value="AdminController">
									<c:param name="command" value="DELETE_ACCOUNT" />
									<c:param name="account_email" value="${tempAccount.getEmail()}" />
								</c:url>
																					
								<tr>
									<td> ${tempAccount.getEmail()}</td>
									<td> ${tempAccount.getFirstName()} </td>
									<td> ${tempAccount.getLastName()} </td>
									<td> ${tempAccount.getAddress()} </td>
									<td> ${tempAccount.getCity()} </td>
									<td> ${tempAccount.getPostal()} </td>
									<td> ${tempAccount.getPhone()} </td>
									<td> ${tempAccount.getSecurePassword()} </td>
									<td> ${tempAccount.getPasswordSalt()} </td>
									<td> ${tempAccount.getDateOfBirth()} </td>
									<td> 
										<a href="${tempLink}">Update</a> 
										 | 
										<a href="${deleteLink}"
										onclick="if (!(confirm('Are you sure you want to delete this account?'))) return false">
										Delete</a>	
									</td>
								</tr>
							
							</c:forEach>
						</c:when>
						<c:otherwise>
							<tr>
								<td colspan="11">No accounts found.</td>
							</tr>
						</c:otherwise>
					</c:choose>
					<tr>
						<td>
						
						<c:url var="nextLink" value="AdminController">
								<c:param name="command" value="LIST_ACCOUNTS" />
								<c:param name="page" value="${page + 1}" />
							</c:url>
		
							<c:url var="prevLink" value="AdminController">
								<c:param name="command" value="LIST_ACCOUNTS" />
								<c:param name="page" value="${page - 1}" />
							</c:url>
							
							<a href="${prevLink}">Prev</a> 
							 | 
							<a href="${nextLink}">Next</a>
							
							<form action="adminAdd.jsp" method="post">
								<input type="hidden" name="ADD_ACCOUNT" value="stuff" />
								<input type="submit" value="Add Account" />
							</form>
						</td>
					</tr>
				</c:when>
				<c:when test="${not empty ADMIN_SCHEDULES}">
					
			
				<tr>
					<th>Schedule ID</th>
					<th>Theatre</th>
					<th>Movie</th>
					<th>Date</th>
					<th>Time</th>
					<th>Open Seats</th>
					<th>Action</th>
				</tr>
				
				<c:choose>
					<c:when test="${not empty SCHEDULE_LIST}">
						<c:forEach var="tempSchedule" items="${SCHEDULE_LIST}">
							
							<!-- set up a link for each student -->
							<c:url var="tempLink" value="AdminController">
								<c:param name="command" value="LOAD_SCHEDULE" />
								<c:param name="schedule_id" value="${tempSchedule.getId()}" />
							</c:url>
		
							<!--  set up a link to delete a student -->
							<c:url var="deleteLink" value="AdminController">
								<c:param name="command" value="DELETE_SCHEDULE" />
								<c:param name="schedule_id" value="${tempSchedule.getId()}" />
							</c:url>
																				
							<tr>
								<td> ${tempSchedule.getId()}</td>
								<td> ${tempSchedule.getTheatre().getName()} </td>
								<td> ${tempSchedule.getMovie().getTitle()} </td>
								<td> ${tempSchedule.getMovieDate()} </td>
								<td> ${tempSchedule.getMovieTime()} </td>
								<td> ${tempSchedule.getSeats()} </td>
								<td> 
									<a href="${tempLink}">Update</a> 
									 | 
									<a href="${deleteLink}"
									onclick="if (!(confirm('Are you sure you want to delete this schedule?'))) return false">
									Delete</a>	
								</td>
							</tr>
						
						</c:forEach>
					</c:when>
					<c:otherwise>
							<tr>
								<td colspan="7">No schedules found.</td>
							</tr>
						</c:otherwise>
					</c:choose>
					<tr>
						<td>
							<c:url var="nextLink" value="AdminController">
								<c:param name="command" value="LIST_SCHEDULES" />
								<c:param name="page" value="${page + 1}" />
							</c:url>
		
							<c:url var="prevLink" value="AdminController">
								<c:param name="command" value="LIST_SCHEDULES" />
								<c:param name="page" value="${page - 1}" />
							</c:url>
							
							<a href="${prevLink}">Prev</a> 
							 | 
							<a href="${nextLink}">Next</a>
						
							<form action="adminAdd.jsp" method="post">
								<input type="hidden" name="ADD_SCHEDULE" value="stuff" />
								<input type="submit" value="Add Schedule" />
							</form>
						</td>
					</tr>
				</c:when>

				<c:when test="${not empty ADMIN_THEATRES}">
					
			
				<tr>
					<th>Theatre ID</th>
					<th>Theatre Name</th>
					<th>Action</th>
				</tr>
				
				<c:choose>
					<c:when test="${not empty THEATRE_LIST}">
						<c:forEach var="tempTheatre" items="${THEATRE_LIST}">
							
							<!-- set up a link for each student -->
							<c:url var="tempLink" value="AdminController">
								<c:param name="command" value="LOAD_THEATRE" />
								<c:param name="theatre_id" value="${tempTheatre.getId()}" />
							</c:url>
		
							<!--  set up a link to delete a student -->
							<c:url var="deleteLink" value="AdminController">
								<c:param name="command" value="DELETE_THEATRE" />
								<c:param name="theatre_id" value="${tempTheatre.getId()}" />
							</c:url>
																				
							<tr>
								<td> ${tempTheatre.getId()} </td>
								<td> ${tempTheatre.getName()} </td>
								<td> 
									<a href="${tempLink}">Update</a> 
									 | 
									<a href="${deleteLink}"
									onclick="if (!(confirm('Are you sure you want to delete this theatre?'))) return false">
									Delete</a>	
								</td>
							</tr>
						
						</c:forEach>
					</c:when>
					<c:otherwise>
						<tr>
							<td colspan="4">No theatres found.</td>
						</tr>
					</c:otherwise>
				</c:choose>
					<tr>
						<td>
							<c:url var="nextLink" value="AdminController">
								<c:param name="command" value="LIST_THEATRES" />
								<c:param name="page" value="${page + 1}" />
							</c:url>
		
							<c:url var="prevLink" value="AdminController">
								<c:param name="command" value="LIST_THEATRES" />
								<c:param name="page" value="${page - 1}" />
							</c:url>
							
							<a href="${prevLink}">Prev</a> 
							 | 
							<a href="${nextLink}">Next</a>
						
							<form action="adminAdd.jsp" method="post">
								<input type="hidden" name="ADD_THEATRE" value="stuff" />
								<input type="submit" value="Add Theatre" />
							</form>
						</td>
					</tr>
				</c:when>
				<c:when test="${not empty ADMIN_TICKET_TYPES}">
					
			
				<tr>
					<th>Ticket Type ID</th>
					<th>Ticket Type Name</th>
					<th>Price</th>
					<th>Action</th>
				</tr>
				
				<c:choose>
					<c:when test="${not empty TICKET_TYPE_LIST}">
						<c:forEach var="tempTicketType" items="${TICKET_TYPE_LIST}">
							
							<!-- set up a link for each student -->
							<c:url var="tempLink" value="AdminController">
								<c:param name="command" value="LOAD_TICKET_TYPE" />
								<c:param name="ticketType_id" value="${tempTicketType.getId()}" />
							</c:url>
		
							<!--  set up a link to delete a student -->
							<c:url var="deleteLink" value="AdminController">
								<c:param name="command" value="DELETE_TICKET_TYPE" />
								<c:param name="ticketType_id" value="${tempTicketType.getId()}" />
							</c:url>
																				
							<tr>
								<td> ${tempTicketType.getId()}</td>
								<td> ${tempTicketType.getName()} </td>
								<td> ${tempTicketType.getPrice()} </td>
								<td> 
									<a href="${tempLink}">Update</a> 
									 | 
									<a href="${deleteLink}"
									onclick="if (!(confirm('Are you sure you want to delete this ticket type?'))) return false">
									Delete</a>	
								</td>
							</tr>
						
						</c:forEach>
					</c:when>
					<c:otherwise>
						<tr>
							<td colspan="4">No ticket types found.</td>
						</tr>
					</c:otherwise>
				</c:choose>
				<tr>
						<td>
						
						<c:url var="nextLink" value="AdminController">
								<c:param name="command" value="LIST_TICKET_TYPES" />
								<c:param name="page" value="${page + 1}" />
							</c:url>
		
							<c:url var="prevLink" value="AdminController">
								<c:param name="command" value="LIST_TICKET_TYPES" />
								<c:param name="page" value="${page - 1}" />
							</c:url>
							
							<a href="${prevLink}">Prev</a> 
							 | 
							<a href="${nextLink}">Next</a>
							<form action="adminAdd.jsp" method="post">
								<input type="hidden" name="ADD_TICKET_TYPE" value="stuff" />
								<input type="submit" value="Add Ticket Type" />
							</form>
						</td>
					</tr>
				</c:when>
				<c:when test="${not empty ADMIN_TICKETS}">
					
			
				<tr>
					<th>Ticket ID</th>
					<th>User email</th>
					<th>Schedule ID</th>
					<th>Ticket Type ID</th>
					<th>Action</th>
				</tr>
					<c:choose>
						<c:when test="${not empty TICKET_LIST}">
							<c:forEach var="tempTicket" items="${TICKET_LIST}">
								
								<!-- set up a link for each student -->
								<c:url var="tempLink" value="AdminController">
									<c:param name="command" value="LOAD_TICKET" />
									<c:param name="ticket_id" value="${tempTicket.getId()}" />
								</c:url>
			
								<!--  set up a link to delete a student -->
								<c:url var="deleteLink" value="AdminController">
									<c:param name="command" value="DELETE_TICKET" />
									<c:param name="ticket_id" value="${tempTicket.getId()}" />
								</c:url>
																					
								<tr>
									<td> ${tempTicket.getId()}</td>
									<td> ${tempTicket.getEmail()} </td>
									<td> ${tempTicket.getSchedule().getId()} </td>
									<td> ${tempTicket.getTicketType().getId()} </td>
									<td> 
										<a href="${tempLink}">Update</a> 
										 | 
										<a href="${deleteLink}"
										onclick="if (!(confirm('Are you sure you want to delete this ticket?'))) return false">
										Delete</a>	
									</td>
								</tr>
							
							</c:forEach>
						</c:when>
						<c:otherwise>
							<tr>
								<td colspan="5">No tickets found.</td>
							</tr>
						</c:otherwise>
					</c:choose>
					<tr>
						<td>
							<c:url var="nextLink" value="AdminController">
								<c:param name="command" value="LIST_TICKETS" />
								<c:param name="page" value="${page + 1}" />
							</c:url>
		
							<c:url var="prevLink" value="AdminController">
								<c:param name="command" value="LIST_TICKETS" />
								<c:param name="page" value="${page - 1}" />
							</c:url>
							
							<a href="${prevLink}">Prev</a> 
							 | 
							<a href="${nextLink}">Next</a>
							<form action="adminAdd.jsp" method="post">
								<input type="hidden" name="ADD_TICKET" value="stuff" />
								<input type="submit" value="Add Ticket" />
							</form>
						</td>
					</tr>
				</c:when>
				<c:when test="${not empty ADMIN_ADMINS}">
					
			
				<tr>
					<th>Username</th>
					<th>Secure Password</th>
					<th>Password Salt</th>
					<th>email</th>
					<th>Action</th>
				</tr>
					<c:choose>
						<c:when test="${not empty ADMIN_LIST}">
							<c:forEach var="tempAdmin" items="${ADMIN_LIST}">
								
								<!-- set up a link for each student -->
								<c:url var="tempLink" value="AdminController">
									<c:param name="command" value="LOAD_ADMIN" />
									<c:param name="admin_username" value="${tempAdmin.getUsername()}" />
								</c:url>
			
								<!--  set up a link to delete a student -->
								<c:url var="deleteLink" value="AdminController">
									<c:param name="command" value="DELETE_ADMIN" />
									<c:param name="admin_username" value="${tempAdmin.getUsername()}" />
								</c:url>
																					
								<tr>
									<td> ${tempAdmin.getUsername()}</td>
									<td> ${tempAdmin.getSecurePassword()} </td>
									<td> ${tempAdmin.getPasswordSalt()} </td>
									<td> ${tempAdmin.getEmail()} </td>
									
									<td> 
										<a href="${tempLink}">Update</a> 
										 | 
										<a href="${deleteLink}"
										onclick="if (!(confirm('Are you sure you want to delete this admin?'))) return false">
										Delete</a>	
									</td>
								</tr>
							
							</c:forEach>
						</c:when>
						<c:otherwise>
							<tr>
								<td colspan="5">No admins found.</td>
							</tr>
						</c:otherwise>
					</c:choose>
					<tr>
						<td>
						<c:url var="nextLink" value="AdminController">
								<c:param name="command" value="LIST_ADMINS" />
								<c:param name="page" value="${page + 1}" />
							</c:url>
		
							<c:url var="prevLink" value="AdminController">
								<c:param name="command" value="LIST_ADMINS" />
								<c:param name="page" value="${page - 1}" />
							</c:url>
							
							<a href="${prevLink}">Prev</a> 
							 | 
							<a href="${nextLink}">Next</a>
							<form action="adminAdd.jsp" method="post">
								<input type="hidden" name="ADD_ADMIN" value="stuff" />
								<input type="submit" value="Add Admin" />
							</form>
							
						</td>
					</tr>
				</c:when>
			</c:choose>
			</table>
		</main>
	</body>
</html>