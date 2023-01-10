package FriendService.mappers;

import FriendService.dto.FriendshipStatusDTO;
import FriendService.model.FriendshipStatus;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FriendshipStatusMapper {

    FriendshipStatusDTO toDTO(FriendshipStatus friendshipStatus);
    FriendshipStatus toFriendshipStatus(FriendshipStatusDTO friendshipStatusDTO);

}

