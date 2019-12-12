package model;

import java.io.Serializable;

public class Theatre implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private int id;
	private String name;
	
	public Theatre() {

	}
	public Theatre(int id, String name) {
		this.id = id;
		this.name = name;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
