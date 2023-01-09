package FriendService.controllers;

import FriendService.dto.FriendshipDTO;
import FriendService.mappers.FriendshipMapper;
import FriendService.model.Friendship;
import FriendService.repositories.FriendshipRepository;
import FriendService.security.LoginRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/friendship")
@RequiredArgsConstructor
@Tag(name="Friend Service", description="Работа с сервисом друзей")
public class FriendshipController {

    private final FriendshipRepository friendshipRepository;
    private final FriendshipMapper friendshipMapper;

    @PostMapping("/test")
    @Operation(summary = "Тестовый запрос")
    public String login(@Valid @RequestBody LoginRequest request) {
        return "testtest";
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получение друга по Id")
    public FriendshipDTO get(@PathVariable long id) {
        Optional<Friendship> optionalTask = friendshipRepository.findById(id);
        if (!optionalTask.isPresent()) {
            return friendshipMapper.toDTO(new Friendship());
        }
        return friendshipMapper.toDTO(optionalTask.get());
    }

    @PostMapping("/friendships")
    @Operation(summary = "Получение списка друзей")
    public FriendshipDTO post(@RequestBody Friendship friendship) {
        Friendship newFriendship = friendshipRepository.save(friendship);
        return friendshipMapper.toDTO(newFriendship);
    }
}