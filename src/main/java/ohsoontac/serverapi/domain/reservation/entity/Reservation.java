package ohsoontac.serverapi.domain.reservation.entity;


import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ohsoontac.serverapi.domain.common.ReservationStatus;
import ohsoontac.serverapi.domain.common.Sex;
import ohsoontac.serverapi.domain.participation.entity.Participation;
import ohsoontac.serverapi.domain.reservation.exception.NotHostException;
import ohsoontac.serverapi.domain.user.entity.User;
import ohsoontac.serverapi.global.database.BaseEntity;



import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Reservation extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservation_id")
    private Long id;

    @OneToMany(mappedBy = "reservation",cascade = CascadeType.ALL)
    private List<Participation> participations = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private LocalDate reserveDate;
    private LocalTime reserveTime;

    private String title;
    private String startPlace;
    private String destination;

    @Enumerated(EnumType.STRING)
    private Sex sex;

    @Enumerated(EnumType.STRING)
    private ReservationStatus reservationStatus;

    private Integer passengerNum;
    private Integer currentNum;

    private String challengeWord;
    private String countersignWord;

    private Double startLatitude;
    private Double startLongitude;

    private Double finishLatitude;
    private Double finishLongitude;


    @Builder
    public Reservation(User user, LocalDate reserveDate, LocalTime reserveTime, String title, String startPlace, String destination,
                       Sex sex, ReservationStatus reservationStatus, Integer passengerNum, Integer currentNum, String challengeWord, String countersignWord, Double startLatitude,
                       Double startLongitude, Double finishLatitude, Double finishLongitude) {
        this.user=user;
        this.reserveDate = reserveDate;
        this.reserveTime = reserveTime;
        this.title = title;
        this.startPlace = startPlace;
        this.destination = destination;
        this.sex = sex;
        this.reservationStatus = reservationStatus;
        this.passengerNum = passengerNum;
        this.currentNum = currentNum;
        this.challengeWord = challengeWord;
        this.countersignWord = countersignWord;
        this.startLatitude = startLatitude;
        this.startLongitude = startLongitude;
        this.finishLatitude = finishLatitude;
        this.finishLongitude = finishLongitude;

    }

    //== 연관 관계 메서드==//

    public void addParticipation(Participation participation){
        participations.add(participation);
        participation.mappingReservation(this);

    }

    public void subParticipation(Participation participation){
        participations.remove(participation);
        participation.mappingReservation(this);
    }


    //==생성메서드==//
    public static Reservation createReservation(User user,LocalDate reserveDate, LocalTime reserveTime, String title, String startPlace, String destination,
                                                Sex sex, Integer passengerNum, String challengeWord, String countersignWord,Double startLatitude,
                                                Double startLongitude, Double finishLatitude,Double finishLongitude){
        return builder()
                .user(user)
                .reserveDate(reserveDate)
                .reserveTime(reserveTime)
                .title(title)
                .startPlace(startPlace)
                .destination(destination)
                .reservationStatus(ReservationStatus.POSSIBLE)
                .passengerNum(passengerNum)
                .sex(sex)
                .currentNum(0)
                .challengeWord(challengeWord)
                .countersignWord(countersignWord)
                .startLatitude(startLatitude)
                .startLongitude(startLongitude)
                .finishLatitude(finishLatitude)
                .finishLongitude(finishLongitude)
                .build();

    }


    // 이름 변경
    public void changeTitle(String title) {
        this.title=title;
    }

    //인원 추가
    public void addCurrentNum(){this.currentNum++;}


    //인원 빼기
    public void subtractCurrentNum(){
        this.currentNum--;
    }


    // 참석한 인원에 따라 예약 상태 변경 비지니스로직
    public void changeReservationStatus(){
        if(this.passengerNum*0.5 < this.currentNum && this.currentNum < this.passengerNum )  {
            this.changeReserveStatus(ReservationStatus.IMMINENT);
        }
        else if(this.currentNum == this.passengerNum){
            this.changeReserveStatus(ReservationStatus.DEADLINE);
        }
        else {
            this.changeReserveStatus(ReservationStatus.POSSIBLE);
        }

    }

    public void validUserIsHost(String userUid) {
        if (!checkUserIsHost(userUid)) {
            throw NotHostException.EXCEPTION;
        }
    }

    public Boolean checkUserIsHost(String userUid) {
        return user.getUid().equals(userUid);
    }

    public void changeReserveStatus(ReservationStatus reservationStatus){
        this.reservationStatus = reservationStatus;
    }

}
