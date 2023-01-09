package ohsoontac.serverapi.domain.reservation.exception;

import ohsoontac.serverapi.global.error.exception.ErrorCode2;
import ohsoontac.serverapi.global.error.exception.OhSoonTacException;

public class NotHostException extends OhSoonTacException {

    public static final OhSoonTacException EXCEPTION = new NotHostException();

    private NotHostException() {
        super(ErrorCode2.GROUP_NOT_HOST);
    }
}