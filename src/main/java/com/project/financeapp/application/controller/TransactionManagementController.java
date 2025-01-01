package com.project.financeapp.application.controller;

import com.project.financeapp.application.dto.Transaction.AddTransactionRequestDTO;
import com.project.financeapp.application.dto.Transaction.UpdateTransactionRequestDTO;
import com.project.financeapp.domain.Model.Transaction;
import com.project.financeapp.domain.ports.input.TransactionManagementUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class TransactionManagementController {

    private final TransactionManagementUseCase transactionManagementUseCase;

    public TransactionManagementController(TransactionManagementUseCase transactionManagementUseCase) {
        this.transactionManagementUseCase = transactionManagementUseCase;
    }

    @PostMapping("/v1/transaction/add")
    ResponseEntity<?> addTransaction(@RequestBody AddTransactionRequestDTO addTransactionRequestDTO) {
        try{
            Transaction t = new Transaction();
            t.setUserId(addTransactionRequestDTO.getUserId());
            t.setCategoryId(addTransactionRequestDTO.getCategoryId());
            t.setAmount(addTransactionRequestDTO.getAmount());
            t.setDate(addTransactionRequestDTO.getDate());
            t.setDescription(addTransactionRequestDTO.getDescription());

            Long transactionId = transactionManagementUseCase.addTransaction(t);
            return ResponseEntity.ok(transactionId);
        } catch (Exception e) {
            String message  = e.getMessage();
            if(message.contains("BadRequest")){
                return ResponseEntity.badRequest().body(message);
            }
            return ResponseEntity.internalServerError().body(message);
        }
    }

    @PutMapping("/v1/transaction/update")
    ResponseEntity<?> updateTransaction(@RequestBody UpdateTransactionRequestDTO updateTransactionRequestDTO) {
        try{
            Transaction t = new Transaction();
            t.setTransactionId(updateTransactionRequestDTO.getTransactionId());
            t.setUserId(updateTransactionRequestDTO.getUserId());
            t.setCategoryId(updateTransactionRequestDTO.getCategoryId());
            t.setAmount(updateTransactionRequestDTO.getAmount());
            t.setDate(updateTransactionRequestDTO.getDate());
            t.setDescription(updateTransactionRequestDTO.getDescription());

            transactionManagementUseCase.updateTransaction(t);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            String message  = e.getMessage();
            if(message.contains("BadRequest")){
                return ResponseEntity.badRequest().body(message);
            }
            return ResponseEntity.internalServerError().body(message);
        }
    }

    @DeleteMapping("/v1/transaction/delete")
    ResponseEntity<?>deleteTransaction(@RequestBody Long transactionId) {
        try {
            transactionManagementUseCase.deleteTransaction(transactionId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            String message  = e.getMessage();
            if(message.contains("BadRequest")){
                return ResponseEntity.badRequest().body(message);
            }
            return ResponseEntity.internalServerError().body(message);
        }
    }

}
