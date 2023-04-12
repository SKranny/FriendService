package FriendService.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Сущность дружбы")
public class FriendshipDTO {
    @Schema(description = "Идентификатор")
    private Long id;
    @Schema(description = "Статус")
    private Long statusId;
    @Schema(description = "Исходный идентификаторр")
    private Long srcPersonId;
    @Schema(description = "Связанный идентификаторр")
    private Long dstPersonId;

}
