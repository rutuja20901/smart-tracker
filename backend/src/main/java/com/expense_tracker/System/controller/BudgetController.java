package com.expense_tracker.System.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.expense_tracker.System.dto.BudgetComparisonResponse;
import com.expense_tracker.System.entity.Budget;
import com.expense_tracker.System.service.BudgetService;

@RestController
@RequestMapping("/api/user/budget")
public class BudgetController {
	
	@Autowired
	private BudgetService budgetService;
	
	@PostMapping()
	public Budget saveBudget(Authentication authentication ,@RequestBody Budget b) {
		String email = authentication.getName();
		return budgetService.saveBudget(email,b);
	}
	
	@GetMapping("/{userId}")
	public List<Budget> listBudget(Authentication authentication){
		String email = authentication.getName();
		return budgetService.getBudgetsByEmail(email);
	}
	
	
	
	@DeleteMapping("/{budgetId}")
	public void deletBudget(@PathVariable Long budgetId) {
		budgetService.deleteBudget(budgetId);
	}
	
	@GetMapping("/compare/{userId}/{category}")
	public BudgetComparisonResponse compareBudget(@PathVariable Long userId, @PathVariable String category) {
		return budgetService.compareBudget(userId, category);
	}
	
	@PutMapping("/{id}")
	public Budget updateBudget(@PathVariable Long id, @RequestBody Budget budget) {
		return budgetService.updateBudget(id, budget);
	}

}
