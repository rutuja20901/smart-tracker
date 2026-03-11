package com.expense_tracker.System.dto;

public class MonthlyExpenseReportDto {
	

	private int month;
	private int year;
	private double totalExpense;
	public MonthlyExpenseReportDto() {
		super();
	}
	public MonthlyExpenseReportDto(int month, int year, double totalExpense) {
		super();
		this.month = month;
		this.year = year;
		this.totalExpense = totalExpense;
	}
	
	
	
	public MonthlyExpenseReportDto(int month, int year) {
		super();
		this.month = month;
		this.year = year;
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
	public double getTotalExpense() {
		return totalExpense;
	}
	public void setTotalExpense(double totalExpense) {
		this.totalExpense = totalExpense;
	}
	@Override
	public String toString() {
		return "MonthlyExpenseReportDto [month=" + month + ", year=" + year + ", totalExpense=" + totalExpense + "]";
	}
	
	
}
