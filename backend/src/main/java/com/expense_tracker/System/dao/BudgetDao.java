package com.expense_tracker.System.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.expense_tracker.System.entity.Budget;
import com.expense_tracker.System.entity.User;

public interface BudgetDao{

	Budget saveBudget(Budget b);
	
	List<Budget> findUserById(Long userId);
	
	boolean deleteBudget(Long budgetId);
	
	
}
