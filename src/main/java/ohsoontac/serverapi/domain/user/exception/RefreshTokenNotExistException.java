package ohsoontac.serverapi.domain.user.exception;

import ohsoontac.serverapi.global.error.exception.ErrorCode;
import ohsoontac.serverapi.global.error.exception.OhSoonTacException;

public class RefreshTokenNotExistException extends OhSoonTacException {

    public static final OhSoonTacException EXCEPTION = new RefreshTokenNotExistException();
    public RefreshTokenNotExistException() {
        super(ErrorCode.REFRESH_TOKEN_NOT_EXIST);
    }
}
