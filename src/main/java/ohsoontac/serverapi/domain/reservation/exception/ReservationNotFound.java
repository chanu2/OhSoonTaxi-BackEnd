package ohsoontac.serverapi.domain.reservation.exception;


import ohsoontac.serverapi.global.error.exception.ErrorCode2;
import ohsoontac.serverapi.global.error.exception.OhSoonTacException;

public class ReservationNotFound extends OhSoonTacException {
    public static final OhSoonTacException EXCEPTION = new ReservationNotFound();
//
//    public ReservationNotFound() {
//        super(ErrorCode1.RESERVATION_NOT_FOUND);
//    }

    public ReservationNotFound() {
        super(ErrorCode2.RESERVATION_NOT_FOUND);
    }
}
