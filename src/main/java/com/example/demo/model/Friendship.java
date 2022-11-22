package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Friendship {
    @Id
    private long id;
    private long statusId;
    private long srcPersonId;
    private long dstPersonId;

}
