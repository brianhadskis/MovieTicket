package model;

import java.io.Serializable;

public class Admin implements Serializable {
	private static final long serialVersionUID = 1L;

	private String username;
	private String email;
	private String securePassword;
	private String passwordSalt;
	
	public Admin(String username, String securePassword, String passwordSalt, String email) {
		this.username = username;
		this.securePassword = securePassword;
		this.passwordSalt = passwordSalt;
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSecurePassword() {
		return securePassword;
	}

	public void setSecurePassword(String securePassword) {
		this.securePassword = securePassword;
	}

	public String getPasswordSalt() {
		return passwordSalt;
	}

	public void setPasswordSalt(String passwordSalt) {
		this.passwordSalt = passwordSalt;
	}
	
	
}
