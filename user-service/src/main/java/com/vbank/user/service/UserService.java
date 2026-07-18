package com.vbank.user.service;

import com.vbank.user.dto.LoginRequest;
import com.vbank.user.dto.LoginResponse;
import com.vbank.user.dto.RegisterRequest;
import com.vbank.user.dto.RegisterResponse;
import com.vbank.user.dto.UserResponse;
import java.util.UUID;

public interface UserService {

    RegisterResponse register(RegisterRequest request);

    LoginResponse login(LoginRequest request);

    UserResponse getProfile(UUID id);

}
