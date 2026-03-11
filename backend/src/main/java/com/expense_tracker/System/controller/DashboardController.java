package com.expense_tracker.System.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.expense_tracker.System.dto.DashboardSummaryDto;
import com.expense_tracker.System.service.DashboardService;

@RestController
@RequestMapping("/api/user/dashboard")
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    @GetMapping("/summary")
    public DashboardSummaryDto getDashboardSummary(
       
            @RequestParam int month,
            @RequestParam int year,
            Authentication authentication) {

        return dashboardService.getDashboardSummary(
                 month, year, authentication);
    }
}