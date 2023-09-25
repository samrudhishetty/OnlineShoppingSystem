package in.mindcraft.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import in.mindcraft.dao.CustomerDao;
import in.mindcraft.dao.ProductDao;
import in.mindcraft.dao.ShoppingCartDao;
import in.mindcraft.pojos.Product;

@Controller
public class CustomersControllers {
	private ProductDao productdao = new ProductDao();
    private  CustomerDao customerDao = new CustomerDao();
    private ShoppingCartDao cartdao = new ShoppingCartDao(0, 0, null, 0, 0, 0, null);

	public boolean checkcustomer(int cid) throws ClassNotFoundException, SQLException {
		System.out.println(customerDao.checkCustomer(cid));
		System.out.println(cid);
		boolean c = customerDao.checkCustomer(cid);
		return c;
	}
	
    @RequestMapping("/customerlogin")
    public ModelAndView customerLogin(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView("login"); // Set the view name (login.jsp)

        if (request.getMethod().equalsIgnoreCase("POST")) {
            int cid = Integer.parseInt(request.getParameter("cid"));
            String name = request.getParameter("name");

            try {
                boolean isValidCustomer = customerDao.checkCustomerLogin(cid, name);
                if (isValidCustomer) {
                  mv.setViewName("redirect:/customerdashboard");
                } else {
                    mv.addObject("error", "Invalid customer ID or name. Please try again.");
                }
            } catch (Exception e) {
                mv.addObject("error", "Error during login: " + e.getMessage());
                mv.setViewName("login");
            }
        }

        return mv;
    }

	@RequestMapping("/addtoCart")
	public ModelAndView addtoCart() throws ClassNotFoundException, SQLException {
		ModelAndView mv = new ModelAndView();
		List<Product> list = ProductDao.showProduct();
		
		mv.setViewName("addcart.jsp");
		
		mv.addObject("list",list);
		
		return mv;
	}
		
	@RequestMapping("/addItems")
	public ModelAndView addItems(HttpServletRequest request, HttpServletResponse response, @RequestParam("id") int id, @RequestParam("name") String name, @RequestParam("cost") double cost, @RequestParam("discount") double discount) throws ClassNotFoundException, SQLException, InterruptedException, IOException {
		ModelAndView mv = new ModelAndView();

		HttpSession session = request.getSession();
		int cid = (int) session.getAttribute("cid");
		String invoiced = "No";
		int quantity = 1;
		cartdao.addItems(cid, id, name, cost, quantity, discount, invoiced);			
		mv.setViewName("result.jsp");
		return mv;
		
		
	}
		


	

}
	

