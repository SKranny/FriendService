package FriendService.repositories;

import FriendService.constants.FriendshipStatusCode;
import FriendService.model.FriendshipStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FriendshipStatusRepository extends JpaRepository<FriendshipStatus, Long> {

    FriendshipStatus findByStatusCode(FriendshipStatusCode friendshipStatusCode);

}

