package com.ornate.incomeexpense.services;

import com.ornate.incomeexpense.config.TransactionMapper;
import com.ornate.incomeexpense.dto.TransactionsDto;
import com.ornate.incomeexpense.enums.TransactionType;
import com.ornate.incomeexpense.model.Transactions;
import com.ornate.incomeexpense.repository.TransactionRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TransactionService {
    private final TransactionRepository repository;

    private final TransactionMapper transactionMapper = TransactionMapper.INSTANCE;
    public TransactionService(TransactionRepository repository) {
        this.repository = repository;
    }

    public TransactionsDto addTrasactions(TransactionsDto transactionsDto){

        Transactions transactions = transactionMapper.toEntity(transactionsDto);
        Long userId = getAuthUserId();
        transactions.setUserId(userId);
        Transactions saveTransactions = repository.save(transactions);
        return transactionMapper.toDto(saveTransactions);
    }

    public Map<String, BigDecimal> getTransactionSummary(TransactionType type){
        Map<String, BigDecimal> summary = new HashMap<>();
        if (type != null){
            BigDecimal total =  repository.sumAmountByUserIdAndType(getAuthUserId(), type.name());
            summary.put(type == TransactionType.INCOME ? "totalIncome" : "totalExpense", total);
        }else {
            BigDecimal totalIncome =  repository.sumAmountByUserIdAndType(getAuthUserId(), TransactionType.INCOME.name());
            BigDecimal totalExpense =  repository.sumAmountByUserIdAndType(getAuthUserId(), TransactionType.EXPENSE.name());

            BigDecimal sumTotal = totalIncome.subtract(totalExpense);

            summary.put("totalIncome", totalIncome);
            summary.put("totalExpense", totalExpense);
            summary.put("sumTotal", sumTotal);
        }

        return summary;
    }

    private Long getAuthUserId(){
        Long userId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userId;
    }


    public List<TransactionsDto> getAllTransactions() {
        List<Transactions> allTransaction = repository.findAll();

        return allTransaction.stream().map(transactionMapper::toDto).toList();
    }
}
