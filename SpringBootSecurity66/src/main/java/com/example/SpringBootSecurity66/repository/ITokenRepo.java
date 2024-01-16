package com.example.SpringBootSecurity66.repository;

import com.example.SpringBootSecurity66.model.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ITokenRepo extends JpaRepository<Token, Long > {

   // @Query("select  u from User u where u.email=:email")
    Token findByConfirmationToken(String token);

}
