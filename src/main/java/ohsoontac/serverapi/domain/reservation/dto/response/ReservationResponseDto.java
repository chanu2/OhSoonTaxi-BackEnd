package ohsoontac.serverapi.domain.reservation.dto.response;



import lombok.Getter;
import lombok.NoArgsConstructor;
import ohsoontac.serverapi.domain.common.ReservationStatus;
import ohsoontac.serverapi.domain.common.Sex;
import ohsoontac.serverapi.domain.reservation.entity.Reservation;

import java.time.LocalTime;

@Getter
@NoArgsConstructor
public class ReservationResponseDto {


    public Long id;
    private LocalTime reserveTime;

    private String title;

    private String startPlace;
    private String destination;

    private Sex sex;

    private ReservationStatus reservationStatus;

    public ReservationResponseDto(Reservation reservation) {
        this.id = reservation.getId();
        this. reserveTime = reservation.getReserveTime();
        this.title = reservation.getTitle();
        this.startPlace = reservation.getStartPlace();
        this.destination = reservation.getDestination();
        this.sex = reservation.getSex();
        this.reservationStatus = reservation.getReservationStatus();
    }
}
