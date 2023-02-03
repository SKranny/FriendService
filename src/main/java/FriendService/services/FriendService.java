package FriendService.services;

import FriendService.dto.FriendDTO;
import FriendService.feign.PersonService;
import dto.userDto.PersonDTO;
import FriendService.exceptions.FriendshipException;
import FriendService.model.Friendship;
import FriendService.model.FriendshipStatus;
import FriendService.repositories.FriendshipRepository;
import FriendService.repositories.FriendshipStatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static FriendService.constants.FriendshipStatusCode.*;


@Service
@RequiredArgsConstructor
public class FriendService {

    private final FriendshipRepository friendshipRepository;
    private final FriendshipStatusRepository friendshipStatusRepository;
    private final PersonService personService;

    public void approveFriendRequest(Long id) {
        Optional <Friendship> friendship = Optional.ofNullable(friendshipRepository
                .findByFriendshipStatusAndDstId(REQUEST.toString(), id));
        Optional<FriendshipStatus> friendshipStatus = friendshipStatusRepository.findById(friendship.get().getStatusId());
        if (friendship.isPresent() && friendshipStatus.isPresent()) {
            friendshipStatus.get().setStatusCode(FRIEND);
            friendshipStatusRepository.save(friendshipStatus.get());
        } else throw new FriendshipException("No Friendship found with this user: " + friendship.get().getStatusId());
    }

    public void subscribe(Long id) {
        Optional <Friendship> friendship = Optional.ofNullable(friendshipRepository.findByDstPersonId(id));
        Optional<FriendshipStatus> friendshipStatus = friendshipStatusRepository
                .findById(friendship.get().getStatusId());
        if (friendship.isPresent() && friendshipStatus.isPresent()) {
            friendshipStatus.get().setStatusCode(SUBSCRIBED);
            friendshipStatusRepository.save(friendshipStatus.get());
        } else throw new FriendshipException("No Friendship found with this user: " + friendship.get().getStatusId());
    }

    public void delete(Long id) {
        Optional <Friendship> friendship = Optional.ofNullable(friendshipRepository.findByDstPersonId(id));
        Optional<FriendshipStatus> friendshipStatus = friendshipStatusRepository
                .findById(friendship.get().getStatusId());
        if (friendship.isPresent()) {
            friendshipStatusRepository.delete(friendshipStatus.get());
            friendshipRepository.delete(friendship.get());
        } else throw new FriendshipException("No Friendship found with this user: " + friendship.get().getStatusId());
    }

    public void blockFriend(Long id) {
        Optional <Friendship> friendship = Optional.ofNullable(friendshipRepository
                .findByDstPersonId(id));
        Optional <FriendshipStatus> friendshipStatus = friendshipStatusRepository
                .findById(friendship.get().getStatusId());
        if (friendship.isPresent()){
            friendshipStatus.get().setStatusCode(BLOCKED);
            friendshipStatusRepository.save(friendshipStatus.get());
        } else throw new FriendshipException("No Friendship found with this user: " + friendship.get().getStatusId());
    }

    @Transactional
    public void sendFriendshipRequest(String email, Long id) {
        PersonDTO srcUser = personService.getPersonDTOByEmail(email);
        PersonDTO dstUser = personService.getPersonById(id);
        FriendshipStatus friendshipStatus = FriendshipStatus.builder()
                .time(new Date())
                .name(dstUser.getFirstName() + " " + dstUser.getLastName())
                .statusCode(REQUEST)
                .build();
        friendshipStatusRepository.save(friendshipStatus);

        Friendship friendship = Friendship.builder()
                .statusId(friendshipStatus.getId())
                .dstPersonId(id)
                .srcPersonId(srcUser.getId())
                .build();
        friendshipRepository.save(friendship);
    }

    public List<FriendDTO> getAllFriends(String email) {
        PersonDTO srcUser = personService.getPersonDTOByEmail(email);
        List<Friendship> friendships = friendshipRepository
                .findByFriendshipStatusAndSrcId(FRIEND.toString(), srcUser.getId());
        return friendships
                .stream()
                .map(friendship -> {
                    PersonDTO friend = personService.getPersonById(friendship.getDstPersonId());
                    return new FriendDTO(
                            friend.getId(),
                            friend.getPhoto(),
                            friend.getFirstName(),
                            friend.getLastName(),
                            friend.getBirthDay(),
                            friend.getIsOnline(),
                            friendshipStatusRepository.findById(friendship.getStatusId()).get().getStatusCode());
                })
                .collect(Collectors.toList());
    }

    public Long getFriendCount(String email){
        PersonDTO srcUser = personService.getPersonDTOByEmail(email);
        return friendshipRepository.findByFriendshipStatusAndSrcId(FRIEND.toString(), srcUser.getId())
                .stream()
                .count();
    }

    public List<Long> getBlockFriendId(String email){
        PersonDTO srcUser = personService.getPersonDTOByEmail(email);
        return friendshipRepository.findByFriendshipStatusAndSrcId(BLOCKED.toString(), srcUser.getId())
                .stream()
                .map(Friendship::getDstPersonId)
                .collect(Collectors.toList());
    }

    public List<Long> getFriendId(String email){
        PersonDTO srcUser = personService.getPersonDTOByEmail(email);
        return friendshipRepository.findByFriendshipStatusAndSrcId(FRIEND.toString(), srcUser.getId())
                .stream()
                .map(Friendship::getDstPersonId)
                .collect(Collectors.toList());
    }

    public FriendDTO getFriendById(String email, Long id){
        PersonDTO srcUser = personService.getPersonDTOByEmail(email);
        PersonDTO dstUser = personService.getPersonById(id);
        Friendship friendship = friendshipRepository
                .findByFriendshipStatusDstIdSrcId(FRIEND.toString(), dstUser.getId(), srcUser.getId());
        return new FriendDTO(
                dstUser.getId(),
                dstUser.getPhoto(),
                dstUser.getFirstName(),
                dstUser.getLastName(),
                dstUser.getBirthDay(),
                dstUser.getIsOnline(),
                FRIEND
        );

    }

}


