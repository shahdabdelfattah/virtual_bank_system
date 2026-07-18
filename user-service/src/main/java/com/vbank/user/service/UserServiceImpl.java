package com.vbank.user.service;

import com.vbank.user.config.JwtTokenProvider;
import com.vbank.user.dto.LoginRequest;
import com.vbank.user.dto.LoginResponse;
import com.vbank.user.dto.RegisterRequest;
import com.vbank.user.dto.RegisterResponse;
import com.vbank.user.dto.UserResponse;
import com.vbank.user.entity.User;
import com.vbank.user.exception.UserNotFoundException;
import com.vbank.user.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public UserServiceImpl(UserRepository userRepository,
                           PasswordEncoder passwordEncoder,
                           JwtTokenProvider jwtTokenProvider) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public RegisterResponse register(RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username or email already exists.");
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Username or email already exists.");
        }

        String encodedPassword = passwordEncoder.encode(request.getPassword());
        User user = new User(
                request.getUsername(),
                request.getEmail(),
                encodedPassword,
                request.getFirstName(),
                request.getLastName()
        );

        User savedUser = userRepository.save(user);
        return new RegisterResponse(
                savedUser.getId(),
                savedUser.getUsername(),
                "User registered successfully."
        );
    }

    @Override
    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new UserNotFoundException("User not found: " + request.getUsername()));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        String token = jwtTokenProvider.generateToken(user.getId(), user.getUsername());

        return new LoginResponse(
                user.getId(),
                user.getUsername(),
                token
        );
    }

    @Override
    public UserResponse getProfile(UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User with ID " + id + " not found."));

        return new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName()
        );
    }
}
