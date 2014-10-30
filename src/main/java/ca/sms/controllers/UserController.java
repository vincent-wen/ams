package ca.sms.controllers;

import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import ca.sms.models.User;
import ca.sms.services.UserService;

@Controller
public class UserController {

	@Autowired
	private UserService userService;
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView loginPage() {
		if(userService.getCurrentUser() != null) return new ModelAndView("index");
		return new ModelAndView("login");
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<String> login(@RequestBody User user) {
		if(userService.getUser(user.getUsername()) != null) {
			if(userService.validate(user.getUsername(), user.getPassword())) {
				try {
					HttpHeaders headers = new HttpHeaders();
					headers.setLocation(new URI("/"));
					return new ResponseEntity<String>("success", headers, HttpStatus.MOVED_PERMANENTLY);
				}catch(URISyntaxException e) { e.printStackTrace(); }
			}
			return new ResponseEntity<String>("The password is not correct", HttpStatus.UNAUTHORIZED);
		}
		return new ResponseEntity<String>("The user is not found", HttpStatus.NOT_FOUND);
	}
	
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout() {
		userService.logout();
		return "redirect:/login";
	}
	
	@RequestMapping(value = "/api/get-current-user", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<User> getCurrentUser() {
		return new ResponseEntity<User>(userService.getCurrentUser(), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/api/change-password", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<String> changePassword(@RequestBody String newPassword, @RequestBody String oldPassword) {
		User currentUser = userService.getCurrentUser();
		if(currentUser == null) {
			return new ResponseEntity<String>("Login expired", HttpStatus.FORBIDDEN);
		}
		if(!userService.isPasswordValid(currentUser.getPassword(), oldPassword)) {
			return new ResponseEntity<String>("Your old password is incorrect.", HttpStatus.NOT_ACCEPTABLE);
		}
		if(!userService.validatePasswordPattern(newPassword)) {
			return new ResponseEntity<String>("Your new password is not acceptable.", HttpStatus.NOT_ACCEPTABLE);
		}
		userService.updatePassword(currentUser, newPassword);
		return new ResponseEntity<String>("success", HttpStatus.OK);
	}
}
