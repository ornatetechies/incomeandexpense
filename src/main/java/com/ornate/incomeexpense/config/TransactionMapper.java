package com.ornate.incomeexpense.config;

import com.ornate.incomeexpense.dto.TransactionsDto;
import com.ornate.incomeexpense.model.Transactions;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TransactionMapper {

    TransactionMapper INSTANCE = Mappers.getMapper(TransactionMapper.class);

    TransactionsDto toDto(Transactions transaction);

    Transactions toEntity(TransactionsDto transactionsDto);


}
