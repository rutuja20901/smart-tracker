package com.expense_tracker.System.entity;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Budget {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String category;
	
	private double limitAmount;
	
	private LocalDate startDate;
	
	private LocalDate endDate;
	
	@ManyToOne
	@JoinColumn(name="user_id")
	private User user;

	public Budget() {
		super();
	}

	public Budget(String category, double limitAmount, LocalDate startDate, LocalDate endDate, User user) {
		super();
		this.category = category;
		this.limitAmount = limitAmount;
		this.startDate = startDate;
		this.endDate = endDate;
		this.user = user;
	}

	public Budget(Long id, String category, double limitAmount, LocalDate startDate, LocalDate endDate, User user) {
		super();
		this.id = id;
		this.category = category;
		this.limitAmount = limitAmount;
		this.startDate = startDate;
		this.endDate = endDate;
		this.user = user;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public double getLimitAmount() {
		return limitAmount;
	}

	public void setLimitAmount(double limitAmount) {
		this.limitAmount = limitAmount;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "Budget [id=" + id + ", category=" + category + ", limitAmount=" + limitAmount + ", startDate="
				+ startDate + ", endDate=" + endDate + ", user=" + user + "]";
	}
	
	
	

}
