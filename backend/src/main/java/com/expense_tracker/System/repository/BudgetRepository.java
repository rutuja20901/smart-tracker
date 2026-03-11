package com.expense_tracker.System.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.expense_tracker.System.entity.Budget;
import com.expense_tracker.System.entity.User;

@Repository
public interface BudgetRepository extends JpaRepository<Budget,Long>{

	Optional<Budget> findByUser(User user);

	

	
}
