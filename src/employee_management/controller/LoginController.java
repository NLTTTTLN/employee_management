package employee_management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import employee_management.bean.Account;
import employee_management.service.AccountService;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


@Controller
public class LoginController {
	@Autowired
    private AccountService accountService; //Gọi AccountService cho việc authenticate
	// Hàm GET /login trả về trang login
	@RequestMapping(value="/login",method= RequestMethod.GET)
		public String showLoginPage() {
				return "login";
			}
	//Hàm POST /login gửi thông tin đăng nhập 
	@RequestMapping(value="/login",method= RequestMethod.POST)
	public @ResponseBody Map<String, Object> loginAuthenticate(@RequestBody Map<String, String> payload, HttpServletRequest request) {
	    String username = payload.get("username");// Lấy username từ POST form
	    String password = payload.get("password");// Lấy password từ POST form

	    System.out.println("loginAuthenticate called with username: " + username + ", password: " + password);

	    Account account = accountService.authenticate(username, password); //Khởi tạo Object Account để tiến hành authenticate
	    Map<String, Object> response = new HashMap<>(); // Khởi tạo HashMap để tiến hành trả về cho client

	    if (account != null) {
	        HttpSession session = request.getSession();
	        session.setAttribute("user", account); // Tạo session sử dụng cho user 

	        response.put("success", true); // Authenticate thành công
	        // Respone được server trả về tương ứng với role của account
	        response.put("redirectUrl", "admin".equals(account.getRole()) ? "admin/home" : "/user/home");
	    } else {
	        response.put("success", false); // Authenticate thất bại
	    }

	    System.out.println("Response: " + response);
	    return response;
    }
	//Hàm GET /logout để đăng xuất
	@RequestMapping(value="/logout",method= RequestMethod.GET)
		public String logoutHandle(HttpSession session) {
		session.invalidate(); //Hủy session user đã sử dụng
		return "redirect:/login";
	}
	// Hàm redirect thư mục / thành /login
	@RequestMapping("/")
	public String redirectToLogin(RedirectAttributes attributes) {
			return "redirect:/login";
		}
}
