package com.example.SpringBootSecurity66.repository;

import com.example.SpringBootSecurity66.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepository extends JpaRepository<User, Integer> {

    @Query("select  u from User u where u.email=:email")
    User findByEmail(@Param("email") String email);

}
