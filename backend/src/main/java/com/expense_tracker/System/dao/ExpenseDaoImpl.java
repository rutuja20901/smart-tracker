package com.expense_tracker.System.dao;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Repository;

import com.expense_tracker.System.config.LoggedInUser;
import com.expense_tracker.System.dto.ExpenseDaoRequest;
import com.expense_tracker.System.entity.Expense;
import com.expense_tracker.System.entity.User;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

//@Transactional → must for persist()

@Repository
public class ExpenseDaoImpl implements ExpenseDao{
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	private LoggedInUser loggedInUser;
	
	@Override
	@Transactional
	public Expense saveExpense(Expense expense) {
	    /*
	     * persist() is used to save a new entity into the persistence context.
	     * Once persisted, the entity is managed by JPA and automatically stored in DB.
	     */
		
		User user = loggedInUser.getLoggedInUser();
		expense.setUser(user);
	    entityManager.persist(expense);
	    return expense;
	}

	@Override
	public List<Expense> getAllExpense() {
	    /*
	     * JPQL works on Entity names, not database table names.
	     * This query fetches all Expense entities from the database.
	     */
	    return entityManager
	            .createQuery("FROM Expense", Expense.class)
	            .getResultList();
	}

	@Override
	public Expense findById(Long expenseId) {
	    /*
	     * find() retrieves an entity by its primary key.
	     * Returns null if the entity does not exist.
	     */
	    return entityManager.find(Expense.class, expenseId);
	}

	@Override
	@Transactional
	public Expense updateExpense(Long id, ExpenseDaoRequest request) {

	    /*
	     * Fetch the existing expense to ensure we update a managed entity.
	     * If not found, returning null avoids updating non-existent records.
	     */
	    Expense expense = entityManager.find(Expense.class, id);

	    if (expense == null) {
	        return null;
	    }

	    /*
	     * Updating fields on a managed entity.
	     * JPA automatically detects these changes and syncs them with DB
	     * at transaction commit (Dirty Checking).
	     */
	    expense.setAmount(request.getAmount());
	    expense.setCategory(request.getCategory());
	    expense.setExpenseDate(request.getExpenseDate());
	    expense.setNotes(request.getNotes());
	    expense.setTitle(request.getTitle());

	    return expense;
	}

	@Override
	@Transactional
	public boolean deleteExpense(Long id) {

	    /*
	     * First retrieve the entity to make sure it exists
	     * and is managed before deletion.
	     */
	    Expense expense = entityManager.find(Expense.class, id);

	    if (expense == null) {
	        return false;
	    }

	    /*
	     * remove() deletes the managed entity from persistence context
	     * and removes the corresponding record from DB.
	     */
	    entityManager.remove(expense);
	    return true;
	}

	@Override
	public List<Expense> getExpenseByCategory(String category) {

	    /*
	     * JPQL query with named parameter for category-based filtering.
	     * Named parameters improve readability and prevent SQL injection.
	     */
	    String query = "FROM Expense e WHERE e.category = :category";

	    return entityManager
	            .createQuery(query, Expense.class)
	            .setParameter("category", category)
	            .getResultList();
	}

	@Override
	public List<Expense> getExpenseBetweenDates(LocalDate start, LocalDate end) {

	    /*
	     * BETWEEN clause is used to fetch expenses within a date range.
	     * LocalDate is directly supported by JPA for date-based filtering.
	     */
	    String query = "FROM Expense e WHERE e.expenseDate BETWEEN :start AND :end";

	    return entityManager
	            .createQuery(query, Expense.class)
	            .setParameter("start", start)
	            .setParameter("end", end)
	            .getResultList();
	}

	@Override
	public List<Expense> getExpenseBetweenAmount(double min, double max) {

	    /*
	     * BETWEEN clause is used to filter expenses by amount range.
	     * Useful for analyzing expenses within specific budget limits.
	     */
	    String query = "FROM Expense e WHERE e.amount BETWEEN :min AND :max";

	    return entityManager
	            .createQuery(query, Expense.class)
	            .setParameter("min", min)
	            .setParameter("max", max)
	            .getResultList();
	}

	@Override
	public Double getTotalExpese(
			Long userId,
			String category,
			LocalDate start,
			LocalDate end) {
		
		//COALESCE prevents null when no expenses exist.
		String jpql = """
				SELECT COALESCE(SUM(e.amount),0)
				FROM Expense e
				WHERE e.user.id = :userId
				AND e.category = :category
				AND e.expenseDate BETWEEN :start AND :end
				""";
		
		return entityManager.createQuery(jpql,Double.class)
				.setParameter("userId", userId)
				.setParameter("category", category)
				.setParameter("start", start)
				.setParameter("end", end)
				.getSingleResult();
	}

	
	
	

}
