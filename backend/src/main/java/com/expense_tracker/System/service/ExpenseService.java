package com.expense_tracker.System.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.expense_tracker.System.config.LoggedInUser;
import com.expense_tracker.System.dao.ExpenseDaoImpl;
import com.expense_tracker.System.dto.ExpenseDaoRequest;
import com.expense_tracker.System.entity.Expense;

/*
 Controller should NOT talk to EntityManager

Service handles validation & business rules
 */

@Service
public class ExpenseService {
	
	@Autowired
	private ExpenseDaoImpl expenseImpl;
	
	@Autowired
	private LoggedInUser loggedInUser;
	
	/**
     * Adds a new expense entry.
     * Delegates persistence responsibility to DAO layer.
     */
    public Expense addExpense(Expense expense) {
        return expenseImpl.saveExpense(expense);
    }

    /**
     * Retrieves all expense records.
     * Used for displaying expense list or dashboard views.
     */
    public List<Expense> listExpense() {
        return expenseImpl.getAllExpense();
    }

    /**
     * Fetches a specific expense by its ID.
     * Throws an exception if the expense does not exist,
     * ensuring invalid IDs are handled at service level.
     */
    public Expense findExpenseById(Long expenseId) {
        Expense expense = expenseImpl.findById(expenseId);

        if (expense == null) {
            throw new RuntimeException("Expense not found with id: " + expenseId);
        }
        return expense;
    }

    /**
     * Updates an existing expense using request data.
     * Validates existence before returning updated entity.
     */
    public Expense updateExpense(Long expenseId, ExpenseDaoRequest request) {

        Expense updatedExpense = expenseImpl.updateExpense(expenseId, request);

        /*
         * Returning null indicates the expense was not found.
         * This can later be replaced with a custom exception.
         */
        if (updatedExpense == null) {
            return null;
        }

        return updatedExpense;
    }

    /**
     * Deletes an expense by its ID.
     * Throws an exception if the expense does not exist.
     */
    public void deleteExpense(Long expenseId) {

        boolean deleted = expenseImpl.deleteExpense(expenseId);

        if (!deleted) {
            throw new RuntimeException("Expense not found with id: " + expenseId);
        }
    }

    /**
     * Retrieves expenses filtered by category.
     * Useful for category-wise expense analysis.
     */
    public List<Expense> getExpenseByCategory(String category) {
        return expenseImpl.getExpenseByCategory(category);
    }

    /**
     * Retrieves expenses within a specific date range.
     * Commonly used for monthly or custom period reports.
     */
    public List<Expense> getExpenseBetweenDates(LocalDate start, LocalDate end) {
        return expenseImpl.getExpenseBetweenDates(start, end);
    }

    /**
     * Retrieves expenses within a specified amount range.
     * Helps analyze expenses within budget limits.
     */
    public List<Expense> getExpenseBetweenAmount(double min, double max) {
        return expenseImpl.getExpenseBetweenAmount(min, max);
    }
    
    
    
}
