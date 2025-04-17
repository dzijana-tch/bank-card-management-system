package com.charniuk.bankcardmanagementsystem.mapper;

import com.charniuk.bankcardmanagementsystem.dto.request.CardLimitRequest;
import com.charniuk.bankcardmanagementsystem.dto.response.CardLimitResponse;
import com.charniuk.bankcardmanagementsystem.model.Card;
import com.charniuk.bankcardmanagementsystem.model.CardLimit;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants.ComponentModel;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
    componentModel = ComponentModel.SPRING)
public interface CardLimitMapper {

  @Mapping(target = "card", source = "card")
  CardLimit toEntity(CardLimitRequest cardLimitRequest, Card card);

  @Mapping(target = "cardId", source = "card.cardId")
  @Mapping(target = "cardNumber", source = "card.cardNumber")
  CardLimitResponse toResponse(CardLimit cardLimit);
}
