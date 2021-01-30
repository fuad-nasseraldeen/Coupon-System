package com.devtech.Facade;

import java.util.ArrayList;
import java.util.Date;

import com.devtech.exceptions.CompanyException;
import com.devtech.exceptions.CouponException;
import com.devtech.jdbc.connection.DAO.CompaniesDBDAO;
import com.devtech.jdbc.connection.DAO.CouponsDBDAO;
import com.devtech.model.Category;
import com.devtech.model.Company;
import com.devtech.model.Coupon;

public class CompanyFacade extends ClientFacade{
	
	private CompaniesDBDAO companyDB ;
	
	private CouponsDBDAO couponDB ;
	
	private int companyID ;
	
	Company company ;
	
	
	/**
	 * @return the company
	 */
	public Company getCompany() {
		return company;
	}

	/**
	 * @param company the company to set
	 */
	public void setCompany(Company company) {
		this.company = company;
	}

	/**
	 * initialization the constructor
	 */
	public CompanyFacade() throws Exception {
		
		companyDB = new CompaniesDBDAO();
		
		 couponDB = new CouponsDBDAO();
	}

	/**
	 * login method 
	 * if the login successfully save the details company in our company object
	 */
	@Override
	public boolean login(String email, String password) throws Exception {
		
		boolean ifLogin = companyDB.isCompanyExists(email, password);
		if (ifLogin) {
			this.company = companyDB.getCompany(email , password);
			this.setCompanyID(this.company.getId());
			this.company.setCoupons(getCompanyCoupons());
			System.out.printf("Welcome to the %s Company.\n",company.getName());
		}
			return ifLogin;
	}

	/**
	 * @return the companyID
	 */
	public int getCompanyID() {
		return companyID;
	}

	/**
	 * @param companyID the companyID to set
	 */
	public void setCompanyID(int companyID) {
		this.companyID = companyID;
	}
	/**
	 * add coupon to our table 
	 * our title should be unique
	 * @param coupon
	 * @throws Exception
	 */
	public void addCoupon(Coupon coupon) throws  Exception {
		boolean flag=true;
		ArrayList<Coupon> couponOfCompany =companyDB.getCompanyCoupons(this.companyID);
		for (Coupon coup : couponOfCompany) {
			if(coup.getTitle().equals(coupon.getTitle()) && coup.getCompanyId()==coupon.getCompanyId()) {
				flag=false;
				coupon.setId(coup.getId());
			}
		}
		if(flag)
			couponDB.addCoupon(coupon);
		else
			throw new CompanyException("Can't add similar title of Coupon to the Company.");	
	}

	/**
	 * update the coupon 
	 * if the date is expired - don't update
	 * @param coupon
	 * @throws CouponException
	 */
	public void updateCoupon(Coupon coupon) throws CouponException {
		if(coupon.getEndDate().before(new Date())) {
			throw new CouponException("End Date expired.");
		}
			couponDB.updateCoupon(coupon);
	}
	
	/**
	 * delete the coupon , if the coupon in the customers_vs_coupons => update too
	 * @param couponID
	 * @throws CouponException
	 */
	public void deleteCoupon(int couponID) throws CouponException {  
		if(couponDB.isCouponExistsInPurchaseTable(couponID))
			couponDB.deleteCouponPurchase(couponID);	
		couponDB.deleteCoupon(couponID);	
	}
	
	/**
	 * 
	 * @return all the coupons the belong to our company
	 * @throws CouponException
	 */
	public ArrayList<Coupon> getCompanyCoupons() throws CouponException{
		return companyDB.getCompanyCoupons(this.companyID);
	}
	
	/**
	 * 
	 * @param coupon
	 * @return specific coupon
	 * @throws CouponException
	 */
	public Coupon getCoupon(Coupon coupon) throws CouponException {
		Coupon c1 = null;
		ArrayList<Coupon> allCouponsPerCompany = getCompanyCoupons();
		for(Coupon coup: allCouponsPerCompany) {
			if(coup.getTitle().equals(coupon.getTitle()))
				c1 = coup;
		}
		return c1;
	}
	
	/**
	 * 
	 * @param category
	 * @return all the coupons that the customer buy per category
	 * @throws Exception
	 */
	public ArrayList<Coupon> getCompanyCoupons(Category category) throws Exception{
		
		ArrayList<Coupon> allCoupons = companyDB.getCompanyCoupons(this.companyID);
		ArrayList<Coupon> CouponsOfCategory =null;

		for(Coupon coupon : allCoupons) {
			if(coupon.getCategory().equals(category)){
				CouponsOfCategory = new ArrayList<Coupon>();
				break;
			}
		}
		
		if(CouponsOfCategory!=null) {
			for(Coupon coupon : allCoupons) {
				if(coupon.getCategory().equals(category)){
					CouponsOfCategory.add(coupon);
				}
			}
	}else {
		System.err.println("There is No Coupons That Meet The Requierements you Specified.");
	}
		return CouponsOfCategory;
	}
	
	/**
	 * 
	 * @param maxPrice
	 * @return all the coupons that the customer buy per price
	 * @throws Exception
	 */
	public ArrayList<Coupon> getCompanyCoupons(double maxPrice) throws Exception{
		
 		ArrayList<Coupon> allCoupons = companyDB.getCompanyCoupons(this.companyID);
		ArrayList<Coupon> CouponsOfPrice =null;

		for(Coupon coupon : allCoupons) {
			if(coupon.getPrice() <=maxPrice){
				CouponsOfPrice = new ArrayList<Coupon>();
			}
		}
		
		if(CouponsOfPrice!=null) {	
			for(Coupon coupon : allCoupons) {
			
				if(coupon.getPrice() <= maxPrice){
					CouponsOfPrice.add(coupon);
				}
			}
			}else {
				System.err.println("There is No Coupons That Meet The Requierements you Specified.");
			}
			
		return CouponsOfPrice;
	}
	
	/**
	 * 
	 * @return company detail
	 * @throws Exception
	 */
	public Company getCompanyDetails() throws Exception {
		return companyDB.getOneCompany(companyID);
	}
}
