package com.expense_tracker.System.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.expense_tracker.System.dao.BudgetDaoImpl;
import com.expense_tracker.System.dao.ExpenseDaoImpl;
import com.expense_tracker.System.dto.BudgetComparisonResponse;
import com.expense_tracker.System.entity.Budget;
import com.expense_tracker.System.entity.User;
import com.expense_tracker.System.repository.UserRepository;

@Service
public class BudgetService {
	
	@Autowired
	private BudgetDaoImpl budgetDaoImpl;
	
	@Autowired
	private ExpenseDaoImpl expenseDaoImpl;
	
	@Autowired 
	private UserRepository userRepository;																																																																									
	
	
	public Budget saveBudget(Long userId,Budget b) {
		User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found!"));
		b.setUser(user);
		return budgetDaoImpl.saveBudget(b);
	}
	
	public List<Budget> getBudgets(Long userId){
		return budgetDaoImpl.findUserById(userId);
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

}
