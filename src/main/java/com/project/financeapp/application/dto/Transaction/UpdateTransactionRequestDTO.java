package com.project.financeapp.application.dto.Transaction;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateTransactionRequestDTO {
    private Long transactionId;
    private Long userId;
    private Long categoryId;
    private Double amount;
    private Date date;
    private String description;
}
