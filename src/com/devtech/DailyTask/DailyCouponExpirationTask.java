package com.devtech.DailyTask;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.devtech.jdbc.connection.DAO.CouponsDBDAO;
import com.devtech.model.Coupon;

public class DailyCouponExpirationTask implements Runnable{

	private CouponsDBDAO couponDB;
	
	private Boolean quit = false;
	
	private long millis = 60 * 60 * 24 * 1000;
	
	public DailyCouponExpirationTask() {
		
		try {
			couponDB = new CouponsDBDAO();
		}catch(Exception e)	{
			System.err.println(e.getMessage() + ", Failed to Establish Connection to DB");
		}
	}

	// Runs in separate thread
	@Override
	public void run() {
		
		while (!quit) {
			System.out.println("start daily check");
			CouponsDailyJob();
			
			try {
				Thread.sleep(millis);
			} catch (InterruptedException e) {
				System.err.println("Daily remove thread failed. Contact us");
			}
		}
	}
	
	// Removes irrelevant coupons from the system
	private void CouponsDailyJob() {
	
		try {
			Date date = new Date();
			String expectedPattern = "dd-MM-yyyy";
			SimpleDateFormat formatter = new SimpleDateFormat(expectedPattern);
			String dateInString = formatter.format(date);
			ArrayList<Coupon> CouponsTilDate =  couponDB.getCouponsTillDate(dateInString);
			
			if(CouponsTilDate != null) {
				for(Coupon expiredCoupon: CouponsTilDate) {
					try {
						couponDB.deleteCoupon(expiredCoupon.getId());
						if(couponDB.isCouponExistsInPurchaseTable(expiredCoupon.getId()))
							couponDB.deleteCouponPurchase(expiredCoupon.getId());
						System.out.println("Daily remove thread Succeed.");
					} catch (Exception e) {
						System.out.println("Daily remove thread failed. Contact us");
					}
				}
			}else {
				System.out.println("Did Not Find Any Expired Coupons.");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage() + " ,Failed Access to Coupon DB");
		}	
	}
	
	// Stop The thread
	public void stop() {
		
		quit = true;
	}
}
