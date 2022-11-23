package com.example.demo.dto;

import com.example.demo.constants.FriendshipStatusCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FriendshipStatusDTO {

    private Long id;
    private Date time;
    private String name;
    private FriendshipStatusCode code;

}
