package com.example.demo.sec.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.sec.entities.AppUser;

public interface AppUserRepository extends JpaRepository<AppUser,Long> {
    AppUser findByUsername(String username);
}