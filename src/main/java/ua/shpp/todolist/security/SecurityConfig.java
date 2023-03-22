package ua.shpp.todolist.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static ua.shpp.todolist.security.Role.*;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public SecurityConfig(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();
        return http.build();
    }

    @Bean
    protected UserDetailsService userDetailsService() {
        UserDetails bob = User.builder()
                .username("Bob")
                .password(passwordEncoder.encode("bob"))
                .authorities(EMPLOYEE.getGrantedAuthority())
                .build();
        UserDetails admin = User.builder()
                .username("Admin")
                .password(passwordEncoder.encode("admin"))
                .authorities(ADMIN.getGrantedAuthority())
                .build();
        UserDetails manager = User.builder()
                .username("Manager")
                .password(passwordEncoder.encode("manager"))
                .authorities(MANAGER.getGrantedAuthority())
                .build();

        return new InMemoryUserDetailsManager(bob, admin, manager);
    }
}
