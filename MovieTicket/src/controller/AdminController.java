package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import dbUtil.TheatreDBUtil;
import model.Account;
import model.Admin;
import model.Movie;
import model.Schedule;
import model.Theatre;
import model.Ticket;
import model.TicketType;
import myUtil.PasswordUtils;

/**
 * Servlet implementation class AdminController
 */
@WebServlet("/AdminController")
public class AdminController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	private TheatreDBUtil theatreDBUtil;
	
	@Resource(name = "jdbc/prog32758")
	private DataSource dataSource;
    
    public void init() throws ServletException {
    	super.init();
    	
    	try {	
    		theatreDBUtil = new TheatreDBUtil(dataSource);
    	} catch (Exception e) {
    		throw new ServletException(e);
    	}
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			// Read the command parameter
			String command = request.getParameter("command");
			//HttpSession session = request.getSession();
			
			// Set command to unused command to trigger default case and send to login
			if (command == null) {
				command = "HELLO";
			}
			
			switch (command) {
			case "LIST_MOVIES":
				listMovies(request, response);
				break;
			case "LIST_ACCOUNTS":
				listAccounts(request, response);
				break;
			case "LIST_SCHEDULES":
				listSchedules(request, response);
				break;
			case "LIST_THEATRES":
				listTheatres(request, response);
				break;
			case "LIST_TICKET_TYPES":
				listTicketTypes(request, response);
				break;
			case "LIST_TICKETS":
				listTickets(request, response);
				break;
			case "LIST_ADMINS":
				listAdmins(request, response);
				break;
			case "ADD_MOVIE":
				addMovie(request, response);
				break;
			case "ADD_ACCOUNT":
				addAccount(request, response);
				break;
			case "ADD_SCHEDULE":
				addSchedule(request, response);
				break;
			case "ADD_THEATRE":
				addTheatre(request, response);
				break;
			case "ADD_TICKET_TYPE":
				addTicketType(request, response);
				break;
			case "ADD_TICKET":
				addTicket(request, response);
				break;
			case "ADD_ADMIN":
				addAdmin(request, response);
				break;
				
			case "LOAD_MOVIE":
				loadMovie(request, response);
				break;
			case "LOAD_ACCOUNT":
				loadAccount(request, response);
				break;
			case "LOAD_SCHEDULE":
				loadSchedule(request, response);
				break;
			case "LOAD_THEATRE":
				loadTheatre(request, response);
				break;
			case "LOAD_TICKET_TYPE":
				loadTicketType(request, response);
				break;
			case "LOAD_TICKET":
				loadTicket(request, response);
				break;
			case "LOAD_ADMIN":
				loadAdmin(request, response);
				break;
				
			case "UPDATE_MOVIE":
				updateMovie(request, response);
				break;
			case "UPDATE_ACCOUNT":
				updateAccount(request, response);
				break;
			case "UPDATE_SCHEDULE":
				updateSchedule(request, response);
				break;
			case "UPDATE_THEATRE":
				updateTheatre(request, response);
				break;
			case "UPDATE_TICKET_TYPE":
				updateTicketType(request, response);
				break;
			case "UPDATE_TICKET":
				updateTicket(request, response);
				break;
			case "UPDATE_ADMIN":
				updateAdmin(request, response);
				break;
			
			case "DELETE_MOVIE":
				deleteMovie(request, response);
				break;
			case "DELETE_ACCOUNT":
				deleteAccount(request, response);
				break;
			case "DELETE_SCHEDULE":
				deleteSchedule(request, response);
				break;
			case "DELETE_THEATRE":
				deleteTheatre(request, response);
				break;
			case "DELETE_TICKET_TYPE":
				deleteTicketType(request, response);
				break;
			case "DELETE_TICKET":
				deleteTicket(request, response);
				break;
			case "DELETE_ADMIN":
				deleteAdmin(request, response);
				break;
				
			case "LOGIN":
				doLogin(request, response);
				break;
			case "LOGOUT":
				doLogout(request, response);
				break;
			default:
				response.sendRedirect("adminLogin.jsp");
			}
		} catch (ServletException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	private void listMovies(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
		// get movies from database
		int page;
		if (request.getParameter("page") != null) {
			page = Integer.parseInt(request.getParameter("page"));
			if (page < 1) {
				page = 1;
			}
		} else {
			page = 1;
		}
		
		List<Movie> movies = theatreDBUtil.getMovies(page, 20);
		
		request.setAttribute("MOVIE_LIST", movies);
		request.setAttribute("ADMIN_MOVIES", true);
		request.setAttribute("page", page);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/admin.jsp");
		dispatcher.forward(request, response);
		
	}
	
	private void listAccounts(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
		// get accounts from database
		int page;
		if (request.getParameter("page") != null) {
			page = Integer.parseInt(request.getParameter("page"));
			if (page < 1) {
				page = 1;
			}
		} else {
			page = 1;
		}
		
		List<Account> accounts = theatreDBUtil.getAccounts(page, 20);
		request.setAttribute("ACCOUNT_LIST", accounts);
		request.setAttribute("ADMIN_ACCOUNTS", true);
		request.setAttribute("page", page);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/admin.jsp");
		dispatcher.forward(request, response);
		
	}
	
	private void listSchedules(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
		// get schedules from database
		int page;
		if (request.getParameter("page") != null) {
			page = Integer.parseInt(request.getParameter("page"));
			if (page < 1) {
				page = 1;
			}
		} else {
			page = 1;
		}
		
		List<Schedule> schedules = theatreDBUtil.getSchedules(page, 20);
		request.setAttribute("SCHEDULE_LIST", schedules);
		request.setAttribute("ADMIN_SCHEDULES", true);
		request.setAttribute("page", page);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/admin.jsp");
		dispatcher.forward(request, response);
	}
	
	private void listTheatres(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
		int page;
		if (request.getParameter("page") != null) {
			page = Integer.parseInt(request.getParameter("page"));
			if (page < 1) {
				page = 1;
			}
		} else {
			page = 1;
		}
		
		List<Theatre> theatres = theatreDBUtil.getTheatres(page, 20);
		request.setAttribute("THEATRE_LIST", theatres);
		request.setAttribute("ADMIN_THEATRES", true);
		request.setAttribute("page", page);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/admin.jsp");
		dispatcher.forward(request, response);
	}
	
	private void listTicketTypes(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
		int page;
		if (request.getParameter("page") != null) {
			page = Integer.parseInt(request.getParameter("page"));
			if (page < 1) {
				page = 1;
			}
		} else {
			page = 1;
		}
		
		List<TicketType> ticketTypes = theatreDBUtil.getTicketTypes(page, 20);
		request.setAttribute("TICKET_TYPE_LIST", ticketTypes);
		request.setAttribute("ADMIN_TICKET_TYPES", true);
		request.setAttribute("page", page);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/admin.jsp");
		dispatcher.forward(request, response);
	}
	
	private void listTickets(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
		int page;
		if (request.getParameter("page") != null) {
			page = Integer.parseInt(request.getParameter("page"));
			if (page < 1) {
				page = 1;
			}
		} else {
			page = 1;
		}
		
		List<Ticket> tickets = theatreDBUtil.getTickets(page, 20);
		request.setAttribute("TICKET_LIST", tickets);
		request.setAttribute("ADMIN_TICKETS", true);
		request.setAttribute("page", page);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/admin.jsp");
		dispatcher.forward(request, response);
	}
	
	private void listAdmins(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
		int page;
		if (request.getParameter("page") != null) {
			page = Integer.parseInt(request.getParameter("page"));
			if (page < 1) {
				page = 1;
			}
		} else {
			page = 1;
		}
		
		List<Admin> admins = theatreDBUtil.getAdminAccounts(page, 20);
		request.setAttribute("ADMIN_LIST", admins);
		request.setAttribute("ADMIN_ADMINS", true);
		request.setAttribute("page", page);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/admin.jsp");
		dispatcher.forward(request, response);
	}
	
	private void addMovie (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
		// read data from form
		String title = request.getParameter("title");
		String description = request.getParameter("description");
		Movie.Rating rating = Movie.Rating.valueOf(request.getParameter("rating"));
		int length = Integer.parseInt(request.getParameter("length"));
		LocalDate releaseDate = LocalDate.parse(request.getParameter("releaseDate"));
		String posterImage = request.getParameter("posterImage");
		
		Movie movie = new Movie(0, title, description, rating, length, releaseDate, posterImage);
		
		theatreDBUtil.addMovie(movie);
		
		listMovies(request, response);
	}
	
	private void addAccount (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
		// read data from form
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String verify = request.getParameter("verify");
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String address = request.getParameter("address");
		String city = request.getParameter("city");
		String postal = request.getParameter("postal");
		String phone = request.getParameter("phone");
		LocalDate dateOfBirth = LocalDate.parse(request.getParameter("dateOfBirth"));
		
		if (password != verify) {
			response.sendRedirect("/adminAdd.jsp");
		}
		
		String salt = PasswordUtils.getSalt(30);
		
		String securePassword = PasswordUtils.generateSecurePassword(password, salt);
		
		Account account = new Account(email, firstName, lastName, address, city, postal, phone, securePassword, salt, dateOfBirth);
		
		theatreDBUtil.addAccount(account);
		
		listAccounts(request, response);
	}
	
	private void addSchedule(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
		// read data from form
		int theatreId = Integer.parseInt(request.getParameter("theatre"));
		int movieId = Integer.parseInt(request.getParameter("movie"));
		LocalDate date = LocalDate.parse(request.getParameter("date"));
		LocalTime time = LocalTime.parse(request.getParameter("time"));
		int seats = Integer.parseInt(request.getParameter("seats"));
		
		Theatre theatre = theatreDBUtil.getTheatre(theatreId);
		Movie movie = theatreDBUtil.getMovie(movieId);
		
		Schedule schedule = new Schedule(0, movie, theatre, date, time, seats);
		
		theatreDBUtil.addSchedule(schedule);
		
		listSchedules(request, response);
	}
	
	private void addTheatre(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
		// read data from form
		String theatreName = request.getParameter("name");
		
		Theatre theatre = new Theatre(0, theatreName);
		
		theatreDBUtil.addTheatre(theatre);
		
		listTheatres(request, response);
	}
	
	private void addTicketType(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
		// read data from form
		String ticketTypeId = request.getParameter("typeID");
		String ticketTypeName = request.getParameter("typeName");
		float price = Float.parseFloat(request.getParameter("price"));
		
		TicketType ticketType = new TicketType(ticketTypeId, ticketTypeName, price);
		
		theatreDBUtil.addTicketType(ticketType);
		
		listTicketTypes(request, response);
	}
	
	private void addTicket(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
		// read data from form
		int scheduleId = Integer.parseInt(request.getParameter("scheduleID"));
		String ticketTypeId = request.getParameter("ticketType");
		String email = request.getParameter("email");
		
		Schedule schedule = theatreDBUtil.getSchedule(scheduleId);
		TicketType ticketType = theatreDBUtil.getTicketType(ticketTypeId);
		
		Ticket ticket = new Ticket(0, schedule, ticketType, email);
		
		theatreDBUtil.addTicket(ticket);
		
		listTickets(request, response);
	}
	
	private void addAdmin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
		// read data from form
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String verify = request.getParameter("verify");
		String email = request.getParameter("email");
		
		if (password != verify) {
			response.sendRedirect("/adminAdd.jsp");
		}
		
		String salt = PasswordUtils.getSalt(30);
		
		String securePassword = PasswordUtils.generateSecurePassword(password, salt);
		
		Admin admin = new Admin(username, securePassword, salt, email);
		
		theatreDBUtil.addAdminAccount(admin);
		
		listAdmins(request, response);
	}
	
	private void loadMovie(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
		// read student info from form data
		int movieId = Integer.parseInt(request.getParameter("movie_id"));
		
		// get student from the database
		Movie movie = theatreDBUtil.getMovie(movieId);
		
		// place student in the request attribute
		request.setAttribute("LOADED_MOVIE", movie);
		request.setAttribute("MOVIE_ID", movieId);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/adminUpdate.jsp");
		dispatcher.forward(request, response);
		
	}
	
	private void loadAccount(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
		// read student info from form data
		String email = request.getParameter("account_email");
		
		// get student from the database
		Account account = theatreDBUtil.getAccount(email);
		
		// place student in the request attribute
		request.setAttribute("LOADED_ACCOUNT", account);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/adminUpdate.jsp");
		dispatcher.forward(request, response);
		
	}
	
	private void loadSchedule(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
		// read student info from form data
		int scheduleId = Integer.parseInt(request.getParameter("schedule_id"));
		
		// get student from the database
		Schedule schedule = theatreDBUtil.getSchedule(scheduleId);
		
		// place student in the request attribute
		request.setAttribute("LOADED_SCHEDULE", schedule);
		request.setAttribute("LOADED_SCHEDULE_THEATRE_ID", schedule.getTheatre().getId());
		request.setAttribute("LOADED_SCHEDULE_MOVIE_ID", schedule.getMovie().getId());
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/adminUpdate.jsp");
		dispatcher.forward(request, response);
		
	}
	
	private void loadTheatre(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
		// read student info from form data
		int theatreId = Integer.parseInt(request.getParameter("theatre_id"));
		
		// get student from the database
		Theatre theatre = theatreDBUtil.getTheatre(theatreId);
		
		// place student in the request attribute
		request.setAttribute("LOADED_THEATRE", theatre);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/adminUpdate.jsp");
		dispatcher.forward(request, response);
		
	}
	
	private void loadTicketType(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
		// read student info from form data
		String typeId = request.getParameter("ticketType_id");
		
		// get student from the database
		TicketType ticketType = theatreDBUtil.getTicketType(typeId);
		
		// place student in the request attribute
		request.setAttribute("LOADED_TICKET_TYPE", ticketType);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/adminUpdate.jsp");
		dispatcher.forward(request, response);
		
	}
	
	private void loadTicket(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
		// read student info from form data
		int ticketId = Integer.parseInt(request.getParameter("ticket_id"));
		
		// get student from the database
		Ticket ticket = theatreDBUtil.getTicket(ticketId);
		int scheduleId = ticket.getSchedule().getId();
		
		// place student in the request attribute
		request.setAttribute("LOADED_TICKET", ticket);
		request.setAttribute("LOADED_TICKET_SCHEDULE_ID", scheduleId);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/adminUpdate.jsp");
		dispatcher.forward(request, response);
		
	}
	
	private void loadAdmin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
		// read student info from form data
		String username = request.getParameter("admin_username");
		
		// get student from the database
		Admin admin = theatreDBUtil.getAdminAccount(username);
		
		// place student in the request attribute
		request.setAttribute("LOADED_ADMIN", admin);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/adminUpdate.jsp");
		dispatcher.forward(request, response);
		
	}
	
	private void updateMovie(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
		
		int movieId = Integer.parseInt(request.getParameter("movieId"));
		String title = request.getParameter("title");
		String description = request.getParameter("description");
		Movie.Rating rating = Movie.Rating.valueOf(request.getParameter("rating"));
		int length = Integer.parseInt(request.getParameter("length"));
		LocalDate releaseDate = LocalDate.parse(request.getParameter("releaseDate"));
		String posterImage = request.getParameter("posterImage");
		
		Movie movie = new Movie(movieId, title, description, rating, length, releaseDate, posterImage);
		
		theatreDBUtil.updateMovie(movie);
		
		listMovies(request, response);
		
	}
	
	private void updateAccount (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
		// read data from form
		String oldEmail = request.getParameter("oldEmail");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String verify = request.getParameter("verify");
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String address = request.getParameter("address");
		String city = request.getParameter("city");
		String postal = request.getParameter("postal");
		String phone = request.getParameter("phone");
		LocalDate dateOfBirth = LocalDate.parse(request.getParameter("dateOfBirth"));
		
		if (password != verify) {
			loadAccount(request, response);
		}
		
		String salt = PasswordUtils.getSalt(30);
		
		String securePassword = PasswordUtils.generateSecurePassword(password, salt);
		
		Account account = new Account(email, firstName, lastName, address, city, postal, phone, securePassword, salt, dateOfBirth);
		
		theatreDBUtil.updateAccount(account);
		theatreDBUtil.updateTicketAccounts(email, oldEmail);
		
		listAccounts(request, response);
	}
	
	private void updateSchedule(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
		// read data from form
		int scheduleId = Integer.parseInt(request.getParameter("scheduleId"));
		int theatreId = Integer.parseInt(request.getParameter("theatre"));
		int movieId = Integer.parseInt(request.getParameter("movie"));
		LocalDate date = LocalDate.parse(request.getParameter("date"));
		LocalTime time = LocalTime.parse(request.getParameter("time"));
		int seats = Integer.parseInt(request.getParameter("seats"));
		
		Theatre theatre = theatreDBUtil.getTheatre(theatreId);
		Movie movie = theatreDBUtil.getMovie(movieId);
		
		Schedule schedule = new Schedule(scheduleId, movie, theatre, date, time, seats);
		
		theatreDBUtil.updateSchedule(schedule);
		
		listSchedules(request, response);
	}
	
	private void updateTheatre(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
		// read data from form
		int theatreId = Integer.parseInt(request.getParameter("theatreId"));
		String theatreName = request.getParameter("name");
		
		Theatre theatre = new Theatre(0, theatreName);
		
		theatreDBUtil.updateTheatre(theatre);
		
		listTheatres(request, response);
	}
	
	private void updateTicketType(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
		// read data from form
		String oldTypeId = request.getParameter("ticketTypeId");
		String ticketTypeId = request.getParameter("typeID");
		String ticketTypeName = request.getParameter("typeName");
		float price = Float.parseFloat(request.getParameter("price"));
		
		TicketType ticketType = new TicketType(ticketTypeId, ticketTypeName, price);
		
		theatreDBUtil.updateTicketType(ticketType, oldTypeId);
		
		listTicketTypes(request, response);
	}
	
	private void updateTicket(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
		// read data from form
		int ticketId = Integer.parseInt(request.getParameter("ticketId"));
		int scheduleId = Integer.parseInt(request.getParameter("scheduleID"));
		String ticketTypeId = request.getParameter("ticketType");
		String email = request.getParameter("email");
		
		Schedule schedule = theatreDBUtil.getSchedule(scheduleId);
		TicketType ticketType = theatreDBUtil.getTicketType(ticketTypeId);
		
		Ticket ticket = new Ticket(ticketId, schedule, ticketType, email);
		
		theatreDBUtil.updateTicket(ticket);
		
		listTicketTypes(request, response);
	}
	
	private void updateAdmin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
		// read data from form
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String verify = request.getParameter("verify");
		String email = request.getParameter("email");
		
		if (password != verify) {
			loadAdmin(request, response);
		}
		
		String salt = PasswordUtils.getSalt(30);
		
		String securePassword = PasswordUtils.generateSecurePassword(password, salt);
		
		Admin admin = new Admin(username, securePassword, salt, email);
		
		theatreDBUtil.updateAdminAccount(admin);
		
		listTicketTypes(request, response);
	}
	
	public void deleteAdmin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
		
		String username = request.getParameter("admin_username");
		
		theatreDBUtil.deleteAdminAccount(username);
		
		listAdmins(request, response);
	}
	
	public void deleteAccount(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
		
		String emailId = request.getParameter("account_email");
		
		theatreDBUtil.deleteAccount(emailId);
		
		listAccounts(request, response);
	}
	
	public void deleteMovie(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
		
		int movieId = Integer.parseInt(request.getParameter("movie_id"));
		
		theatreDBUtil.deleteMovie(movieId);
		
		listMovies(request, response);
	}
	
	public void deleteSchedule(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
		
		int scheduleId = Integer.parseInt(request.getParameter("schedule_id"));
		
		Schedule schedule = theatreDBUtil.getSchedule(scheduleId);
		theatreDBUtil.deleteSchedule(schedule);
		
		listSchedules(request, response);
	}
	
	public void deleteTheatre(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
		
		int theatreId = Integer.parseInt(request.getParameter("theatre_id"));
		
		theatreDBUtil.deleteTheatre(theatreId);
		
		listTheatres(request, response);
	}
	
	public void deleteTicketType(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
		
		String ticketTypeId = request.getParameter("ticketType_id");
		
		theatreDBUtil.deleteTicketType(ticketTypeId);
		
		listTicketTypes(request, response);
	}
	
	public void deleteTicket(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
		
		int ticketId = Integer.parseInt(request.getParameter("ticket_id"));
		
		theatreDBUtil.deleteTicket(ticketId);
		
		listTickets(request, response);
	}
	
	
	private void doLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		Admin tempAdmin = theatreDBUtil.getAdminAccount(username);
		if (tempAdmin == null) {
			response.sendRedirect("/adminLogin.jsp");
		} else {
			String salt = tempAdmin.getPasswordSalt();
			String securePassword = tempAdmin.getSecurePassword();
			
			if (PasswordUtils.verifyUserPassword(password, securePassword, salt)) {
				HttpSession session = request.getSession();
				session.setAttribute("adm_username", tempAdmin.getUsername());
				session.setAttribute("adm_email", tempAdmin.getEmail());
				RequestDispatcher dispatcher = request.getRequestDispatcher("/admin.jsp");
				dispatcher.forward(request, response);
			} else {
				response.sendRedirect("/adminLogin.jsp");
			}
		}
	}
	
	private boolean checkLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
		HttpSession session = request.getSession();
		if (session.getAttribute("adm_username") != null) {
			String username = (String)session.getAttribute("adm_username");
			Admin tempAdmin = theatreDBUtil.getAdminAccount(username);
			if (tempAdmin != null) {
				return true;
			}
		}
		return false;
	}
	
	private void doLogout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		session.invalidate();
		
		System.out.println("invalidate done");
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/adminLogin.jsp");
		dispatcher.forward(request, response);
	}

}
