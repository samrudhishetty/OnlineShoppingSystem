package in.mindcraft.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import in.mindcraft.pojos.Product;
import in.mindcraft.utils.DButils;

@Component
public class ProductDao {

	private static Connection cn;
	private PreparedStatement psmt1;
	private static PreparedStatement psmt2;
	
	public void addProduct(Product p) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		cn = DButils.openConnection();
		psmt1 = cn.prepareStatement("Insert into product values(?,?,?,?,?)");
		psmt1.setInt(1, p.getId());
		psmt1.setString(2, p.getName());
		psmt1.setDouble(3, p.getCost());
		psmt1.setInt(4, p.getQuantity());
		psmt1.setDouble(5, p.getDiscount());
		psmt1.execute();
		
		DButils.closeConnection();
		
	}
	
	public static List<Product> showProduct() throws ClassNotFoundException, SQLException {
		cn = DButils.openConnection();
		
		List<Product> product_list = new ArrayList<>();
		
		psmt2 = cn.prepareStatement("select * from product");
		
		ResultSet rst = psmt2.executeQuery();

		while(rst.next()) {
			product_list.add(new Product(rst.getInt(1), rst.getString(2), rst.getDouble(3), rst.getInt(4),rst.getDouble(5)));
		}
		System.out.println("running");
		
		DButils.closeConnection();
		
		return product_list;
		
	}
	
}
