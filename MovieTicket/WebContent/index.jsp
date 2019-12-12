<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="ISO-8859-1">
		<title>JavaPlex Theatre</title>
		<script src="js/jquery.js"></script>
		<script src="js/boxOffice.js"></script>
		<link type="text/css" rel="stylesheet" href="css/style.css" />
	</head>
	<body id="wrapper">
		<header>
			<h1>JavaPlex Theatre</h1>
		</header>
		<nav>
			<section id="accountInfo">
				<c:choose>
					<c:when test="${sessionScope.email_id != null }">
						Hello, <c:out value="${sessionScope.firstName }"></c:out><br>
						<form action="BoxOffice" method="post">
							<input type="hidden" name="command" value="LOGOUT" />
							<input type="submit" value="Logout" />
						</form>
					</c:when>
					<c:otherwise>
						<a href="login.jsp">Login</a> | <a href="register.jsp">Register</a>
					</c:otherwise>
				</c:choose>
			</section>
		</nav>
		<main>
			<section>
				<form id="movieFinder" action="BoxOffice" method="post">
					<section id="banner">
					</section>
					<div id="movieSelector">
						<input type="hidden" name="command" value="PURCHASE" />
						<input type="text" id="selectMovieTitle" name="selectMovieTitle" list="selectMovieList" placeholder="Select or type movie title" />
						<datalist id="selectMovieList">
						</datalist>
						<select name="selectMovieDate" id="selectMovieDate" disabled>
						</select>
					</div>
					<div id="timeSelector">
					</div>
				</form>
			</section>
			<section id="movieBrowse">
				<div id="browseMenu">
					<a id="browseAll" href="#">Show All</a>
					<a id="browsePlaying" href="#">Now Playing</a>
					<a id="browseComingSoon" href="#">Coming Soon</a>
				</div>
				<div id="browseContent">
				</div>
			</section>
			
		</main>
	</body>
</html>