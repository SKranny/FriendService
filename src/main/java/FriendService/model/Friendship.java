package FriendService.model;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "friendship")
public class Friendship {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "friendship_id_gen")
    @SequenceGenerator(name = "friendship_id_gen", sequenceName = "friendship_id_seq", allocationSize = 1)
    @Column(name = "id")
    private Long id;
    private Long statusId;
    private Long srcPersonId;
    private Long dstPersonId;

}

