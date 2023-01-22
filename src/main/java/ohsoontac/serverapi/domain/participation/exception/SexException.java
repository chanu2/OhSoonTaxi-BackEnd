package ohsoontac.serverapi.domain.participation.exception;


import ohsoontac.serverapi.global.error.exception.ErrorCode;
import ohsoontac.serverapi.global.error.exception.OhSoonTacException;


public class SexException extends OhSoonTacException {

    public static final OhSoonTacException EXCEPTION = new SexException();
    public SexException() {
        super(ErrorCode.MISMATCH_SEX);
    }


}
