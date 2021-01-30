package com.devtech.services;

import java.util.ArrayList;

import com.devtech.jdbc.connection.pooling.ConnectionPool;
import com.devtech.model.Customer;

public interface CustomersDAO {
	
	ConnectionPool pool = null;
	
	
	public void addCustomer(Customer customer) throws Exception;
	
	public void deleteCustomer(int customerID)throws Exception;
	
	public Customer getOneCustomer(int customerID)throws Exception;

	public void updateCustomer(Customer customer) throws Exception;

	public ArrayList<Customer> getAllCustomers()throws Exception;

	public boolean isCustomerExists(String email, String password) throws Exception;

	public Customer getCustomer(String email, String password) throws Exception;

	

	
}
