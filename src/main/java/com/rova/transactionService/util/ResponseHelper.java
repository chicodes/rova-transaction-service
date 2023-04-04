package com.rova.transactionService.util;

import com.rova.transactionService.dto.TransactionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class ResponseHelper<T> {

    public TransactionResponse getResponse (String failedCode, String message, T body, HttpStatus httpStatus) {
        TransactionResponse response = new TransactionResponse();
        response.setRespCode(failedCode);
        response.setRespDescription(message);
        response.setRespBody(body);
        response.setHttpStatus(httpStatus);
        return response;
    }
}


