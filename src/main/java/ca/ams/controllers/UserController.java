package ca.ams.controllers;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Iterator;
import java.util.List;

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

import ca.ams.models.*;
import ca.ams.services.*;

@Controller
public class UserController {
	@Autowired
	private UserService userService;
	@Autowired
	private ProfessorService professorService;
	@Autowired
	private CourseService courseService;
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView loginPage() {
		if(userService.getCurrentUser() != null) return new ModelAndView("index");
		return new ModelAndView("login");
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<String> login(@RequestBody User requestUser) {
		User user = userService.getUser(requestUser.getUsername());
		if(user != null) {
			if(userService.isPasswordValid(user.getPassword(), requestUser.getPassword())) {
				userService.loginProceed(user);
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
		User user = userService.getCurrentUser();
		user.setPassword(null);
		if(user.getRole() == Role.ROLE_STUDENT) {
			Student student = (Student) user;
			for(CourseSection section : student.getRegisteredSections()) {
				String id = section.getCourseObjectId();
				section.setCourseId(courseService.getCourseById(id).getCourseId());
				Professor instructor = professorService.getProfessorById(section.getInstructorId());
				professorService.clearSensitiveInfo(instructor);
				section.setInstructor(instructor);
			}
		}
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/api/change-password", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<String> changePassword(@RequestBody User requestUser) {
		String newPassword = requestUser.getNewpassword();
		String oldPassword = requestUser.getPassword();
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
	
	@RequestMapping(value = "/api/user/change-phone-number", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<String> changePhoneNumber(@RequestBody String phoneNumber) {
		User currentUser = userService.getCurrentUser();
		if(!userService.validatePhoneNumber(phoneNumber))
			return new ResponseEntity<String>("The format of your phone number is invalid.", HttpStatus.NOT_ACCEPTABLE);;
		currentUser.setPhoneNumber(phoneNumber);
		userService.save(currentUser);
		return new ResponseEntity<String>("success", HttpStatus.OK);
	}
	
	@RequestMapping(value = "/api/user/change-email", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<String> changeEmail(@RequestBody String email) {
		User currentUser = userService.getCurrentUser();
		if(!userService.validateEmail(email))
			return new ResponseEntity<String>("The format of your email is invalid", HttpStatus.NOT_ACCEPTABLE);;
		currentUser.setEmail(email);;
		userService.save(currentUser);
		return new ResponseEntity<String>("success", HttpStatus.OK);
	}
	
	@RequestMapping(value = "/api/user/get-all-professors", method = RequestMethod.POST)
	public @ResponseBody List<Professor> getAllProfessors() {
		User currentUser = userService.getCurrentUser();
		if(currentUser.getRole() == Role.ROLE_GPD) {
			List<Professor> professors = professorService.getAllProfessors();
			return professors;
		}
		return null;
	}
}
