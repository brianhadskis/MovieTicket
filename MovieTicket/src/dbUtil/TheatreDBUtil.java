package dbUtil;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import java.time.LocalDate;
import java.time.LocalTime;
import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import model.Account;
import model.Admin;
import model.Movie;
import model.Schedule;
import model.Theatre;
import model.Ticket;
import model.TicketType;

public class TheatreDBUtil {
	private DataSource dataSource;
	
	public TheatreDBUtil(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	public List<String> tableNames = new ArrayList<String>();
	public LinkedHashMap<String, String> columnData;
	
	
	public List<String> getTableNames() throws SQLException {
		Connection conn = null;
		Statement stmt = null;
		ResultSet result = null;
		
		try {
			conn = dataSource.getConnection();
			
			DatabaseMetaData md = conn.getMetaData();

			result = md.getTables("theatre", null, "%", null);
			while (result.next()) {
				tableNames.add(result.getString(3));
				
			}

			return tableNames;
			
		} finally {
			close(conn, result, stmt);
		}
	}
	
	public LinkedHashMap<String, String> getColumnData(String tableName) throws SQLException {
		Connection conn = null;
		Statement stmt = null;
		ResultSet result = null;
		
		columnData = new LinkedHashMap<String, String>();
		
		try {
			conn = dataSource.getConnection();
			
			stmt = conn.createStatement();
			String sql = "select * from " + tableName;
			result = stmt.executeQuery(sql);
			
			ResultSetMetaData rsmd = result.getMetaData();
			for (int j = 1; j <= rsmd.getColumnCount(); j++) {
				columnData.put(rsmd.getColumnName(j), rsmd.getColumnTypeName(j));
			}
			
			return columnData;
		} finally {
			close(conn, result, stmt);
		}
	}
	
	public void addAccount(Account account) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		
		try {
			conn = dataSource.getConnection();
			
			String sql = "insert into accounts (email_id, first_name, last_name, address, city, postal, phone,"
					+ " password_secure, password_salt, date_of_birth) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			ps = conn.prepareStatement(sql);
			
			ps.setString(7, account.getEmail());
			ps.setString(1, account.getFirstName());
			ps.setString(2, account.getLastName());
			ps.setString(3, account.getAddress());
			ps.setString(4, account.getCity());
			ps.setString(5, account.getPostal());
			ps.setString(6, account.getPhone());
			ps.setString(8, account.getSecurePassword());
			ps.setString(9, account.getPasswordSalt());
			ps.setDate(10, java.sql.Date.valueOf(account.getDateOfBirth()));
			
			ps.execute();
		} finally {
			close(conn, null, ps);
		}
	}
	
	public Account getAccount(String email) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet result = null;
		
		Account account;
		
