package com.ornate.incomeexpense.controllers;

import com.ornate.incomeexpense.dto.TransactionsDto;
import com.ornate.incomeexpense.enums.TransactionType;
import com.ornate.incomeexpense.services.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping
    public ResponseEntity<TransactionsDto> addTransaction(@RequestBody TransactionsDto transactionsDto){
        TransactionsDto savedTransaction = transactionService.addTrasactions(transactionsDto);
        return ResponseEntity.ok(savedTransaction);
    }

    @GetMapping("/summary")
    public ResponseEntity<Map<String, BigDecimal>> getTransactionSummary(@RequestParam(required = false) String type){
        TransactionType transactionType = type != null ? TransactionType.fromString(type) : null;

        Map<String, BigDecimal> summary = transactionService.getTransactionSummary(transactionType);
        return ResponseEntity.ok(summary);
    }

    

}
