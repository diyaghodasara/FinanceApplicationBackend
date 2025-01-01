package com.project.financeapp.domain.Service;

import com.project.financeapp.domain.Model.Transaction;
import com.project.financeapp.domain.ports.input.TransactionManagementUseCase;
import com.project.financeapp.domain.ports.output.TransactionRepository;
import org.springframework.stereotype.Component;

@Component
public class TransactionManagementService implements TransactionManagementUseCase {

    private final TransactionRepository transactionRepository;
    public TransactionManagementService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    public Long addTransaction(Transaction transaction) {
        try {
            return transactionRepository.add(transaction);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void updateTransaction(Transaction transaction) {
        try {
            transactionRepository.update(transaction);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void deleteTransaction(Long transactionId) {
        try {
             transactionRepository.delete(transactionId);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
