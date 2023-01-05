package ohsoontac.serverapi.domain.participation.dto.request;


import lombok.Getter;

@Getter
public class AddParticipationDto {


    private Long reservationId;
    private Integer seatPosition;

}
