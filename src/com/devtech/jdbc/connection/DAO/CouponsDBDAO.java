package com.devtech.jdbc.connection.DAO;

import com.devtech.exceptions.CouponException;
import com.devtech.jdbc.connection.pooling.ConnectionPool;
import com.devtech.model.Category;
import com.devtech.model.Coupon;
import com.devtech.services.CouponsDAO;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;


/**
* @Description:
	 * CouponDBDAO holds all the methods refers to Coupons table.

	 */
	 
public class CouponsDBDAO implements CouponsDAO {
	
	private ConnectionPool pool = ConnectionPool.getInstance();
	Connection connection = null;

	/**
	 * @param
	 * A method that gets Coupon object to construct an SQL query from it's values
	 * and insert the object values into the related table.
	 * @throws Exception 
	 * */
	@Override
	public void addCoupon(Coupon coupon) throws CouponException{

		try {
				connection = pool.getConnection();
				
				String sql = "INSERT INTO devtech_project.coupons (company_id , category_id , "
						+ "title , description , start_date , end_date , amount , price , image) VALUES (?,?,?,?,?,?,?,?,?)";
				PreparedStatement statement = connection.prepareStatement(sql);
				
				statement.setInt(1,coupon.getCompanyId());
				statement.setString(2, coupon.getCategory().toString());
				statement.setString(3, coupon.getTitle());
				statement.setString(4, coupon.getDescription());
				statement.setDate(5,   coupon.getStartDate());
				statement.setDate(6, coupon.getEndDate());
				statement.setInt(7, coupon.getAmount());
				statement.setDouble(8, coupon.getPrice());
				statement.setString(9, coupon.getImage());
				statement.execute();
				
				//set company ID
				coupon.setCompanyId(coupon.getCompanyId());
				
				statement.close();
				
				Set<Coupon> allCoupons = getAllCoupons();
				for(Coupon coup: allCoupons) {
					if(coupon.getTitle().equals(coup.getTitle()))
						coupon.setId(coup.getId());
				}
				
				System.out.println("\nCoupon created\n" + coupon.toString());
		} catch (SQLException e) {
			throw new CouponException("\nCoupon parameters are invaled, or exist. Please try again.");
		}finally {
			pool.releaseConnection(connection);
			connection = null;
		}
	}
	
/**
 * @param return spisific coupon holds unique titke
 */
	@Override
	public Coupon getCoupon(String title) throws CouponException {
		
		Coupon coupon = null;
		try {
			connection = pool.getConnection();
			String sql = "SELECT * FROM devtech_project.coupons WHERE id= (SELECT MAX(id) FROM coupons) AND title =?";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, title);
			ResultSet rs = statement.executeQuery();
			while(rs.next()) {
				if(rs.getString("title").equals(title)){
					coupon = new Coupon(rs.getInt("id"), rs.getInt("company_id"), 
							Category.valueOf(rs.getString("category_id")), rs.getString("title"),
							rs.getString("description"),rs.getDate("start_date"),
							rs.getDate("end_date"), rs.getInt("amount"), 
							rs.getDouble("price"), rs.getString("image"));
				}
			}
		} catch (SQLException e) {
			throw new CouponException("Coupon parameters are invaled, or exists. Please try again");
		}finally {
			pool.releaseConnection(connection);
			connection = null;
		}
		return coupon;
	}

	/**
	 * @throws Exception 
	 * @Param
	 *  update the object values into the related table.
	 *  */
	@Override
	public void updateCoupon(Coupon coupon) throws CouponException{	
		
		try {
			System.out.println(coupon);
			connection = pool.getConnection();

			String sql = "SELECT * FROM devtech_project.coupons WHERE id = ? ";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1, coupon.getId());
			ResultSet rs = ps.executeQuery();
			if (!rs.next()) {
				throw new CouponException("Coupon id doesn't exist");
			}
			
			String query = "UPDATE devtech_project.coupons SET"
					+ " end_date=? ,amount=? ,description=?,price=? WHERE (id=?)";
			PreparedStatement statement1 = connection.prepareStatement(query);
			
			statement1.setDate(1, coupon.getEndDate());
			statement1.setInt(2, coupon.getAmount());
			statement1.setString(3, coupon.getDescription());
			statement1.setDouble(4, coupon.getPrice());
			statement1.setInt(5, coupon.getId());		
			statement1.executeUpdate();
			System.out.println(coupon.getDescription());
			statement1.close();
			
		} catch (Exception e) {
			throw new CouponException("Parameters invalid. Please try again");

		}finally {
			pool.releaseConnection(connection);
			connection = null;
		}
	}
	

	/**
	 * @throws Exception 
	 * @param 
	 * Remove Coupon.
	 * Will remove Coupons from coupon tables depends on coupon_ID and company_ID. 
	 */
	@Override
	public void deleteCoupon(int couponID) throws CouponException {

		try {
			connection = pool.getConnection();

			
			String sql = "SELECT * FROM  devtech_project.coupons WHERE id=?";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, couponID);
			ResultSet rs = statement.executeQuery();
			if(rs.next()) {
				//Deleting the table
				String query = "DELETE FROM devtech_project.coupons WHERE id=?";
				PreparedStatement stat = connection.prepareStatement(query);
				stat.setInt(1, couponID);
				stat.executeUpdate();
			}
			else
				throw new CouponException("Coupon ID doesn't exist");

		} catch (SQLException e) {
			throw new CouponException("Parameters invalid. Please try again");
		}finally {
			pool.releaseConnection(connection);
			connection=null;
		}
	}
	/**
	 * @Description:
	 * A method that return all coupons
	 * the method send a query to the database and retrieves results.
	 * with ResultSet and While loop the method construct Coupon object and add it the the collection. 
	 * @return Set<Coupon> set
	 * @throws Exception 
	 */
	@Override
	public synchronized Set<Coupon> getAllCoupons() throws CouponException {
		
		Set<Coupon> set = new HashSet<>();
		try{
			connection = pool.getConnection();
			
			String sql = "SELECT * FROM devtech_project.coupons";
			Statement statement = connection.createStatement(); 
			ResultSet rs = statement.executeQuery(sql);
			while (rs.next()) {
				Coupon coupon = new Coupon();
				coupon.setId(rs.getInt("id"));
				coupon.setCompanyId(rs.getInt("company_id"));
				coupon.setCategory(Category.valueOf(rs.getString("category_id")));
				coupon.setTitle(rs.getString("title"));
				coupon.setDescription(rs.getString("description"));
				coupon.setStartDate(rs.getDate("start_date"));
				coupon.setEndDate(rs.getDate("end_Date"));
				coupon.setAmount(rs.getInt("amount"));
				coupon.setPrice(rs.getDouble("price"));
				coupon.setImage(rs.getString("image"));
				set.add(coupon);
				}
			} catch (SQLException e) {
				throw new CouponException("Parameters invalid. Please try again");
			} finally {
			pool.releaseConnection(connection);
			connection = null;
		}
		return set;
	}
	
	/**
	 * @throws Exception 
	 * @Description:
	 * A method that return coupon By id
	 * the method send a query to the database and retrieves results.
	 */
	@Override
	public Coupon getOneCoupon(int couponID) throws CouponException {
		Coupon c1 = null;
		try{
			connection = pool.getConnection();
			String sql = "SELECT * FROM devtech_project.coupons WHERE id=?";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, couponID);
			ResultSet resultSet = statement.executeQuery();
		
			if(resultSet.next()) {
				c1 = new Coupon(resultSet.getInt("id"), resultSet.getInt("company_id"), 
						Category.valueOf(resultSet.getString("category_id")), resultSet.getString("title"),
						resultSet.getString("description"),resultSet.getDate("start_date"),
						resultSet.getDate("end_date"), resultSet.getInt("amount"), 
						resultSet.getDouble("price"), resultSet.getString("image"));
			}
			}catch (Exception e) {
				throw new CouponException("Parameters invalid. Please try again");
		}finally {
			pool.releaseConnection(connection);
			connection = null;
			}
		return c1;
		
	}

	/**
	 * @throws Exception 
	 * @Description:
	 * A method that return wether the coupon in our db or not
	 * the method send a query to the database and retrieves results.
	 */

	@Override
	public boolean isCouponExistsInPurchaseTable(int couponID){

		boolean flag = false;
		try {
			connection = pool.getConnection();
			String sql = "SELECT * FROM devtech_project.customers_vs_coupons where coupon_id= ?";
			PreparedStatement st = connection.prepareStatement(sql);
			st.setInt(1, couponID);
			ResultSet resultSet = st.executeQuery();
			if (resultSet.next()) {
				flag = true;
			}
			st.close();
		} catch (Exception e) {
			
		}finally {
			pool.releaseConnection(connection);
			connection=null;
		}
		return flag;
	}
	
	
	
	
	/**
	 * @throws Exception 
	 * @Description:
	 * A method that gets an customerID and couponID 
	 * and insert the coupon values into the related table.
	 * */
	@Override
	public void addCouponPurchase(int customerID, int couponID) throws CouponException {
		
		try {
			connection = pool.getConnection();
			String sql = "INSERT INTO  devtech_project.customers_vs_coupons (customer_id,coupon_id) VALUES (?,?)";
			PreparedStatement stetment = connection.prepareStatement(sql);
			stetment.setInt(1, customerID);
			stetment.setInt(2, couponID);
			stetment.executeUpdate();
			
			System.out.println("couponID = "+couponID+" customerID = " + customerID);
			System.out.println("\nSuccess. \nAdded Operation Finished.");
			stetment.close();
			
		}catch (Exception e) {
			throw new CouponException("Parameters invalid. Please try again");
		}finally {
			pool.releaseConnection(connection);
			connection=null;		}
	}
	
	/**
	 * delete coupon with specific ID
	 */
	@Override
	public void deleteCouponPurchase(int couponID) throws CouponException {
		try {
			connection = pool.getConnection();
			String sql = "SELECT * FROM  devtech_project.customers_vs_coupons WHERE coupon_id=?";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, couponID);
			ResultSet rs = statement.executeQuery();
			if(rs.next()) {
				String query = "DELETE FROM devtech_project.customers_vs_coupons WHERE coupon_id=?";
				PreparedStatement stat = connection.prepareStatement(query);
				stat.setInt(1, couponID);
				stat.executeUpdate();
			}
			else
				throw new CouponException("This coupon doesn't exist in Table customers_vs_coupons.");
		} catch (Exception e) {
			throw new CouponException("Parameters invalid. Please try again");
		}finally {
			pool.releaseConnection(connection);
			connection=null;
		}
	}
	
	/**
	 * get all the coupon the date is expired
	 */
	@Override
	public ArrayList<Coupon> getCouponsTillDate(String dateInString) throws CouponException {
		
		ArrayList<Coupon> couponExpiredDate = null;
		java.util.Date date = new java.util.Date();
		try {
			Set<Coupon> allCoupons =  getAllCoupons();
			for(Coupon coupon : allCoupons) {
				Date endDate = coupon.getEndDate();
				
				if(endDate.before(new Date())) {
					couponExpiredDate = new ArrayList<Coupon>();
					break;
				}
			}
			if(couponExpiredDate !=null) {
				for(Coupon coupon : allCoupons) {
					
					Date endDate = coupon.getEndDate();
					if(endDate.before(new Date())){
						couponExpiredDate.add(coupon);
					}
				}
			}
		} catch (Exception e) {
			throw new CouponException("Failed to Access to Coupon DB");
		}
		return couponExpiredDate; 
	}
	
	/**
	 * change the Util date to sql date.
	 * @param currentDate
	 * @return
	 * @throws ParseException
	 */
	public static java.sql.Date StringToSQLDate(String currentDate) throws ParseException {
		
		String pattern = "MM/dd/yyyy";
		DateFormat formatter = new SimpleDateFormat(pattern);
		Date endDate = formatter.parse(currentDate);
		
		java.sql.Date dateSQL = new java.sql.Date(endDate.getTime());
		return dateSQL;
	}

	
	}
