package com.code.pavan.expensetrackerapi.controller;

import com.code.pavan.expensetrackerapi.entity.Expense;
import com.code.pavan.expensetrackerapi.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class ExpenseController {
    @Autowired
    ExpenseService expenseService;

    @GetMapping("/expenses")
    public ResponseEntity<List<Expense>> getAllExpenses(@RequestParam Integer page, @RequestParam Integer size ){
        return new ResponseEntity<>(expenseService.getAllExpenses(page,size),HttpStatus.OK);
    }

    @GetMapping("/expenses/{id}")
    public ResponseEntity<Expense> getExpensesById(@PathVariable Long id){
        return new ResponseEntity<>(expenseService.getExpenseById(id),HttpStatus.OK);
    }

    @DeleteMapping("/expenses")
    public void deleteExpenseById(@RequestParam("id") Long id){
         expenseService.deleteExpenseById(id);
    }
    @PostMapping("/saveExpenses")
    public ResponseEntity<Expense> saveExpenses(@Valid @RequestBody Expense expense){
        return new ResponseEntity<>(expenseService.saveExpensesDetails(expense),HttpStatus.CREATED);
    }
    @PutMapping("/updateExpenses/{id}")
    public ResponseEntity<Expense> updateExpenses(@PathVariable Long id, @RequestBody Expense expense){
        return new ResponseEntity<>(expenseService.updateExpenseDetails(id,expense),HttpStatus.CREATED);
    }

    @GetMapping("/expenses/category")
    public List<Expense> getExpenseByCategory(@RequestParam String category, @RequestParam  Pageable page){
        return expenseService.readByCategory(category,page);
    }
}

