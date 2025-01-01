package com.project.financeapp.domain.ports.output;

import com.project.financeapp.domain.Model.Transaction;


public interface TransactionRepository {
    Long add(Transaction transaction);
    void delete(Long transactionId);
    void update(Transaction transaction);
}
