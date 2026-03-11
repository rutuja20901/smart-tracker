package com.expense_tracker.System.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.expense_tracker.System.dao.ReportDaoImpl;
import com.expense_tracker.System.dto.CategoryWiseReportDto;
import com.expense_tracker.System.dto.MonthlyExpenseReportDto;
import com.expense_tracker.System.dto.SavingsReportDto;
import com.expense_tracker.System.entity.User;
import com.expense_tracker.System.repository.UserRepository;

@Service
public class ReportService {

	@Autowired
	private ReportDaoImpl reportDaoImpl;

	@Autowired
	private UserRepository userRepository;

	public MonthlyExpenseReportDto getExpenseMonthly( int month, int year, Authentication authentication) {
		String username = authentication.getName();
		User user = userRepository.findByEmail(username).orElseThrow(() -> new RuntimeException("User not found!"));

		double totalExpense = reportDaoImpl.getExpenseMonthly(user.getId(), month, year);
 
		return new MonthlyExpenseReportDto(month, year, totalExpense);
	}
	
	public List<CategoryWiseReportDto> getCategoryWiseReport(Authentication authentication) {
		String username = authentication.getName();
		User user = userRepository.findByEmail(username).orElseThrow(() -> new RuntimeException("User not found!"));
		
		return reportDaoImpl.getCategoryWiseReport(user.getId());
	}
	
	public SavingsReportDto DashboardSummaryDto(int month, int year, Authentication authentication) {
		String username = authentication.getName();
		User user = userRepository.findByEmail(username).orElseThrow(() -> new RuntimeException("User not found!"));
		
		LocalDate monthStart = LocalDate.of(year, month, 1);
	    LocalDate monthEnd = monthStart.withDayOfMonth(monthStart.lengthOfMonth());
		
		Double totalExpense = reportDaoImpl.getExpenseMonthly(user.getId(), month, year);
		
		Double budgetAmount = reportDaoImpl.getTotalSvings(user.getId(), monthStart, monthEnd);
		
		Double remainingAmount = budgetAmount - totalExpense;
		
		boolean budgetExceeded = totalExpense > budgetAmount;
		
		double exceededAmount = budgetExceeded
				? totalExpense - budgetAmount
				: 0.0;
		
		System.out.println("Budget = " + budgetAmount);
		System.out.println("Expense = " + totalExpense);
		System.out.println("Exceeded? = " + budgetExceeded);
		System.out.println("Exceeded Amount = " + exceededAmount);

	
		return new SavingsReportDto(month, year, budgetAmount,totalExpense,remainingAmount,budgetExceeded,exceededAmount);
	}

}
