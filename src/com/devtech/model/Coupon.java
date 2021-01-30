package com.devtech.model;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import com.devtech.jdbc.connection.DAO.CouponsDBDAO;

public class Coupon {
	
	/** 
	 * The id of the Coupon.
	 */
	private int id ; 
	
	/** 
	 * The Company name that own this Coupon.
	 */
	private int companyId ; 
	
	/**
	 * Which Category this Coupon belong.
	 */
	private Category category ;
	
	/**
	 * The Title of the Coupon.
	 */
	private String title ;
	
	/**
	 * Description for the Coupon.
	 */
	private String description ;
	
	/**
	 * When this Coupon available.
	 */
	private Date startDate ;
	
	/**
	 * The expire Date for this Coupon.
	 */
	private Date endDate ;
	
	
	/**
	 * How many Coupon available.
	 */
	private int amount ;
	
	/**
	 * The Price for this Coupon.
	 */
	private double price ;
	
	/**
	 * Image that Descripe the Coupon.
	 */
	private String image ;
	
	Company company = new Company();

	
	public Coupon() {
	}
	
	/**
	 * @param id			The id of the Coupon
	 * @param companyId		The Company name that own this Coupon
	 * @param category		Which Category this Coupon belong
	 * @param title			The Title of the Coupon
	 * @param description	Description for the Coupon
	 * @param startDate		When this Coupon available
	 * @param endDate		The expire Date for this Coupon
	 * @param amount		How many Coupon available
	 * @param price			The Price for this Coupon
	 * @param image			Image that Descripe the Coupon
	 */
	public Coupon(int id, int companyId, Category category, String title, String description, Date  startDate,
			Date  endDate, int amount, double price, String image) {
	
		this.id = id;						
		this.companyId = companyId;
		this.category = category;
		this.title = title;
		this.description = description;
		this.startDate = startDate;
		this.endDate = endDate;
		this.amount = amount;
		this.price = price;
		this.image = image;
	}

	public Coupon(int companyId, Category category, String title, String description, Date startDate,
			Date endDate, int amount, double price, String image) {
;
		this.id = id;						
		this.companyId = companyId;
		this.category = category;
		this.title = title;
		this.description = description;
		this.startDate =startDate;
		this.endDate = endDate;
		this.amount = amount;
		this.price = price;
		this.image = image;
		
	}

/**
 * 
 * @param endDate
 * @param amount
 * @param price
 *to update the coupon
 **/
	public Coupon (int id , int companyId ,Date endDate, int amount, double price) {
		
		this.id = id;						
		this.companyId = companyId;
		this.amount = amount;
		this.price = price;
		this.endDate = endDate;
		
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
	 * @return the companyId
	 */
	public int getCompanyId() {
		return companyId;
	}

	/**
	 * @param companyId the companyId to set
	 */
	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}

	/**
	 * @return the category
	 */
	public Category getCategory() {
		return category;
	}

	/**
	 * @param category the category to set
	 */
	public void setCategory(Category category) {
		this.category = category;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the startDate
	 */
	public Date  getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return the endDate
	 */
	public Date  getEndDate() {
		return endDate;
	}

	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	/**
	 * @return the amount
	 */
	public int getAmount() {
		return amount;
	}

	/**
	 * @param amount the amount to set
	 */
	public void setAmount(int amount) {
		this.amount = amount;
	}

	/**
	 * @return the price
	 */
	public double getPrice() {
		return price;
	}

	/**
	 * @param price the price to set
	 */
	public void setPrice(double price) {
		this.price = price;
	}

	/**
	 * @return the image
	 */
	public String getImage() {
		return image;
	}

	/**
	 * @param image the image to set
	 */
	public void setImage(String image) {
		this.image = image;
	}

	@Override
	public String toString() {
		return "Coupon [id=" + id + ", companyId=" + companyId + ", category=" + category + ", title=" + title
				+ ", description=" + description + ", startDate=" + startDate + ", endDate=" + endDate + ", amount="
				+ amount + ", price=" + price + ", image=" + image + "]";
	}

	/**
	 * update the number of the coupon
	 * @param coupon
	 */

	public String getStartDate(String startDate) {
		setStartDate(getStartDate());
		return startDate;
		}

	public String getEndDate(String endDate) {
		setEndDate(getEndDate());
		return endDate;
	}
}
