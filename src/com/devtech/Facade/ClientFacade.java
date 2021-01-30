/**
 * 
 */
package com.devtech.Facade;

import com.devtech.services.CompaniesDAO;
import com.devtech.services.CouponsDAO;
import com.devtech.services.CustomersDAO;

/**
 * @author fuads
 *
 */
public abstract class ClientFacade {

	protected CompaniesDAO companiesDAO;
	protected CustomersDAO customersDAO;
	protected CouponsDAO couponsDAO;
	
	public abstract boolean login(String email , String password) throws Exception;
	
}
