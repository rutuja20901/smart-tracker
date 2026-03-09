package com.expense_tracker.System.dao;

import java.time.LocalDate;
import java.util.List;

import com.expense_tracker.System.dto.CategoryWiseReportDto;

public interface ReportDao {

	Double getExpenseMonthly(Long userId, int month, int year);
	
	List<CategoryWiseReportDto> getCategoryWiseReport(Long userId);
	
	double getTotalSvings(Long userId, LocalDate monthStart, LocalDate monthEnd);
}
