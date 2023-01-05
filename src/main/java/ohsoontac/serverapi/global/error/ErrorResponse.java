package ohsoontac.serverapi.global.error;


import lombok.Builder;
import lombok.Data;
import ohsoontac.serverapi.global.error.exception.ErrorCode1;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

@Data
@Builder
public class ErrorResponse {

    private final boolean success = false;
    private final int status;
    private final String code;
    private final String reason;
    private final LocalDateTime timeStamp;

    public static ResponseEntity<ErrorResponse> toResponseEntity(ErrorCode1 e){
        return ResponseEntity
                .status(e.getHttpStatus())
                .body(ErrorResponse.builder()
                        .status(e.getHttpStatus().value())
                        .code(e.name())
                        .reason(e.getReason())
                        .timeStamp(LocalDateTime.now())
                        .build()
                );
    }


}