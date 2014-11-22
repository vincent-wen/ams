package ca.ams.services;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.jasypt.springsecurity.StrongPasswordEncoder;

import ca.ams.models.*;

@Component
public class UserService {
	@Autowired
	private StudentRepository studentRepos;
	@Autowired
	private ProfessorRepository professorRepos;
	@Autowired
	private GPDRepository gpdRepos;
	@Autowired
	private RegistrarRepository registrarRepos;
	/**
	 * Algorithm: SHA-256.
	 * Salt: Random.
	 * Salt size: 16 bytes.
	 * Iterations: 100000.
	 */
	private StrongPasswordEncoder encryptor = new StrongPasswordEncoder();
	private static final List<GrantedAuthority> AUTHORITIES = new ArrayList<GrantedAuthority>();
	private static final String passwordPattern = "^.*(?=.{6,20})(?=.*\\d)(?=.*[a-zA-Z]).*$";
	// Start with a character, end with a character
	private static final String emailPattern = "^[a-zA-Z]+[\\d\\w\\.\\-\\_]*@[\\w\\.\\-\\_]+\\.[a-zA-Z]+$";
	
	// "5144308745" or "514-430-8745" or "514 430 8745" or "514.430.8745", can be followed by " ext12345" or " ext123"
	private static final String phoneNumberPattern = "\\(?\\d{3}\\)?[-\\.\\s]?\\d{3}[-\\.\\s]?\\d{4}(\\s(ext)\\d{3,5})?";
			
			
	
	public void loginProceed(User user) {
		try {
			AUTHORITIES.add(new SimpleGrantedAuthority(user.getRole().toString()));
			Authentication auth = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(), AUTHORITIES);
			SecurityContextHolder.getContext().setAuthentication(auth);
		} catch(AuthenticationException e) {
			e.printStackTrace();
		}
	}
	
	public boolean isPasswordValid(String encryptedPassword, String rawPassword) {
		return encryptor.isPasswordValid(encryptedPassword, rawPassword, null);
	}
	
	public void encryptPassword(User user) {
		user.setPassword(encryptor.encodePassword(user.getPassword(), null));
	}
	
	public User save(User user) {
		switch(user.getRole()) {
			case ROLE_STUDENT : return studentRepos.save((Student) user);
			case ROLE_PROFESSOR : return professorRepos.save((Professor) user);
			case ROLE_REGISTRAR : return registrarRepos.save((Registrar) user);
			case ROLE_GPD : return gpdRepos.save((GPD) user);
			default : return null;
		}
	}
	
	public User getCurrentUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if(auth == null) return null;
		Role userRole = Role.valueOf(getUserRole());
		User currentUser = null;
		switch(userRole) {
			case ROLE_STUDENT : currentUser = studentRepos.findByUsername(auth.getName()); break;
			case ROLE_PROFESSOR : currentUser = professorRepos.findByUsername(auth.getName()); break;
			case ROLE_REGISTRAR : currentUser = registrarRepos.findByUsername(auth.getName()); break;
			case ROLE_GPD : currentUser = gpdRepos.findByUsername(auth.getName()); break;
			default : return null;
		}
		return currentUser;
	}
	
	public String getUserRole() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if(auth == null) return null;
		for(GrantedAuthority authority : auth.getAuthorities()) {
			return authority.getAuthority();
		}
		return null;
	}
	
	public User getUser(String username) {
		Student student = studentRepos.findByUsername(username);
		if(student != null) return student;
		Professor professor = professorRepos.findByUsername(username);
		if(professor != null) return professor;
		Registrar registrar = registrarRepos.findByUsername(username);
		if(registrar != null) return registrar;
		GPD gpd = gpdRepos.findByUsername(username);
		if(gpd != null) return gpd;
		return null;
	}
	
	public boolean validatePasswordPattern(String password) {
		return Pattern.matches(passwordPattern, password);
	}

	public void logout() {
		SecurityContextHolder.getContext().setAuthentication(null);
		AUTHORITIES.clear();
	}

	public void updatePassword(User currentUser, String newpassword) {
		currentUser.setPassword(newpassword);
		encryptPassword(currentUser);
		save(currentUser);
	}

	public boolean validateEmail(String email) {
		return email.matches(emailPattern);
	}

	public boolean validatePhoneNumber(String phoneNumber) {
		return phoneNumber.matches(phoneNumberPattern);
	}
}