		try {
			conn = dataSource.getConnection();
			String sql = "select * from accounts where email_id = ?";
			
			ps = conn.prepareStatement(sql);
			ps.setString(1, email);
			
			result = ps.executeQuery();
			
			if (result.next()) {
				String email_id = result.getString("email_id");
				String firstName = result.getString("first_name");
				String lastName = result.getString("last_name");
				String address = result.getString("address");
				String city = result.getString("city");
				String postal = result.getString("postal");
				String phone = result.getString("phone");
				String securePassword = result.getString("password_secure");
				String passwordSalt = result.getString("password_salt");
				LocalDate dateOfBirth = result.getDate("date_of_birth").toLocalDate();
				
				account = new Account(email_id, firstName, lastName, address, city, postal, phone, securePassword, passwordSalt, dateOfBirth);
			} else {
				return null;
			}
			
			return account;
		} finally {
			close(conn, result, ps);
		}
	}
	
	public void updateAccount(Account account) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		
		try {
			conn = dataSource.getConnection();
			
			String sql = "update accounts set first_name = ?,"
					+ " last_name = ?,"
					+ " address = ?,"
					+ " city = ?,"
					+ " postal = ?,"
					+ " phone = ?,"
					+ " email = ?,"
					+ " password_secure = ?,"
					+ " password_salt = ?,"
					+ " date_of_birth = ? where email_id = ?";
			ps = conn.prepareStatement(sql);
			
			ps.setString(1, account.getFirstName());
			ps.setString(2, account.getLastName());
			ps.setString(3, account.getAddress());
			ps.setString(4, account.getCity());
			ps.setString(5, account.getPostal());
			ps.setString(6, account.getPhone());
			ps.setString(7, account.getSecurePassword());
			ps.setString(8, account.getPasswordSalt());
			ps.setDate(9, java.sql.Date.valueOf(account.getDateOfBirth()));
			ps.setString(10, account.getEmail());
			
			ps.execute();
		} finally {
			close(conn, null, ps);
		}
	}
	
	public void deleteAccount(String email) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		
		try {
			conn = dataSource.getConnection();
			
			// First, delete all tickets associated with the email (account)
			String sql = "delete from tickets where email = ?";
			ps = conn.prepareStatement(sql);
			
			ps.setString(1, email);
			
			ps.execute();
			
			close(null, null, ps);
			
			// Then, delete the account
			sql = "delete from accounts where email_id = ?";
			ps = conn.prepareStatement(sql);
			
			ps.setString(1, email);
			
			ps.execute();
		} finally {
			close(conn, null, ps);
		}
	}
	
	public List<Account> getAccounts(int pageNum, int rowLimit) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet result = null;
		
		List<Account> accountList = new ArrayList<Account>();
		
		try {
			conn = dataSource.getConnection();
			String sql = "select * from accounts";
			int pageStart = 0;
			
			if (rowLimit > 0) {
				sql += " limit ?";
				if (pageNum > 1) {
					sql += ", ?";
					pageStart = (pageNum - 1) * rowLimit;
				}
				sql += rowLimit;
			}
			
			ps = conn.prepareStatement(sql);
			
			int setCount = 1;
			
			if (pageStart > 0) {
				ps.setInt(setCount++, pageStart);
			}
			if (rowLimit > 0) {
				ps.setInt(setCount++, rowLimit);
			}
			
			result = ps.executeQuery();
			
			while (result.next()) {
				String email = result.getString("email_id");
				String firstName = result.getString("first_name");
				String lastName = result.getString("last_name");
				String address = result.getString("address");
				String city = result.getString("city");
				String postal = result.getString("postal");
				String phone = result.getString("phone");
				String securePassword = result.getString("password_secure");
				String passwordSalt = result.getString("password_salt");
				LocalDate dateOfBirth = result.getDate("date_of_birth").toLocalDate();
				
				Account tempAccount = new Account(email, firstName, lastName, address, city, postal, phone, securePassword, passwordSalt, dateOfBirth);
				accountList.add(tempAccount);
			}
			
			return accountList;
		} finally {
			close(conn, result, ps);
		}
	}
	
	public List<Movie> getMovies(String title) throws SQLException {
		List<Movie> movies = new ArrayList<>();
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet result = null;
		
		try {
			conn = dataSource.getConnection();
			String sql = "select * from movies";
			
			sql += " where";
			if (title != null && title != "") {
				sql += " title like ? and";
				title = "%" + title + "%";
			}
			
			sql += " movie_id in (" + 
					"select movie_id from schedules "
					+ "where movie_date >= ?)";
			
			LocalDate today = LocalDate.now();
			ps = conn.prepareStatement(sql);
			int setCount = 1;
			
			if (title != null && title != "") {
				ps.setString(setCount++, title);
			}
			ps.setDate(setCount++, java.sql.Date.valueOf(today));
			
			result = ps.executeQuery();
			
			while (result.next()) {
				int movie_id = result.getInt("movie_id");
				String movie_title = result.getString("title");
				String movie_desc = result.getString("description");
				Movie.Rating movie_rating = Movie.Rating.valueOf(result.getString("rating"));
				int movie_length = result.getInt("length");
				LocalDate movie_release = result.getDate("releaseDate").toLocalDate();
				String posterImage = result.getString("poster_image");
				
				Movie tempMovie = new Movie(movie_id, movie_title, movie_desc, movie_rating, movie_length, movie_release, posterImage);
				movies.add(tempMovie);
			}
		} finally {
			close(conn, result, ps);
		}
		
		return movies;
	}
	
	public List<Movie> getMovies(int pageNum, int rowLimit) throws SQLException {
		List<Movie> movies = new ArrayList<>();
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet result = null;
		
		try {
			conn = dataSource.getConnection();
			String sql = "select * from movies";
			int pageStart = 0;
			
			if (rowLimit > 0) {
				sql += " limit ?";
				if (pageNum > 1) {
					sql += ", ?";
					pageStart = (pageNum - 1) * rowLimit;
				}
				sql += rowLimit;
			}
			
			ps = conn.prepareStatement(sql);
			int setCount = 1;
			
			if (pageStart > 0) {
				ps.setInt(setCount++, pageStart);
			}
			if (rowLimit > 0) {
				ps.setInt(setCount++, rowLimit);
			}
			
			result = ps.executeQuery();
			
			while (result.next()) {
				int movie_id = result.getInt("movie_id");
				String movie_title = result.getString("title");
				String movie_desc = result.getString("description");
				Movie.Rating movie_rating = Movie.Rating.valueOf(result.getString("rating"));
				int movie_length = result.getInt("length");
				LocalDate movie_release = result.getDate("releaseDate").toLocalDate();
				String posterImage = result.getString("poster_image");
				
				Movie tempMovie = new Movie(movie_id, movie_title, movie_desc, movie_rating, movie_length, movie_release, posterImage);
				movies.add(tempMovie);
			}
		} finally {
			close(conn, result, ps);
		}
		
		return movies;
	}
	
	public List<Movie> getMoviesByRelease(String filter) throws SQLException {
		List<Movie> movies = new ArrayList<>();
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet result = null;
		
		try {
			conn = dataSource.getConnection();
			String sql = "select * from movies where";
			boolean hasFilter = false;

			if (filter == "PLAYING") {
				sql += " releaseDate <= ?";
			} else if (filter == "COMING_SOON") {
				sql += " releaseDate > ?";
			}
			
			sql += " movie_id in (" + 
					"select movie_id from schedules "
					+ "where movie_date >= ?)";
			
			
			LocalDate today = LocalDate.now();
			ps = conn.prepareStatement(sql);
			int setCount = 1;
			if (hasFilter) {
				ps.setDate(1, java.sql.Date.valueOf(today));
				setCount++;
			}
			ps.setDate(setCount, java.sql.Date.valueOf(today));
			
			result = ps.executeQuery();
			
			while (result.next()) {
				int movie_id = result.getInt("movie_id");
				String movie_title = result.getString("title");
				String movie_desc = result.getString("description");
				Movie.Rating movie_rating = Movie.Rating.valueOf(result.getString("rating"));
				int movie_length = result.getInt("length");
				LocalDate movie_release = result.getDate("releaseDate").toLocalDate();
				String posterImage = result.getString("poster_image");
				
				Movie tempMovie = new Movie(movie_id, movie_title, movie_desc, movie_rating, movie_length, movie_release, posterImage);
				movies.add(tempMovie);
			}
		} finally {
			close(conn, result, ps);
		}
		
		return movies;
	}
	
	public Movie getMovie(int movieId) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet result = null;
		
		Movie movie;
		
		try {
			conn = dataSource.getConnection();
			String sql = "select * from movies where movie_id = ?";
//			if (movieId > 0) {
//				sql += " where movie_id = ?";
//				ps = conn.prepareStatement(sql);
//				ps.setInt(1, movieId);
//			}
			
			ps = conn.prepareStatement(sql);
			ps.setInt(1, movieId);
			
			result = ps.executeQuery();
			
			if (result.next()) {
				int movie_id = result.getInt("movie_id");
				String movie_title = result.getString("title");
				String movie_desc = result.getString("description");
				Movie.Rating movie_rating = Movie.Rating.valueOf(result.getString("rating"));
				int movie_length = result.getInt("length");
				LocalDate movie_release = result.getDate("releaseDate").toLocalDate();
				String posterImage = result.getString("poster_image");
				
				movie = new Movie(movie_id, movie_title, movie_desc, movie_rating, movie_length, movie_release, posterImage);
			} else {
				throw new SQLException("Could not find movie id");
			}
			
			return movie;
		} finally {
			close(conn, result, ps);
		}
	}
	
	public void addMovie(Movie movie) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		
		try {
			conn = dataSource.getConnection();
			
			String sql = "insert into movies (title, description, rating, length, releaseDate, poster_image) values (?, ?, ?, ?, ?, ?)";
			ps = conn.prepareStatement(sql);
			
			ps.setString(1, movie.getTitle());
			ps.setString(2, movie.getDescription());
			ps.setString(3, movie.getRating().toString());
			ps.setInt(4, movie.getLength());
			ps.setDate(5, java.sql.Date.valueOf(movie.getReleaseDate()));
			ps.setString(6, movie.getPosterImage());
			
			ps.execute();
		} finally {
			close(conn, null, ps);
		}
	}
	
	public void updateMovie(Movie movie) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		
		try {
			conn = dataSource.getConnection();
			
			String sql = "update movies set title = ?, description = ?, rating = ?, length = ?, releaseDate = ?, poster_image = ? where movie_id = ?";
			ps = conn.prepareStatement(sql);
			
			ps.setString(1, movie.getTitle());
			ps.setString(2, movie.getDescription());
			ps.setString(3, movie.getRating().toString());
			ps.setInt(4, movie.getLength());
			ps.setDate(5, java.sql.Date.valueOf(movie.getReleaseDate()));
			ps.setString(6, movie.getPosterImage());
			ps.setInt(7,  movie.getId());
			
			ps.execute();
		} finally {
			close(conn, null, ps);
		}
	}
	
	public void deleteMovie(int movie_id) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		
		try {
			conn = dataSource.getConnection();
			
			// First, delete all tickets for this movie
			String sql = "delete from tickets where schedule_id in ("
					+ "select schedule_id from schedules where movie_id = ?)";
			ps = conn.prepareStatement(sql);
			
			ps.setInt(1,  movie_id);
			
			ps.execute();
			
			close(null, null, ps);
			
			// Next, delete the schedules for this movie
			sql = "delete from schedules where movie_id = ?";
			ps = conn.prepareStatement(sql);
			
			ps.setInt(1,  movie_id);
			
			ps.execute();
			
			close(null, null, ps);
			
			// Finally, delete the movie
			sql = "delete from movies where movie_id = ?";
			ps = conn.prepareStatement(sql);
			
			ps.setInt(1,  movie_id);
			
			ps.execute();
		} finally {
			close(conn, null, ps);
		}
	}
	
	public Theatre getTheatre(int theatreId) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet result = null;
		
		Theatre theatre;
		
		try {
			conn = dataSource.getConnection();
			String sql = "select * from theatres";
			
			if (theatreId > 0) {
				sql += " where theatre_id = ?";
				ps = conn.prepareStatement(sql);
				ps.setInt(1, theatreId);
			}
			
			result = ps.executeQuery();
			
			if (result.next()) {
				int theatre_id = result.getInt("theatre_id");
				String theatre_name = result.getString("theatre_name");
				
				theatre = new Theatre(theatre_id, theatre_name);
			} else {
				throw new SQLException("Could not find theatre id");
			}
			
			return theatre;
		} finally {
			close(conn, result, ps);
		}
	}
	
	public List<Theatre> getTheatres(int pageNum, int rowLimit) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet result = null;
		
		List<Theatre> theatres = new ArrayList<Theatre>();
		
		try {
			conn = dataSource.getConnection();
			String sql = "select * from theatres";
			int pageStart = 0;
			int setCount = 1;
			
			if (rowLimit > 0) {
				sql += " limit ?";
				if (pageNum > 1) {
					sql += ", ?";
					pageStart = (pageNum - 1) * rowLimit;
				}
				sql += rowLimit;
			}
			
			ps = conn.prepareStatement(sql);
			
			if (rowLimit > 0) {
				ps.setInt(setCount++, rowLimit);
			}
			if (pageStart > 0) {
				ps.setInt(setCount, pageStart);
			}
			
			result = ps.executeQuery();
			
			while (result.next()) {
				int theatre_id = result.getInt("theatre_id");
				String theatre_name = result.getString("theatre_name");
				
				Theatre tempTheatre = new Theatre(theatre_id, theatre_name);
				theatres.add(tempTheatre);
			}
			
			return theatres;
		} finally {
			close(conn, result, ps);
		}
	}
	
	public void addTheatre(Theatre theatre) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		
		try {
			
			String sql = "insert into theatres (theatre_name) values (?)";
			ps = conn.prepareStatement(sql);
			
			ps.setString(1, theatre.getName());
			
			ps.execute();
		} finally {
			close(conn, null, ps);
		}
	}
	
	public void updateTheatre(Theatre theatre) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		
		try {
			conn = dataSource.getConnection();
			
			String sql = "update theatres set name = ? where theatre_id = ?";
			ps = conn.prepareStatement(sql);
			
			ps.setString(1, theatre.getName());
			ps.setInt(2,  theatre.getId());
			
			ps.execute();
		} finally {
			close(conn, null, ps);
		}
	}
	
	public void deleteTheatre(int theatreId) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		
		try {
			conn = dataSource.getConnection();
			
			// First, delete all tickets which use the schedule that uses this theatre
			String sql = "delete from tickets where schedule_id in ("
					+ "select schedule_id from schedules where theatre_id = ?)";
			
			ps = conn.prepareStatement(sql);
			
			ps.setInt(1,  theatreId);
			
			ps.execute();
			
			close(null, null, ps);
			
			// Then, delete the schedules that use this theatre
			sql = "delete from schedules where theatre_id = ?";
			
			ps = conn.prepareStatement(sql);
			
			ps.setInt(1,  theatreId);
			
			ps.execute();
			
			close(null, null, ps);
			
			// Finally, delete the theatre
			sql = "delete from theatre where theatre_id = ?";
			ps = conn.prepareStatement(sql);
			
			ps.setInt(1,  theatreId);
			
			ps.execute();
		} finally {
			close(conn, null, ps);
		}
	}
	
	public List<LocalDate> getMovieDates() throws SQLException {
		return getMovieDates(null);
	}
	
	public List<LocalDate> getMovieDates(Movie movie) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet result = null;
		
		List<LocalDate> movieDates = new ArrayList<LocalDate>();
		
		try {
			conn = dataSource.getConnection();
			
			String sql = "select movie_date from schedules";
			
			if (movie != null) {
				sql += " where movie_id = ?";
			}
			
			sql += " group by movie_date";
			
			ps = conn.prepareStatement(sql);
			
			if (movie != null) {
				ps.setInt(1, movie.getId());
			}
			
			result = ps.executeQuery();
			
			while (result.next()) {
				LocalDate movie_date = result.getDate("movie_date").toLocalDate();
				if (!movie_date.isBefore(LocalDate.now())) {
					movieDates.add(movie_date);
				}
			}	
		} finally {
			close(conn, result, ps);
		}
		
		return movieDates;
	}
	
	public List<LocalTime> getMovieTimes(Movie movie, LocalDate date) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet result = null;
		
		List<LocalTime> movieTimes = new ArrayList<LocalTime>();
		
		try {
			conn = dataSource.getConnection();
			
			String sql = "select movie_time from schedules where movie_id = ? and movie_date = ? order by movie_time";
			ps = conn.prepareStatement(sql);
			
			if (movie != null) {
				ps.setInt(1, movie.getId());
				ps.setDate(2, java.sql.Date.valueOf(date));
			}
			
			result = ps.executeQuery();
			
			while (result.next()) {
				LocalTime movie_time = result.getTime("movie_time").toLocalTime();
				
				movieTimes.add(movie_time);
			}	
		} finally {
			close(conn, result, ps);
		}
		
		return movieTimes;
	}
	
	public List<Schedule> getSchedules(int pageNum, int rowLimit) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet result = null;
		int pageStart = 0;
		
		List<Schedule> schedules = new ArrayList<Schedule>();
		
		try {
			conn = dataSource.getConnection();
			
			String sql = "select * from schedules";
			
			if (rowLimit > 0) {
				sql += " limit ?";
				if (pageNum > 1) {
					sql += ", ?";
					pageStart = (pageNum - 1) * rowLimit;
				}
				sql += rowLimit;
			}
			
			ps = conn.prepareStatement(sql);
			
			int setCount = 1;
			
			if (rowLimit > 0) {
				ps.setInt(setCount++, rowLimit);
			}
			if (pageStart > 0) {
				ps.setInt(setCount, pageStart);
			}
			
			result = ps.executeQuery();
			
			while (result.next()) {
				int schedule_id = result.getInt("schedule_id");
				int theatre_id = result.getInt("theatre_id");
				int movie_id = result.getInt("movie_id");
				LocalDate movie_date = result.getDate("movie_date").toLocalDate();
				LocalTime movie_time = result.getTime("movie_time").toLocalTime();
				int seats = result.getInt("seats");
				
				System.out.println(movie_id);
				
				Movie tempMovie = getMovie(movie_id);
				Theatre tempTheatre = getTheatre(theatre_id);
				
				Schedule tempSchedule = new Schedule(schedule_id, tempMovie, tempTheatre, movie_date, movie_time, seats);
				schedules.add(tempSchedule);
			}	
		} finally {
			close(conn, result, ps);
		}
		
		return schedules;
	}
	
	public List<Schedule> getSchedules(Movie movie, LocalDate date) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet result = null;
		
		List<Schedule> schedules = new ArrayList<Schedule>();
		
		try {
			conn = dataSource.getConnection();
			
			String sql = "select * from schedules where movie_id = ? and movie_date = ? order by movie_time";
			
			ps = conn.prepareStatement(sql);
			
			ps.setInt(1, movie.getId());
			ps.setDate(2, java.sql.Date.valueOf(date));
			
			result = ps.executeQuery();
			
			while (result.next()) {
				int schedule_id = result.getInt("schedule_id");
				int theatre_id = result.getInt("theatre_id");
				int movie_id = result.getInt("movie_id");
				LocalDate movie_date = result.getDate("movie_date").toLocalDate();
				LocalTime movie_time = result.getTime("movie_time").toLocalTime();
				int seats = result.getInt("seats");
				
				Movie tempMovie = getMovie(movie_id);
				Theatre tempTheatre = getTheatre(theatre_id);
				
				Schedule tempSchedule = new Schedule(schedule_id, tempMovie, tempTheatre, movie_date, movie_time, seats);
				schedules.add(tempSchedule);
			}	
		} finally {
			close(conn, result, ps);
		}
		
		return schedules;
	}
	
	public Schedule getSchedule(int scheduleId) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet result = null;
		
		Schedule schedule;
		
		try {
			conn = dataSource.getConnection();
			
			String sql = "select * from schedules where schedule_id = ?";
			
			ps = conn.prepareStatement(sql);
			
			ps.setInt(1, scheduleId);
			
			result = ps.executeQuery();
			
			if (result.next()) {
				int schedule_id = result.getInt("schedule_id");
				int theatre_id = result.getInt("theatre_id");
				int movie_id = result.getInt("movie_id");
				LocalDate movie_date = result.getDate("movie_date").toLocalDate();
				LocalTime movie_time = result.getTime("movie_time").toLocalTime();
				int seats = result.getInt("seats");
				
				Movie tempMovie = getMovie(movie_id);
				Theatre tempTheatre = getTheatre(theatre_id);
				
				schedule = new Schedule(schedule_id, tempMovie, tempTheatre, movie_date, movie_time, seats);
			} else {
				throw new SQLException("Schedule not found");
			}
			
		} finally {
			close(conn, result, ps);
		}
		
		return schedule;
	}
	
	public void addSchedule(Schedule schedule) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		
		try {
			conn = dataSource.getConnection();
			
			String sql = "insert into schedules (theatre_id, movie_id, movie_date, movie_time, seats) values (?, ?, ?, ?, ?)";
			ps = conn.prepareStatement(sql);
			
			ps.setInt(1, schedule.getTheatre().getId());
			ps.setInt(2, schedule.getMovie().getId());
			ps.setDate(3, java.sql.Date.valueOf(schedule.getMovieDate()));
			ps.setTime(4, java.sql.Time.valueOf(schedule.getMovieTime()));
			ps.setInt(5, schedule.getSeats());
			
			ps.execute();
		} finally {
			close(conn, null, ps);
		}
	}
	
	public void updateSchedule(Schedule schedule) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		
		try {
			conn = dataSource.getConnection();
			
			String sql = "update schedules set theatre_id = ?, movie_id = ?, movie_date = ?,"
					+ " movie_time = ?, seats = ? where schedule_id = ?";
			ps = conn.prepareStatement(sql);
			
			ps.setInt(1, schedule.getTheatre().getId());
			ps.setInt(2, schedule.getMovie().getId());
			ps.setDate(3, java.sql.Date.valueOf(schedule.getMovieDate()));
			ps.setTime(4, java.sql.Time.valueOf(schedule.getMovieTime()));
			ps.setInt(5, schedule.getSeats());
			ps.setInt(6, schedule.getId());
			
			ps.execute();
		} finally {
			close(conn, null, ps);
		}
	}
	
	public void deleteSchedule(Schedule schedule) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		
		try {
			conn = dataSource.getConnection();
			
			// First delete all tickets that have this schedule
			String sql = "delete from tickets where schedule_id = ?";
			ps = conn.prepareStatement(sql);
			
			ps.setInt(1, schedule.getId());
			
			ps.execute();
			
			close(null, null, ps);
			
			// Then delete the schedule
			sql = "delete from schedules where schedule_id = ?";
			ps = conn.prepareStatement(sql);
			
			ps.setInt(1, schedule.getId());
			
			ps.execute();
		} finally {
			close(conn, null, ps);
		}
	}
	
	public TicketType getTicketType(String ticketTypeId) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet result = null;
		
		TicketType ticketType;
		
		try {
			conn = dataSource.getConnection();
			String sql = "select * from ticket_types";
			if (ticketTypeId != null & ticketTypeId != "") {
				sql += " where ticket_type_id = ?";
				ps = conn.prepareStatement(sql);
				ps.setString(1, ticketTypeId);
			}
			
			result = ps.executeQuery();
			
			if (result.next()) {
				String type = result.getString("type_name");
				float price = result.getFloat("price");
				
				ticketType = new TicketType(ticketTypeId, type, price);
			} else {
				throw new SQLException("Could not find ticket type id");
			}
			
			return ticketType;
		} finally {
			close(conn, result, ps);
		}
	}
	
	public List<TicketType> getTicketTypes(int pageNum, int rowLimit) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet result = null;
		
		List<TicketType> ticketTypes = new ArrayList<TicketType>();
		
		try {
			conn = dataSource.getConnection();
			String sql = "select * from ticket_types";
			int pageStart = 0;
			
			if (rowLimit > 0) {
				sql += " limit ?";
				if (pageNum > 1) {
					sql += ", ?";
					pageStart = (pageNum - 1) * rowLimit;
				}
				sql += rowLimit;
			}
			ps = conn.prepareStatement(sql);
			
			int setCount = 1;
			
			if (rowLimit > 0) {
				ps.setInt(setCount++, rowLimit);
			}
			if (pageStart > 0) {
				ps.setInt(setCount, pageStart);
			}
			
			result = ps.executeQuery();
			
			while (result.next()) {
				String ticketTypeId = result.getString("ticket_type_id");
				String type = result.getString("type_name");
				float price = result.getFloat("price");
				
				TicketType tempTicketType = new TicketType(ticketTypeId, type, price);
				
				ticketTypes.add(tempTicketType);
			}
			
			return ticketTypes;
		} finally {
			close(conn, result, ps);
		}
	}
	
	public void addTicketType(TicketType ticketType) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		
		try {
			conn = dataSource.getConnection();
			
			String sql = "insert into ticket_types (ticket_type_id, type_name, price) values (?, ?)";
			ps = conn.prepareStatement(sql);
			
			ps.setString(1, ticketType.getId());
			ps.setString(2, ticketType.getName());
			ps.setFloat(3, ticketType.getPrice());
			
			ps.execute();
		} finally {
			close(conn, null, ps);
		}
	}
	
	public void updateTicketType(TicketType newTicketType, String oldTicketTypeId) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		
		try {
			conn = dataSource.getConnection();
			// First, update all tickets using this ticket type in case the id has changed
			String sql = "update tickets set set ticket_type_id = ? where ticket_type_id = ?";
			ps = conn.prepareStatement(sql);
			
			ps.setString(1, newTicketType.getId());
			ps.setString(2, oldTicketTypeId);
			
			close(null, null, ps);
			
			// Then update the ticket type
			sql = "update ticket_types set type_name = ?, price = ? where ticket_type_id = ?";
			ps = conn.prepareStatement(sql);
			
			ps.setString(1, newTicketType.getName());
			ps.setFloat(2,  newTicketType.getPrice());
			ps.setString(3, newTicketType.getId());
			
			ps.execute();
		} finally {
			close(conn, null, ps);
		}
	}
	
	public void deleteTicketType(String ticketTypeId) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		
		try {
			conn = dataSource.getConnection();
			
			// First, delete all tickets using this ticket type
			String sql = "delete from tickets where ticket_type_id = ?";
			ps = conn.prepareStatement(sql);
			
			ps.setString(1, ticketTypeId);
			
			ps.execute();
			
			close(null, null, ps);
			
			// Then, delete the ticket type
			sql = "delete from ticket_types where ticket_type_id = ?";
			ps = conn.prepareStatement(sql);
			
			ps.setString(1,  ticketTypeId);
			
			ps.execute();
		} finally {
			close(conn, null, ps);
		}
	}
	
	public Ticket getTicket(int ticketId) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet result = null;
		
		Ticket ticket;
		
		try {
			conn = dataSource.getConnection();
			String sql = "select * from tickets";
//			if (ticketId > 0) {
				sql += " where ticket_id = ?";
				ps = conn.prepareStatement(sql);
				ps.setInt(1, ticketId);
//			}
			
			result = ps.executeQuery();
			
			if (result.next()) {
				TicketType ticketType = getTicketType(result.getString("ticket_type_id"));
				String email = result.getString("email");
				Schedule schedule = getSchedule(result.getInt("schedule_id"));
				
				
				ticket = new Ticket(ticketId, schedule, ticketType, email);
			} else {
				throw new SQLException("Could not find ticket id");
			}
			
			return ticket;
		} finally {
			close(conn, result, ps);
		}
	}
	
	public List<Ticket> getTickets(int pageNum, int rowLimit) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet result = null;
		
		List<Ticket> tickets = new ArrayList<Ticket>();
		
		try {
			conn = dataSource.getConnection();
			String sql = "select * from tickets";
			int pageStart = 0;
			
			if (rowLimit > 0) {
				sql += " limit ?";
				if (pageNum > 1) {
					sql += ", ?";
					pageStart = (pageNum - 1) * rowLimit;
				}
				sql += rowLimit;
			}
			
			ps = conn.prepareStatement(sql);
			
			int setCount = 1;
			
			if (rowLimit > 0) {
				ps.setInt(setCount++, rowLimit);
			}
			if (pageStart > 0) {
				ps.setInt(setCount, pageStart);
			}
			
			result = ps.executeQuery();
			
			while (result.next()) {
				int ticketId = result.getInt("ticket_id");
				TicketType ticketType = getTicketType(result.getString("ticket_type_id"));
				String email = result.getString("email");
				Schedule schedule = getSchedule(result.getInt("schedule_id"));
				
				
				Ticket tempTicket = new Ticket(ticketId, schedule, ticketType, email);
				tickets.add(tempTicket);
			}
			
			return tickets;
		} finally {
			close(conn, result, ps);
		}
	}
	
	public void addTicket(Ticket ticket) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		
		try {
			conn = dataSource.getConnection();
			
			String sql = "insert into tickets (email, schedule_id, ticket_type_id) values (?, ?, ?)";
			ps = conn.prepareStatement(sql);
			
			ps.setString(1, ticket.getEmail());
			ps.setInt(2, ticket.getSchedule().getId());
			ps.setString(3, ticket.getTicketType().getId());
			
			ps.execute();
		} finally {
			close(conn, null, ps);
		}
	}
	
	public void updateTicket(Ticket ticket) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		
		try {
			conn = dataSource.getConnection();
			
			String sql = "update tickets set email = ?, schedule_id = ?, ticket_type_id = ? where ticket_id = ?";
			ps = conn.prepareStatement(sql);
			
			ps.setString(1, ticket.getEmail());
			ps.setInt(2,  ticket.getSchedule().getId());
			ps.setString(3, ticket.getTicketType().getId());
			ps.setInt(4, ticket.getId());
			
			ps.execute();
		} finally {
			close(conn, null, ps);
		}
	}
	
	public void updateTicketAccounts(String newEmail, String oldEmail) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		
		try {
			conn = dataSource.getConnection();
			
			String sql = "update tickets set email = ? where email = ?";
			ps = conn.prepareStatement(sql);
			
			ps.setString(1, newEmail);
			ps.setString(2, oldEmail);
			
			ps.execute();
		} finally {
			close(conn, null, ps);
		}
	}
	
	public void deleteTicket(int ticketId) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		
		try {
			conn = dataSource.getConnection();
			
			String sql = "delete from tickets where ticket_id = ?";
			ps = conn.prepareStatement(sql);
			
			ps.setInt(1,  ticketId);
			
			ps.execute();
		} finally {
			close(conn, null, ps);
		}
	}
	
	public Admin getAdminAccount(String username) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet result = null;
		
		Admin admin;
		
		try {
			conn = dataSource.getConnection();
			String sql = "select * from admins where username = ?";
			
			ps = conn.prepareStatement(sql);
			ps.setString(1, username);
			
			result = ps.executeQuery();
			
			if (result.next()) {
				String securePassword = result.getString("password_secure");
				String passwordSalt = result.getString("password_salt");
				String email = result.getString("email");
				
				admin = new Admin(username, securePassword, passwordSalt, email);
			} else {
				return null;
			}
			
			return admin;
		} finally {
			close(conn, result, ps);
		}
	}
	
	public List<Admin> getAdminAccounts(int pageNum, int rowLimit) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet result = null;
		
		List<Admin> admins = new ArrayList<Admin>();
		
		try {
			conn = dataSource.getConnection();
			String sql = "select * from admins";
			
			int pageStart = 0;
			
			if (rowLimit > 0) {
				sql += " limit ?";
				if (pageNum > 1) {
					sql += ", ?";
					pageStart = (pageNum - 1) * rowLimit;
				}
				sql += rowLimit;
			}
			
			ps = conn.prepareStatement(sql);
			
			int setCount = 1;
			
			if (rowLimit > 0) {
				ps.setInt(setCount++, rowLimit);
			}
			if (pageStart > 0) {
				ps.setInt(setCount, pageStart);
			}
			
			result = ps.executeQuery();
			
			while (result.next()) {
				String username = result.getString("username");
				String securePassword = result.getString("password_secure");
				String passwordSalt = result.getString("password_salt");
				String email = result.getString("email");
				
				System.out.println(username);
				
				Admin tempAdmin = new Admin(username, securePassword, passwordSalt, email);
				admins.add(tempAdmin);
			}
			
			return admins;
		} finally {
			close(conn, result, ps);
		}
	}
	
	public void addAdminAccount(Admin admin) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		
		try {
			conn = dataSource.getConnection();
			
			String sql = "insert into admins (username, password_secure, password_salt, email) values (?, ?, ?, ?)";
			ps = conn.prepareStatement(sql);
			
			ps.setString(1, admin.getUsername());
			ps.setString(2, admin.getSecurePassword());
			ps.setString(3, admin.getPasswordSalt());
			ps.setString(4, admin.getEmail());
			
			ps.execute();
		} finally {
			close(conn, null, ps);
		}
	}
	
	public void updateAdminAccount(Admin admin) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		
		try {
			conn = dataSource.getConnection();
			
			String sql = "update admins set "
					+ " password_secure = ?,"
					+ " password_salt = ?,"
					+ " email = ? where username = ?";
			ps = conn.prepareStatement(sql);
			
			ps.setString(1, admin.getSecurePassword());
			ps.setString(2, admin.getPasswordSalt());
			ps.setString(3, admin.getEmail());
			ps.setString(4, admin.getUsername());
			
			ps.execute();
		} finally {
			close(conn, null, ps);
		}
	}
	
	public void deleteAdminAccount(String username) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = dataSource.getConnection();
			
			String sql = "delete from admins where username = ?";
			ps = conn.prepareStatement(sql);
			
			ps.setString(1, username);
			
			ps.execute();
		} finally {
			close(conn, null, ps);
		}
	}
	
	private void close(Connection conn, ResultSet result, Statement stmt) throws SQLException {
		if (conn != null) {
			conn.close();
		}
		if (result != null) {
			result.close();
		}
		if (stmt != null) {
			stmt.close();
		}
	}
}
