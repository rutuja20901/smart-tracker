package com.expense_tracker.System.dto;

public class DashboardSummaryDto {

	private double totalMonthlyExpense;
	
	private double budgetLimit;
	
	private boolean budgetExceeded;
	
	private double remainingAmount;
	
	private double exceededAmount;

	public DashboardSummaryDto() {
		super();
	}

	public DashboardSummaryDto(double totalMonthlyExpense, double budgetLimit, boolean budgetExceeded,double remainingAmount,double exceededAmount) {
		super();
		this.totalMonthlyExpense = totalMonthlyExpense;
		this.budgetLimit = budgetLimit;
		this.budgetExceeded = budgetExceeded;
		this.budgetExceeded= budgetExceeded;
		this.remainingAmount = remainingAmount;
	}

	public double getTotalMonthlyExpense() {
		return totalMonthlyExpense;
	}

	public void setTotalMonthlyExpense(double totalMonthlyExpense) {
		this.totalMonthlyExpense = totalMonthlyExpense;
	}

	public double getBudgetLimit() {
		return budgetLimit;
	}

	public void setBudgetLimit(double budgetLimit) {
		this.budgetLimit = budgetLimit;
	}

	public boolean getBudgetExceeded() {
		return budgetExceeded;
	}

	public void setBudgetExceeded(boolean budgetExceeded) {
		this.budgetExceeded = budgetExceeded;
	}


	

	public double getRemainingAmount() {
		return remainingAmount;
	}

	public void setRemainingAmount(double remainingAmount) {
		this.remainingAmount = remainingAmount;
	}
	
	

	public double getExceededAmount() {
		return exceededAmount;
	}

	public void setExceededAmount(double exceededAmount) {
		this.exceededAmount = exceededAmount;
	}

	@Override
	public String toString() {
		return "DashboardSummaryDto [totalMonthlyExpense=" + totalMonthlyExpense + ", budgetLimit=" + budgetLimit
				+ ", budgetExceeded=" + budgetExceeded + ", savings=" +  "]";
	}
	
	
}
