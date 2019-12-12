<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="ISO-8859-1">
		<title>JavaPlex Theatre</title>
	</head>
	<body>
		<header>
			<h1>JavaPlex Theatre</h1>
		</header>
		<nav>
			
		</nav>
		<main>
			<section id="searchBar">
				<form action="BoxOffice" method="post">
					<input type="hidden" name="command" value="BROWSE" />
					<input name="searchMovieTitle" type="text" placeholder="Search for movie title" />
					<select name="searchDates">
						<option disabled selected value>Select date</option>
						<c:forEach var="tempDateList" items="${SEARCH_DATE_LIST}">
							<option value="${tempDateList}">${tempDateList}</option>
						</c:forEach>
					</select>
					<input type="submit" value="Search" />
				</form>
			</section>
			<section id="movieList">
				<c:choose>
					<c:when test="${not empty movie_list}">
						<c:forEach var="tempMovie" items="${movie_list}">
							<div class="movieBrowse">
							
								<!-- set up a link for each movie -->
								<c:url var="tempLink" value="BoxOffice">
									<c:param name="command" value="DETAIL" />
									<c:param name="movie_id" value="${tempMovie.id}" />
								</c:url>
								
								<a href="${tempLink}">
									<img src="images/movie_${tempMovie.id}}" />
									${tempMovie.title}
								</a>
							</div>
						</c:forEach>	
					</c:when>
					<c:when test="${not empty movie}">
						<div class="movieDetail">
							<c:out value="${movie.id}" /><br>
							<c:out value="${movie.title}" /><br>
							<c:out value="${movie.description}" /><br>
							<c:out value="${movie.rating}" /><br>
							<c:out value="${movie.length}" /><br>
							<c:out value="${movie.releaseDate}" /><br>
						</div>
						<div class="movieTimes">
							<c:if test="${not empty MOVIE_TIMES}">
								<c:forEach var="movieTimes" items="${MOVIE_TIMES}">
									<c:out value="${movieTimes}" /><br>
								</c:forEach>
							</c:if>
						</div>
					</c:when>
					<c:otherwise>
						Sorry, we are unable to find any movies matching your search.
					</c:otherwise>
				</c:choose>
			</section>
		</main>
	</body>
</html>