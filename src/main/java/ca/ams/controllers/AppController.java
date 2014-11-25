package ca.ams.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ca.ams.services.*;

@Controller
public class AppController {
	@Autowired
	private AdminService adminService;
	@Autowired
	private UserService userService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home() {
		if(!userService.hasLogin()) return "redirect:/login";
		return "index";
	}
	
	@RequestMapping(value = "/api/partials/{pageName}", method = RequestMethod.GET)
	public String partialPages(@PathVariable String pageName) {
		if(!userService.hasLogin()) return null;
		return "partials/" + pageName;
	}
	
	@RequestMapping(value = "/{pageName}", method = RequestMethod.GET)
	public String others1(@PathVariable String pageName) {
		return home();
	}
	@RequestMapping(value = "/payment/paypal/{pageName}", method = RequestMethod.GET)
	public String others2(@PathVariable String pageName) {
		return home();
	}
	
	@RequestMapping("/init")
	public String forTest() {
		adminService.init();
		return "redirect:/login";
	}
}
