package ohsoontac.serverapi.domain.reservation.dto.request;



import lombok.Getter;
import lombok.NoArgsConstructor;
import ohsoontac.serverapi.domain.common.Sex;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@NoArgsConstructor
public class RequestAddReservationDto {

    private LocalDate reserveDate;
    private LocalTime reserveTime;

    private String title;
    private String startPlace;
    private String destination;

    private Sex sex;

    private Integer passengerNum;
    private String challengeWord;
    private String countersignWord;
}
