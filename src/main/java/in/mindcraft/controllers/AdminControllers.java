package in.mindcraft.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import in.mindcraft.dao.CustomerDao;
import in.mindcraft.dao.ProductDao;
import in.mindcraft.pojos.Customer;
import in.mindcraft.pojos.Product;

@Controller
public class AdminControllers {

	private ProductDao productdao = new ProductDao();
	private CustomerDao customerdao = new CustomerDao();

	@RequestMapping("/addproducts")
	public ModelAndView addProducts() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("product.jsp");
		System.out.println("Adding Products");
		return mv;
	}

	@RequestMapping("/getProducts")
	public ModelAndView getProducts(HttpServletRequest request, HttpServletResponse response)
			throws ClassNotFoundException, SQLException, InterruptedException, IOException {
		ModelAndView mv = new ModelAndView();

		int id = Integer.parseInt(request.getParameter("id"));
		String name = request.getParameter("name");
		double cost = Double.parseDouble(request.getParameter("cost"));
		int quantity = Integer.parseInt(request.getParameter("quantity"));
		double discount = Double.parseDouble(request.getParameter("discount"));
		System.out.println("Getting Products Details.....");
		Product p = new Product(id, name, cost, quantity, discount);
		productdao.addProduct(p);
		mv.setViewName("result.jsp");
		return mv;
	}

	@RequestMapping("/showproducts")
	public ModelAndView showProduct() throws ClassNotFoundException, SQLException {
		ModelAndView mv = new ModelAndView();

		List<Product> list = productdao.showProduct();

		mv.setViewName("display_product.jsp");
		mv.addObject("list", list);
		System.out.println("Showing All Products...");
		return mv;
	}

	@RequestMapping("/addcustomers")
	public ModelAndView addCustomers() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("customer_details.jsp");
		System.out.println("Adding Customers...");
		return mv;
	}

	@RequestMapping("/getCustomers")
	public ModelAndView getCustomers(HttpServletRequest request, HttpServletResponse response)
			throws ClassNotFoundException, SQLException, InterruptedException, IOException {
		ModelAndView mv = new ModelAndView();
		int cid = Integer.parseInt(request.getParameter("cid"));
		String name = request.getParameter("name");
		double balance = Double.parseDouble(request.getParameter("balance"));
		System.out.println("Getting Customer Details.....");
		Customer c = new Customer(cid, name, balance);
		customerdao.addCustomer(c);
		mv.setViewName("result.jsp");
		return mv;
	}

	@RequestMapping("/showcustomers")
	public ModelAndView showCustomer() throws ClassNotFoundException, SQLException {
		ModelAndView mv = new ModelAndView();
		List<Customer> list = customerdao.showCustomer();
		mv.setViewName("display_customer.jsp");
		mv.addObject("list", list);
		System.out.println("Showing All Customers...");

		return mv;
	}

	@RequestMapping("/removecustomers")
	public ModelAndView rCustomers() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("delete_customer_details.jsp");
		System.out.println("Deleting Customers...");
		return mv;
	}
	
	@RequestMapping("/deleteCustomer")
	public ModelAndView removeCustomer(HttpServletRequest request, HttpServletResponse response) throws ClassNotFoundException, SQLException, IOException {
		ModelAndView mv = new ModelAndView();	
		int cid = Integer.parseInt(request.getParameter("cid"));	
		customerdao.removeCustomer(cid);	
		mv.setViewName("result.jsp");
	
		 
		return mv;

	}

}
