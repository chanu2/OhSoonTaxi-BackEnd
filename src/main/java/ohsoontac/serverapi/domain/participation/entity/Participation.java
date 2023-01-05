package ohsoontac.serverapi.domain.participation.entity;

import ohsoontac.serverapi.domain.reservation.entity.Reservation;
import ohsoontac.serverapi.domain.user.entity.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Participation {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="participation_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;
    private Integer seatPosition;

    @Builder
    public Participation(User user, Reservation reservation, Integer seatPosition) {
        this.user = user;
        this.reservation = reservation;
        this.seatPosition = seatPosition;
    }
    public void mappingReservation(Reservation reservation) {
        this.reservation=reservation;
    }
}
