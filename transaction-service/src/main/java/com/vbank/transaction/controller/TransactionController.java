package com.vbank.transaction.controller;

import com.vbank.transaction.dto.request.TransferExecutionRequest;
import com.vbank.transaction.dto.request.TransferInitiationRequest;
import com.vbank.transaction.dto.response.TransferExecutionResponse;
import com.vbank.transaction.dto.response.TransferInitiationResponse;
import com.vbank.transaction.entity.Transaction;
import com.vbank.transaction.service.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping("/transfer/initiation")
    @ResponseStatus(HttpStatus.CREATED)
    public TransferInitiationResponse initiateTransfer(
            @Valid @RequestBody TransferInitiationRequest request
    ){
        return transactionService.initiate(request);
    }

    @PostMapping("/transactions/transfer/execution")
    public TransferExecutionResponse executeTransfer(
            @Valid @RequestBody TransferExecutionRequest request
    ){
        return transactionService.execute(request);
    }
//
//    @GetMapping("/accounts/{accountId}/transactions")
//    public List<Transaction> getAllTransactions(
//            @PathVariable UUID accountId
//    ){
//        return transactionService.getTransactions(accountId);
//    }

}
