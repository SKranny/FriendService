package FriendService.services;

import FriendService.constants.FriendshipStatusCode;
import FriendService.dto.FriendDTO;
import FriendService.dto.FriendNameDTO;
import FriendService.feign.PersonService;
import constants.NotificationType;
import dto.friendDto.FriendsNotificationRequest;
import dto.notification.ContentDTO;
import dto.userDto.PersonDTO;
import FriendService.exceptions.FriendshipException;
import FriendService.model.Friendship;
import FriendService.model.FriendshipStatus;
import FriendService.repositories.FriendshipRepository;
import FriendService.repositories.FriendshipStatusRepository;
import kafka.annotation.SubmitToKafka;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
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

    public void approveFriendRequest(Long id, String email) {
        PersonDTO srcUser = personService.getPersonDTOByEmail(email);
        Optional<Friendship> friendshipRequest = getFriendship(REQUEST.toString(), srcUser.getId(), id);
        Optional<Friendship> friendshipFriend = getFriendship(FRIEND.toString(), srcUser.getId(), id);
        if (friendshipRequest.isPresent() && friendshipFriend.isEmpty()) {
            updateFriendship(friendshipRequest.get(), FRIEND);
        } else throw new FriendshipException("No Friendship request found with this user: " +
                friendshipRequest.get().getStatusId(), HttpStatus.BAD_REQUEST);
        Optional<Friendship> friendship2 = getFriendship(REQUEST.toString(),srcUser.getId(), id);
        if (friendship2.isPresent()) {
            updateFriendship(friendship2.get(), FRIEND);
        } else {
            createFriendshipStatus(srcUser.getFirstName(), srcUser.getLastName(), srcUser.getId(), id, FRIEND);
        }
    }

    public void delete(Long id, String email) {
        PersonDTO srsUser = personService.getPersonDTOByEmail(email);
        Optional <Friendship> friendship = Optional.ofNullable(friendshipRepository
                .findByFriendshipStatusDstIdSrcId(FRIEND.toString(), id, srsUser.getId()));
        Optional <Friendship> friendship2 = Optional.ofNullable(friendshipRepository
                .findByFriendshipStatusDstIdSrcId(FRIEND.toString(), srsUser.getId(), id));
        if (friendship.isPresent()) {
            deleteFriendship(friendship.get());
            deleteFriendship(friendship2.get());
        } else throw new FriendshipException("No Friendship found with this user: " + friendship.get().getStatusId());
    }

    private void deleteFriendship (Friendship friendship){
        Optional<FriendshipStatus> friendshipStatus = friendshipStatusRepository.findById(friendship.getStatusId());
        friendshipStatusRepository.delete(friendshipStatus.get());
        friendshipRepository.delete(friendship);
    }

    public void blockFriend(String email, Long id) {
        PersonDTO srcUser = personService.getPersonDTOByEmail(email);
        Optional<Friendship> friendship = getFriendship(FRIEND.toString(), id, srcUser.getId());
        if (friendship.isPresent()){
            updateFriendship(friendship.get(), BLOCKED);
            Optional<Friendship> friendship2 = getFriendship(FRIEND.toString(), srcUser.getId(), id);
            deleteFriendship(friendship2.get());

        } else throw new FriendshipException("No Friendship found with this user: " + friendship.get().getStatusId());
    }

    private void updateFriendship(Friendship friendship, FriendshipStatusCode fsCode){
        Optional<FriendshipStatus> fs = getFriendshipStatus(friendship.getStatusId());
        fs.get().setStatusCode(fsCode);
        friendshipStatusRepository.save(fs.get());
    }

    private Optional<Friendship> getFriendship(String status, Long dstUserid, Long srcUserid ){
        return Optional.ofNullable(friendshipRepository
                .findByFriendshipStatusDstIdSrcId(status, dstUserid, srcUserid));
    }

    private Optional<FriendshipStatus> getFriendshipStatus(Long statusId){
        return friendshipStatusRepository.findById(statusId);
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

    @Transactional
    public void sendFriendshipRequest(String email, Long id) {
        PersonDTO srcUser = personService.getPersonDTOByEmail(email);
        if (srcUser.getId() != id){
            if (!isFriendOrRequested(srcUser.getId(), id)){
                PersonDTO dstUser = personService.getPersonById(id);
                createFriendshipStatus(dstUser.getFirstName(), dstUser.getLastName(), srcUser.getId(), id, REQUEST);
            } else throw new FriendshipException("You've already sent a request to the user", HttpStatus.BAD_REQUEST);
        } else throw new FriendshipException("You can't send a friend request to yourself ", HttpStatus.BAD_REQUEST);
    }

    private Boolean isFriendOrRequested(Long srcUserId, Long dstUserId) {
        Optional <Friendship> friendRequest1 = Optional.ofNullable(friendshipRepository
                .findByFriendshipStatusDstIdSrcId(REQUEST.toString(), srcUserId, dstUserId));
        Optional <Friendship> friendRequest2 = Optional.ofNullable(friendshipRepository
                .findByFriendshipStatusDstIdSrcId(REQUEST.toString(), dstUserId, srcUserId));
        Optional <Friendship> friendship1 = Optional.ofNullable(friendshipRepository
                .findByFriendshipStatusDstIdSrcId(FRIEND.toString(), srcUserId, dstUserId));
        Optional <Friendship> friendship2 = Optional.ofNullable(friendshipRepository
                .findByFriendshipStatusDstIdSrcId(FRIEND.toString(), dstUserId, srcUserId));

        return friendRequest1.isPresent() || friendRequest2.isPresent()
                || friendship1.isPresent() || friendship2.isPresent();
    }

    private void createFriendshipStatus(String firstName, String lastName, Long srcUserId, Long dstUserId, FriendshipStatusCode code){
        FriendshipStatus friendshipStatus = FriendshipStatus.builder()
                .time(new Date())
                .name(firstName + " " + lastName)
                .statusCode(code)
                .build();
        friendshipStatusRepository.save(friendshipStatus);
        createFriendship(friendshipStatus.getId(), srcUserId, dstUserId);

    }

    private void createFriendship(Long id, Long srcUserId, Long dstUserId){
        Friendship friendship = Friendship.builder()
                .statusId(id)
                .dstPersonId(dstUserId)
                .srcPersonId(srcUserId)
                .build();
        friendshipRepository.save(friendship);
        createNotification(friendship);
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
                            friend.getAddress(),
                            friendshipStatusRepository.findById(friendship.getStatusId()).get().getStatusCode());
                })
                .collect(Collectors.toList());
    }

    public Long getFriendCount(String email){
        PersonDTO srcUser = personService.getPersonDTOByEmail(email);
        return friendshipRepository.findByStatusAndDstId(REQUEST.toString(), srcUser.getId())
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
                dstUser.getAddress(),
                friendshipStatusRepository.findById(friendship.getStatusId()).get().getStatusCode()
        );

    }

    public List<FriendDTO> getRequests(String email){
        PersonDTO srcUser = personService.getPersonDTOByEmail(email);
        return friendshipRepository.findByStatusAndDstId(REQUEST.toString(), srcUser.getId())
                .stream()
                .map(friendship -> {
                    PersonDTO friend = personService.getPersonById(friendship.getSrcPersonId());
                    return new FriendDTO(friend.getId(), friend.getPhoto(), friend.getFirstName(), friend.getLastName(),
                            friend.getBirthDay(), friend.getIsOnline(), friend.getAddress(), REQUEST);
                })
                .collect(Collectors.toList());
    }

    public List<FriendDTO> getMyRequests(String email){
        PersonDTO srcUser = personService.getPersonDTOByEmail(email);
        return friendshipRepository.findByStatusAndSrcId(REQUEST.toString(), srcUser.getId())
                .stream()
                .map(friendship -> {
                    PersonDTO friend = personService.getPersonById(friendship.getDstPersonId());
                    return new FriendDTO(friend.getId(), friend.getPhoto(), friend.getFirstName(), friend.getLastName(),
                            friend.getBirthDay(), friend.getIsOnline(), friend.getAddress(), REQUEST);
                })
                .collect(Collectors.toList());
    }

    public void cancelMyFriendRequest(Long id, String email){
        PersonDTO srcUser = personService.getPersonDTOByEmail(email);
        Friendship friendship = friendshipRepository
                .findByFriendshipStatusDstIdSrcId(REQUEST.toString(), id, srcUser.getId());
        FriendshipStatus friendshipStatus = friendshipStatusRepository.findById(friendship.getStatusId()).get();
        friendshipRepository.delete(friendship);
        friendshipStatusRepository.delete(friendshipStatus);
    }

    public void cancelFriendRequest(Long id, String email){
        PersonDTO srcUser = personService.getPersonDTOByEmail(email);
        Friendship friendship = friendshipRepository
                .findByFriendshipStatusDstIdSrcId(REQUEST.toString(), srcUser.getId(), id);
        FriendshipStatus friendshipStatus = friendshipStatusRepository.findById(friendship.getStatusId()).get();
        friendshipRepository.delete(friendship);
        friendshipStatusRepository.delete(friendshipStatus);
    }

    @SubmitToKafka(topic = "Friends")
    private FriendsNotificationRequest createNotification(Friendship friendship){
        return FriendsNotificationRequest.builder()
                .authorId(friendship.getSrcPersonId())
                .recipientId(friendship.getDstPersonId())
                .content(ContentDTO.builder()
                        .text("")
                        .attaches(new ArrayList<>())
                        .build())
                .type(NotificationType.FRIEND_REQUEST)
                .build();
    }

}


