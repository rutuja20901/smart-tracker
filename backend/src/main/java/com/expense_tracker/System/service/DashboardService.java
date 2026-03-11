package com.expense_tracker.System.service;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.expense_tracker.System.dto.DashboardSummaryDto;
import com.expense_tracker.System.dto.MonthlyExpenseReportDto;
import com.expense_tracker.System.dto.SavingsReportDto;

@Service
public class DashboardService {

	@Autowired
	private ExpenseService expenseService;
	
	@Autowired
	private ReportService reportService;
	
	@Autowired
	private BudgetService budgetService;
	
	public DashboardSummaryDto getDashboardSummary( int month, int year, Authentication authentication) {
		
	
		
		MonthlyExpenseReportDto monthlyReport = reportService.getExpenseMonthly(month, year, authentication);
		
		double totalExpense = monthlyReport.getTotalExpense();
		
		double totalBudgetLimit = budgetService.getTotalActiveBudgetLimit(authentication);
		
		
		
		boolean budgetExceeded = totalExpense > totalBudgetLimit;
		
		
		double exceededAmount = budgetExceeded
				? totalExpense - totalBudgetLimit
				: 0.0;
		SavingsReportDto remainingAmount = reportService.DashboardSummaryDto(month, year, authentication);
		
		double totalSavings = remainingAmount.getSavings();
		
		DashboardSummaryDto dto = new DashboardSummaryDto();
		dto.setBudgetExceeded(budgetExceeded);
		dto.setTotalMonthlyExpense(totalExpense);
		dto.setBudgetLimit(totalBudgetLimit);
		dto.setRemainingAmount(totalSavings);
		dto.setExceededAmount(exceededAmount);
		return dto;
		
		}
}
