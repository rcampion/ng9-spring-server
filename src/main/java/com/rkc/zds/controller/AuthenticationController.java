package com.rkc.zds.controller;

import com.rkc.zds.dto.LoginDto;
import com.rkc.zds.dto.UserContactElementDto;
import com.rkc.zds.entity.AuthorityEntity;
import com.rkc.zds.entity.ContactEntity;
import com.rkc.zds.entity.UserContactEntity;
import com.rkc.zds.entity.UserEntity;
import com.rkc.zds.service.AuthenticationService;
import com.rkc.zds.service.ContactService;
import com.rkc.zds.service.UserContactsService;
import com.rkc.zds.service.UserService;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.PlatformTransactionManager;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping(value = "/api")
public class AuthenticationController {

	@Autowired
	private UserService userService;

	@Autowired
	UserContactsService userContactsService;

	@Autowired
	ContactService contactService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private AuthenticationService authenticationService;

	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public UserEntity authenticate(@RequestBody LoginDto loginDTO, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		// createDefaultAccount();

		// resetAllPasswords();

		// fixUserContacts();

		return authenticationService.authenticate(loginDTO, request, response);
	}
	
	private void createDefaultAccount() {
		UserEntity user = new UserEntity();
		user.setLogin("admin");
		user.setUserName("admin");
		user.setFirstName("Admin");
		user.setLastName("Master");
		user.setPassword("ChangeIt");
		String encodedPassword = passwordEncoder.encode(user.getPassword());
		user.setPassword(encodedPassword);
		user.setEnabled(1);
		userService.saveUser(user);

		AuthorityEntity role = new AuthorityEntity();
		role.setUserName(user.getLogin());
		role.setAuthority("ROLE_ADMIN");
		userService.saveAuthority(role);
	}
	
	private void resetAllPasswords() {
		List<UserEntity> list = userService.getUsers();
		for (UserEntity user : list) {
			user.setPassword("ChangeIt");
			String encodedPassword = passwordEncoder.encode(user.getPassword());
			user.setPassword(encodedPassword);
			userService.saveUser(user);
		}
	}

	private void fixUserContacts() {

		List<UserContactEntity> contents = userContactsService.getAllUserContacts();

		ContactEntity contact;
		for (UserContactEntity element : contents) {
			contact = contactService.getContact(element.getContactId());
			// ignore contacts that may have been deleted
			if (contact != null) {
				// update the user contacts info
				element.setFirstName(contact.getFirstName());
				element.setLastName(contact.getLastName());
				element.setCompany(contact.getCompany());
				element.setTitle(contact.getTitle());
				userContactsService.saveUserContact(element);
			} else {
				// delete the user contact, the contact no longer exists
				userContactsService.deleteUserContact(element.getId());
			}
		}

	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public void logout() {
		authenticationService.logout();
	}
}
