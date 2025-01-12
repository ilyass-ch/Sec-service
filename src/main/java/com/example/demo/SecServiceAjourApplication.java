package com.example.demo;

import java.util.ArrayList;

import com.example.demo.sec.entities.AppUser;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.example.demo.sec.entities.AppRole;
import com.example.demo.sec.service.AccountService;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@EnableMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecServiceAjourApplication {

	public static void main(String[] args) {
		SpringApplication.run(SecServiceAjourApplication.class, args);
	}

	@Bean
	PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	CommandLineRunner start(AccountService accountService){
		return args -> {
			accountService.addNewRole(new AppRole(1,"USER"));
			accountService.addNewRole(new AppRole(2,"ADMIN"));
			accountService.addNewRole(new AppRole(3,"CUSTOMER_MANAGER"));
			accountService.addNewRole(new AppRole(4,"PRODUCT_MANAGER"));
			accountService.addNewRole(new AppRole(5,"BILLS_MANAGER"));

			accountService.addNewUser(new AppUser(1,"admin","123",new ArrayList<>()));
			accountService.addNewUser(new AppUser(2,"user1","123",new ArrayList<>()));
			accountService.addNewUser(new AppUser(3,"user2","123",new ArrayList<>()));
			accountService.addNewUser(new AppUser(4,"user3","123",new ArrayList<>()));
			accountService.addNewUser(new AppUser(5,"user4","123",new ArrayList<>()));

			accountService.addRoleToUser("user1","USER");
			accountService.addRoleToUser("admin","ADMIN");
			accountService.addRoleToUser("admin","USER");
			accountService.addRoleToUser("user2","USER");
			accountService.addRoleToUser("user2","CUSTOMER_MANAGER");
			accountService.addRoleToUser("user3","USER");
			accountService.addRoleToUser("user3","PRODUCT_MANAGER");
			accountService.addRoleToUser("user4","USER");
			accountService.addRoleToUser("user4","BILLS_MANAGER");



		};
	}

}
