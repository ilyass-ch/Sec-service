package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.demo.sec.entities.AppRole;
import com.example.demo.sec.entities.AppUser;
import com.example.demo.sec.service.AccountService;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class SecServiceAjourApplicationTests {

	@MockBean
	private AccountService accountService;

	@MockBean
	private PasswordEncoder passwordEncoder;

	@Test
	void contextLoads() {

	}

	@Test
	void testAddNewUser() {
		AppUser user = new AppUser(1L, "username", "password", null);
		when(accountService.addNewUser(any(AppUser.class))).thenReturn(user);
		AppUser result = accountService.addNewUser(user);
		assertEquals("username", result.getUsername());
		verify(accountService).addNewUser(user);
	}

	@Test
	void testAddNewRole() {
		AppRole role = new AppRole(1L, "USER");
		when(accountService.addNewRole(any(AppRole.class))).thenReturn(role);
		AppRole result = accountService.addNewRole(role);
		assertEquals("USER", result.getRoleName());
		verify(accountService).addNewRole(role);
	}

	@Test
	void testAddRoleToUser() {
		doNothing().when(accountService).addRoleToUser("username", "USER");
		accountService.addRoleToUser("username", "USER");
		verify(accountService).addRoleToUser("username", "USER");
	}

	@Test
	void testLoadUserByUsername() {
		AppUser user = new AppUser(1L, "username", "password", null);
		when(accountService.loadUserByUsername("username")).thenReturn(user);
		AppUser result = accountService.loadUserByUsername("username");
		assertNotNull(result);
		assertEquals("username", result.getUsername());
		verify(accountService).loadUserByUsername("username");
	}

	@Test
	void testListUsers() {
		List<AppUser> users = Arrays.asList(
				new AppUser(1L, "user1", "password1", null),
				new AppUser(2L, "user2", "password2", null)
		);
		when(accountService.listUsers()).thenReturn(users);
		List<AppUser> result = accountService.listUsers();
		assertFalse(result.isEmpty());
		assertEquals(2, result.size());
		assertEquals("user1", result.get(0).getUsername());
		verify(accountService).listUsers();
	}
}
