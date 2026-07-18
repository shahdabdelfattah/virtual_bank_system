package com.vbank.transaction.controller;

import com.vbank.transaction.dto.request.TransferExecutionRequest;
import com.vbank.transaction.dto.request.TransferInitiationRequest;
import com.vbank.transaction.dto.response.TransactionHistoryResponse;
import com.vbank.transaction.dto.response.TransferExecutionResponse;
import com.vbank.transaction.dto.response.TransferInitiationResponse;
import com.vbank.transaction.entity.Transaction;
import com.vbank.transaction.kafka.producer.LogProducer;
import com.vbank.transaction.service.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;
    private final LogProducer logProducer;

    @PostMapping("/transactions/transfer/initiation")
    @ResponseStatus(HttpStatus.CREATED)
    public TransferInitiationResponse initiateTransfer(
            @Valid @RequestBody TransferInitiationRequest request
    ){
        logProducer.publishRequest(request);
        TransferInitiationResponse response = transactionService.initiate(request);
        logProducer.publishResponse(response);

        return response;
    }

    @PostMapping("/transactions/transfer/execution")
    @ResponseStatus(HttpStatus.OK)
    public TransferExecutionResponse executeTransfer(
            @Valid @RequestBody TransferExecutionRequest request
    ){
        logProducer.publishRequest(request);
        TransferExecutionResponse response = transactionService.execute(request);
        logProducer.publishResponse(response);

        return response;
    }

    @GetMapping("/accounts/{accountId}/transactions")
    public List<TransactionHistoryResponse> getTransactionHistory(
            @PathVariable UUID accountId
    ){
        Map<String, UUID> request = Map.of("accountId", accountId);
        logProducer.publishRequest(request);
        List<TransactionHistoryResponse> responses = transactionService.getTransactions(accountId);
        logProducer.publishResponse(responses);

        return responses;
    }

}
