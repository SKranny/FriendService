package com.example.demo.mappers;

import com.example.demo.dto.FriendshipStatusDTO;
import com.example.demo.model.FriendshipStatus;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface FriendshipStatusMapper {

    FriendshipStatusMapper INSTANCE = Mappers.getMapper(FriendshipStatusMapper.class);

    FriendshipStatusDTO toDTO(FriendshipStatus friendshipStatus);
}
