package model;

import java.io.Serializable;

public class Ticket implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private int id;
	private Schedule schedule;
	private TicketType ticketType;
	private String email;
	
	public Ticket(int id, Schedule schedule, TicketType ticketType, String email) {
		this.id = id;
		this.schedule = schedule;
		this.ticketType = ticketType;
		this.email = email;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Schedule getSchedule() {
		return schedule;
	}
	public void setSchedule(Schedule schedule) {
		this.schedule = schedule;
	}
	public TicketType getTicketType() {
		return ticketType;
	}
	public void setTicketType(TicketType ticketType) {
		this.ticketType = ticketType;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	
}
