package FriendService.mappers;

import FriendService.dto.FriendshipDTO;
import FriendService.model.Friendship;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FriendshipMapper {


    FriendshipDTO toDTO(Friendship friendship);
    Friendship toFriendship (FriendshipDTO friendshipDTO);
}

