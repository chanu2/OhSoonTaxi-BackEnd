package ohsoontac.serverapi.domain.participation.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ohsoontac.serverapi.global.error.exception.ErrorCode1;
import ohsoontac.serverapi.global.error.exception.ErrorCode2;
import ohsoontac.serverapi.global.error.exception.OhSoonTacException;



public class SexException extends OhSoonTacException {

    public static final OhSoonTacException EXCEPTION = new SexException();


    public SexException() {
        super(ErrorCode2.MISMATCH_SEX);
    }

    //ErrorCode1 errorCode;
}
