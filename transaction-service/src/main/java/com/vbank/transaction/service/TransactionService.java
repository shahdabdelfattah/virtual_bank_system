package com.vbank.transaction.service;

import com.vbank.transaction.client.AccountServiceClient;
import com.vbank.transaction.dto.request.TransferExecutionRequest;
import com.vbank.transaction.dto.request.TransferInitiationRequest;
import com.vbank.transaction.dto.response.TransactionHistoryResponse;
import com.vbank.transaction.dto.response.TransferExecutionResponse;
import com.vbank.transaction.dto.response.TransferInitiationResponse;
import com.vbank.transaction.entity.Transaction;
import com.vbank.transaction.enums.TransactionStatus;
import com.vbank.transaction.exception.BusinessException;
import com.vbank.transaction.exception.ResourceNotFoundException;
import com.vbank.transaction.mapper.TransactionMapper;
import com.vbank.transaction.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;
    private final AccountServiceClient accountServiceClient;

    public TransferInitiationResponse initiate(
            TransferInitiationRequest request
    ){
        Transaction transaction = transactionMapper.toEntity(request);
        transaction = transactionRepository.save(transaction);
        return transactionMapper.toTransferInitiationResponse(transaction);
    }

    public TransferExecutionResponse execute(
            TransferExecutionRequest request
    ) {
        Transaction transaction = transactionRepository
                .findById(request.transactionId())
                .orElseThrow(() -> new ResourceNotFoundException("No transaction with given id = " + request.transactionId() ));

        if(transaction.getStatus() != TransactionStatus.INITIATED){
            throw new BusinessException("The transaction status is " + transaction.getStatus().name() + " so it cant be executed");
        }

        // test what happens if debit done but credit no (salma)
        accountServiceClient.debit(transaction.getFromAccountId(), transaction.getAmount());
        accountServiceClient.credit(transaction.getToAccountId(), transaction.getAmount());

        // only if debit & credit done (salma)
        transaction.setStatus(TransactionStatus.SUCCESS);

        transaction = transactionRepository.save(transaction);
        return transactionMapper.toTransferExecutionResponse(transaction);
    }

    private BigDecimal signedAmountFor(Transaction transaction, UUID accountId) {
        boolean isOutgoing = transaction.getFromAccountId().equals(accountId);
        return isOutgoing ? transaction.getAmount().negate() : transaction.getAmount();
    }

    public List<TransactionHistoryResponse> getTransactions(UUID accountId) {
        List<Transaction> transactions = transactionRepository.findByFromAccountIdOrToAccountId(accountId, accountId);
        return transactions.stream()
                .map(transaction -> transactionMapper.toTransferHistoryResponse(
                        transaction,
                        signedAmountFor(transaction, accountId)
                ))
                .toList();
    }

}
