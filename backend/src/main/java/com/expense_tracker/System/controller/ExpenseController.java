package com.expense_tracker.System.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.expense_tracker.System.dto.ExpenseDaoRequest;
import com.expense_tracker.System.entity.Expense;
import com.expense_tracker.System.service.ExpenseService;

@RestController
@RequestMapping("/api/user/expense")
public class ExpenseController {
	
	@Autowired
	private ExpenseService expenseService;
	
	/**
     * Creates a new expense entry.
     * Accepts expense details in request body and forwards
     * them to the service layer for processing.
     */
    @PostMapping
    public Expense addExpense(@RequestBody Expense expense) {
        return expenseService.addExpense(expense);
    }

    /**
     * Retrieves all expenses.
     * Used to display the complete expense list.
     */
    @GetMapping
    public List<Expense> listExpense() {
        return expenseService.listExpense();
    }

    /**
     * Retrieves a single expense by its ID.
     * Path variable is used to identify the expense.
     */
    @GetMapping("/{id}")
    public Expense findExpenseById(@PathVariable Long id) {
        return expenseService.findExpenseById(id);
    }

    /**
     * Updates an existing expense.
     * Uses expense ID from path and updated fields from request body.
     */
    @PutMapping("/{id}")
    public Expense updateExpense(
            @PathVariable Long id,
            @RequestBody ExpenseDaoRequest request) {

        return expenseService.updateExpense(id, request);
    }

    /**
     * Deletes an expense by its ID.
     * Returns a success message after deletion.
     */
    @DeleteMapping("/{id}")
    public String deleteExpense(@PathVariable Long id) {
        expenseService.deleteExpense(id);
        return "Expense deleted successfully!";
    }

    /**
     * Retrieves expenses filtered by category.
     * Example: /expenses/category/FOOD
     */
    @GetMapping("/category/{category}")
    public ResponseEntity<List<Expense>> getExpenseByCategory(
            @PathVariable String category) {

        List<Expense> expenses = expenseService.getExpenseByCategory(category);
        return ResponseEntity.ok(expenses);
    }

    /**
     * Retrieves expenses within a specified date range.
     * Uses request parameters instead of path variables for flexibility.
     */
    @GetMapping("/date")
    public ResponseEntity<List<Expense>> getExpenseBetweenDates(
            @RequestParam LocalDate start,
            @RequestParam LocalDate end) {

        List<Expense> expenses = expenseService.getExpenseBetweenDates(start, end);
        return ResponseEntity.ok(expenses);
    }

    /**
     * Retrieves expenses within a specific amount range.
     * Useful for analyzing expenses between minimum and maximum values.
     */
    @GetMapping("/amount")
    public ResponseEntity<List<Expense>> getExpenseBetweenAmount(
            @RequestParam double min,
            @RequestParam double max) {

        List<Expense> expenses = expenseService.getExpenseBetweenAmount(min, max);
        return ResponseEntity.ok(expenses);
    }
}
