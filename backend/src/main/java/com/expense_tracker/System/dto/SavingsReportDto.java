package com.expense_tracker.System.dto;

public class SavingsReportDto {

	private int month;
	
	private int year;
	
	private double budgetAmount;
	
	private double totalExpense;
	
	private double savings;
	
	private boolean budgetExceeded;
	
	private double exceededAmount;

	public SavingsReportDto() {
		super();
	}



	public SavingsReportDto(int month, int year, double budgetAmount, double totalExpense, double savings) {
		super();
		this.month = month;
		this.year = year;
		this.budgetAmount = budgetAmount;
		this.totalExpense = totalExpense;
		this.savings = savings;
	}


	

	public SavingsReportDto(int month, int year, double budgetAmount, double totalExpense, double savings,
			boolean budgetExceeded, double exceededAmount) {
		super();
		this.month = month;
		this.year = year;
		this.budgetAmount = budgetAmount;
		this.totalExpense = totalExpense;
		this.savings = savings;
		this.budgetExceeded = budgetExceeded;
		this.exceededAmount = exceededAmount;
	}



	public int getMonth() {
		return month;
	}



	public void setMonth(int month) {
		this.month = month;
	}



	public int getYear() {
		return year;
	}



	public void setYear(int year) {
		this.year = year;
	}



	public double getBudgetAmount() {
		return budgetAmount;
	}



	public void setBudgetAmount(double budgetAmount) {
		this.budgetAmount = budgetAmount;
	}



	public double getTotalExpense() {
		return totalExpense;
	}



	public void setTotalExpense(double totalExpense) {
		this.totalExpense = totalExpense;
	}



	public double getSavings() {
		return savings;
	}



	public void setSavings(double savings) {
		this.savings = savings;
	}



	public boolean isBudgetExceeded() {
		return budgetExceeded;
	}



	public void setBudgetExceeded(boolean budgetExceeded) {
		this.budgetExceeded = budgetExceeded;
	}



	public double getExceededAmount() {
		return exceededAmount;
	}



	public void setExceededAmount(double exceededAmount) {
		this.exceededAmount = exceededAmount;
	}



	@Override
	public String toString() {
		return "SavingsReportDto [month=" + month + ", year=" + year + ", budgetAmount=" + budgetAmount
				+ ", totalExpense=" + totalExpense + ", savings=" + savings + "]";
	}
	
	
	
	
}
