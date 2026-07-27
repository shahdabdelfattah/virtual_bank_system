package com.vbank.transaction.mapper;

import com.vbank.transaction.dto.request.TransferInitiationRequest;
import com.vbank.transaction.dto.response.TransactionHistoryResponse;
import com.vbank.transaction.dto.response.TransferExecutionResponse;
import com.vbank.transaction.dto.response.TransferInitiationResponse;
import com.vbank.transaction.entity.Transaction;
import com.vbank.transaction.enums.TransactionStatus;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.math.BigDecimal;

@Mapper(componentModel = "spring", imports = TransactionStatus.class)
public interface TransactionMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "timestamp", ignore = true)
    @Mapping(target = "status", expression = "java(TransactionStatus.INITIATED)")
    Transaction toEntity(TransferInitiationRequest request);

    @Mapping(target = "transactionId", source = "id")
    @Mapping(target = "status", expression = "java(transaction.getStatus().name())")
    TransferInitiationResponse toTransferInitiationResponse(Transaction transaction);


    @Mapping(target = "transactionId", source = "id")
    @Mapping(target = "status", expression = "java(transaction.getStatus().name())")
    TransferExecutionResponse toTransferExecutionResponse(
            Transaction transaction
    );


    @Mapping(target = "transactionId", source = "transaction.id")
    @Mapping(target = "amount", source = "signedAmount")
    @Mapping(target = "deliveryStatus", expression = "java(transaction.getStatus().name())")
    TransactionHistoryResponse toTransferHistoryResponse(Transaction transaction, BigDecimal signedAmount);
}
