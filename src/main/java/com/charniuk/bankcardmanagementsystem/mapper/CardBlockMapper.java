package com.charniuk.bankcardmanagementsystem.mapper;

import com.charniuk.bankcardmanagementsystem.dto.request.CardBlockRequestDto;
import com.charniuk.bankcardmanagementsystem.dto.response.CardBlockResponse;
import com.charniuk.bankcardmanagementsystem.model.Card;
import com.charniuk.bankcardmanagementsystem.model.CardBlockRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants.ComponentModel;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
    componentModel = ComponentModel.SPRING)
public interface CardBlockMapper {

  @Mapping(target = "card", source = "card")
  @Mapping(target = "status", constant = "PENDING")
  CardBlockRequest toEntity(CardBlockRequestDto cardBlockRequestDto, Card card);

  @Mapping(target = "cardId", source = "card.cardId")
  @Mapping(target = "cardNumber", source = "card.cardNumber")
  CardBlockResponse toResponse(CardBlockRequest cardBlockRequest);

}
