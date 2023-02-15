package com.hihusky.auth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.hihusky.auth.models.Authority;
import com.hihusky.auth.models.User;
import com.hihusky.auth.repositories.UserRepository;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

import static org.springframework.security.config.Customizer.withDefaults;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfiguration {

    UserRepository userRepository;

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // @formatter:off
        http
            .authorizeHttpRequests((authorize) -> authorize
                            .anyRequest().authenticated()
            )
            .httpBasic(withDefaults())
            .formLogin(withDefaults());
        // @formatter:on
        return http.build();
    }

    // @Bean
    // InMemoryUserDetailsManager userDetailsService() {
    //     // @formatter:off
    //     UserDetails user = User.withDefaultPasswordEncoder()
    //             .username("root")
    //             .password("root")
    //             .roles("USER")
    //             .build();
    //     return new InMemoryUserDetailsManager(user);
    //     // @formatter:on
    // }

    @Bean
    UserDetailsService customUserDetailsService() {
        return new UserDetailsService() {
            @Override
            @Transactional
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
                List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
                for (Authority authority: user.getAuthorities()) {
                    SimpleGrantedAuthority simpleGrantedAuthority =
                            new SimpleGrantedAuthority(authority.getAuthority());
                    grantedAuthorities.add(simpleGrantedAuthority);
                }
                return org.springframework.security.core.userdetails.User.builder()
                        .username(user.getUsername())
                        .password(user.getPassword())
                        .authorities(grantedAuthorities)
                        .build();
            }
        };
    }
}
