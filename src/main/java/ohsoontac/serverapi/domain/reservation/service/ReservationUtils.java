package ohsoontac.serverapi.domain.reservation.service;

import ohsoontac.serverapi.domain.common.Sex;
import ohsoontac.serverapi.domain.reservation.entity.Reservation;

public interface ReservationUtils {

    Reservation findReservation(Long id);

    void matchSex(Sex userSex, Sex reservationSex);

}
