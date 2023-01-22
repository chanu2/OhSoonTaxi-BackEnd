package ohsoontac.serverapi.domain.user.exception;

import ohsoontac.serverapi.global.error.exception.ErrorCode;
import ohsoontac.serverapi.global.error.exception.OhSoonTacException;

public class DuplicateLoginException extends OhSoonTacException {

    public static final OhSoonTacException EXCEPTION = new DuplicateLoginException();
    public DuplicateLoginException() {
        super(ErrorCode.DUPLICATE_LOGIN);
    }
}
