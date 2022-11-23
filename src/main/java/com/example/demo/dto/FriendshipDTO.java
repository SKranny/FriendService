package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FriendshipDTO {

    private Long id;
    private Long statusId;
    private Long srcPersonId;
    private Long dstPersonId;

}
