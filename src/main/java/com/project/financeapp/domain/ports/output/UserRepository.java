package com.project.financeapp.domain.ports.output;

import com.project.financeapp.domain.Model.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository {
    User save(User user);
    User findByEmail(User user);

}
