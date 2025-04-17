package com.charniuk.bankcardmanagementsystem.mapper;

import com.charniuk.bankcardmanagementsystem.dto.request.UserRequest;
import com.charniuk.bankcardmanagementsystem.dto.response.UserResponse;
import com.charniuk.bankcardmanagementsystem.model.User;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants.ComponentModel;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
    componentModel = ComponentModel.SPRING)
public interface UserMapper {

  UserResponse toResponse(User user);

  List<UserResponse> toResponse(List<User> users);

  User toEntity(UserRequest userRequest);

  void updateUserFromRequest(@MappingTarget User user, UserRequest request);
}
