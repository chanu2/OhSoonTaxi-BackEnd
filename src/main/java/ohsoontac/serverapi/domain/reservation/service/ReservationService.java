package ohsoontac.serverapi.domain.reservation.service;



import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ohsoontac.serverapi.domain.common.Sex;
import ohsoontac.serverapi.domain.participation.exception.SexException;
import ohsoontac.serverapi.domain.participation.repository.ParticipationRepository;
import ohsoontac.serverapi.domain.reservation.dto.request.AddReservationDto;
import ohsoontac.serverapi.domain.reservation.dto.request.UpdateReservationDto;
import ohsoontac.serverapi.domain.reservation.dto.response.*;
import ohsoontac.serverapi.domain.reservation.entity.Reservation;
import ohsoontac.serverapi.domain.reservation.exception.ReservationNotFound;
import ohsoontac.serverapi.domain.reservation.repository.ReservationRepository;
import ohsoontac.serverapi.domain.user.entity.User;
import ohsoontac.serverapi.domain.user.repository.UserRepository;
import ohsoontac.serverapi.global.utils.UserUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReservationService implements ReservationUtils {

    private final ReservationRepository reservationRepository;

    private final UserRepository userRepository;

    private final UserUtils userUtils;




    // 방 생성

    @Transactional
    public Long addReservation(AddReservationDto addReservationDto, String userUid) throws IOException {

        User user = userUtils.getUserUid(userUid);

        log.info("=================================================");

        matchSex(user.getSex(),addReservationDto.getSex());

        log.info("=================================================");

        Reservation reservation = Reservation.createReservation(user, addReservationDto.getReserveDate(), addReservationDto.getReserveTime(), addReservationDto.getTitle(), addReservationDto.getStartPlace()
                , addReservationDto.getDestination(), addReservationDto.getSex(), addReservationDto.getPassengerNum(), addReservationDto.getChallengeWord(), addReservationDto.getCountersignWord(),
                addReservationDto.getStartLatitude(),addReservationDto.getStartLongitude(),addReservationDto.getFinishLatitude(),addReservationDto.getFinishLongitude());

        reservationRepository.save(reservation);
        log.info("=================================================");
        log.info("reservation={}",reservation);

        return reservation.getId();
    }


    //방 취소
    @Transactional
    public Long deleteReservation(Long reservationId, String userUid) throws  IOException{

        User user = userUtils.getUserUid(userUid);

        if(reservationRepository.findById(reservationId).get().getUser()!=user)return null;

        reservationRepository.deleteById(reservationId);

        return reservationId;

    }

    // 방 업데이트

    @Transactional
    public Long updateReservation(UpdateReservationDto updateReservationDto, String userUid)throws IOException{
        User user = userRepository.findByUid(userUid).get();
        Optional<Reservation> reservation = reservationRepository.findById(updateReservationDto.getId());
        if(reservation.get().getUser()!=user) return null;

        reservation.get().changeTitle(updateReservationDto.getTitle());

        return reservation.get().getId();


    }

    // 키워드 검색
    @Transactional
    public List<SearchResponseDto> getSearchReservation(String keyWord){
        List<Reservation> reservation = reservationRepository.findByTitleContaining(keyWord);

        return reservation.stream().map(r -> new SearchResponseDto(r)).collect(Collectors.toList());

    }



    //  원하는 날짜 시간 정렬해서 택시방 보여 주기
    @Transactional
    public List<ReservationResponseDto> getReservationSortList(LocalDate reserveDate){

        List<Reservation> list = reservationRepository.findBySortDate(reserveDate);

        return list.stream().map( r -> new ReservationResponseDto(r)).collect(Collectors.toList());

    }



    // 택시방 관련 정보 상세 보여주기
    @Transactional
    public ReserveDetailResponseDto getReservationDetail (Long reservationId){


        Reservation findReservation = reservationRepository.findById(reservationId).get();
        ReserveDetailResponseDto reserveDetailResponseDto = new ReserveDetailResponseDto(findReservation);

        return reserveDetailResponseDto;


    }


    // 내가 작성한 게시글
    @Transactional
    public List<ReservedByMeResponseDto> reservedByMe (String userUid){

        Optional<User> user = userRepository.findByUid(userUid);

        List<Reservation> reservations = reservationRepository.reservedByMe(user.get().getId());

        return reservations.stream().map(r -> new ReservedByMeResponseDto(r)).collect(Collectors.toList());


    }

    // 내가 참여한 게시글 조회
    @Transactional
    public List<ParticipatedReserveResponseDto> participatedReservation(String userUid){


        List<Reservation> participatedReserve = reservationRepository.findParticipatedReserve(userUid);

        return participatedReserve.stream().map( r -> new ParticipatedReserveResponseDto(r)).collect(Collectors.toList());

    }

    // 암구호 보여주기

    @Transactional
    public PassphraseResponseDto getPassphrase(Long reservationId, String userUid){

        Reservation reservation = reservationRepository.findById(reservationId).get();

        PassphraseResponseDto passphraseResponseDto = new PassphraseResponseDto(reservation);

        return passphraseResponseDto;
    }


    @Override
    public Reservation findReservation(Long id) {
        log.info("로직 탑니다");
        return reservationRepository.findById(id).orElseThrow(
                () -> ReservationNotFound.EXCEPTION);

    }

    @Override
    public void matchSex(Sex userSex, Sex reservationSex) {
        if(!(userSex.equals(reservationSex) || reservationSex.equals(Sex.ALL))){
            throw SexException.EXCEPTION;
        }
    }
}
