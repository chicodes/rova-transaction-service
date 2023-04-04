package com.rova.transactionService.service;

import com.rova.transactionService.dto.TransactionResponse;
import com.rova.transactionService.dto.CreateTransactionRequestDto;
import com.rova.transactionService.model.Transaction;
import com.rova.transactionService.repository.TransactionRepository;
import com.rova.transactionService.util.ResponseHelper;
import com.rova.transactionService.util.Utility;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;

import com.rova.transactionService.http.HttpClient;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;

import static com.rova.transactionService.util.Constants.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final ResponseHelper responseHelper;
    private final HttpClient httpClient;
    @Value("${transactionServiceUrl}")
    private String transactionServiceUrl;

    @Override
    public TransactionResponse createTransaction(CreateTransactionRequestDto request){
        try {
            Transaction transaction = new Transaction();
            transaction.setAmount(new BigDecimal(request.getInitialCredit()));
            transaction.setCustomerId(request.getCustomerId());
            transaction.setDateCreated(Utility.getCurrentDate());
            transactionRepository.save(transaction);
            return responseHelper.getResponse(SUCCESS_CODE, SUCCESS, transaction, HttpStatus.CREATED);
        }
        catch (Exception e) {
            return responseHelper.getResponse(FAILED_CODE, FAILED, e.getStackTrace(), HttpStatus.EXPECTATION_FAILED);
        }
    }

    @Override
    public TransactionResponse getTransaction(String id){
        Optional <Transaction> transaction = transactionRepository.findById(Long.valueOf(id));

        return responseHelper.getResponse(SUCCESS_CODE, SUCCESS, transaction.get(), HttpStatus.OK);
    }

    private Map<String, String> getHeader(){
        return Map.of(
                "Content-Type", "application/json; charset=utf-8",
                "Accept", "application/json"
        );
    }
}
