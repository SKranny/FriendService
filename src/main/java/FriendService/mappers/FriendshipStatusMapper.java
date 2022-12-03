package FriendService.mappers;

import FriendService.dto.FriendshipStatusDTO;
import FriendService.model.FriendshipStatus;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface FriendshipStatusMapper {

    FriendshipStatusMapper INSTANCE = Mappers.getMapper(FriendshipStatusMapper.class);

    FriendshipStatusDTO toDTO(FriendshipStatus friendshipStatus);
}
