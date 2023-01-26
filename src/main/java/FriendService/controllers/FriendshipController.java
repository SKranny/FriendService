package FriendService.controllers;

import FriendService.dto.FriendDTO;
import FriendService.services.FriendService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;


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
    public void sendFriendshipRequest(Principal principal, @PathVariable Long id){
        friendService.sendFriendshipRequest(principal, id);
    }

    @PostMapping("/subscribe/{id}")
    @Operation(summary = "Подписка")
    public void subscribe(@PathVariable Long id){
        friendService.subscribe(id);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удаление друга")
    public void delete(@PathVariable Long id){
        friendService.delete(id);
    }

    @GetMapping
    @ResponseBody
    @Operation(summary = "Получение всех друзей")
    public List<FriendDTO> getAllFriends(Principal principal){
        return friendService.getAllFriends(principal);
    }

    @GetMapping("{accountId}")
    @Operation(summary = "Получение друга по id")
    public FriendDTO getFriendById(Principal principal, @PathVariable Long accountId){
        return friendService.getFriendById(principal, accountId);
    }

    @GetMapping("/friendId")
    @Operation(summary = "Получение id друзей")
    public List<Long> getFriendId(Principal principal){
        return friendService.getFriendId(principal);
    }

    @GetMapping("/count")
    @Operation(summary = "Получение количества друзей")
    public Long getFriendCount(Principal principal){
        return friendService.getFriendCount(principal);
    }

    @GetMapping("/blockFriendId")
    @Operation(summary = "Получение id заблокированных друзей")
    public List<Long> getBlockFriendId(Principal principal){
        return friendService.getBlockFriendId(principal);
    }






/*
    @GetMapping("/recommendations")
    public ResponseEntity getRecommendations(){
        return friendService.getRecommendations;
    }
    */
}
