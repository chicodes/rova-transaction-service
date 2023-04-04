package com.rova.transactionService.service;

import com.rova.transactionService.dto.TransactionResponse;
import com.rova.transactionService.dto.CreateTransactionRequestDto;

public interface TransactionService {

    TransactionResponse createTransaction(CreateTransactionRequestDto request);

    TransactionResponse getTransaction(String id);
}
