package com.project.financeapp.domain.Service;

import com.project.financeapp.domain.Model.User;
import com.project.financeapp.domain.ports.output.SecurityRepository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {

    private final SecurityRepository securityRepository;

    public CustomUserDetailsService(SecurityRepository securityRepository) {
        this.securityRepository = securityRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = securityRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + email));
        log.info("{}this is in service", user.toString());
        // Return a basic UserDetails object without roles/authorities
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                new ArrayList<>() // Empty authorities list
        );
    }
}

