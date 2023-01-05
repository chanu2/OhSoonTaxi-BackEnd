package ohsoontac.serverapi.domain.reservation.dto.response;


import lombok.Getter;
import ohsoontac.serverapi.domain.reservation.entity.Reservation;

@Getter
public class PassphraseResponseDto {

    private String challengeWord;
    private String countersignWord;

    public PassphraseResponseDto(Reservation reservation) {
        challengeWord = reservation.getChallengeWord();
        countersignWord = reservation.getCountersignWord();
    }
}
