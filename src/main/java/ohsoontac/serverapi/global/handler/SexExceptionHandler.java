package ohsoontac.serverapi.global.handler;


import ohsoontac.serverapi.domain.participation.exception.SexException;
import ohsoontac.serverapi.global.error.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

//@ControllerAdvice
//public class SexExceptionHandler {
//    @ExceptionHandler(SexException.class)
//    protected ResponseEntity<ErrorResponse> handleParticipateException(SexException e) {
//        return ErrorResponse.toResponseEntity(e.getErrorCode());
//    }
//}
