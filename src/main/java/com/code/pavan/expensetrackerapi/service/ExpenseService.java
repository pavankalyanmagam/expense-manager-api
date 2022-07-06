package com.code.pavan.expensetrackerapi.service;

import com.code.pavan.expensetrackerapi.entity.Expense;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ExpenseService {

    List<Expense> getAllExpenses(int pageNumber, int pageSize);

    Expense getExpenseById(Long id);

    void deleteExpenseById(Long id);

    Expense saveExpensesDetails(Expense expense);

    Expense updateExpenseDetails(Long id, Expense expense);

    List<Expense> readByCategory(String category, Pageable page);
}
