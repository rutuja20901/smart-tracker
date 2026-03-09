package com.expense_tracker.System.dao;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.expense_tracker.System.dto.CategoryWiseReportDto;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Repository
public class ReportDaoImpl implements ReportDao {

	@PersistenceContext
	private EntityManager entityManager;

	@Transactional
	@Override
	public Double getExpenseMonthly(Long userId, int month, int year) {
		String jpql = """
				SELECT COALESCE (SUM(e.amount),0)
				FROM Expense e
				WHERE e.user.id = :userId
				AND MONTH(e.expenseDate) = :month
				AND YEAR(e.expenseDate) = :year
				""";

		return entityManager.createQuery(jpql, Double.class).setParameter("userId", userId).setParameter("month", month)
				.setParameter("year", year).getSingleResult();
	}

	@Override
	public List<CategoryWiseReportDto> getCategoryWiseReport(Long userId) {
		String jpql = """
				SELECT new com.expense_tracker.System.dto.CategoryWiseReportDto(
				e.category,
				COALESCE(SUM(e.amount),0))
				FROM Expense e
				WHERE e.user.id = :userId
				GROUP BY e.category
				""";

		return entityManager.createQuery(jpql, CategoryWiseReportDto.class).setParameter("userId", userId)
				.getResultList();
	}

	@Override
	public double getTotalSvings(Long userId, LocalDate monthStart, LocalDate monthEnd) {
		String jpql = """
				SELECT COALESCE(b.limitAmount ,0)
				FROM Budget b
				WHERE b.user.id = :userId
				AND b.startDate <= :monthEnd
				AND b.endDate >= :monthStart
				""";

		return entityManager.createQuery(jpql, Double.class).setParameter("userId", userId).setParameter("monthStart", monthStart)
				.setParameter("monthEnd", monthEnd).getSingleResult();
	}
}
