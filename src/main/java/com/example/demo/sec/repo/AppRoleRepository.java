package com.example.demo.sec.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.sec.entities.AppRole;

public interface AppRoleRepository extends JpaRepository<AppRole,Long> {
    AppRole findByRoleName(String username);


}
