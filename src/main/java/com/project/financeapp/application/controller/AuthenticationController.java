package com.project.financeapp.application.controller;

import com.project.financeapp.application.dto.Auth.AuthResponseDTO;
import com.project.financeapp.application.dto.Auth.LoginRequestDTO;
import com.project.financeapp.application.dto.Auth.SignUpRequestDTO;
import com.project.financeapp.domain.Model.User;
import com.project.financeapp.domain.ports.input.AuthenticationUseCase;
import com.project.financeapp.application.security.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {

    private final AuthenticationUseCase authenticationUseCase;
    private final JwtUtil jwtUtil;
    public AuthenticationController(AuthenticationUseCase authenticationUseCase, JwtUtil jwtUtil) {
        this.authenticationUseCase = authenticationUseCase;
        this.jwtUtil = jwtUtil;
    }

    @GetMapping("/v1/auth/login")
    ResponseEntity<?> login(@RequestBody LoginRequestDTO loginRequestDTO){
        try{
            User user1 = new User();
            user1.setEmail(loginRequestDTO.getEmail());
            user1.setPassword(loginRequestDTO.getPassword());

            User user2 = authenticationUseCase.login(user1);

            AuthResponseDTO authResponseDTO = generateResponse(user2);
            return ResponseEntity.ok(authResponseDTO);

        } catch (Exception e){
            String message  = e.getMessage();
            if(message.contains("InValid")){
                return ResponseEntity.badRequest().body(message);
            }
            return ResponseEntity.internalServerError().body(message);
        }
    }

    @PostMapping("/v1/auth/signup")
    ResponseEntity<?> signUp(@RequestBody SignUpRequestDTO signUpRequestDTO){
        try {
            User user1 = new User();
            user1.setEmail(signUpRequestDTO.getEmail());
            user1.setPassword(signUpRequestDTO.getPassword());
            user1.setUsername(signUpRequestDTO.getUsername());

            User user2 = authenticationUseCase.signUp(user1);

            AuthResponseDTO authResponseDTO = generateResponse(user2);
            return ResponseEntity.ok(authResponseDTO);
        } catch (Exception e){
            String message  = e.getMessage();
            if(message.contains("AlreadyExists") || message.contains("InValid")){
                return ResponseEntity.badRequest().body(message);
            }
            return ResponseEntity.internalServerError().body(message);
        }
    }

    private AuthResponseDTO generateResponse(User user){
        AuthResponseDTO authResponseDTO = new AuthResponseDTO();
        String token = jwtUtil.generateToken(user.getEmail(), user.getUserId());
        authResponseDTO.setUsername(user.getUsername());
        authResponseDTO.setUserId(user.getUserId());
        authResponseDTO.setToken(token);
        return authResponseDTO;
    }
}
