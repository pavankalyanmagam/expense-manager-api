package com.code.pavan.expensetrackerapi.repository;

import com.code.pavan.expensetrackerapi.entity.Expense;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense,Long> {
    // SELECT * FROM tbl_expenses where category=?
    Page<Expense> findByCategory(String category, Pageable page);

    List<Expense> findByUserId(Long userId, Pageable page);

    Optional<Expense> findByUserIdAndId(Long userId, Long id);
}
