package com.example.demo.controllers;

import com.example.demo.dto.FriendshipDTO;
import com.example.demo.mappers.FriendshipMapper;
import com.example.demo.model.Friendship;
import com.example.demo.repositories.FriendshipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/")
public class FriendshipController {

    @Autowired
    private FriendshipRepository friendshipRepository;

    private FriendshipMapper friendshipMapper;

    @GetMapping("/friendship/{id}")
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