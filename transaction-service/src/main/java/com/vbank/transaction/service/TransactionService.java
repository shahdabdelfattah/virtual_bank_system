package com.vbank.transaction.service;

import com.vbank.transaction.dto.request.TransferExecutionRequest;
import com.vbank.transaction.dto.request.TransferInitiationRequest;
import com.vbank.transaction.dto.response.TransferExecutionResponse;
import com.vbank.transaction.dto.response.TransferInitiationResponse;
import com.vbank.transaction.entity.Transaction;
import com.vbank.transaction.mapper.TransactionMapper;
import com.vbank.transaction.repository.TransactionRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;

    public TransferInitiationResponse initiate(
            TransferInitiationRequest request
    ){
        Transaction transaction = transactionMapper.toEntity(request);
        transaction = transactionRepository.save(transaction);
        return transactionMapper.toTransferInitiationResponse(transaction);
    }

//    public TransferExecutionResponse execute(
//            TransferExecutionRequest request) {
//
//    }
//
//    public List<Transaction> getTransactions(UUID accountId) {
//        return transactionRepository.findByFromAccountId(accountId);
//    }
}
