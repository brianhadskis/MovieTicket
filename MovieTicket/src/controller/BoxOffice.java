package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
import model.Movie;
import model.Schedule;
import model.Ticket;
import model.TicketType;
import myUtil.PasswordUtils;

/**
 * Servlet implementation class BoxOffice
 */
@WebServlet("/BoxOffice")
public class BoxOffice extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private TheatreDBUtil theatreDBUtil;
	private DateTimeFormatter dtf;
	
	@Resource(name = "jdbc/prog32758")
	private DataSource dataSource;
	
	public void init() throws ServletException { // see notes on servlet life cycle
    	super.init();
    	
    	try {
    		// we want to create a theatreDBUtil obj and pass it a connection
    		
    		theatreDBUtil = new TheatreDBUtil(dataSource);
    	} catch (Exception e) {
    		throw new ServletException(e);
    	}
    }
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BoxOffice() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			// Read the command parameter
			String command = request.getParameter("command");
			HttpSession session = request.getSession();
			
			if (command == null) {
				command = "BROWSE";
			}
			
			switch (command) {
			case "BROWSE":
				response.sendRedirect("index.jsp");
				break;
			case "REGISTER":
				doRegister(request, response);
				break;
			case "LOGIN":
				doLogin(request, response);
				break;
			case "LOGOUT":
				doLogout(request, response);
				break;
			case "PURCHASE":
				ticketPurchase(request, response);
				break;
			case "CONFIRM":
				confirmPurchase(request, response);
				break;
			case "THANKS":
				showTickets(request, response);
				break;
			default:
				response.sendRedirect("index.jsp");
			}
		} catch (ServletException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	
	private void doRegister(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
		
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String address = request.getParameter("address");
		String city = request.getParameter("city");
		String postal = request.getParameter("postal");
		String phone = request.getParameter("phone");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String verify = request.getParameter("verify");
		LocalDate dateOfBirth = LocalDate.parse(request.getParameter("dateOfBirth"));
		
		// Check password and verify are the same (this should have been checked already though!)
		// If not, redirect back to register page
		if (password != verify) {
			response.sendRedirect("/register.jsp");
		}
		
		// Hash password before storing
		// Create salt
		String salt = PasswordUtils.getSalt(30);
		
		// Use salt and password to create hashed password
		String securePassword = PasswordUtils.generateSecurePassword(password, salt);
		
		Account tempAccount = new Account(email, firstName, lastName, address, city, postal, phone, securePassword, salt, dateOfBirth);
		
		theatreDBUtil.addAccount(tempAccount);
		
		tempAccount = theatreDBUtil.getAccount(email);
		
		HttpSession session = request.getSession();
		session.setAttribute("email_id", tempAccount.getEmail());
		session.setAttribute("firstName", tempAccount.getFirstName());
		RequestDispatcher dispatcher = request.getRequestDispatcher("/index.jsp");
		dispatcher.forward(request, response);
	}
	
	private void doLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		
		Account tempAccount = theatreDBUtil.getAccount(email);
		
		if (tempAccount == null) {
			response.sendRedirect("/login.jsp");
		}
		
		String salt = tempAccount.getPasswordSalt();
		String securePassword = tempAccount.getSecurePassword();
		
		if (PasswordUtils.verifyUserPassword(password, securePassword, salt)) {
			HttpSession session = request.getSession();
			session.setAttribute("email_id", tempAccount.getEmail());
			session.setAttribute("firstName", tempAccount.getFirstName());
			RequestDispatcher dispatcher = request.getRequestDispatcher("/index.jsp");
			dispatcher.forward(request, response);
		} else {
			response.sendRedirect("/login.jsp");
		}
	}
	
	private void doLogout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		session.invalidate();
		
		System.out.println("invalidate done");
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/index.jsp");
		dispatcher.forward(request, response);
	}

	private void ticketPurchase(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
		HttpSession session = request.getSession();
		
		float generalTicket = theatreDBUtil.getTicketType("GENERAL").getPrice();
		float childTicket = theatreDBUtil.getTicketType("CHILD").getPrice();
		float tuesdayTicket = theatreDBUtil.getTicketType("TUESDAY").getPrice();
		
		if (session.getAttribute("email_id") != null) {
			generalTicket -= (generalTicket * 0.2);
			childTicket -= (childTicket * 0.2);
			tuesdayTicket -= (tuesdayTicket * 0.2);
		}
		
		int scheduleId = Integer.parseInt(request.getParameter("movieTime"));
		Schedule schedule = theatreDBUtil.getSchedule(scheduleId);
		
		session.setAttribute("MOVIE_SCHEDULE", schedule);
		if (schedule.getMovieDate().getDayOfWeek() == schedule.getMovieDate().getDayOfWeek().TUESDAY) {
			request.setAttribute("IS_TUESDAY", true);
		}
		
		session.setAttribute("GENERAL_TICKET_PRICE", generalTicket);
		session.setAttribute("CHILD_TICKET_PRICE", childTicket);
		session.setAttribute("TUESDAY_TICKET_PRICE", tuesdayTicket);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/purchase.jsp");
		dispatcher.forward(request, response);
	}
	
	private void confirmPurchase(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
		HttpSession session = request.getSession();
		String email = request.getParameter("email");
		
		//System.out.println(request.getParameter("SCHEDULE_ID"));
		
		//int scheduleId = Integer.parseInt(request.getParameter("SCHEDULE_ID"));
		//Schedule schedule = theatreDBUtil.getSchedule(scheduleId);
		Schedule schedule = (Schedule)session.getAttribute("MOVIE_SCHEDULE");
		
		float generalTicket = (float)session.getAttribute("GENERAL_TICKET_PRICE");
		float childTicket = (float)session.getAttribute("CHILD_TICKET_PRICE");
		float tuesdayTicket = (float)session.getAttribute("TUESDAY_TICKET_PRICE");
		
//		if (session.getAttribute("email_id") != null) {
//			generalTicket -= (generalTicket * 0.2);
//			childTicket -= (childTicket * 0.2);
//			tuesdayTicket -= (tuesdayTicket * 0.2);
//		}
		
		int numGeneral = 0;
		int numChild = 0;
		int numTuesday = 0;
		
		float totalGeneral = 0;
		float totalChild = 0;
		float totalTuesday = 0;
		
		List<Ticket> ticketList = new ArrayList<Ticket>();
		if (request.getParameter("general") != null) {
			numGeneral = Integer.parseInt(request.getParameter("general"));
		}
		if (request.getParameter("child") != null) {
			numChild = Integer.parseInt(request.getParameter("child"));
		}
		if (request.getParameter("tuesday") != null) {
			numTuesday = Integer.parseInt(request.getParameter("tuesday"));
		}
		
		for (int i = 0; i < numGeneral; i++) {
			TicketType tempType = theatreDBUtil.getTicketType("GENERAL");
			Ticket ticket = new Ticket(0, schedule, tempType, email);
			ticketList.add(ticket);
			totalGeneral += generalTicket;
		}
		for (int i = 0; i < numChild; i++) {
			TicketType tempType = theatreDBUtil.getTicketType("CHILD");
			Ticket ticket = new Ticket(0, schedule, tempType, email);
			ticketList.add(ticket);
			totalChild += childTicket;
		}
		for (int i = 0; i < numTuesday; i++) {
			TicketType tempType = theatreDBUtil.getTicketType("TUESDAY");
			Ticket ticket = new Ticket(0, schedule, tempType, email);
			ticketList.add(ticket);
			totalTuesday += tuesdayTicket;
		}
		
		float totalPrice = totalGeneral + totalChild + totalTuesday;
		
		session.setAttribute("NUM_GENERAL", numGeneral);
		session.setAttribute("NUM_CHILD", numChild);
		session.setAttribute("NUM_TUESDAY", numTuesday);
		
		session.setAttribute("TOTAL_GENERAL", totalGeneral);
		session.setAttribute("TOTAL_CHILD", totalChild);
		session.setAttribute("TOTAL_TUESDAY", totalTuesday);
		
		session.setAttribute("TOTAL_PRICE", totalPrice);
		
		session.setAttribute("MOVIE_TICKET_LIST", ticketList);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/confirm.jsp");
		dispatcher.forward(request, response);
	}
	
	private void showTickets(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
		HttpSession session = request.getSession();
		String email = request.getParameter("email");
		
		//System.out.println(request.getParameter("SCHEDULE_ID"));
		
		//int scheduleId = Integer.parseInt(request.getParameter("SCHEDULE_ID"));
		//Schedule schedule = theatreDBUtil.getSchedule(scheduleId);
		Schedule schedule = (Schedule)session.getAttribute("MOVIE_SCHEDULE");
		List<Ticket> ticketList = (List<Ticket>)session.getAttribute("MOVIE_TICKET_LIST");
		
//		System.out.println(request.getParameter("general"));
//		System.out.println(request.getParameter("child"));
//		System.out.println(request.getParameter("tuesday"));
		
		int numGeneral = 0;
		int numChild = 0;
		int numTuesday = 0;
		
		for (Ticket ticket : ticketList) {
			theatreDBUtil.addTicket(ticket);
		}
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/thanks.jsp");
		dispatcher.forward(request, response);
	}
}
