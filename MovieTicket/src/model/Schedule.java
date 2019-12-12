package model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

public class Schedule implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	public Schedule() {
		
	}
	
	public Schedule(int id, Movie movie, Theatre theatre, LocalDate movieDate, LocalTime movieTime, int seats) {
		this.id = id;
		this.movie = movie;
		this.theatre = theatre;
		this.movieDate = movieDate;
		this.movieTime = movieTime;
		this.seats = seats;
	}
	private int id;
	private Movie movie;
	private Theatre theatre;
	private LocalDate movieDate;
	private LocalTime movieTime;
	private int seats;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Movie getMovie() {
		return movie;
	}
	public void setMovie(Movie movie) {
		this.movie = movie;
	}
	public Theatre getTheatre() {
		return theatre;
	}
	public void setTheatre(Theatre theatre) {
		this.theatre = theatre;
	}
	public LocalDate getMovieDate() {
		return movieDate;
	}
	public void setMovieDate(LocalDate movieDate) {
		this.movieDate = movieDate;
	}
	public LocalTime getMovieTime() {
		return movieTime;
	}
	public void setMovieTime(LocalTime movieTime) {
		this.movieTime = movieTime;
	}

	public int getSeats() {
		return seats;
	}

	public void setSeats(int seats) {
		this.seats = seats;
	}
	
	
}
