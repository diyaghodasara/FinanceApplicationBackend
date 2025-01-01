package com.project.financeapp.domain.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {
    private Long transactionId;
    private Long userId;
    private Long categoryId;
    private Double amount;
    private Date date;
    private String description;
}
