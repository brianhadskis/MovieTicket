package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import dbUtil.TheatreDBUtil;
import model.Movie;
import model.Schedule;

/**
 * Servlet implementation class JQueryHandler
 */
@WebServlet("/JQueryHandler")
public class JQueryHandler extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	TheatreDBUtil db;
	
	@Resource(name = "jdbc/prog32758")
	private DataSource dataSource;
	
	public void init() throws ServletException {
    	super.init();
    	
    	try {
    		db = new TheatreDBUtil(dataSource);
    	} catch (Exception e) {
    		throw new ServletException(e);
    	}
    }
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public JQueryHandler() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// Command - GET_MOVIE_LIST
		if (request.getParameter("command").equals("GET_MOVIE_LIST")) {
			try {
				//List<String> test = db.getColumnData("movies");
				String movieTitle = (String)request.getParameter("movieTitle");
				List<Movie> movieList = db.getMovies(movieTitle);
				for (Movie movie : movieList) {
					PrintWriter out = response.getWriter();
					out.println("<option value='" + movie.getTitle() + "'></option>");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		// Command - GET_MOVIE_DATES
		else if (request.getParameter("command").equals("GET_MOVIE_DATES")) {
			try {
				String movieTitle = (String)request.getParameter("movieTitle");
				List<Movie> movieList = db.getMovies(movieTitle);
				Movie movie = movieList.get(0);
				List<LocalDate> dateList = db.getMovieDates(movie);
				PrintWriter out = response.getWriter();
				
				out.println("<option selected disabled>Select date</option>");
				
				for (LocalDate date : dateList) {
					System.out.println(date);
					out.println("<option value='" + date + "'>" + date + "</option>");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		// Command - GET_MOVIE_TIMES
		else if (request.getParameter("command").equals("GET_MOVIE_TIMES")) {
			try {
				String movieTitle = (String)request.getParameter("movieTitle");
				List<Movie> movieList = db.getMovies(movieTitle);
				Movie movie = movieList.get(0);
				LocalDate date = LocalDate.parse(request.getParameter("movieDate"));
				
				List<Schedule> scheduleList = db.getSchedules(movie, date);
				
				PrintWriter out = response.getWriter();

				for (Schedule schedule : scheduleList) {
					String disableButton = "";
					if (schedule.getMovieTime().isBefore(LocalTime.now()) && schedule.getMovieDate().equals(LocalDate.now())) {
						disableButton = " disabled";
					}
					out.println("<button class='movieTime' name='movieTime' type='submit' value='"
							+ schedule.getId()  + "'" + disableButton + ">"
							+ schedule.getMovieTime().toString() + "</button>");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		// Command - GET_BROWSE
		else if (request.getParameter("command").equals("GET_BROWSE")) {
			try {
				String filter = request.getParameter("filter");
				List<Movie> movieList = db.getMoviesByRelease(filter);
				
				PrintWriter out = response.getWriter();
				
				for (Movie movie : movieList) {
					out.println("<div class='browse' name='" + movie.getTitle() + "'>");
					out.println("<img src='" + movie.getPosterImage() + "' />");
					out.println("<label>" + movie.getTitle() + "</label>");
					out.println("</div>");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		else if (request.getParameter("command").equals("TEST")) {
			try {
			List<Movie> movies = db.getMoviesByRelease("ALL");
			
			request.setAttribute("TEST_MOVIES", movies);
			
			PrintWriter out = response.getWriter();
			
			out.println("<form action='#' method='post'>");
			out.println("<input type='hidden' name='command' value='OTHER' />");
			out.println("<input id='testButton' type='submit' value='To Other' />");
			out.println("</form>");
			
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		else if (request.getParameter("command").equals("OTHER")) {
			try {
			List<Movie> movies = db.getMoviesByRelease("ALL");
			
			request.setAttribute("TEST_MOVIES", movies);
			
			PrintWriter out = response.getWriter();
			
			out.println("<form id='testButton' action='#' method='post'>");
			out.println("<input type='hidden' name='command' value='TEST' />");
			out.println("<input type='submit' value='To Test' />");
			out.println("</form>");
			
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else if (request.getAttribute("command").equals("OTHER")) {
			try {
				List<Movie> movies = db.getMoviesByRelease("ALL");
				
				request.setAttribute("TEST_MOVIES", movies);
				
				PrintWriter out = response.getWriter();
				
				out.println("<form id='testButton' action='#' method='post'>");
				out.println("<input type='hidden' name='command' value='TEST' />");
				out.println("<input type='submit' value='To Test' />");
				out.println("</form>");
				
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
