package com.expense_tracker.System.dao;

import java.util.List;

import com.expense_tracker.System.entity.Budget;

public interface BudgetDao {

	Budget saveBudget(Budget b);
	
	List<Budget> findUserById(Long userId);
	
	boolean deleteBudget(Long budgetId);
}
