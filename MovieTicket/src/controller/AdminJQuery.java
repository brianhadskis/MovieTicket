package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
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
import model.Theatre;
import model.TicketType;

/**
 * Servlet implementation class AdminJQuery
 */
@WebServlet("/AdminJQuery")
public class AdminJQuery extends HttpServlet {
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
    public AdminJQuery() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		// Command - GET_MOVIE_LIST
		try {
			if (request.getParameter("command").equals("GET_RATING_LIST")) {
				for (Movie.Rating rating : Movie.Rating.values()) {
					out.println("<option value='" + rating + "'>" + rating.getRating() + "</option>");
				}
			} else if (request.getParameter("command").equals("GET_THEATRE_LIST")) {
				
				List<Theatre> theatreList = db.getTheatres(0, 0);
				
				for (Theatre theatre : theatreList) {
					out.println("<option value='" + theatre.getId() + "'>" + theatre.getName() + "</option>");
				}
			} else if (request.getParameter("command").equals("GET_MOVIE_LIST")) {
				List<Movie> movieList = db.getMovies(0, 0);
				
				for (Movie movie : movieList) {
					out.println("<option value='" + movie.getId() + "'>" + movie.getTitle() + "</option>");
				}
			} else if (request.getParameter("command").equals("GET_TICKET_TYPE_LIST")) {
				List<TicketType> typeList = db.getTicketTypes(0, 0);
				
				for (TicketType type : typeList) {
					out.println("<option value='" + type.getId() + "'>" + type.getName() + "</option>");
				}
			} else if (request.getParameter("command").equals("GET_RATING_LIST_SELECTED")) {
				System.out.println(request.getParameter("theMovieId"));
				int movieId = Integer.parseInt(request.getParameter("theMovieId"));
				Movie movie = db.getMovie(movieId);
				Movie.Rating selectRating = movie.getRating();
				for (Movie.Rating rating : Movie.Rating.values()) {
					if (rating == movie.getRating()) {
						out.println("<option value='" + rating + "' selected>" + rating.getRating() + "</option>");
					} else {
						out.println("<option value='" + rating + "'>" + rating.getRating() + "</option>");
					}
				}
			} else if (request.getParameter("command").equals("GET_THEATRE_LIST_SELECTED")) {
				System.out.println(request.getParameter("theTheatreId"));
				int theatreId = Integer.parseInt(request.getParameter("theTheatreId"));
				List<Theatre> theatreList = db.getTheatres(0, 0);
				for (Theatre theatre : theatreList) {
					if (theatre.getId() == theatreId) {
						out.println("<option value='" + theatre.getId() + "' selected>" + theatre.getName() + "</option>");
					} else {
						out.println("<option value='" + theatre.getId() + "'>" + theatre.getName() + "</option>");
					}
				}
			} else if (request.getParameter("command").equals("GET_MOVIE_LIST_SELECTED")) {
				System.out.println(request.getParameter("theMovieId"));
				int movieId = Integer.parseInt(request.getParameter("theMovieId"));
				List<Movie> movieList = db.getMovies(0, 0);
				for (Movie movie : movieList) {
					if (movie.getId() == movieId) {
						out.println("<option value='" + movie.getId() + "' selected>" + movie.getTitle() + "</option>");
					} else {
						out.println("<option value='" + movie.getId() + "'>" + movie.getTitle() + "</option>");
					}
				}
			} else if (request.getParameter("command").equals("GET_TICKET_TYPE_LIST_SELECTED")) {
				String ticketTypeId = request.getParameter("theTicketTypeId");
				List<TicketType> typeList = db.getTicketTypes(0, 0);
				
				for (TicketType tType : typeList) {
					if (tType.getId() == ticketTypeId) {
						out.println("<option value='" + tType.getId() + "' selected>" + tType.getName() + "</option>");
					} else {
						out.println("<option value='" + tType.getId() + "'>" + tType.getName() + "</option>");
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
