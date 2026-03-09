package com.expense_tracker.System.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.expense_tracker.System.dto.CategoryWiseReportDto;
import com.expense_tracker.System.dto.MonthlyExpenseReportDto;
import com.expense_tracker.System.dto.SavingsReportDto;
import com.expense_tracker.System.service.ReportService;

@RestController
@RequestMapping("/api/user/report")
public class ReportController {
	
	@Autowired
	private ReportService reportService;
	
	@GetMapping("/monthly")
	public MonthlyExpenseReportDto getExpenseByMonthly(@RequestParam int month, @RequestParam int year, Authentication authentication) {
		return reportService.getExpenseMonthly(month, year, authentication);
	}
	
	@GetMapping("/category-wise")
	public List<CategoryWiseReportDto> getCategoryWiseReport(Authentication authentication){
		return reportService.getCategoryWiseReport(authentication);
	}
	
	@GetMapping("/savings")
	public SavingsReportDto getSavings(@RequestParam int month, @RequestParam int year, Authentication authentication) {
		return reportService.getSavings(month, year, authentication);
	}

}
