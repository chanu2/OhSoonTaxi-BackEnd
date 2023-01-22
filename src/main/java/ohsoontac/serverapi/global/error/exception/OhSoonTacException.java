package ohsoontac.serverapi.global.error.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class OhSoonTacException extends RuntimeException{
    ErrorCode errorCode;

}
