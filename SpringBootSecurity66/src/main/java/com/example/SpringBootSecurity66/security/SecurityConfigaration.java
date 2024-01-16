package com.example.SpringBootSecurity66.security;

import com.example.SpringBootSecurity66.model.User;
import com.example.SpringBootSecurity66.repository.IUserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.authentication.jaas.DefaultJaasAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Optional;

@Configuration
@EnableWebSecurity
public class SecurityConfigaration {
    private final UserDetailsService userDetailsService;

    public SecurityConfigaration(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public PasswordEncoder encoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider provider=new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(encoder());

        return provider;

    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws  Exception{

//        http
//                .authorizeHttpRequests( (au)->au
//                        .requestMatchers( "/assets/**","/public/**","/")
//                        .permitAll()
//                        .requestMatchers("/admin/**")
//                        .hasRole("ADMIN")
//                        .anyRequest().authenticated()
//                )
//                .httpBasic(Customizer.withDefaults())
//                .formLogin(Customizer.withDefaults("/user/login"))
//                .userDetailsService(userDetailsService);

        http
                . csrf(csrf -> csrf.disable())
                .authorizeHttpRequests((au)->au
                        .requestMatchers( "/assets/**","/public/**","/","/login")
                        .permitAll()
                        .requestMatchers("/admin/**")
                        .hasRole("ADMIN")
                        .anyRequest().authenticated()

                )

                .formLogin(login-> login
                        .loginPage("/login")
                        .usernameParameter("email")
                        .successForwardUrl("/admin/user/all")
                        .permitAll()
                )
                .logout(logout->logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                        .permitAll()
                )
                .userDetailsService(userDetailsService);

        return http.build();

    }



}
