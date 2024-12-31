package com.project.financeapp.domain.Service;

import com.project.financeapp.domain.Model.User;
import com.project.financeapp.domain.ports.input.AuthenticationUseCase;
import com.project.financeapp.domain.ports.output.UserRepository;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationService implements AuthenticationUseCase {
    private final UserRepository userRepository;
    public AuthenticationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Override
    public User signUp(User user) {
        try {
            return userRepository.save(user);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public User login(User user) {
        try {
            return userRepository.findByEmail(user);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
