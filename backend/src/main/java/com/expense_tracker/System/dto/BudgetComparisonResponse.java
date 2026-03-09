package com.expense_tracker.System.dto;

public class BudgetComparisonResponse {
	
	private String category;
	
	private Double budgetLimit;
	
	private Double totalExpense;
	
	private Double remainingAmount;
	
	private String status;
	
	

	public BudgetComparisonResponse() {
		super();
	}

	public BudgetComparisonResponse(String category, Double budgetLimit, Double totalExpense, Double remainingAmount,
			String status) {
		super();
		this.category = category;
		this.budgetLimit = budgetLimit;
		this.totalExpense = totalExpense;
		this.remainingAmount = remainingAmount;
		this.status = status;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Double getBudgetLimit() {
		return budgetLimit;
	}

	public void setBudgetLimit(Double budgetLimit) {
		this.budgetLimit = budgetLimit;
	}

	public Double getTotalExpense() {
		return totalExpense;
	}

	public void setTotalExpense(Double totalExpense) {
		this.totalExpense = totalExpense;
	}

	public Double getRemainingAmount() {
		return remainingAmount;
	}

	public void setRemainingAmount(Double remainingAmount) {
		this.remainingAmount = remainingAmount;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "BudgetComparisonResponse [category=" + category + ", budgetLimit=" + budgetLimit + ", totalExpense="
				+ totalExpense + ", remainingAmount=" + remainingAmount + ", status=" + status + "]";
	}
	
	
	

}
