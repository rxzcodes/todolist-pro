package com.portfolio.todolist.service;

import com.portfolio.todolist.dto.AuthResponseDTO;
import com.portfolio.todolist.dto.LoginRequestDTO;
import com.portfolio.todolist.dto.RegisterRequestDTO;
import com.portfolio.todolist.model.User;
import com.portfolio.todolist.repository.UserRepository;
import com.portfolio.todolist.security.JwtTokenProvider;
import com.portfolio.todolist.exception.DataConflictException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;

    // Registrar novo usuário
    public AuthResponseDTO register(RegisterRequestDTO registerDTO) {
        // Verifica se username já existe
        if (userRepository.existsByUsername(registerDTO.getUsername())) {
            throw new DataConflictException("Nome de usuário já está em uso");
        }

        // Verifica se email já existe
        if (userRepository.existsByEmail(registerDTO.getEmail())) {
            throw new DataConflictException("Email já está em uso");
        }

        // Cria o usuário
        User user = new User();
        user.setUsername(registerDTO.getUsername());
        user.setEmail(registerDTO.getEmail());
        user.setPassword(passwordEncoder.encode(registerDTO.getPassword())); // Criptografa a senha

        userRepository.save(user);

        // Autentica automaticamente após registro
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        registerDTO.getUsername(),
                        registerDTO.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = tokenProvider.generateToken(authentication);

        return new AuthResponseDTO(token, user.getUsername(), user.getEmail());
    }

    // Login
    public AuthResponseDTO login(LoginRequestDTO loginDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDTO.getUsername(),
                        loginDTO.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = tokenProvider.generateToken(authentication);

        User user = userRepository.findByUsername(loginDTO.getUsername())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        return new AuthResponseDTO(token, user.getUsername(), user.getEmail());
    }
}