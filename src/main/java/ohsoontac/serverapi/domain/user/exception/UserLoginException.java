package ohsoontac.serverapi.domain.user.exception;

import ohsoontac.serverapi.global.error.exception.ErrorCode;
import ohsoontac.serverapi.global.error.exception.OhSoonTacException;

public class UserLoginException extends OhSoonTacException {
    public static final OhSoonTacException EXCEPTION = new UserLoginException();
    public UserLoginException() {
        super(ErrorCode.LOGIN_NOT_FOUND);
    }

}
