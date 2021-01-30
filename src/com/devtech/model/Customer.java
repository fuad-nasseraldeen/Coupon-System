package com.devtech.model;

import java.util.ArrayList;

public class Customer {

	/**
	 * The id of the Customer
	 */
	private int id ;
	
	/** 
	 * The first name of the Customer.
	 */
	private String firstName ;
	
	/**
	 * The last name of the Customer.
	 */
	private String lastName ; 
	
	/**
	 * The email of the Customer.
	 */
	private String email ;
	
	/**
	 * The password of the Customer.
	 */
	private String password ;
	
	/**
	 * The list of coupons's for this Customer.
	 */
	private ArrayList<Coupon> coupons ;

	/**
	 * @param firstName		The first name of the Customer
	 * @param lastName		The last name of the Customer
	 * @param email			The email of the Customer
	 * @param password		The password of the Customer
	 * @param coupons		The list of coupons's for this Customer
	 */
	public Customer(String firstName, String lastName, String email, String password) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.coupons = new ArrayList<Coupon>();
	}

public Customer(int id,String firstName, String lastName, String email, String password) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.coupons = new ArrayList<Coupon>();
	}
	public Customer() {
	// TODO Auto-generated constructor stub
}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

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
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the coupons
	 */
	public ArrayList<Coupon> getCoupons() {
		return coupons;
	}
	
	/**
	 * @param coupons the coupons to set
	 */
	public void setCoupons(ArrayList<Coupon> coupons) {
		this.coupons = coupons;
	}
	
	@Override
	public String toString() {
		return "Customer [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email
				+ ", password=" + password + ", coupons=" + coupons + "]";
	}
}
