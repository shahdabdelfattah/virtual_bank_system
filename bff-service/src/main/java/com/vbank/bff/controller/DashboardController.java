package com.vbank.bff.controller;

import com.vbank.bff.dto.response.DashboardResponse;
import com.vbank.bff.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/bff")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping("/dashboard/{userId}")
    public DashboardResponse getDashboard(
            @PathVariable UUID userId
    ) {
        return dashboardService.getDashboard(userId);
    }
}