package com.devtech.Facade;

public class LoginManager {

	/**
	 *  variable use to initialization the instance of the class
	 */
	private static LoginManager singleton = null;
	
	/**
	 * private constractor
	 */
	private LoginManager() {

	}

	/**
	 * make an instance - only one instance of class is active.
	 * @return
	 */
	public static LoginManager getInstance() {
		singleton = new LoginManager();
		return singleton;
	}

	/**
	 * @param
	 * @param email
	 * @param password
	 * @param clientType - admin - customer - company
	 * @return - return true if the client type and the login (email,password) correct
	 * @throws Exception
	 */
	public ClientFacade Login(String email , String password , ClientType clientType) throws Exception{
	
		ClientFacade clientFacade = null;
		if (clientType == ClientType.Company) {
			clientFacade = new CompanyFacade();
			
		} else if (clientType == ClientType.Customer) {
			clientFacade = new CustomerFacade();
	
		} else if (clientType == ClientType.Administrator) {
				clientFacade = new AdminFacade();	
		}
			if(clientFacade != null){
				if(clientFacade.login(email, password)) {
					System.out.println("login Successfully!");
				}else
					throw new Exception("Failed Login, Invalid Parameters.");
			}else 
				throw new Exception("There is Wrong in ClientType. ");
			
		return clientFacade;
		
		}
	}