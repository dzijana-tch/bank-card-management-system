package com.charniuk.bankcardmanagementsystem.mapper;

import com.charniuk.bankcardmanagementsystem.dto.response.TransactionResponse;
import com.charniuk.bankcardmanagementsystem.model.Transaction;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants.ComponentModel;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
    componentModel = ComponentModel.SPRING)
public interface TransactionMapper {

  List<TransactionResponse> toResponse(List<Transaction> transactions);
}
