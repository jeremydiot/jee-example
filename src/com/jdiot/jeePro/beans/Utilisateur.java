package com.jdiot.jeePro.beans;

import java.sql.Timestamp;

public class Utilisateur {
	
	private Long id;
	private String email;
	private String password;
	private String username;
	private Timestamp dateInscription;
	
	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * @return the dateInscription
	 */
	public Timestamp getDateInscription() {
		return dateInscription;
	}
	/**
	 * @param dateInscription the dateInscription to set
	 */
	public void setDateInscription(Timestamp dateInscription) {
		this.dateInscription = dateInscription;
	}
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}
	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}
}
