package FriendService.repositories;

import FriendService.model.FriendshipStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FriendshipStatusRepository extends JpaRepository<FriendshipStatus, Long> {

    @Query(value = "SELECT f FROM friendship_status f WHERE f.id = :id AND friendship_status.statusCode = :statusCode",
            nativeQuery = true)
    FriendshipStatus findByIdandStatusCode(@Param("id") Long id, @Param("statusCode") String statusCode);
}

