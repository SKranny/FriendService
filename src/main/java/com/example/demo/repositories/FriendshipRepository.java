package com.example.demo.repositories;

import com.example.demo.model.Friendship;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FriendshipRepository extends CrudRepository<Friendship, Long> {
}
