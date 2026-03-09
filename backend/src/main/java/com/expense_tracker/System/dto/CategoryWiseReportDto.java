package com.expense_tracker.System.dto;




public class CategoryWiseReportDto {
	
	private String category;
	private double totalmount;
	public CategoryWiseReportDto() {
		super();
	}
	public CategoryWiseReportDto(String category, double totalmount) {
		super();
		this.category = category;
		this.totalmount = totalmount;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public double getTotalmount() {
		return totalmount;
	}
	public void setTotalmount(double totalmount) {
		this.totalmount = totalmount;
	}
	@Override
	public String toString() {
		return "CategoryWiseReportDto [category=" + category + ", totalmount=" + totalmount + "]";
	}
	
	

}
