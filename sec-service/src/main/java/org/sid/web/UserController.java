package org.sid.web;

import org.sid.entities.AppUser;
import org.sid.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.Data;

@RestController
public class UserController {
	@Autowired
	private AccountService accountService;

	@PostMapping("/register")
	public AppUser register(@RequestBody UserRegistrationForm userRegistrationForm) {
		return accountService.saveUser(userRegistrationForm.getUsername(), userRegistrationForm.getPassword(), userRegistrationForm.getRePassword());
	}
}

@Data
class UserRegistrationForm{
	private String username;
	private String password;
	private String rePassword;
}
