package ohsoontac.serverapi.domain.participation.exception;



import ohsoontac.serverapi.global.error.exception.ErrorCode;
import ohsoontac.serverapi.global.error.exception.OhSoonTacException;


public class ParticipationNotFound extends OhSoonTacException {

   public static final OhSoonTacException EXCEPTION = new ParticipationNotFound();
   public ParticipationNotFound() {
        super(ErrorCode.PARTICIPATION_NOT_FOUND);
    }


}
