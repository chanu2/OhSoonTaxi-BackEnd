package ohsoontac.serverapi.global.error.exception;



import lombok.Getter;


import java.time.LocalDateTime;

@Getter
public class ErrorResponse1 {

    private final boolean success = false;
    private final int status;
    //private final String code;
    private final String reason;
    private final LocalDateTime timeStamp;

    private final String path;



    public ErrorResponse1(int status, String reason,String path) {
        this.status = status;
        //this.code = code;
        this.reason = reason;
        this.timeStamp = LocalDateTime.now();
        this.path = path;
    }
}