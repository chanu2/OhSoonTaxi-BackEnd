package ohsoontac.serverapi.domain.reservation.controller;



import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ohsoontac.serverapi.domain.participation.repository.ParticipationRepository;
import ohsoontac.serverapi.domain.reservation.dto.request.AddReservationDto;
import ohsoontac.serverapi.domain.reservation.dto.request.UpdateReservationDto;
import ohsoontac.serverapi.domain.reservation.dto.response.*;
import ohsoontac.serverapi.domain.reservation.service.ReservationService;
import ohsoontac.serverapi.domain.user.repository.UserRepository;
import ohsoontac.serverapi.global.response.StatusCode;
import ohsoontac.serverapi.global.successResponse.SuccessResponse1;
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

    private final UserRepository userRepository;

    private final ParticipationRepository participationRepository;


    // 경기 생성
//    @PostMapping("/add")
//    public ResponseEntity createReserve(@RequestBody AddReservationDto addReservationDto, @RequestParam(name = "userUid") String userUid) throws IOException {
//
//        Long reservationId = reservationService.addReservation(addReservationDto, userUid);
//
//        return reservationId !=null ?
//                new ResponseEntity(DefaultRes.res(StatusCode.OK, "방생성 완료"), HttpStatus.OK):
//                new ResponseEntity(DefaultRes.res(StatusCode.BAD_REQUEST, "잘못된 요청"), HttpStatus.OK);
//    }

    @PostMapping("/add")
    public ResponseEntity createReserve(@RequestBody AddReservationDto addReservationDto) throws IOException {

        Long reserveId = reservationService.addReservation(addReservationDto);

        return SuccessResponse1.successtoResponseEntity(StatusCode.OK,reserveId, ResponseMessage.CREATE_RESERVATION);
    }



    // 경기 삭제
//    @DeleteMapping("/delete/{reservationId}")
//    public ResponseEntity deleteReserve(@PathVariable("reservationId")Long reservationId, @RequestParam(name = "userUid") String userUid) throws IOException {
//
//        Long deleteReservationId = reservationService.deleteReservation(reservationId, userUid);
//
//        return deleteReservationId !=null ?
//                new ResponseEntity(DefaultRes.res(StatusCode.OK, "방 삭제 완료"), HttpStatus.OK):
//                new ResponseEntity(DefaultRes.res(StatusCode.BAD_REQUEST, "잘못된 요청"), HttpStatus.OK);
//    }
    @DeleteMapping("/delete/{reservationId}")
    public ResponseEntity deleteReserve(@PathVariable("reservationId")Long reservationId) throws IOException {

        reservationService.deleteReservation(reservationId);

        return SuccessResponse1.successtoResponseEntity(StatusCode.OK,null, ResponseMessage.DELETE_RESERVATION);
    }

    //방 정보 업데이트
    @PostMapping("/edit")
    public ResponseEntity updateReservation(@RequestBody UpdateReservationDto updateReservationDto) throws IOException{

         reservationService.updateReservation(updateReservationDto);

        return SuccessResponse1.successtoResponseEntity(StatusCode.OK,null, ResponseMessage.UPDATE_RESERVATION);}




    // 날짜 정렬 방들 보여주기
