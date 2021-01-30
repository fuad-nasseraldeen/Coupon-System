package com.devtech.jdbc.connection.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import com.devtech.exceptions.CouponException;
import com.devtech.exceptions.CustomerException;
import com.devtech.jdbc.connection.pooling.ConnectionPool;
import com.devtech.model.Coupon;
import com.devtech.model.Customer;
import com.devtech.services.CustomersDAO;


public class CustomersDBDAO implements CustomersDAO{
	ConnectionPool pool = ConnectionPool.getInstance();
	Connection connection = null;
	
	/**
	 * @param
	 * add custoemr to DB.
	 * if the title is the same of the title in the DB => don't add
	 */
	@Override
	public void addCustomer(Customer newCustomer) throws CustomerException {
		try {
			if(!isCustomerExists(newCustomer.getEmail(),newCustomer.getPassword())) {
			connection = pool.getConnection();
			String sql = "INSERT INTO devtech_project.customers (first_name,last_name,email,password) "
					+ "VALUES (?,?,?,?)";
			PreparedStatement statement = connection.prepareStatement(sql);

			statement.setString(1, newCustomer.getFirstName());
			statement.setString(2, newCustomer.getLastName());
			statement.setString(3, newCustomer.getEmail());
			statement.setString(4, newCustomer.getPassword());

			statement.executeUpdate();
			newCustomer.setId(getCustomer(newCustomer.getEmail() , newCustomer.getPassword()).getId());
			System.out.println("Customer created.\n" + newCustomer.toString());
			}
		} catch (Exception e) {
			throw new CustomerException("Failed To Create Customer Exception.");
		} finally {
			pool.releaseConnection(connection);
			connection = null;
		}
	}

	/**
	 * @param
	 * update the customer 
	 */
	@Override
	public void updateCustomer(Customer customer) throws CustomerException {

		try {
			connection = pool.getConnection();
			String sql = "UPDATE devtech_project.customers SET first_name = ? "
					+ ",last_name = ? ,email=? , password=? WHERE (id=?)";
			PreparedStatement statment = connection.prepareStatement(sql);
			statment.setString(1, customer.getFirstName());
			statment.setString(2, customer.getLastName());
			statment.setString(3, customer.getEmail());
			statment.setString(4, customer.getPassword());
			statment.setInt(5, customer.getId());
			statment.executeUpdate();
		}catch (Exception e) {
			throw new CustomerException("Incorrect Details Exception.");
		}finally {
			pool.releaseConnection(connection);
			connection = null;
		}
	}

	/**
	 * @param 
	 * delete customer from the table
	 */
	@Override
	public void deleteCustomer(int customerID) throws CustomerException {

		try {
			connection = pool.getConnection();
			String sql = "DELETE FROM devtech_project.customers WHERE id=?";
			PreparedStatement statment = connection.prepareStatement(sql);
			statment.setInt(1,customerID);
			statment.executeUpdate();
		}catch (Exception e) {
			throw new CustomerException("Incorrect Details Exception.");
		}finally {
		pool.releaseConnection(connection);
			connection=null;
		}
	}

	/**
	 * @param
	 * return one spisific customer 
	 */
	@Override
	public Customer getOneCustomer(int customerID) throws CustomerException {
		
			Customer customer = null ;
			try {
				connection = pool.getConnection();
				String sql = "SELECT * FROM devtech_project.customers where id=?";
				PreparedStatement statment = connection.prepareStatement(sql);
				statment.setInt(1, customerID);
				ResultSet resultSet = statment.executeQuery();
				if(resultSet.next()) {
					customer = new Customer(resultSet.getInt("id"), resultSet.getString("first_name"), resultSet.getString("last_name"),
							resultSet.getString("email"), resultSet.getString("password"));
				}
			}catch (Exception e) {
				throw new CustomerException("Incorrect Details Exception.\nThere is No Customer With this ID.");
			}finally {
				pool.releaseConnection(connection);
				connection = null;
				
			}
		return customer;
	}
	
	/**
	 * @param
	 * return all the customer in the table per company
	 */
	@Override
	public ArrayList<Customer> getAllCustomers() throws CustomerException {
		
		ArrayList<Customer> listOfCustomers = new ArrayList<Customer>();
		try {
		
		connection = pool.getConnection();
		String sql = "SELECT * FROM devtech_project.customers";
		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultSet = statement.executeQuery();
		while(resultSet.next()) {
			listOfCustomers.add(new Customer(resultSet.getInt("id"), resultSet.getString("first_name")
					, resultSet.getString("last_name"), resultSet.getString("email") , 
					resultSet.getString("password")));
		}
		}catch (Exception e) {
			throw new CustomerException("\nIncorrect Details Exception.\nList of Customer is not available. Please Try again");
		}finally {
			pool.releaseConnection(connection);
			connection = null;
		}
		return listOfCustomers;
		
	}
	
	/**
	 * @param
	 * return true if the customer exist
	 */
	@Override
	public boolean isCustomerExists(String email, String password) throws CustomerException {

		boolean bool = false;
		try {
			
			connection = pool.getConnection();
			String sql = "SELECT * FROM devtech_project.customers WHERE email=? AND password=?";
			PreparedStatement statment = connection.prepareStatement(sql);
			statment.setString(1, email);
			statment.setString(2, password);
			ResultSet resultSet = statment.executeQuery();
			
			if (resultSet.next()) {
				bool = true;
				return bool;
			}
			
			}catch (Exception e) {
				throw new CustomerException("Customer login Failed");
			}finally {
				pool.releaseConnection(connection);
				connection = null;
			}
			return bool;
		}
	
	/**
	 * @param
	 * return customer from DB 
	 */
	@Override
	public Customer getCustomer(String email, String password) throws CustomerException {

		Customer customer = new Customer();
		try {
			pool = ConnectionPool.getInstance();
			connection = pool.getConnection();
			String sql = "SELECT * FROM devtech_project.customers WHERE email = ? AND password = ?";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, email);
			statement.setString(2, password);
			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				customer.setId(rs.getInt("id"));
				customer.setFirstName(rs.getString("first_name"));
				customer.setLastName(rs.getString("last_name"));
				customer.setEmail(rs.getString("email"));
				customer.setPassword(rs.getString("password"));
			}
		
		} catch (Exception e) {
			throw new CustomerException("Incorrect Details Exception.\nThere is No Customer With This Email");
		} finally {
			pool.releaseConnection(connection);
			connection= null;
		}
		return customer;
	}

	/**
	 * return array list hold the coupons of the customer.
	 * @param customerID
	 * @return
	 * @throws CouponException
	 */
	public ArrayList<Coupon> getCustomerCoupons(int customerID) throws CouponException {
		
		ArrayList<Coupon> CouponsOfCustomer = new ArrayList<>();
		try {
			
			connection = pool.getConnection();
			String sql = "SELECT * FROM devtech_project.customers_vs_coupons WHERE customer_id = ?";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, customerID);
			ResultSet resultSet = statement.executeQuery();
		
			CouponsDBDAO CouponDAO = new CouponsDBDAO();
			while (resultSet.next()) {
			
				Coupon coupon = new Coupon();
				int id = resultSet.getInt("coupon_id");
				coupon = CouponDAO.getOneCoupon(id);
			
				CouponsOfCustomer.add(coupon);
				}
		} catch (Exception e) {
			throw new CouponException("Parameters invalid. Please try again.");
		} finally {
			pool.releaseConnection(connection);
			connection = null;
		}
		return CouponsOfCustomer;
	
	}
}