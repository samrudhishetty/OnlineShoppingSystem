package in.mindcraft.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import in.mindcraft.pojos.Customer;
import in.mindcraft.pojos.Product;
import in.mindcraft.utils.DButils;


public class ShoppingCartDao {
	private Connection cn;
	private PreparedStatement psmt1;
	private PreparedStatement psmt2;
	private PreparedStatement psmt3;
	private PreparedStatement psmt4;
	private PreparedStatement psmt5;
	private PreparedStatement psmt6;
	

	public ShoppingCartDao(int int1, int int2, String string, double double1, int int3, double double2,
			String string2) {
		// TODO Auto-generated constructor stub
	}

	public double calculateSum(List<Double> numbers) {
        return numbers.stream().mapToDouble(Double::doubleValue).sum();
    }
	
	public void addItems(int cid, int id, String product_name, double product_cost,int quantity ,double discount,String invoiced) throws ClassNotFoundException, SQLException {
		
		cn = DButils.openConnection();
		
		psmt1 = cn.prepareStatement("Insert into cart values(?,?,?,?,?,?,?)");
		psmt1.setInt(1, cid);
		psmt1.setInt(2, id);
		psmt1.setString(3, product_name);
		psmt1.setDouble(4, product_cost);
		psmt1.setInt(5, quantity);
		psmt1.setDouble(6, discount);
		psmt1.setString(7, invoiced);
		psmt1.execute();
		
		psmt2 = cn.prepareStatement("Update product set quantity = quantity - 1 where id = ?"); 
		psmt2.setInt(1, id);
		psmt2.execute();		
		
		DButils.closeConnection();
	}
	
	public List<ShoppingCartDao> showCart(int cid) throws ClassNotFoundException, SQLException {
		cn = DButils.openConnection();
		
		List<ShoppingCartDao> cart_list = new ArrayList<>();
		
		psmt2 = cn.prepareStatement("select * from cart where cid = ? and invoiced = 'No'");
		
		psmt2.setInt(1, cid);
		
		ResultSet rst = psmt2.executeQuery();
		
		while(rst.next()) {
			cart_list.add(new ShoppingCartDao(rst.getInt(1), rst.getInt(2), rst.getString(3), rst.getDouble(4),rst.getInt(5), rst.getDouble(6), rst.getString(7)));
		}
		
		DButils.closeConnection();
		
		return cart_list;
	}


	public List<ShoppingCartDao> purchased(int cid) throws ClassNotFoundException, SQLException {
		cn = DButils.openConnection();
		
		List<ShoppingCartDao> cart_list = new ArrayList<>();
		
		psmt2 = cn.prepareStatement("select * from cart where cid = ? and invoiced = 'Yes'");
		
		psmt2.setInt(1, cid);
		
		ResultSet rst = psmt2.executeQuery();
		
		while(rst.next()) {
			cart_list.add(new ShoppingCartDao(rst.getInt(1), rst.getInt(2), rst.getString(3), rst.getDouble(4),rst.getInt(5), rst.getDouble(6), rst.getString(7)));
		}
		
		DButils.closeConnection();
		
		return cart_list;
	}

	
	public List<ShoppingCartDao> billPay(int cid) throws ClassNotFoundException, SQLException {
		cn = DButils.openConnection();
		
		List<ShoppingCartDao> cart_list = new ArrayList<>();
		
		List<Double> discountAmount = new ArrayList<>();
		
		psmt3 = cn.prepareStatement("select * from cart where cid = ? and invoiced = 'No'");
		
		psmt3.setInt(1, cid);
		
		ResultSet rst = psmt3.executeQuery();
		
		while(rst.next()) {

			cart_list.add(new ShoppingCartDao(rst.getInt(1), rst.getInt(2), rst.getString(3), rst.getDouble(4),rst.getInt(5), rst.getDouble(6), rst.getString(7)));
		}
		
		DButils.closeConnection();
		
		return cart_list;
	}
	
	public double billPay1(int cid) throws ClassNotFoundException, SQLException {
		cn = DButils.openConnection();
			
		List<Double> actual_Amount = new ArrayList<>();
		
		double sum = 0;
		psmt4 = cn.prepareStatement("select * from cart  where cid = ? and invoiced = 'No'");
		psmt4.setInt(1,cid);
		
		ResultSet rst = psmt4.executeQuery();
		
		while(rst.next()) {
			double actualAmt = rst.getDouble(4);
			actual_Amount.add((double) actualAmt);
			sum = calculateSum(actual_Amount);
			System.out.println("List is" +actual_Amount);
		}
		
		DButils.closeConnection();
		
		return sum;
	}
	
	public double billPay2(int cid) throws ClassNotFoundException, SQLException {
		cn = DButils.openConnection();
			
		List<Double> discount_Amount = new ArrayList<>();
		
		double sum = 0;
		psmt5 = cn.prepareStatement("select * from cart  where cid = ? and invoiced = 'No'");
		psmt5.setInt(1, cid);

		ResultSet rst = psmt5.executeQuery();
		
		while(rst.next()) {
			double discountAmt = (rst.getDouble(6) / 100) * rst.getDouble(4);
			double actualAmt = rst.getDouble(4);
			double finalAmt = actualAmt - discountAmt;
			discount_Amount.add((double) finalAmt);
			sum = calculateSum(discount_Amount);
			System.out.println("List is" +discount_Amount);
		}
		
		DButils.closeConnection();
		
		return sum;
	}
	
	public double billPay3(int cid) throws ClassNotFoundException, SQLException {
		cn = DButils.openConnection();
			
		List<Double> saved_Amount = new ArrayList<>();
		
		double sum = 0;
		psmt6 = cn.prepareStatement("select * from cart  where cid = ? and invoiced = 'No'");
		psmt6.setInt(1, cid);

		ResultSet rst = psmt6.executeQuery();
		
		while(rst.next()) {
			double discountAmt = (rst.getDouble(6) / 100) * rst.getDouble(4);
			saved_Amount.add((double) discountAmt);
			sum = calculateSum(saved_Amount);
			System.out.println("List is" +saved_Amount);
		}
		
		DButils.closeConnection();
		
		return sum;
	}
	
	
}
