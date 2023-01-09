package FriendService.mappers;

import FriendService.dto.FriendshipDTO;
import FriendService.model.Friendship;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring",
        uses = {FriendshipStatusMapper.class})
public interface FriendshipMapper {

    FriendshipMapper INSTANCE = Mappers.getMapper(FriendshipMapper.class);

    FriendshipDTO toDTO(Friendship friendship);
}
