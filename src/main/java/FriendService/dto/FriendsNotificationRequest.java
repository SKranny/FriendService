package FriendService.dto;

import dto.notification.ContentDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FriendsNotificationRequest {

    private Long srcPersonId;

    private Long dstPersonId;

    private ContentDTO content;

}
