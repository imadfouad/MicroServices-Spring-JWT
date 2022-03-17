package org.sid;

import org.sid.entities.AppRole;
import org.sid.service.AccountService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class SecServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SecServiceApplication.class, args);
	}
	
	@Bean
	CommandLineRunner start(AccountService accountService) {
		return args->{
			accountService.saveRole("USER");
			accountService.saveRole("ADMIN");
			accountService.saveUser("user", "1234", "1234");
			accountService.saveUser("admin", "1234", "1234");
			accountService.addRoleToUser("admin", "ADMIN");
		};
	}
	
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
