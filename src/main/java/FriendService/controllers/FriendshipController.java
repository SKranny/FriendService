package FriendService.controllers;

import FriendService.dto.FriendDTO;
import FriendService.dto.FriendNameDTO;
import FriendService.services.FriendService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import security.TokenAuthentication;

import java.util.List;


@RestController
@RequestMapping("/api/v1/friends")
@RequiredArgsConstructor
@Tag(name="Friend Service", description="Работа с сервисом друзей")
public class FriendshipController {

    private final FriendService friendService;

    @PutMapping("/{id}/approve")
    @Operation(summary = "Подтверждение дружбы")
    public void approveFriendRequest(@PathVariable Long id, TokenAuthentication authentication){
        friendService.approveFriendRequest(id, authentication.getTokenData().getEmail());
    }

    @PutMapping("/block/{id}")
    @Operation(summary = "Блокировка друга")
    public void blockFriend(TokenAuthentication authentication, @PathVariable Long id){
        friendService.blockFriend(authentication.getTokenData().getEmail(), id);
    }

    @PostMapping("/{id}/request")
    @Operation(summary = "Добавление друга")
    public void sendFriendshipRequest(TokenAuthentication authentication, @PathVariable Long id){
        friendService.sendFriendshipRequest(authentication.getTokenData().getEmail(), id);
    }

    @PostMapping("/subscribe/{id}")
    @Operation(summary = "Подписка")
    public void subscribe(@PathVariable Long id){
        friendService.subscribe(id);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удаление друга")
    public void delete(@PathVariable Long id, TokenAuthentication authentication){
        friendService.delete(id, authentication.getTokenData().getEmail());
    }

    @GetMapping
    @ResponseBody
    @Operation(summary = "Получение всех друзей")
    public List<FriendDTO> getAllFriends(TokenAuthentication authentication){
        return friendService.getAllFriends(authentication.getTokenData().getEmail());
    }

    @GetMapping("{accountId}")
    @Operation(summary = "Получение друга по id")
    public FriendDTO getFriendById(TokenAuthentication authentication, @PathVariable Long accountId){
        return friendService.getFriendById(authentication.getTokenData().getEmail(), accountId);
    }

    @GetMapping("/ids")
    @Operation(summary = "Получение id друзей")
    public List<Long> getFriendId(TokenAuthentication authentication){
        return friendService.getFriendId(authentication.getTokenData().getEmail());
    }

    @GetMapping("/count")
    @Operation(summary = "Получение количества запросов в друзья")
    public Long getFriendCount(TokenAuthentication authentication){
        return friendService.getFriendCount(authentication.getTokenData().getEmail());
    }

    @GetMapping("/ids/block")
    @Operation(summary = "Получение id заблокированных друзей")
    public List<Long> getBlockFriendId(TokenAuthentication authentication){
        return friendService.getBlockFriendId(authentication.getTokenData().getEmail());
    }

    @GetMapping("/requests")
    @Operation(summary = "Получение входящих заявок в друзья")
    public List<FriendDTO> getRequests(TokenAuthentication authentication){
        return friendService.getRequests(authentication.getTokenData().getEmail());
    }

    @GetMapping("/myrequests")
    @Operation(summary = "Получение исходящих заявок в друзья")
    public List<FriendDTO> getMyRequests(TokenAuthentication authentication){
        return friendService.getMyRequests(authentication.getTokenData().getEmail());
    }

    @DeleteMapping("/{id}/cancelmyrequest")
    @Operation(summary = "Отмена исходящей заявки")
    public void cancelMyFriendRequest(@PathVariable Long id, TokenAuthentication authentication){
        friendService.cancelMyFriendRequest(id, authentication.getTokenData().getEmail());
    }

    @DeleteMapping("/{id}/cancelrequest")
    @Operation(summary = "Отмена входящей заявки")
    public void cancelFriendRequest(@PathVariable Long id, TokenAuthentication authentication){
        friendService.cancelFriendRequest(id, authentication.getTokenData().getEmail());
    }

}
