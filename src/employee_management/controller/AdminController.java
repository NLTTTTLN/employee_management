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
@RequestMapping("/admin")
public class AdminController{
	// GET /home cho trang chính của admin
	@RequestMapping(value="/home", method = RequestMethod.GET)
		public String showAdminHomePage() {
		return "admin/home";
	}
	// Redirect /admin về trang chính của admin
	@RequestMapping("/admin")
	public String redirectToHome(RedirectAttributes attributes) {
			return "redirect:/admin/home";
		}
}