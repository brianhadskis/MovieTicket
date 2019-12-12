package model;

import java.io.Serializable;
import java.time.LocalDate;

public class Account implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private String email_id;
	private String firstName;
	private String lastName;
	private String address;
	private String city;
	private String postal;
	private String phone;
	private String securePassword;
	private String passwordSalt;
	private LocalDate dateOfBirth;
	
	public Account(String email_id, String firstName, String lastName, String address, String city, String postal, String phone,
			String securePassword, String passwordSalt, LocalDate dateOfBirth) {
		this.email_id = email_id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.city = city;
		this.postal = postal;
		this.phone = phone;
		this.securePassword = securePassword;
		this.passwordSalt = passwordSalt;
		this.dateOfBirth = dateOfBirth;
	}
	public String getEmail() {
		return email_id;
	}
	public void setEmail(String email) {
		this.email_id = email;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getPostal() {
		return postal;
	}
	public void setPostal(String postal) {
		this.postal = postal;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getSecurePassword() {
		return securePassword;
	}
	public void setSecurePassword(String password) {
		this.securePassword = password;
	}
	public String getPasswordSalt() {
		return passwordSalt;
	}
	public void setPasswordSalt(String passwordSalt) {
		this.passwordSalt = passwordSalt;
	}
	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	
	
}
