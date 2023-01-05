package ohsoontac.serverapi.domain.reservation.dto.response;


import lombok.Getter;
import ohsoontac.serverapi.domain.common.ReservationStatus;
import ohsoontac.serverapi.domain.common.Sex;
import ohsoontac.serverapi.domain.participation.dto.response.ParticipationDto;
import ohsoontac.serverapi.domain.reservation.entity.Reservation;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class ReserveDetailResponseDto {

    private Long id;
    private String title;
    private LocalDate reserveDate;
    private LocalTime reserveTime;
    private String startingPlace;
    private String destination;

    private Sex sex;
    private Integer passengerNum;

    private Integer currentNum;
    private String challengeWord;
    private String countersignWord;
    private Double startLatitude;
    private Double startLongitude;
    private Double finishLatitude;
    private Double finishLongitude;
    private String name;
    private String schoolNum;

    private LocalDateTime createdAt;

    private ReservationStatus reservationStatus;
    private List<ParticipationDto> participations;



    public ReserveDetailResponseDto(Reservation reservation){
        id= reservation.getId();
        title = reservation.getTitle();
        reserveDate = reservation.getReserveDate();
        reserveTime =reservation.getReserveTime();
        startingPlace = reservation.getStartPlace();
        destination = reservation.getDestination();
        sex = reservation.getSex();
        passengerNum = reservation.getPassengerNum();
        currentNum = reservation.getCurrentNum();
        challengeWord = reservation.getChallengeWord();
        countersignWord = reservation.getCountersignWord();
        startLatitude = reservation.getStartLatitude();
        startLongitude = reservation.getStartLongitude();
        finishLatitude = reservation.getFinishLatitude();
        finishLongitude = reservation.getFinishLongitude();
        name = reservation.getUser().getName();
        schoolNum = reservation.getUser().getSchoolNum();
        reservationStatus = reservation.getReservationStatus();
        createdAt = reservation.getCreatedAt();
        participations = reservation.getParticipations().stream().map(participation -> new ParticipationDto(participation)).collect(Collectors.toList());

    }




}
