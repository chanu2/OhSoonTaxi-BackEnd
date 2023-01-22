package ohsoontac.serverapi.global.handler;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ohsoontac.serverapi.global.error.ErrorResponse;
import ohsoontac.serverapi.global.error.exception.ErrorCode;
import ohsoontac.serverapi.global.error.exception.OhSoonTacException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
@Slf4j
@RequiredArgsConstructor
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {


    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return super.handleMethodArgumentNotValid(ex, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return super.handleBindException(ex, headers, status, request);
    }

    @ExceptionHandler(OhSoonTacException.class)
    public ResponseEntity<ErrorResponse> ohsoontacExceptionHandler(
            OhSoonTacException e, HttpServletRequest request) {

        log.error("exception={}",e);
        ErrorCode code = e.getErrorCode();
        ErrorResponse errorResponse =
                new ErrorResponse(
                        code.getStatus(),
                        code.getReason(),
                        request.getRequestURL().toString());
        return ResponseEntity.status(HttpStatus.valueOf(code.getStatus())).body(errorResponse);
    }
}
