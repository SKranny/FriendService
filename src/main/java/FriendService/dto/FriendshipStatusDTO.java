package FriendService.dto;

import FriendService.constants.FriendshipStatusCode;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Статус дружбы")
public class FriendshipStatusDTO {
    @Schema(description = "Идентификатоп")
    private Long id;
    @Schema(description = "Время дружбы")
    private Date time;
    @Schema(description = "Имя")
    private String name;
    @Schema(description = "Код статуса дружбы")
    private FriendshipStatusCode code;

}
