package ohsoontac.serverapi.global.handler;


import ohsoontac.serverapi.domain.participation.exception.ParticipateException;
import ohsoontac.serverapi.global.error.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ParticipateExceptionHandler {
    @ExceptionHandler(ParticipateException.class)
    protected ResponseEntity<ErrorResponse> handleParticipateException(ParticipateException e) {
        return ErrorResponse.toResponseEntity(e.getErrorCode());
    }
}
