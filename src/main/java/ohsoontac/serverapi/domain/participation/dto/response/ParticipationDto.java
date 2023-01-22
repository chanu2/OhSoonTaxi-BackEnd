package ohsoontac.serverapi.domain.participation.dto.response;

import lombok.Getter;
import ohsoontac.serverapi.domain.participation.entity.Participation;

@Getter
public class ParticipationDto {

    private String name;
    private String schoolNum;
    private Integer seatPosition;

    public ParticipationDto(Participation participation){

        name= participation.getUser().getName();
        schoolNum = participation.getUser().getSchoolNum();
        seatPosition = participation.getSeatPosition();
    }
}
