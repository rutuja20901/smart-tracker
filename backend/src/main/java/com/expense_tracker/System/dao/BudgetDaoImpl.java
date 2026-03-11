package com.expense_tracker.System.dao;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.FluentQuery.FetchableFluentQuery;
import org.springframework.stereotype.Repository;

import com.expense_tracker.System.entity.Budget;
import com.expense_tracker.System.entity.User;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Repository
public class BudgetDaoImpl implements BudgetDao{
	
	@PersistenceContext
	private EntityManager entityManager;
	
	
	@Override
	@Transactional
	public Budget saveBudget(Budget b) {
		entityManager.persist(b);
		return b;
	}
	
	public List<Budget> findUserById(Long userId){
		String jpql = "FROM Budget b WHERE b.user.id =:userId";
		return entityManager.createQuery(jpql,Budget.class)
				.setParameter("userId", userId).getResultList();
	}
	
	
	@Override
	@Transactional
	public boolean deleteBudget(Long budgetId) {
		Budget budget = entityManager.find(Budget.class, budgetId);
		
		if(budget == null) {
			return false;
		}
		
		entityManager.remove(budget);
		return true;
	}

	public Budget findByUserAndCategory(Long userId, String category) {
		String jpql = "FROM Budget b WHERE b.user.id = :userId AND b.category = :category";
		return entityManager.createQuery(jpql,Budget.class)
				.setParameter("userId", userId)
				.setParameter("category",category)
				.getSingleResult();
		
	}

	


	
	

	
	

}
