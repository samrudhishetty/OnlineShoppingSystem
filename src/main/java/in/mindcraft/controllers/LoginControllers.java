//package in.mindcraft.controllers;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//
//@Controller
//public class LoginController {
//
//    // Hardcoded admin credentials
//    private static final String ADMIN_USERNAME = "admin";
//    private static final String ADMIN_PASSWORD = "admin123";
//
//    // Map to store customer credentials (username -> password)
//    private static final Map<String, String> customerCredentials = new HashMap<>();
//
//    @GetMapping("/login")
//    public String showLoginForm() {
//        return "login";
//    }
//
//    @PostMapping("/login")
//    public String login(@RequestParam("username") String username, @RequestParam("password") String password, Model model) {
//        if (ADMIN_USERNAME.equals(username) && ADMIN_PASSWORD.equals(password)) {
//            // Admin login successful, redirect to admin page
//            return "redirect:/admin";
//        } else if (customerCredentials.containsKey(username) && customerCredentials.get(username).equals(password)) {
//            // Customer login successful, redirect to customer page
//            return "redirect:/customer";
//        } else {
//            // Invalid credentials, show an error message
//            model.addAttribute("error", "Invalid username or password");
//            return "login";
//        }
//    }
//}
