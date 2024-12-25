package com.project.financeapp.domain.ports.input;

import com.project.financeapp.domain.Model.User;
import org.springframework.stereotype.Component;

@Component
public interface AuthenticationUseCase {
    User signUp(User user);
    User login(User user);
}
