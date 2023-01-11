package FriendService.services;

import FriendService.constants.FriendshipStatusCode;
import FriendService.dto.FriendshipDTO;
import FriendService.exceptions.FriendshipException;
import FriendService.mappers.FriendshipMapper;
import FriendService.model.Friendship;
import FriendService.model.FriendshipStatus;
import FriendService.repositories.FriendshipRepository;
import FriendService.repositories.FriendshipStatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;


@Service
@RequiredArgsConstructor
public class FriendService {

    private final FriendshipRepository friendshipRepository;
    private final FriendshipStatusRepository friendshipStatusRepository;
    private final FriendshipMapper friendshipMapper;

    public void approveFriendRequest(Long id) {
        friendshipRepository.findById(id)
                .flatMap(f -> friendshipStatusRepository.findById(f.getStatusId()))
                .filter(s -> s.getStatusCode() == FriendshipStatusCode.REQUEST)
                .or(() -> { throw new FriendshipException("The user hasn't a friend request", HttpStatus.BAD_REQUEST);})
                .map(s -> {
                    s.setStatusCode(FriendshipStatusCode.FRIEND);
                    return s;
                })
                .ifPresent(friendshipStatusRepository::save);
    }

    public void blockFriend(Long id){
        friendshipRepository.findById(id)
                .flatMap(f -> friendshipStatusRepository.findById(f.getStatusId()))
                .or(() -> {throw new FriendshipException("The user doesn't exist", HttpStatus.BAD_REQUEST);})
                .map(s -> {
                    s.setStatusCode(FriendshipStatusCode.BLOCKED);
                    return s;
                })
                .ifPresent(friendshipStatusRepository::save);
    }

    public void subscribe(Long id){
        friendshipRepository.findById(id)
                .flatMap(f -> friendshipStatusRepository.findById(f.getStatusId()))
                .or(() -> {throw new FriendshipException("The user doesn't exist", HttpStatus.BAD_REQUEST);})
                .map(s -> {
                    s.setStatusCode(FriendshipStatusCode.SUBSCRIBED);
                    return s;
                })
                .ifPresent(friendshipStatusRepository::save);
    }

    public void delete(Long id){
        Friendship friendship = friendshipRepository
                .findById(id)
                .orElseThrow(() -> new FriendshipException("Friendship request with the id doesn't exist", HttpStatus.BAD_REQUEST));
        FriendshipStatus friendshipStatus = friendshipStatusRepository
                .findById(friendship.getStatusId())
                .orElseThrow(() -> new FriendshipException("Friendship status with the id doesn't exist", HttpStatus.BAD_REQUEST));
        friendshipRepository.delete(friendship);
        friendshipStatusRepository.delete(friendshipStatus);
    }

    @Transactional
    public void sendFriendshipRequest(FriendshipDTO friendshipDTO){
        //Заглушка. нужно получать имя пользователя из userService
    }



    /*public List<FriendshipDTO> getAllFriends(){
        заглушка, нужно получать по айди конкретного юзера
        return friendshipRepository.findAll()
                .stream()
                .map(friendshipMapper::toDTO)
                .collect(Collectors.toList());
    }*/


}

