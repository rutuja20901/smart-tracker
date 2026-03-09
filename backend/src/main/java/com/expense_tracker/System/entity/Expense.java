package com.expense_tracker.System.entity;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Entity
@Table(name="expenses")
public class Expense {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String title;
	
	@NotNull
	@Positive
	private double amount;
	
	private String category;
	
	private LocalDate expenseDate;
	
	private String notes;
	
	@ManyToOne
	@JoinColumn(name="user_id")
	private User user;

	public Expense() {
		super();
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Expense(String title, double amount, String category, LocalDate expenseDate, String notes) {
		super();
		this.title = title;
		this.amount = amount;
		this.category = category;
		this.expenseDate = expenseDate;
		this.notes = notes;
	}

	public Expense(Long id, String title, double amount, String category, LocalDate expenseDate, String notes) {
		super();
		this.id = id;
		this.title = title;
		this.amount = amount;
		this.category = category;
		this.expenseDate = expenseDate;
		this.notes = notes;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public LocalDate getExpenseDate() {
		return expenseDate;
	}

	public void setExpenseDate(LocalDate expenseDate) {
		this.expenseDate = expenseDate;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	@Override
	public String toString() {
		return "Expense [id=" + id + ", title=" + title + ", amount=" + amount + ", category=" + category
				+ ", expenseDate=" + expenseDate + ", notes=" + notes + "]";
	}
	
	

}
