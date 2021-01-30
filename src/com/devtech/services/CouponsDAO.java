package com.devtech.services;

import java.util.ArrayList;
import java.util.Set;

import com.devtech.exceptions.CouponException;
import com.devtech.jdbc.connection.pooling.ConnectionPool;
import com.devtech.model.Coupon;

public interface CouponsDAO {
	
	ConnectionPool pool = null;
	
	public Set<Coupon> getAllCoupons() throws Exception;
	
	public Coupon getOneCoupon(int couponID) throws Exception;
	
	public void addCouponPurchase(int customerID, int couponID) throws Exception;
	
	public void addCoupon(Coupon coupon) throws Exception;

	public void deleteCoupon(int couponID) throws Exception;

	public void updateCoupon(Coupon coupon) throws Exception;

	public void deleteCouponPurchase(int couponId) throws Exception;

	public Coupon getCoupon(String title) throws Exception;

	public boolean isCouponExistsInPurchaseTable(int couponID) throws Exception;
	
	public ArrayList<Coupon> getCouponsTillDate(String dateInString) throws CouponException;

	
}
