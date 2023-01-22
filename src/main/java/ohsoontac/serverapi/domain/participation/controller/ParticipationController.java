package ohsoontac.serverapi.domain.participation.controller;

import lombok.RequiredArgsConstructor;
import ohsoontac.serverapi.domain.participation.dto.request.AddParticipationDto;
import ohsoontac.serverapi.domain.participation.service.ParticipationService;
import ohsoontac.serverapi.global.response.StatusCode;
import ohsoontac.serverapi.global.successResponse.SuccessResponse;
import ohsoontac.serverapi.global.successResponse.ResponseMessage;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@RequiredArgsConstructor
@RequestMapping("/participation")
public class ParticipationController {

    private final ParticipationService participationService;


    // 참여하기
    @PostMapping("/add")
    public ResponseEntity addParticipate(@RequestBody AddParticipationDto addParticipationDto){

        participationService.addParticipation(addParticipationDto);

        return SuccessResponse.successtoResponseEntity(StatusCode.OK,null, ResponseMessage.PARTICIPATE_RESERVATION);
    }


    // 참여 취소
    @DeleteMapping("/delete/{reservationId}")
    public ResponseEntity deleteParticipation(@PathVariable Long reservationId){

        participationService.deleteParticipation(reservationId);

        return SuccessResponse.successtoResponseEntity(StatusCode.OK,null, ResponseMessage.CANCEL_PARTICIPATION);
    }

    // 참여상태 확인
    @GetMapping("/check/{reservationId}")
    public ResponseEntity checkParticipation(@PathVariable Long reservationId){

        String status = participationService.checkParticipation(reservationId);

        switch (status){
            case "1" : return SuccessResponse.successtoResponseEntity(StatusCode.OK,null, ResponseMessage.SHOW_COUNTERSIGN);
            case "2" : return SuccessResponse.successtoResponseEntity(StatusCode.OK,null, ResponseMessage.CANCEL_PARTICIPATION);
            case "3" : return SuccessResponse.successtoResponseEntity(StatusCode.OK,null, ResponseMessage.PARTICIPATE);
            default: return SuccessResponse.successtoResponseEntity(StatusCode.OK,null, ResponseMessage.DEADLINE_RESERVATION);
        }


    }


}
