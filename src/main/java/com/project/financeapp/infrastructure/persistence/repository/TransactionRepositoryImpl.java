package com.project.financeapp.infrastructure.persistence.repository;

import com.project.financeapp.domain.Model.Transaction;
import com.project.financeapp.domain.ports.output.TransactionRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class TransactionRepositoryImpl implements TransactionRepository {

    private final SimpleJdbcCall simpleJdbcCall1;
    private final SimpleJdbcCall simpleJdbcCall2;
    private final SimpleJdbcCall simpleJdbcCall3;

    public TransactionRepositoryImpl(@Qualifier("AddTransaction")SimpleJdbcCall simpleJdbcCall1,
                                     @Qualifier("UpdateTransaction")SimpleJdbcCall simpleJdbcCall2,
                                     @Qualifier("DeleteTransaction")SimpleJdbcCall simpleJdbcCall3) {
        this.simpleJdbcCall1 = simpleJdbcCall1;
        this.simpleJdbcCall2 = simpleJdbcCall2;
        this.simpleJdbcCall3 = simpleJdbcCall3;
    }
    @Override
    public Long add(Transaction transaction) {
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("in_user_id",transaction.getUserId());
            params.put("in_date",transaction.getDate());
            params.put("in_amount",transaction.getAmount());
            params.put("in_category_id",transaction.getCategoryId());
            params.put("in_description",transaction.getDescription());

            Map<String, Object> results = simpleJdbcCall1.execute(params);
            String message = (String) results.get("out_message");
            if(message != null && !message.isEmpty() && !"Success".equalsIgnoreCase(message)){
                throw new RuntimeException(message);
            }
            return (Long) results.get("out_transaction_id");
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void delete(Long transactionId) {
        try{
            Map<String, Object> params = new HashMap<>();
            params.put("in_transaction_id",transactionId);

            Map<String, Object> results = simpleJdbcCall3.execute(params);
            String message = (String) results.get("out_message");
            if(message != null && !message.isEmpty() && !"Success".equalsIgnoreCase(message)){
                throw new RuntimeException(message);
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void update(Transaction transaction) {
        try{
            Map<String, Object> params = new HashMap<>();
            params.put("in_transaction_id",transaction.getTransactionId());
            params.put("in_user_id",transaction.getUserId());
            params.put("in_date",transaction.getDate());
            params.put("in_amount",transaction.getAmount());
            params.put("in_category_id",transaction.getCategoryId());
            params.put("in_description",transaction.getDescription());
            Map<String, Object> results = simpleJdbcCall2.execute(params);
            String message = (String) results.get("out_message");
            if(message != null && !message.isEmpty() && !"Success".equalsIgnoreCase(message)){
                throw new RuntimeException(message);
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
