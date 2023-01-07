package ohsoontac.serverapi.domain.participation.exception;


import ohsoontac.serverapi.global.error.exception.ErrorCode1;
import ohsoontac.serverapi.global.error.exception.ErrorCode2;
import ohsoontac.serverapi.global.error.exception.OhSoonTacException;


public class DuplicatedParticipationException extends OhSoonTacException {

   public static final OhSoonTacException EXCEPTION = new DuplicatedParticipationException();

//    public DuplicatedParticipationException() {
//        super(ErrorCode1.DUPLICATE_PARTICIPATION);
//    }
    public DuplicatedParticipationException() {
        super(ErrorCode2.DUPLICATE_PARTICIPATION);
}

}
