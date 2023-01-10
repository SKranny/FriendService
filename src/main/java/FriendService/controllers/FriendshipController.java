package FriendService.controllers;

import FriendService.dto.FriendshipDTO;
import FriendService.mappers.FriendshipMapper;
import FriendService.model.Friendship;
import FriendService.repositories.FriendshipRepository;
import FriendService.security.LoginRequest;
import FriendService.services.FriendService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/friends")
@RequiredArgsConstructor
@Tag(name="Friend Service", description="Работа с сервисом друзей")
public class FriendshipController {

    private final FriendService friendService;

    @PutMapping("/{id}/approve")
    @Operation(summary = "Подтверждение дружбы")
    public void approveFriendRequest(@PathVariable Long id){
        friendService.approveFriendRequest(id);
    }

    @PutMapping("/block/{id}")
    @Operation(summary = "Блокировка друга")
    public void blockFriend(@PathVariable Long id){
        friendService.blockFriend(id);
    }

    @PostMapping("/{id}/request")
    @Operation(summary = "Добавление друга")
    public void sendFriendshipRequest(@RequestBody FriendshipDTO friendshipDTO){
        friendService.sendFriendshipRequest(friendshipDTO);
    }


    @PutMapping("/subscribe/{id}")
    @Operation(summary = "Подписка")
    public void subscribe(@PathVariable Long id){
        friendService.subscribe(id);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удаление друга")
    public void delete(@PathVariable Long id){
        friendService.delete(id);
    }


/*    @GetMapping
    @ResponseBody
    public List<FriendshipDTO> getAllFriends(){
        return friendService.getAllFriends();
    }*/

    /*
     @GetMapping("/count")
    public ResponseEntity getFriendCount(){
        return friendService.getFriendCount;
    }

     @GetMapping("/blockFriendId")
    public ResponseEntity getBlockFriendId(){
        return friendService.getBlockFriendId;
    }

     @GetMapping("/{id}")
    public Person getFriendById(@PathVariable Long id){
        return friendService.getFriendById;
    }

    @GetMapping("/recommendations")
    public ResponseEntity getRecommendations(){
        return friendService.getRecommendations;
    }

    @GetMapping("/friendId")
    public ResponseEntity getFriendId(){
        return friendService.getFriendId;
    }
    */
}
