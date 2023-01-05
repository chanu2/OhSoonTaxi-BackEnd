package ohsoontac.serverapi.domain.reservation.dto.request;



import lombok.Getter;
import lombok.NoArgsConstructor;
import ohsoontac.serverapi.domain.common.Sex;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@NoArgsConstructor
public class AddReservationDto {

    private LocalDate reserveDate;
    private LocalTime reserveTime;

    private String title;
    private String startPlace;
    private String destination;

    private Sex sex;

    private Integer passengerNum;
    private String challengeWord;
    private String countersignWord;

    private Double startLatitude;
    private Double startLongitude;

    private Double finishLatitude;
    private Double finishLongitude;


//    public AddReservationDto(LocalDate reserveDate, LocalTime startT, String title, String startPlace, String destination, Sex sex, Integer passengerNum, String challengeWord, String countersignWord) {
//        this.reserveDate = reserveDate;
//        this.startT = startT;
//        this.title = title;
//        this.startPlace = startPlace;
//        this.destination = destination;
//        this.sex = sex;
//        this.passengerNum = passengerNum;
//        this.challengeWord = challengeWord;
//        this.countersignWord = countersignWord;
//    }



}
