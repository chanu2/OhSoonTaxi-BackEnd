package ohsoontac.serverapi.domain.reservation.controller;



import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ohsoontac.serverapi.domain.reservation.dto.request.AddReservationDto;
import ohsoontac.serverapi.domain.reservation.dto.request.UpdateReservationDto;
import ohsoontac.serverapi.domain.reservation.dto.response.*;
import ohsoontac.serverapi.domain.reservation.service.ReservationService;
import ohsoontac.serverapi.global.response.StatusCode;
import ohsoontac.serverapi.global.successResponse.SuccessResponse;
import ohsoontac.serverapi.global.successResponse.ResponseMessage;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/reservation")
public class ReservationController {

    private final ReservationService reservationService;


    // 경기 생성
    @PostMapping("/add")
    public ResponseEntity createReserve(@RequestBody AddReservationDto addReservationDto) throws IOException {

        Long reserveId = reservationService.addReservation(addReservationDto);

        return SuccessResponse.successtoResponseEntity(StatusCode.OK,reserveId, ResponseMessage.CREATE_RESERVATION);
    }



    // 경기 삭제
    @DeleteMapping("/delete/{reservationId}")
    public ResponseEntity deleteReserve(@PathVariable("reservationId")Long reservationId) throws IOException {

        reservationService.deleteReservation(reservationId);

        return SuccessResponse.successtoResponseEntity(StatusCode.OK,null, ResponseMessage.DELETE_RESERVATION);
    }

    //방 정보 업데이트
    @PostMapping("/edit")
    public ResponseEntity updateReservation(@RequestBody UpdateReservationDto updateReservationDto) throws IOException{

         reservationService.updateReservation(updateReservationDto);

        return SuccessResponse.successtoResponseEntity(StatusCode.OK,null, ResponseMessage.UPDATE_RESERVATION);
    }


    // 날짜 정렬 방들 보여주기
    @GetMapping("/list")
    public ResponseEntity getReservationList(@RequestParam(name = "reserveDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate reserveDate){

        List<ReservationResponseDto> reservation = reservationService.getReservationSortList(reserveDate);

        return SuccessResponse.successtoResponseEntity(StatusCode.OK,reservation, ResponseMessage.READ_RESERVATION);
    }

    //경기 세부 정보 보여주기
    @GetMapping("/{reservationId}")
    public ResponseEntity reserve1(@PathVariable("reservationId") Long reservationId) {

        ReserveDetailResponseDto reservationDetail = reservationService.getReservationDetail(reservationId);

        return SuccessResponse.successtoResponseEntity(StatusCode.OK,reservationDetail, ResponseMessage.INFO_RESERVATION);
    }

    // 내가 참여한 경기 보여주기
    @GetMapping("/list/participations")
    public ResponseEntity getParticipatedList(){

        List<ParticipatedReserveResponseDto> participatedReserveResponseDtoList = reservationService.participatedReservation();

        return SuccessResponse.successtoResponseEntity(StatusCode.OK,participatedReserveResponseDtoList, ResponseMessage.READ_RESERVATION);
    }

    //내가 생성한 약속 보여주기
    @GetMapping("/list/reservations")
    public ResponseEntity getReservedByMe(){

        List<ReservedByMeResponseDto> reservedByMeResponseDtoList = reservationService.reservedByMe();

        return SuccessResponse.successtoResponseEntity(StatusCode.OK,reservedByMeResponseDtoList, ResponseMessage.READ_RESERVATION);
    }

    //키워드 검색을 통한 방 리스트 보여주기
    @GetMapping("/search/list")
    public ResponseEntity getSearchReservation(@RequestParam(name = "keyword") String keyword){

        List<SearchResponseDto> searchReservation = reservationService.getSearchReservation(keyword);

        return SuccessResponse.successtoResponseEntity(StatusCode.OK,searchReservation, ResponseMessage.READ_RESERVATION);
    }

    //암구호 보여주기
    @GetMapping("/passphrase/{reservationId}")
    public ResponseEntity getPassphrase(@PathVariable("reservationId") Long reservationId){

        PassphraseResponseDto passphrase = reservationService.getPassphrase(reservationId);

        return SuccessResponse.successtoResponseEntity(StatusCode.OK,passphrase, ResponseMessage.SHOW_COUNTERSIGN);
    }

}
