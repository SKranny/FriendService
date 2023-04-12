package FriendService.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class FriendshipException extends RuntimeException{

    private final HttpStatus httpStatus;

    public FriendshipException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public FriendshipException(String message) {
        this(message, HttpStatus.BAD_REQUEST);
    }
}

