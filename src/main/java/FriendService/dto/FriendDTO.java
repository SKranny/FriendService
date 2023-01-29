package FriendService.dto;

import FriendService.constants.FriendshipStatusCode;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Сущность друг")
public class FriendDTO {
    private Long id;
    private String photo;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private boolean isOnline;
    private FriendshipStatusCode statusCode;

}
