package ohsoontac.serverapi.global.successResponse;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

@Getter
@Builder
public class SuccessResponse {

    private final boolean success = true;
    private final int status;
    private final Object data;
    private final LocalDateTime timeStamp;

    public static ResponseEntity<SuccessResponse> successtoResponseEntity(final int status,final Object data){
        return ResponseEntity
                .status(status)
                .body(SuccessResponse.builder()
                        .status(status)
                        .data(data)
                        .timeStamp(LocalDateTime.now())
                        .build()
                );
    }
}