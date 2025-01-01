package com.project.financeapp.domain.ports.output;

import com.project.financeapp.domain.Model.User;

import java.util.Optional;

public interface SecurityRepository {
    Optional<User> findByEmail(String email) ;
}
