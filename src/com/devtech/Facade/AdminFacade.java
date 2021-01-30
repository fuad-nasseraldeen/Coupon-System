package com.devtech.Facade;

import java.util.ArrayList;
import java.util.List;
import com.devtech.exceptions.CompanyException;
import com.devtech.exceptions.CustomerException;
import com.devtech.jdbc.connection.DAO.CompaniesDBDAO;
import com.devtech.jdbc.connection.DAO.CouponsDBDAO;
import com.devtech.jdbc.connection.DAO.CustomersDBDAO;
import com.devtech.model.Company;
import com.devtech.model.Coupon;
import com.devtech.model.Customer;


public class AdminFacade extends ClientFacade{

	private String email = "admin@admin.com";
	
	private String password = "admin";
	
	private CustomersDBDAO customerDB ;
	
	private CompaniesDBDAO companyDB ;
	
	private CouponsDBDAO couponDB ;
	
	
	/**
	 * initialization the constructor
	 */
	public AdminFacade() {

		customerDB = new CustomersDBDAO();
		companyDB = new CompaniesDBDAO();
		couponDB = new CouponsDBDAO();
		
	}

	/**
	 * login method 
	 * if the login successfully save the details customer in our customer object 
	 */
	@Override
	public boolean login(String email, String password) {

		if(email.equals(this.email) && password.equals(this.password)) {
			System.out.println("Welcome Admin.\n");
			return true;
		}
		return false;
	}
	
	/**
	 * add company to the table 
	 * if the name of the company is already exist - don't add
	 * @param addCompany
	 * @throws CompanyException
	 */
	public void addCompany(Company addCompany)throws CompanyException {
		List<Company> companies = companyDB.getAllCompanies();
		for (Company comp : companies) {
			if (comp.getName().equals(addCompany.getName())) {
				throw new CompanyException("Company Already Exist Exception " +addCompany.getName());
			}
			if (comp.getEmail().equals(addCompany.getEmail())) {
				throw new CompanyException("Failed To Create Company Exception.");
			}
		}
		companyDB.addCompany(addCompany);
		
	}
	
	/**
	 * @return the company
	 * @throws Exception 
	 */
	public Company getCompany(Company company) throws Exception {
		ArrayList<Company> allCompanies = companyDB.getAllCompanies();
		for(Company comp : allCompanies) {
			if(comp.getName().equals(company.getName()))
				company = comp;
				company.setId(comp.getId());
				company.setCoupons(companyDB.getCompanyCoupons(company.getId()));
				break;
		}
		return company;
	}
	
	/**
	 * 
	 * @return all the companies
	 * @throws CompanyException
	 */
	public ArrayList<Company> getAllCompanies() throws CompanyException{
		return companyDB.getAllCompanies();
		
	}
	
	
	 /**
	  * 
	  * @param companyID
	  * @return specific company 
	  * @throws CompanyException
	  */
	public Company getOneCompany(int companyID) throws CompanyException {
			
		return companyDB.getOneCompany(companyID);
		
	}

	/**
	 * udpate the company 
	 * @param company
	 * @throws CompanyException
	 */
	public void updateCompany(Company company) throws CompanyException {
		List<Company> companies = companyDB.getAllCompanies();
		for (Company comp : companies) {
			if (comp.getName().equals(company.getName())){
				companyDB.updateCompany(company);
				System.out.println("Company updated Successfully");
			}		
			else 
				throw new CompanyException("Incorrect Details Exception. \nNAME - cannot change the Company `NAME`\n");
		}
	}

	/**
	 * delete company and the coupons that belong to this company if exist
	 * @param companyID
	 * @throws Exception
	 */
	public void DeleteCompany(int companyID) throws Exception {
		
		ArrayList<Coupon> couponsCompany = companyDB.getCompanyCoupons(companyID);
		if(couponsCompany.size() == 0) {
			System.out.println("There is No Coupons That Belong To This Company Therefore it Can be Deleted.");
			companyDB.deleteCompany(companyID);
			System.out.println("Company Deleted Successfully.");
		}else {
			
			for(Coupon coupon: couponsCompany) {
				couponDB.deleteCouponPurchase(coupon.getId());
			}
			for(Coupon coupon: couponsCompany) {
				couponDB.deleteCoupon(coupon.getId());
			}
			
			companyDB.deleteCompany(companyID);
			System.out.println("Company Deleted Successfully.");
		}
	}
	
	/**
	 * add customer 
	 * if the email of the user exist in our table => don't add
	 * @param customer
	 * @throws CustomerException
	 */
	public void addCustomer(Customer customer) throws CustomerException {
		List<Customer> customers = customerDB.getAllCustomers();
		for (Customer cust : customers) {
			if (cust.getEmail().equals(customer.getEmail())) {
				throw new CustomerException("\nIncorrect Details Exception.\nEmail: " + customer.getEmail() + " Already Exit.");
			}
		}
		customerDB.addCustomer(customer); 
	}
	
	/**
	 * 
	 * @param customer
	 * @return specific customer 
	 * @throws Exception
	 */
	public Customer getCustomer(Customer customer) throws Exception {
		ArrayList<Customer> allCustomers = customerDB.getAllCustomers();
		for(Customer cu : allCustomers) {
			if(cu.getEmail().equals(customer.getEmail()))
				customer = cu;
				customer.setId(cu.getId());
				customer.setCoupons(customerDB.getCustomerCoupons(customer.getId()));
				break;
		}
		return customer;
	}
	
	/**
	 * delete customer and the coupons that he buy
	 * @param customerID
	 * @throws Exception
	 */
	public void deleteCustomer(int customerID) throws Exception {
		
		ArrayList<Coupon> couponsCustomer = customerDB.getCustomerCoupons(customerID);
		
		if(couponsCustomer.size() == 0) {
			System.out.println("There is No Coupons That Belong To This Customer Therefore it Can be Deleted.");
			customerDB.deleteCustomer(customerID);
			System.out.println("Customer Deleted Successfully.");
		}else {
			for(Coupon coupon: couponsCustomer) {
				couponDB.deleteCouponPurchase(coupon.getId()); 
			}
			customerDB.deleteCustomer(customerID);
			System.out.println("Customer Deleted Successfully.");
		}
		
	}
	
	/**
	 * 
	 * @param customerID
	 * @return specific customer 
	 * @throws Exception
	 */
	public Customer getOneCustomer(int customerID) throws Exception {
		return customerDB.getOneCustomer(customerID);
		
	}

	/**
	 * update the customer 
	 * @param customer
	 * @throws Exception
	 */
	public void updateCustomer(Customer customer) throws Exception {
		
		ArrayList<Customer> allCustomer = customerDB.getAllCustomers();
	
		for (Customer cu : allCustomer) {
			if (cu.getId() == customer.getId()) {
				customerDB.updateCustomer(customer);
				System.out.println("Customer updated Successfully");
			}
		}
	}
	
	/**
	 * 
	 * @return return all the customer in our table 
	 * @throws Exception
	 */
	public ArrayList<Customer> getAllCustomers() throws Exception{
		return customerDB.getAllCustomers();	
	}
}