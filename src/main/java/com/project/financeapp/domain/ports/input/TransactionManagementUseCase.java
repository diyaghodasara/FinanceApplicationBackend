package com.project.financeapp.domain.ports.input;

import com.project.financeapp.domain.Model.Transaction;
import org.springframework.stereotype.Component;

@Component
public interface TransactionManagementUseCase {
    Long addTransaction(Transaction transaction);
    void updateTransaction(Transaction transaction);
    void deleteTransaction(Long transactionId);
}
