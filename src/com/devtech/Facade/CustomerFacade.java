package com.devtech.Facade;

import java.util.ArrayList;
import java.util.Date;
import com.devtech.exceptions.CustomerException;
import com.devtech.jdbc.connection.DAO.CompaniesDBDAO;
import com.devtech.jdbc.connection.DAO.CouponsDBDAO;
import com.devtech.jdbc.connection.DAO.CustomersDBDAO;
import com.devtech.model.Category;
import com.devtech.model.Coupon;
import com.devtech.model.Customer;
 
public class CustomerFacade extends ClientFacade{
	
	private CustomersDBDAO customerDB;
	
	private CouponsDBDAO couponDB;

	private int customerID;
	
	private Customer customer ; 
	
	/**
	 * @return the customerID
	 */
	public int getCustomerID() {
		return customerID;
	}

	/**
	 * @param customerID the customerID to set
	 */
	public void setCustomerID(int customerID) {
		this.customerID = customerID;
	}

	/**
	 * initialization the constructor
	 */
	public CustomerFacade() {

		customerDB = new CustomersDBDAO();
		couponDB = new CouponsDBDAO();
	}
	
	/**
	 * login method 
	 * if the login successfully save the details customer in our customer object 
	 */
	@Override
	public boolean login(String email, String password) throws Exception {
		boolean ifLogin = customerDB.isCustomerExists(email, password);
		if (ifLogin) {
			customer = customerDB.getCustomer(email , password);
			this.customerID = customer.getId();
			System.out.printf("Welcome %s %s.\n" , customer.getFirstName() , customer.getLastName());
		}
			return ifLogin;
	}
	/**
	 * cheack if the coupon is not duplicated for the user.
	 * check if the coupn end date is not expired.
	 * check if the amount of the coupon is available.
	 * convert the date to string that why we can comparable with our coupon end date.
	 * @param coupon
	 * @throws Exception
	 */
	public void purchaseCoupon(Coupon coupon) throws Exception  {
		
		if(coupon.getId()==0) {
			ArrayList<Coupon> allCompanyCoupons = new CompaniesDBDAO().getCompanyCoupons(coupon.getCompanyId());
			for(Coupon coup: allCompanyCoupons) {
				if(coupon.getTitle().equals(coup.getTitle())){
					coupon.setId(coup.getId());
				}
			}
		}
	
		for(Coupon coup: getCustomerCoupons()) {
			if(coupon.getId()==coup.getId()) {
				throw new CustomerException("This customer bought this coupon! , Can't buy it again.");
			}
		}
		
		if(coupon.getEndDate().before(new Date())) 
			throw new CustomerException("Expired Date!");
		
		if (couponDB.getOneCoupon(coupon.getId()).getAmount()<=0) 
			throw new CustomerException("This Coupon Not Available. Please Try later.");
		
		coupon.setAmount(coupon.getAmount()-1);
		couponDB.updateCoupon(coupon);
		couponDB.addCouponPurchase(customerID, coupon.getId());
	}

	/**
	 *
	 * @return all the coupons that the customer buy
	 * @throws Exception
	 */
		public ArrayList<Coupon> getCustomerCoupons() throws Exception{
			
			return customerDB.getCustomerCoupons(customerID);
		}
		
		/**
		 * 
		 * @param category
		 * @return all the coupons that the customer buy per category
		 * @throws Exception
		 */
		public ArrayList<Coupon> getCustomerCoupons(Category category) throws Exception{
			ArrayList<Coupon> allCoupons = customerDB.getCustomerCoupons(this.customerID);
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
			System.err.println("There are No Coupons That Meet The Requierements you Specified.");
		}
			return CouponsOfCategory;
		}
		
		/**
		 * 
		 * @param maxPrice
		 * @return all the coupons that the customer buy per price
		 * @throws Exception
		 */
		public ArrayList<Coupon> getCustomerCoupons(double maxPrice) throws Exception{
			
			ArrayList<Coupon> allCoupons = customerDB.getCustomerCoupons(this.customerID);
			ArrayList<Coupon> CouponsOfPrice =null;

			for(Coupon coupon : allCoupons) {
				if(coupon.getPrice() <=maxPrice){
					CouponsOfPrice = new ArrayList<Coupon>();
					break;
				}
			}
			if(CouponsOfPrice!=null) {	
				for(Coupon coupon : allCoupons) {
				
					if(coupon.getPrice() <= maxPrice){
						CouponsOfPrice.add(coupon);
					}
				}
				}else {
					System.err.println("There are No Coupons That Meet The Requierements you Specified.");
				}
				
			
			return CouponsOfPrice;
		}
		
		/**
		 * 
		 * @return customer detail
		 * @throws Exception
		 */
		public Customer getCustomerDetails() throws Exception {
			return customerDB.getOneCustomer(customerID);
		}
}