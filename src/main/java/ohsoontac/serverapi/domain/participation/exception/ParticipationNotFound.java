package ohsoontac.serverapi.domain.participation.exception;


import ohsoontac.serverapi.global.error.exception.ErrorCode1;
import ohsoontac.serverapi.global.error.exception.ErrorCode2;
import ohsoontac.serverapi.global.error.exception.OhSoonTacException;


public class ParticipationNotFound extends OhSoonTacException {

   public static final OhSoonTacException EXCEPTION = new ParticipationNotFound();

//    public ParticipationNotFound() {
//        super(ErrorCode1.PARTICIPATION_NOT_FOUND);
//    }

    public ParticipationNotFound() {
        super(ErrorCode2.PARTICIPATION_NOT_FOUND);
    }


}
