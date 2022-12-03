package FriendService.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FriendshipDTO {

    private Long id;
    private Long statusId;
    private Long srcPersonId;
    private Long dstPersonId;

}
