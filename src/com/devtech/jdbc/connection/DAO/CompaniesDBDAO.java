package com.devtech.jdbc.connection.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import com.devtech.exceptions.CompanyException;
import com.devtech.exceptions.CouponException;
import com.devtech.jdbc.connection.pooling.ConnectionPool;
import com.devtech.model.Category;
import com.devtech.model.Company;
import com.devtech.model.Coupon;
import com.devtech.services.CompaniesDAO;

public class CompaniesDBDAO implements CompaniesDAO {
	
	private ConnectionPool pool = ConnectionPool.getInstance();
	
	private Connection connection;

	/**
	 * add the company to the table
	 */
	@Override
	public void addCompany(Company addCompany) throws CompanyException {
		
		try {
			connection = pool.getConnection();
			String sql = "INSERT INTO devtech_project.companies (name, email, password) VALUES (?,?,?)";
			PreparedStatement statement = connection.prepareStatement(sql);
			
			statement.setString(1, addCompany.getName());
			statement.setString(2, addCompany.getEmail());
			statement.setString(3, addCompany.getPassword());
			statement.executeUpdate();
			Company company = getCompany(addCompany.getEmail(), addCompany.getPassword());
			addCompany.setId(company.getId());
			System.out.printf("\n %s Company added Succeccfully.\n" , addCompany.getName());
		} catch (Exception e) {
			throw new CompanyException("Can't add this Company, Please try again.");
		} finally {
			pool.releaseConnection(connection);
			connection = null;
		}
	}

	/**
	 * update the company
	 */
	@Override
	public void updateCompany(Company company) throws CompanyException {
		try {
			connection = pool.getConnection();
			String sql = "UPDATE devtech_project.companies SET email = ?, password = ? WHERE (id = ?)";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, company.getEmail());
			statement.setString(2, company.getPassword());
			statement.setInt(3, company.getId());
			statement.executeUpdate();
			company.setId(company.getId());
		} catch (Exception e) {
			throw new CompanyException("company id doesn't exist");
		} finally {
			pool.releaseConnection(connection);
			connection = null;
		}
	}
	
	/**
	 * delete the company
	 */
	@Override
	public void deleteCompany(int companyID) throws CompanyException {
		
		try {
			connection = pool.getConnection();
			String sql = "DELETE FROM devtech_project.companies WHERE id=?";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, companyID);
			statement.executeUpdate();
		}catch (Exception e) {
			throw new CompanyException("Can't Delete this Company, Please try again.");
		}finally {
			pool.releaseConnection(connection);
			connection = null;
		}
	}
	
	/**
	 * @param
	 * return array list holds all the companies
	 */
	@Override
	public ArrayList<Company> getAllCompanies() throws CompanyException {
	
		ArrayList<Company> getAllCompany = new ArrayList<Company>();
		try {
			connection = pool.getConnection();
			String sql ="SELECT * FROM devtech_project.companies";
			PreparedStatement statment = connection.prepareStatement(sql);
			ResultSet resultSet = statment.executeQuery();
			while(resultSet.next()) {
				Company company = new Company(resultSet.getInt("id") , resultSet.getString("name") , resultSet.getString("email"),
						resultSet.getString("password"));
				company.setCoupons(getCompanyCoupons(company.getId()));
				getAllCompany.add(company);
			}
		}catch (Exception e) {
			throw new CompanyException("List of Companies are Invalid, Please Try Again");
		}finally {
			pool.releaseConnection(connection);
			connection = null;
		}
		return getAllCompany;
	}

	

	
	/**
	 * return one specific company
	 */
	@Override
	public Company getOneCompany(int companyID) throws CompanyException {
		
		Company company = new Company();
		try {
			connection = pool.getConnection();
			String sql = "SELECT * FROM devtech_project.companies WHERE id = ?";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, companyID);
			ResultSet resultSet = statement.executeQuery();
			
			if(!resultSet.next()) 
				throw new CompanyException("Company or Company ID doesn't Exist.");
			
			company.setName(resultSet.getString("name"));
        	company.setEmail(resultSet.getString("email"));
        	company.setPassword(resultSet.getString("password"));
        	company.setId(companyID);
        	company.setCoupons(getCompanyCoupons(company.getId()));
			
		} catch (Exception e) {
			throw new CompanyException("Company id doesn't Exist.");
		} finally {
			pool.releaseConnection(connection);
			connection = null;
		}
		return company;
	}
	
	/**
	 * @param
	 * return true if the company exist
	 */
	@Override
	public  boolean isCompanyExists(String email, String password) throws CompanyException {
	
		boolean bool = false;
		try {
			connection = pool.getConnection();
			String sql = "SELECT * FROM devtech_project.companies WHERE email = ? AND password = ?";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, email);
			statement.setString(2, password);
			ResultSet rs = statement.executeQuery();
			if (rs.next()) 
				bool = true;
		} catch (Exception e) {
			throw new CompanyException("Company login Failed");
		} finally {
			pool.releaseConnection(connection);
			connection= null;
		}
		return bool;
	}
	
	/**
	 * @param
	 * return specific company per email,password
	 */
	@Override
	public Company getCompany(String email , String password) throws CompanyException {
		
		Company company = new Company();
		try {
			pool = ConnectionPool.getInstance();
			connection = pool.getConnection();
			String sql = "SELECT * FROM devtech_project.companies WHERE email = ? AND password = ?";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, email);
			statement.setString(2, password);
			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				company.setId(rs.getInt("id"));
				company.setName(rs.getString("name"));
				company.setEmail(rs.getString("email"));
				company.setPassword(rs.getString("password"));
				company.setCoupons(getCompanyCoupons(company.getId()));
			}
		} catch (Exception e) {
			throw new CompanyException("There is No Company With This Email");
		} finally {
			pool.releaseConnection(connection);
			connection= null;
		}
		return company;
	}
	

	
	/**
	 * @param
	 * return array list holds all the coupons that belong to this company
	 */
	@Override
	public ArrayList<Coupon> getCompanyCoupons(int companyID)throws CouponException {
		ArrayList<Coupon> CouponsOfCompany = new ArrayList<>();
		try {
			connection = pool.getConnection();
			String sql = "SELECT * FROM devtech_project.coupons WHERE company_id = ?";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, companyID);
			ResultSet resultSet = statement.executeQuery();
		
			while (resultSet.next()) {
				Coupon coupon = new Coupon();
				coupon.setId(resultSet.getInt("id"));
				coupon.setCompanyId(resultSet.getInt("company_id"));
				coupon.setCategory(Category.valueOf(resultSet.getString("category_id")));
				coupon.setTitle(resultSet.getString("title"));
				coupon.setDescription(resultSet.getString("description"));
				coupon.setStartDate(resultSet.getDate("start_date"));
				coupon.setEndDate(resultSet.getDate("end_Date"));
				coupon.setAmount(resultSet.getInt("amount"));
				coupon.setPrice(resultSet.getDouble("price"));
				coupon.setImage(resultSet.getString("image"));
				CouponsOfCompany.add(coupon);
				}
		} catch (Exception e) {
			throw new CouponException("Parameters invalid. Please try again");
		} finally {
			pool.releaseConnection(connection);
			connection = null;
		}
		return CouponsOfCompany;
	
	}
}