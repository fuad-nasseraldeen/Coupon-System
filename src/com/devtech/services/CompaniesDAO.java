package com.devtech.services;

import java.util.ArrayList;
import com.devtech.exceptions.CompanyException;
import com.devtech.exceptions.CouponException;
import com.devtech.jdbc.connection.pooling.ConnectionPool;
import com.devtech.model.Company;
import com.devtech.model.Coupon;

public interface CompaniesDAO {
	
	ConnectionPool pool = null;
	
	public void addCompany(Company company) throws CompanyException;
	
	public ArrayList<Company> getAllCompanies() throws CompanyException;
	
	public Company getOneCompany(int companyID) throws CompanyException;

	public void deleteCompany(int companyID) throws CompanyException;

	public void updateCompany(Company company) throws CompanyException;

	public boolean isCompanyExists(String email, String password) throws CompanyException;

	public ArrayList<Coupon> getCompanyCoupons(int companyID) throws CouponException;
	
	public Company getCompany(String email, String password) throws CompanyException;

}
