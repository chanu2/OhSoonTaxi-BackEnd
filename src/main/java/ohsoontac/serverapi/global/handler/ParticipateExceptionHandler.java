package ohsoontac.serverapi.global.handler;


import ohsoontac.serverapi.domain.participation.exception.DuplicatedParticipationException;
import ohsoontac.serverapi.global.error.ErrorResponse;
import ohsoontac.serverapi.global.error.exception.ErrorCode1;
import ohsoontac.serverapi.global.error.exception.ErrorResponse1;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ParticipateExceptionHandler {
//    @ExceptionHandler(DuplicatedParticipationException.class)
//    protected ResponseEntity<ErrorResponse> handleParticipateException(DuplicatedParticipationException e) {
//        return ErrorResponse.toResponseEntity(e.getErrorCode());
//    }


    @ExceptionHandler(DuplicatedParticipationException.class)
    public ResponseEntity<ErrorResponse> duplicationHandler(
            DuplicatedParticipationException e, HttpServletRequest request) {
        ErrorCode1 code = e.getErrorCode1();

        ErrorResponse1 errorResponse =
                new ErrorResponse1(
                        code.getHttpStatus(),
                        code.getReason(),
                        request.getRequestURL().toString());
        return ResponseEntity.status(HttpStatus.valueOf(code.getStatus())).body(errorResponse);
    }
}
