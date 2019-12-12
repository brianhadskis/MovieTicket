package model;

import java.io.Serializable;
import java.time.LocalDate;

public class Movie implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	public static enum Rating {
		G("G"),
		PG("PG"),
		PG13("PG-13"),
		R("R"),
		NC17("NC-17");
		private String ratingName;
		private Rating(String n) {
			this.ratingName = n;
		}
		
		public String getRating() {
			return ratingName;
		}
	}
	
	public Movie() {
		
	}
	
	public Movie(int id, String title, String description, Rating rating, int length, LocalDate releaseDate, String posterImage) {
		this.id = id;
		this.title = title;
		this.description = description;
		this.rating = rating;
		this.length = length;
		this.releaseDate = releaseDate;
		this.posterImage = posterImage;
	}
	
	private int id;
	private String title;
	private String description;
	private Rating rating;
	private int length;	// in minutes
	private LocalDate releaseDate;
	private String posterImage;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Rating getRating() {
		return rating;
	}
	public void setRating(Rating rating) {
		this.rating = rating;
	}
	public int getLength() {
		return length;
	}
	public void setLength(int length) {
		this.length = length;
	}
	public LocalDate getReleaseDate() {
		return releaseDate;
	}
	public void setReleaseDate(LocalDate releaseDate) {
		this.releaseDate = releaseDate;
	}

	public String getPosterImage() {
		return posterImage;
	}

	public void setPosterImage(String posterImage) {
		this.posterImage = posterImage;
	}
	
	
	
}
