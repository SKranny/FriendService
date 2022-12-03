package FriendService.dto;

import FriendService.constants.FriendshipStatusCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FriendshipStatusDTO {

    private Long id;
    private Date time;
    private String name;
    private FriendshipStatusCode code;

}
