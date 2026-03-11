package com.expense_tracker.System.dao;

import java.time.LocalDate;
import java.util.List;

import org.springframework.security.core.Authentication;

import com.expense_tracker.System.dto.ExpenseDaoRequest;
import com.expense_tracker.System.entity.Expense;

public interface ExpenseDao {
	
	
	
	/**
     * Creates and saves a new expense record.
     * Used when a user adds a fresh expense entry.
     */
    Expense saveExpense(Expense expense);

    /**
     * Retrieves all expense records.
     * Mainly used for listing expenses on dashboard or history screens.
     */
    List<Expense> getAllExpense();

    /**
     * Fetches a single expense based on its unique ID.
     * Used for viewing expense details or before updating an expense.
     */
    Expense findById(Long expenseId);

    /**
     * Updates an existing expense using request data.
     * Used when user edits an already saved expense.
     */
    Expense updateExpense(Long id, ExpenseDaoRequest request);

    /**
     * Deletes an expense by its ID.
     * Used when a user removes an expense entry permanently.
     */
    boolean deleteExpense(Long id);

    /**
     * Retrieves expenses filtered by category.
     * Used for category-based expense analysis (e.g., FOOD, TRAVEL).
     */
    List<Expense> getExpenseByCategory(String category);

    /**
     * Retrieves expenses within a specific date range.
     * Used for monthly or custom date-based expense tracking.
     */
    List<Expense> getExpenseBetweenDates(LocalDate start, LocalDate end);

    /**
     * Retrieves expenses within a specified amount range.
     * Used for analyzing expenses between minimum and maximum values.
     */
    List<Expense> getExpenseBetweenAmount(double min, double max);
    
    
    Double getTotalExpese(Long userId, String category, LocalDate start, LocalDate end);
    
   

       
    
    
}
