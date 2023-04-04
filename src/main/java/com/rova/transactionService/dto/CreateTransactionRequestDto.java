package com.rova.transactionService.dto;


import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreateTransactionRequestDto {
    String customerId;
    String initialCredit;
}
