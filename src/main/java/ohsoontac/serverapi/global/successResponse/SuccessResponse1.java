package ohsoontac.serverapi.global.successResponse;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

@Getter
@Builder
public class SuccessResponse1 {

    private final boolean success = true;
    private final int status;
    private final Object data;
    private final String successResponseMessage;
    private final LocalDateTime timeStamp;

    public static ResponseEntity<SuccessResponse1> successtoResponseEntity(final int status, final Object data, final String successResponseMessage){
        return ResponseEntity
                .status(status)
                .body(SuccessResponse1.builder()
                        .status(status)
                        .data(data)
                        .successResponseMessage(successResponseMessage)
                        .timeStamp(LocalDateTime.now())
                        .build()
                );
    }
}