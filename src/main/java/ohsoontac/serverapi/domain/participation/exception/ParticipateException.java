package ohsoontac.serverapi.domain.participation.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ohsoontac.serverapi.global.error.exception.ErrorCode1;

@AllArgsConstructor
@Getter
public class ParticipateException extends RuntimeException{

    ErrorCode1 errorCode;
}
