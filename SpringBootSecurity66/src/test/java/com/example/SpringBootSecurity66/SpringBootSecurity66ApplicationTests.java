package com.example.SpringBootSecurity66;

import com.example.SpringBootSecurity66.model.Role;
import com.example.SpringBootSecurity66.model.User;
import com.example.SpringBootSecurity66.repository.IUserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootTest
class SpringBootSecurity66ApplicationTests {

	@Test
	void contextLoads() {
	}

	@Autowired
	IUserRepository userRepository;




	@Test
	public void saveUSer(){
		BCryptPasswordEncoder encoder=new BCryptPasswordEncoder();
		User user=new User();
		user.setName("Tusher");
		user.setEmail("tusher@gmail.com");
		user.setPassword(encoder.encode("1234"));
		Role role=new Role(1);
		user.addRole(role);
		userRepository.save(user);
	}



}
