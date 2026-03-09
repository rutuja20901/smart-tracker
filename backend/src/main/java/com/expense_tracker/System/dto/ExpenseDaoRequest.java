package com.expense_tracker.System.dto;

import java.time.LocalDate;

public class ExpenseDaoRequest {
	
	private String category;
	
	private String title;
	
	private String notes;
	
	private double amount;
	
	private LocalDate expenseDate;

	public ExpenseDaoRequest() {
		super();
	}

	public ExpenseDaoRequest(String category, String title, String notes, double amount, LocalDate expenseDate) {
		super();
		this.category = category;
		this.title = title;
		this.notes = notes;
		this.amount = amount;
		this.expenseDate = expenseDate;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public LocalDate getExpenseDate() {
		return expenseDate;
	}

	public void setExpenseDate(LocalDate expenseDate) {
		this.expenseDate = expenseDate;
	}

	@Override
	public String toString() {
		return "ExpenseDaoRequest [category=" + category + ", title=" + title + ", notes=" + notes + ", amount="
				+ amount + ", expenseDate=" + expenseDate + "]";
	}

	

}
