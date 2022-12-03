package FriendService.controllers;

import FriendService.dto.FriendshipDTO;
import FriendService.mappers.FriendshipMapper;
import FriendService.model.Friendship;
import FriendService.repositories.FriendshipRepository;
import FriendService.security.LoginRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/friendship")
@RequiredArgsConstructor
public class FriendshipController {

    private final FriendshipRepository friendshipRepository;
    private final FriendshipMapper friendshipMapper;

    @PostMapping("/test")
    public String login(@Valid @RequestBody LoginRequest request) {
        return "testtest";
    }

    @GetMapping("/{id}")
    public FriendshipDTO get(@PathVariable long id) {
        Optional<Friendship> optionalTask = friendshipRepository.findById(id);
        if (!optionalTask.isPresent()) {
            return friendshipMapper.toDTO(new Friendship());
        }
        return friendshipMapper.toDTO(optionalTask.get());
    }

    @PostMapping("/friendships")
    public FriendshipDTO post(@RequestBody Friendship friendship) {
        Friendship newFriendship = friendshipRepository.save(friendship);
        return friendshipMapper.toDTO(newFriendship);
    }
}