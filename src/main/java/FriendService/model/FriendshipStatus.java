package FriendService.model;

import FriendService.constants.FriendshipStatusCode;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "friendship_status")
public class FriendshipStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "friendship_id_gen")
    @SequenceGenerator(name = "friendship_id_gen", sequenceName = "friendship_id_seq", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    private Date time;

    private String name;

    @Enumerated(EnumType.STRING)
    private FriendshipStatusCode statusCode;

}

