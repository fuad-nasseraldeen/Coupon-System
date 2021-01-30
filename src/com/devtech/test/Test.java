package com.devtech.test;

import java.util.ArrayList;

import javax.swing.JOptionPane;

import com.devtech.DailyTask.DailyCouponExpirationTask;
import com.devtech.Facade.AdminFacade;
import com.devtech.Facade.ClientType;
import com.devtech.Facade.CompanyFacade;
import com.devtech.Facade.CustomerFacade;
import com.devtech.Facade.LoginManager;
import com.devtech.jdbc.connection.DAO.CouponsDBDAO;
import com.devtech.jdbc.connection.pooling.ConnectionPool;
import com.devtech.model.Category;
import com.devtech.model.Company;
import com.devtech.model.Coupon;
import com.devtech.model.Customer;

public class Test {
	
	static LoginManager singleton = LoginManager.getInstance();
	public static void testAll() throws Exception {
		
		space();
		AdminFacade admin = (AdminFacade) singleton.Login("admin@admin.com", "admin", ClientType.Administrator);

		Company company1 = new Company("Amdocs", "Amdocs@.com", "1234");
		admin.addCompany(company1);
		System.out.println(admin.getOneCompany(admin.getCompany(company1).getId()));
		space();
		
		CompanyFacade companyFacade = (CompanyFacade) singleton.Login("Amdocs@.com", "1234" , ClientType.Company);
		Company company2=new Company("amazon", "amazon@gmail.com", "1234");
		admin.addCompany(company2);
		System.out.println(admin.getAllCompanies());
		space();
		admin.DeleteCompany(company2.getId());
		System.out.println(admin.getAllCompanies());
		space();
		
		
//		Pattern Date "MM/dd/yyyy"
		Coupon c1= new Coupon(companyFacade.getCompanyID(), Category.Electricity,"title1",
				 "description1" , CouponsDBDAO.StringToSQLDate("01/01/2020") ,CouponsDBDAO.StringToSQLDate("04/12/2021"),1,250,"C:/Users/fuads/Pic/pic1.JPG");
			
		Coupon c2= new Coupon(companyFacade.getCompanyID(), Category.Food,"title2",
					 "description2" , CouponsDBDAO.StringToSQLDate("01/05/2021") ,CouponsDBDAO.StringToSQLDate("01/05/2021"),2,7.99,"C:/Users/fuads/Pic/pic2.JPG");

		Coupon c3= new Coupon(companyFacade.getCompanyID(), Category.Food,"title3",
					 "description3" , CouponsDBDAO.StringToSQLDate("01/05/2021") ,CouponsDBDAO.StringToSQLDate("01/30/2021"),10,99.99,"C:/Users/fuads/Pic/pic3.JPG");
			
		Coupon c4= new Coupon(companyFacade.getCompanyID(), Category.Electricity,"title4",
					 "description4" , CouponsDBDAO.StringToSQLDate("01/05/2020") ,CouponsDBDAO.StringToSQLDate("02/01/2021"),7,5.99,"C:/Users/fuads/Pic/pic4.JPG");
			
		Coupon c5= new Coupon(companyFacade.getCompanyID(), Category.Restaurant,"title5",
					 "description5" , CouponsDBDAO.StringToSQLDate("01/05/2021") ,CouponsDBDAO.StringToSQLDate("02/10/2021"),4,12.5,"C:/Users/fuads/Pic/pic5.JPG");

		Coupon c6= new Coupon(companyFacade.getCompanyID(), Category.Restaurant,"title6",
					 "description6" ,CouponsDBDAO.StringToSQLDate("12/09/2020") ,CouponsDBDAO.StringToSQLDate("01/29/2021"),2,49.99,"C:/Users/fuads/Pic/pic6.JPG");
			
		System.out.println(companyFacade.getCompanyDetails());
		space();
	
		companyFacade.addCoupon(c1);
		companyFacade.addCoupon(c2);
		companyFacade.addCoupon(c3);
		companyFacade.addCoupon(c4);
		companyFacade.addCoupon(c5);
		companyFacade.addCoupon(c6);
		companyFacade.deleteCoupon(companyFacade.getCoupon(c6).getId());
		
		space();
		ArrayList<Coupon> companyCoupons = companyFacade.getCompanyCoupons(-1);
		if(companyCoupons !=null) {
			System.out.println("List Of all The Coupon's");
			for(Coupon coupon:companyCoupons) {
				System.out.println(coupon + "\n");
			}
		}
		space();
		Customer customer =new Customer("fuad", "sami", "fuad@gmail.com", "1234");
		Customer customer2 =new Customer("adi", "ozlo", "salim@gmail.com", "1234");
		admin.addCustomer(customer);
		admin.addCustomer(customer2);
		admin.getAllCustomers();
		
		System.out.println(admin.getCustomer(customer));
		space();
		Customer customerUpdated =new Customer(admin.getCustomer(customer).getId(),"fuad", "sami", "fuad@gmail.com", "1234");
		admin.updateCustomer(customerUpdated);
		
		System.out.println(admin.getOneCustomer(admin.getCustomer(customer).getId()));
		admin.deleteCustomer(admin.getCustomer(customer2).getId());
		admin.getAllCustomers();
		space();
		
		CustomerFacade customerFacade = (CustomerFacade) singleton.Login("fuad@gmail.com", "1234" , ClientType.Customer);
		customerFacade.purchaseCoupon(c1);
		customerFacade.purchaseCoupon(c2);
		customerFacade.purchaseCoupon(c3);
		customerFacade.purchaseCoupon(c4);
		customerFacade.purchaseCoupon(c5);		
		space();

		ArrayList<Coupon> customerCoupons = customerFacade.getCustomerCoupons(Category.Electricity);
		if(customerCoupons !=null) {
			System.out.println("List Of all The Coupon's");
			for(Coupon coupon:customerCoupons) {
				System.out.println(coupon + "\n");
			}
		}else
			System.out.println("There is No Coupon's.");
		space();
		
		ConnectionPool.getInstance().closeAllConnections();
		
		//if istablish the Daily Job or not..
		int dialogButton = JOptionPane.YES_NO_OPTION;
	    int StopTheSystem = JOptionPane.showConfirmDialog (null, "Would You Like to Log out from the system?","Warning",dialogButton);

	    if (StopTheSystem == JOptionPane.YES_OPTION) {
	    	DailyCouponExpirationTask job = new DailyCouponExpirationTask();
	    	job.run();
	    }
	}
	public static void space() {
		System.out.println();
		System.out.println("* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *");
		System.out.println();
	}
}
