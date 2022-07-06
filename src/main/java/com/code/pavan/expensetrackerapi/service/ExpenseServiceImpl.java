package com.code.pavan.expensetrackerapi.service;

import com.code.pavan.expensetrackerapi.entity.Expense;
import com.code.pavan.expensetrackerapi.exception.ResourceNotFoundException;
import com.code.pavan.expensetrackerapi.repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ExpenseServiceImpl implements ExpenseService {
    @Autowired
    ExpenseRepository expenseRepository;
    @Autowired
    private UserService userService;
    @Override
    public List<Expense> getAllExpenses(int pageNumber, int pageSize) {
        Pageable pages = PageRequest.of(pageNumber,pageSize, Sort.Direction.ASC,"id");
        return expenseRepository.findByUserId(userService.getLoggedInUser().getId(),pages);
    }

    @Override
    public Expense getExpenseById(Long id) {
        Optional<Expense> expense=expenseRepository.findByUserIdAndId(userService.getLoggedInUser().getId(),id);
        if(expense.isPresent()){
            return expense.get();
        }
        throw new ResourceNotFoundException("Expense is not found for the id "+id);
    }

    @Override
    public void deleteExpenseById(Long id) {
        expenseRepository.deleteById(id);
    }

    @Override
    public Expense saveExpensesDetails(Expense expense) {
        expense.setUser(userService.getLoggedInUser());
        return expenseRepository.save(expense);
    }

    @Override
    public Expense updateExpenseDetails(Long id, Expense expense) {
       Expense existingExpense = getExpenseById(id);
       existingExpense.setName(expense.getName() != null ? expense.getName() : existingExpense.getName());
       existingExpense.setDescription(expense.getDescription() != null ? expense.getDescription() : existingExpense.getDescription());
       existingExpense.setAmount(expense.getAmount() !=null ? expense.getAmount():existingExpense.getAmount());
       existingExpense.setCategory(expense.getCategory() !=null?expense.getCategory():existingExpense.getCategory());
       existingExpense.setDate(expense.getDate()!=null?expense.getDate():existingExpense.getDate());
       return expenseRepository.save(existingExpense);
    }

    @Override
    public List<Expense> readByCategory(String category, Pageable page) {
       return expenseRepository.findByCategory(category,page).toList();
    }
}
