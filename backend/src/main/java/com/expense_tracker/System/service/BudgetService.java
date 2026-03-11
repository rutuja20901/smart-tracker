package com.expense_tracker.System.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;

import org.springframework.stereotype.Service;

import com.expense_tracker.System.dao.BudgetDaoImpl;
import com.expense_tracker.System.dao.ExpenseDaoImpl;
import com.expense_tracker.System.dto.BudgetComparisonResponse;
import com.expense_tracker.System.entity.Budget;
import com.expense_tracker.System.entity.User;
import com.expense_tracker.System.repository.BudgetRepository;
import com.expense_tracker.System.repository.UserRepository;

@Service
public class BudgetService {
	
	@Autowired
	private BudgetDaoImpl budgetDaoImpl;
	
	@Autowired
	private ExpenseDaoImpl expenseDaoImpl;
	
	@Autowired 
	private UserRepository userRepository;	
	
	@Autowired
	private BudgetRepository budgetRepositry;
	
	
	public Budget saveBudget(String email ,Budget b) {
		User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found!"));
		b.setUser(user);
		return budgetDaoImpl.saveBudget(b);
	}
	
	public List<Budget> getBudgetsByEmail(String email) {
	    User user = userRepository.findByEmail(email)
	            .orElseThrow(() -> new RuntimeException("User not found"));

	    return budgetDaoImpl.findUserById(user.getId());
	}
	
	public void deleteBudget(Long budgetId) {
		boolean deleted = budgetDaoImpl.deleteBudget(budgetId);
		if(!deleted) {
			throw new RuntimeException("Budget not found with id: " + budgetId);
		}
	}
	
	public BudgetComparisonResponse compareBudget(Long userId, String category) {
		Budget budget = budgetDaoImpl.findByUserAndCategory(userId,category);
		
		Double totalExpense = expenseDaoImpl.getTotalExpese(userId, category, budget.getStartDate(), budget.getEndDate());
		
		BudgetComparisonResponse response = new BudgetComparisonResponse();
		response.setCategory(category);
		response.setBudgetLimit(budget.getLimitAmount());
		response.setTotalExpense(totalExpense);
		response.setRemainingAmount(budget.getLimitAmount() - totalExpense);
		
		if(totalExpense > budget.getLimitAmount()) {
			response.setStatus("LIMIT EXCEEDED");
		}else {
			response.setStatus("WITHIN LIMIT");
		}
		
		return response;
		
	}
	
	
	public double getTotalActiveBudgetLimit(Authentication authentication) {

		String email = authentication.getName();
	    User user = userRepository.findByEmail(email)
	            .orElseThrow(() -> new RuntimeException("User not found"));

	    LocalDate today = LocalDate.now();

	    return budgetRepositry.findByUser(user).stream()
	            .filter(b ->
	                !today.isBefore(b.getStartDate()) &&
	                !today.isAfter(b.getEndDate())
	            )
	            .mapToDouble(Budget::getLimitAmount)
	            .sum();
	}

	


	public Budget updateBudget(Long id, Budget budget) {
		Budget b = budgetRepositry.findById(id).orElseThrow(() -> new RuntimeException("Budget not found!"));
		
		if(b == null) {
			return null;
		}
		
		b.setCategory(budget.getCategory());
		b.setStartDate(budget.getStartDate());
		b.setLimitAmount(budget.getLimitAmount());
		b.setEndDate(budget.getEndDate());
		
		return budgetRepositry.save(b);
		
		
	}
}