//    @GetMapping("/list")
//    public ResponseEntity getReservationList(@RequestParam(name = "reserveDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate reserveDate){
//
//        List<ReservationResponseDto> reservation = reservationService.getReservationSortList(reserveDate);
//
//        return reservation.size() != 0 ?
//                new ResponseEntity(DefaultRes.res(StatusCode.OK, "방 조회 완료", reservation), HttpStatus.OK):
//                new ResponseEntity(DefaultRes.res(StatusCode.BAD_REQUEST, "방 없음", new ArrayList()), HttpStatus.OK);
//
//    }



    @GetMapping("/list")
    public ResponseEntity getReservationList(@RequestParam(name = "reserveDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate reserveDate){

        List<ReservationResponseDto> reservation = reservationService.getReservationSortList(reserveDate);

        return SuccessResponse1.successtoResponseEntity(StatusCode.OK,reservation, ResponseMessage.READ_RESERVATION);

    }

    //경기 세부 정보 보여주기
//    @GetMapping("/{reservationId}")
//    public ResponseEntity reserve1(@PathVariable("reservationId") Long reservationId) {
//
//        ReserveDetailResponseDto reservationDetail = reservationService.getReservationDetail(reservationId);
//
//
//        return reservationDetail != null ?
//                new ResponseEntity(DefaultRes.res(StatusCode.OK, "경기 정보 보여주기 완료", reservationDetail), HttpStatus.OK) :
//                new ResponseEntity(DefaultRes.res(StatusCode.BAD_REQUEST, "잘못된 요청", reservationDetail), HttpStatus.OK);
//
//    }

    @GetMapping("/{reservationId}")
    public ResponseEntity reserve1(@PathVariable("reservationId") Long reservationId) {

        ReserveDetailResponseDto reservationDetail = reservationService.getReservationDetail(reservationId);


        return SuccessResponse1.successtoResponseEntity(StatusCode.OK,reservationDetail, ResponseMessage.INFO_RESERVATION);

    }

    // 내가 참여한 경기 보여주기
//    @GetMapping("/list/participations")
//    public ResponseEntity getParticipatedList(@RequestParam(name = "userUid") String userUid){
//
//
//        List<ParticipatedReserveResponseDto> participatedReserveResponseDtoList = reservationService.participatedReservation(userUid);
//
//        return participatedReserveResponseDtoList != null ?
//                new ResponseEntity(DefaultRes.res(StatusCode.OK, "경기 정보 보여주기 완료", participatedReserveResponseDtoList), HttpStatus.OK) :
//                new ResponseEntity(DefaultRes.res(StatusCode.BAD_REQUEST, "잘못된 요청", participatedReserveResponseDtoList), HttpStatus.OK);
//
//    }

    @GetMapping("/list/participations")
    public ResponseEntity getParticipatedList(){

        List<ParticipatedReserveResponseDto> participatedReserveResponseDtoList = reservationService.participatedReservation();

        return SuccessResponse1.successtoResponseEntity(StatusCode.OK,participatedReserveResponseDtoList, ResponseMessage.READ_RESERVATION);

    }

    //내가 생성한 약속 보여주기
//    @GetMapping("/list/reservations")
//    public ResponseEntity getReservedByMe(@RequestParam(name = "userUid") String userUid){
//
//
//        List<ReservedByMeResponseDto> reservedByMeResponseDtoList = reservationService.reservedByMe(userUid);
//
//        return reservedByMeResponseDtoList != null ?
//                new ResponseEntity(DefaultRes.res(StatusCode.OK, "경기 정보 보여주기 완료", reservedByMeResponseDtoList), HttpStatus.OK) :
//                new ResponseEntity(DefaultRes.res(StatusCode.BAD_REQUEST, "잘못된 요청",reservedByMeResponseDtoList), HttpStatus.OK);
//
//    }
    @GetMapping("/list/reservations")
    public ResponseEntity getReservedByMe(){

        List<ReservedByMeResponseDto> reservedByMeResponseDtoList = reservationService.reservedByMe();

        return SuccessResponse1.successtoResponseEntity(StatusCode.OK,reservedByMeResponseDtoList, ResponseMessage.READ_RESERVATION);

    }

    //키워드 검색을 통한 방 리스트 보여주기

//    @GetMapping("/search/list")
//    public ResponseEntity getSearchReservation(@RequestParam(name = "keyword") String keyword){
//
//
//        List<SearchResponseDto> searchReservation = reservationService.getSearchReservation(keyword);
//
//        return searchReservation != null ?
//                new ResponseEntity(DefaultRes.res(StatusCode.OK, "경기 정보 보여주기 완료", searchReservation), HttpStatus.OK) :
//                new ResponseEntity(DefaultRes.res(StatusCode.BAD_REQUEST, "잘못된 요청",searchReservation), HttpStatus.OK);
//
//    }

    @GetMapping("/search/list")
    public ResponseEntity getSearchReservation(@RequestParam(name = "keyword") String keyword){

        List<SearchResponseDto> searchReservation = reservationService.getSearchReservation(keyword);

        return SuccessResponse1.successtoResponseEntity(StatusCode.OK,searchReservation, ResponseMessage.READ_RESERVATION);
    }

    //암구호 보여주기
//    @GetMapping("/passphrase/{reservationId}")
//    public ResponseEntity getPassphrase(@PathVariable("reservationId") Long reservationId,@RequestParam(name = "userUid") String userUid){
//
//
//        PassphraseResponseDto passphrase = reservationService.getPassphrase(reservationId,userUid);
//
//        return passphrase != null ?
//                new ResponseEntity(DefaultRes.res(StatusCode.OK, "경기 정보 보여주기 완료", passphrase), HttpStatus.OK) :
//                new ResponseEntity(DefaultRes.res(StatusCode.BAD_REQUEST, "잘못된 요청",passphrase), HttpStatus.OK);
//
//    }

    @GetMapping("/passphrase/{reservationId}")
    public ResponseEntity getPassphrase(@PathVariable("reservationId") Long reservationId){


        PassphraseResponseDto passphrase = reservationService.getPassphrase(reservationId);

        return SuccessResponse1.successtoResponseEntity(StatusCode.OK,passphrase, ResponseMessage.SHOW_COUNTERSIGN);

    }



}
