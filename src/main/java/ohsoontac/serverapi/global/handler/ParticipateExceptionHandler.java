package ohsoontac.serverapi.global.handler;


import ohsoontac.serverapi.domain.participation.exception.DuplicatedParticipationException;
import ohsoontac.serverapi.global.error.ErrorResponse;
import ohsoontac.serverapi.global.error.exception.ErrorCode1;
import ohsoontac.serverapi.global.error.exception.ErrorCode2;
import ohsoontac.serverapi.global.error.exception.ErrorResponse1;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<ErrorResponse1> duplicationHandler(
            DuplicatedParticipationException e, HttpServletRequest request) {
        ErrorCode2 code = e.getErrorCode2();

        ErrorResponse1 errorResponse =
                new ErrorResponse1(
                        code.getStatus(),
                        code.getReason(),
                        request.getRequestURL().toString());
        return ResponseEntity.status(HttpStatus.valueOf(code.getStatus())).body(errorResponse);
    }
}
