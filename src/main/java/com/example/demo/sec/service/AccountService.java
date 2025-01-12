package com.example.demo.sec.service;

import java.util.List;

import com.example.demo.sec.entities.AppRole;
import com.example.demo.sec.entities.AppUser;

public interface AccountService {
    AppUser addNewUser(AppUser appUser);
    AppRole addNewRole(AppRole appRole);
    void addRoleToUser(String username,String roleName);
    AppUser loadUserByUsername(String username);
    List<AppUser> listUsers();
}
