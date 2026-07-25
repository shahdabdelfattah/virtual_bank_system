package com.vbank.bff.dto.response;

import java.util.UUID;

public record UserProfileResponse(

        UUID userId,
        String username,
        String email,
        String firstName,
        String lastName
        
) { }