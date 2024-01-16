package com.example.SpringBootSecurity66.repository;

import com.example.SpringBootSecurity66.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRoleRepo extends JpaRepository<Role, Integer> {
}
