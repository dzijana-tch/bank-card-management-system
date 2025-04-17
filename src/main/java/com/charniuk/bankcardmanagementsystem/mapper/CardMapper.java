package com.charniuk.bankcardmanagementsystem.mapper;

import com.charniuk.bankcardmanagementsystem.dto.request.CardRequest;
import com.charniuk.bankcardmanagementsystem.dto.response.CardResponse;
import com.charniuk.bankcardmanagementsystem.model.Card;
import com.charniuk.bankcardmanagementsystem.model.User;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants.ComponentModel;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
    componentModel = ComponentModel.SPRING,
    uses = {UserMapper.class})
public interface CardMapper {

  @Mapping(target = "user", source = "user")
  Card toEntity(CardRequest cardRequest, User user);

  CardResponse toResponse(Card card);

  List<CardResponse> toResponse(List<Card> cards);
}
