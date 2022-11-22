package com.example.demo.mappers;

import com.example.demo.dto.FriendshipDTO;
import com.example.demo.model.Friendship;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface FriendshipMapper {

    FriendshipMapper INSTANCE = Mappers.getMapper(FriendshipMapper.class);

    FriendshipDTO toDTO(Friendship friendship);
}
