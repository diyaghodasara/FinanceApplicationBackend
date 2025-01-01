package com.project.financeapp.infrastructure.persistence.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.financeapp.domain.Model.User;
import com.project.financeapp.domain.ports.output.SecurityRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
@Slf4j
public class SecurityRepositoryImpl implements SecurityRepository {
    private final SimpleJdbcCall simpleJdbcCall;
    private final ObjectMapper objectMapper;
    public SecurityRepositoryImpl(@Qualifier("Security")SimpleJdbcCall simpleJdbcCall,
                                  ObjectMapper objectMapper) {
        this.simpleJdbcCall = simpleJdbcCall;
        this.objectMapper = objectMapper;
    }
    @Override
    public Optional<User> findByEmail(String email) {
        try{
            Map<String, Object> params = new HashMap<>();
            params.put("in_email", email);

            Map<String, Object> results = simpleJdbcCall.execute(params);
            String message = (String) results.get("out_message");
            if(message != null && !message.isEmpty() && !"Success".equalsIgnoreCase(message)){
                throw new RuntimeException(message);
            }
            Object userRowObject = results.get("out_user_row");
            String userJson = (String) userRowObject;
            User obtainedUser = objectMapper.readValue(userJson, User.class);
            return Optional.ofNullable(obtainedUser);
        }catch (Exception e){
            log.error(e.getMessage());
            return Optional.empty();
        }
    }
}
