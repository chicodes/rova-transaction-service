package com.rova.transactionService.controller;

import com.rova.transactionService.dto.TransactionResponse;
import com.rova.transactionService.dto.CreateTransactionRequestDto;
import com.rova.transactionService.service.TransactionService;
import com.rova.transactionService.util.Constants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;

import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@Slf4j
@Validated
@RequiredArgsConstructor
@RequestMapping(Constants.ACCOUNT_BASE_URL+"/transaction")
public class TransactionController {

    private final TransactionService transactionService;
    @PostMapping("")
    public ResponseEntity<TransactionResponse> addTransaction(@RequestBody CreateTransactionRequestDto request){
        TransactionResponse resp = transactionService.createTransaction(request);
        return new ResponseEntity<>(resp, resp.getHttpStatus());
    }

    @GetMapping("{id}")
    public ResponseEntity<TransactionResponse> getTransaction(@PathVariable String id){
        TransactionResponse resp = transactionService.getTransaction(id);
        return new ResponseEntity<>(resp, resp.getHttpStatus());
    }
}
