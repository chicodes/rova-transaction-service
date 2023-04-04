package com.rova.transactionService.listener;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.DeleteMessageRequest;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;
import com.google.gson.Gson;
import com.rova.transactionService.dto.CreateTransactionRequestDto;
import com.rova.transactionService.dto.SqsTransactionDto;
import com.rova.transactionService.service.TransactionService;
import com.rova.transactionService.service.TransactionServiceImpl;
import io.awspring.cloud.messaging.listener.annotation.SqsListener;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;


@Slf4j
@Component
@RequiredArgsConstructor
public class SqsListeners {

    //private final TranFailCheckService tranFailCheckService;
    private final TransactionService transactionService;

    private final AmazonSQS amazonSQS;

    private final Gson gson;

    private final Environment env;


    @Scheduled(fixedDelay = 10000)
    //@SqsListener("rova_transactions")
    public void getMessage() {
        log.info("Receiving messages from Transaction Queue.\n");
        final ReceiveMessageRequest receiveMessageRequest =
                new ReceiveMessageRequest(env.getProperty("transaction.queue.url"))
                        .withMaxNumberOfMessages(10)
                        .withWaitTimeSeconds(10)
                        .withVisibilityTimeout(20);
        final List<Message> messages = amazonSQS.receiveMessage(receiveMessageRequest)
                .getMessages();
        for (Message message : messages) {
            log.debug("Message");
            log.debug("  MessageId:     "
                    + message.getMessageId());
            log.debug("  ReceiptHandle: "
                    + message.getReceiptHandle());
            log.debug("  MD5OfBody:     "
                    + message.getMD5OfBody());
            log.info("  Transaction Body:          "
                    + message.getBody());
            if (!"".equals(message.getBody())) {
                CreateTransactionRequestDto transaction = gson.fromJson(message.getBody(), CreateTransactionRequestDto.class);
                log.info("request message :: {}", transaction);
                transactionService.createTransaction(transaction);
               amazonSQS.deleteMessage(new DeleteMessageRequest(env.getProperty("transaction.queue.url"), message.getReceiptHandle()));
               log.info("message deleted from queue");
            }
        }
    }
}
