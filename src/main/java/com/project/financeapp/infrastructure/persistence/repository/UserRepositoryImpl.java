package com.project.financeapp.infrastructure.persistence.repository;

import com.project.financeapp.domain.Model.User;
import com.project.financeapp.domain.ports.output.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class UserRepositoryImpl implements UserRepository {

    private final SimpleJdbcCall simpleJdbcCall1;
    private final SimpleJdbcCall simpleJdbcCall2;
    private final PasswordEncoder passwordEncoder;
    private final ObjectMapper objectMapper;

    public UserRepositoryImpl(@Qualifier("SignUpUser") SimpleJdbcCall simpleJdbcCall1,
                              @Qualifier("LoginUser") SimpleJdbcCall simpleJdbcCall2,
                              PasswordEncoder passwordEncoder,ObjectMapper objectMapper) {
        this.simpleJdbcCall1 = simpleJdbcCall1;
        this.simpleJdbcCall2 = simpleJdbcCall2;
        this.passwordEncoder = passwordEncoder;
        this.objectMapper = objectMapper;
    }
    @Override
    public User save(User user) {
        try{
            Map<String, Object> params = new HashMap<>();
            params.put("in_email", user.getEmail());
            String hashedPassword = passwordEncoder.encode(user.getPassword());
            params.put("in_password_hash", hashedPassword);
            params.put("in_name", user.getUsername());

            Map<String, Object> results = simpleJdbcCall1.execute(params);
            String message = (String) results.get("out_message");
            if(message != null && !message.isEmpty() && !"Success".equalsIgnoreCase(message)){
                throw new RuntimeException(message);
            }
            Object userRowObject = results.get("out_user_row");

            String userJson = (String) userRowObject;
            return objectMapper.readValue(userJson, User.class);

        } catch(Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public User findByEmail(User user) {
        try{
            Map<String, Object> params = new HashMap<>();
            params.put("in_email", user.getEmail());

            Map<String, Object> results = simpleJdbcCall2.execute(params);
            String message = (String) results.get("out_message");
            if(message != null && !message.isEmpty() && !"Success".equalsIgnoreCase(message)){
                throw new RuntimeException(message);
            }
            Object userRowObject = results.get("out_user_row");
            String userJson = (String) userRowObject;
            User obtainedUser = objectMapper.readValue(userJson, User.class);
            if (!passwordEncoder.matches(user.getPassword(), obtainedUser.getPassword())) {
                throw new RuntimeException("Invalid Password");
            }
            return obtainedUser;
        } catch(Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }
}
