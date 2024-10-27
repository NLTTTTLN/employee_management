package employee_management.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import javax.servlet.http.HttpSession;

@Controller
public class LoginController {
	@RequestMapping(value="/login",method= RequestMethod.GET)
		public String showLoginPage() {
				return "login";
			}
	@RequestMapping(value="/logout",method= RequestMethod.GET)
		public String logoutHandle(HttpSession session) {
		session.invalidate();
		return "redirect:/login";
	}
	@RequestMapping("/")
	public String redirectToLogin(RedirectAttributes attributes) {
			return "redirect:/login";
		}
}
