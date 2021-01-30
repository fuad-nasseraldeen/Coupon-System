package com.devtech.model;

import java.util.ArrayList;

public class Company {
	
	/**
	 * The Company Id 
	 */
	private int id ;
	
	/** 
	 * The name of the Company
	 */
	private String name ;
	
	/** 
	 * The password of the Company
	 */
	private String password ;
	
	/**
	 * The email of the Company
	 */
	private String email ;
	
	/**
	 * The list of Coupon's for this Company.
	 */
	private ArrayList<Coupon> coupons ;

	/**
	 * @param id		The Company Id 
	 * @param name		The name of the Company
	 * @param email		The email of the Company
	 * @param password	The password of the Company
	 * @param coupons	The list of Coupon's for this Company
	 */
	public Company(String name, String email , String password) {
		this.id=id;
		this.name = name;
		this.email = email;
		this.password = password;
	}
	/**
	 * @param id
	 * @param name
	 * @param email
	 * @param password
	 */
	public Company(int id ,String name,String email , String password) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.password = password;
	}
	
	/**
	 * update the company
	 * @param id
	 * @param email
	 * @param password
	 */
	public Company(int id , String email , String password) {
		this.id = id;
		this.email = email;
		this.password = password;
	}

	/**
	 * Defualt constructor
	 */
	public Company() {
		// TODO Auto-generated constructor stub
	}

	

	public Company(String email, String password) {
		this.email = email;
		this.password = password;	
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

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}

	public ArrayList<Coupon> getCoupons() {
		return coupons;
	}

	public void setCoupons(ArrayList<Coupon> coupons) {
		this.coupons = coupons;
	}
	

	@Override
	public String toString() {
		return "Company [ID=" + id + " , password=" + password + " , name=" + name+  " , email=" + email + " , coupons=" + coupons + "]";
	}
}
