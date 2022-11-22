package com.example.demo.model;

import com.example.demo.constants.FriendshipStatusCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class FriendshipStatus {

    @Id
    private long id;
    private Date time;
    private String name;
    private FriendshipStatusCode code;

}
