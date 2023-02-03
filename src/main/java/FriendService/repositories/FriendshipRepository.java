package FriendService.repositories;

import FriendService.model.Friendship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FriendshipRepository extends JpaRepository<Friendship, Long> {
    List<Friendship> findBySrcPersonId(Long Long);
    Friendship findByDstPersonId(Long Long);
    @Query(value = "SELECT * FROM friendship JOIN friendship_status ON friendship.status_id = friendship_status.id " +
            "WHERE friendship_status.status_code = :statusCode AND friendship.src_person_id = :id", nativeQuery = true)
    List<Friendship> findByFriendshipStatusAndSrcId(@Param("statusCode") String statusCode, @Param("id") Long id);
    @Query(value = "SELECT * FROM friendship JOIN friendship_status ON friendship.status_id = friendship_status.id " +
            "WHERE friendship_status.status_code = :statusCode AND friendship.dst_person_id = :id1 " +
            "AND friendship.src_person_id = :id2", nativeQuery = true)
    Friendship findByFriendshipStatusDstIdSrcId(@Param("statusCode") String statusCode, @Param("id1") Long id1,
                                              @Param("id2") Long id2);
    @Query(value = "SELECT * FROM friendship JOIN friendship_status ON friendship.status_id = friendship_status.id " +
            "WHERE friendship_status.status_code = :statusCode AND friendship.dst_person_id = :id", nativeQuery = true)
    Friendship findByFriendshipStatusAndDstId(@Param("statusCode") String statusCode, @Param("id") Long id);

}

