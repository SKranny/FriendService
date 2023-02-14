package FriendService.services;

import FriendService.dto.FriendDTO;
import FriendService.dto.FriendNameDTO;
import FriendService.feign.PersonService;
import dto.userDto.PersonDTO;
import FriendService.exceptions.FriendshipException;
import FriendService.model.Friendship;
import FriendService.model.FriendshipStatus;
import FriendService.repositories.FriendshipRepository;
import FriendService.repositories.FriendshipStatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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

    public void approveFriendRequest(Long id, String email) {
        PersonDTO srcUser = personService.getPersonDTOByEmail(email);
        Optional<Friendship> friendship = getFriendship(REQUEST.toString(), id, srcUser.getId());
        if (friendship.isPresent()) {
            updateFriendship(friendship.get());
        } else throw new FriendshipException("No Friendship found with this user: " + friendship.get().getStatusId());
        Optional<Friendship> friendship2 = getFriendship(REQUEST.toString(),srcUser.getId(), id);
        friendship2.ifPresent(this::updateFriendship);
    }

    private void updateFriendship(Friendship friendship){
        Optional<FriendshipStatus> fs = getFriendshipStatus(friendship.getStatusId());
        fs.get().setStatusCode(FRIEND);
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
        Optional <Friendship> friendship = Optional.ofNullable(friendshipRepository
                .findByFriendshipStatusDstIdSrcId(REQUEST.toString(),id ,srcUser.getId()));
        if (friendship.isEmpty()){
            PersonDTO dstUser = personService.getPersonById(id);
            createFriendshipStatus(dstUser.getFirstName(), dstUser.getLastName(), srcUser.getId(), id);
        } else throw new FriendshipException("You've already sent a request to the user", HttpStatus.BAD_REQUEST);

    }

    private void createFriendshipStatus(String firstName, String lastName, Long srcUserId, Long dstUserId){
        FriendshipStatus friendshipStatus = FriendshipStatus.builder()
                .time(new Date())
                .name(firstName + " " + lastName)
                .statusCode(REQUEST)
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

}


