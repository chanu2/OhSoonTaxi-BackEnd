package ohsoontac.serverapi.domain.reservation.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ohsoontac.serverapi.domain.common.Sex;
import ohsoontac.serverapi.domain.participation.exception.ParticipationNotFound;
import ohsoontac.serverapi.domain.participation.exception.SexException;
import ohsoontac.serverapi.domain.participation.repository.ParticipationRepository;
import ohsoontac.serverapi.domain.reservation.dto.request.AddReservationDto;
import ohsoontac.serverapi.domain.reservation.dto.request.UpdateReservationDto;
import ohsoontac.serverapi.domain.reservation.dto.response.*;
import ohsoontac.serverapi.domain.reservation.entity.Reservation;
import ohsoontac.serverapi.domain.reservation.exception.ReservationNotFound;
import ohsoontac.serverapi.domain.reservation.repository.ReservationRepository;
import ohsoontac.serverapi.domain.user.entity.User;
import ohsoontac.serverapi.global.utils.security.SecurityUtils;
import ohsoontac.serverapi.global.utils.user.UserUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReservationService implements ReservationUtils {
    private final ReservationRepository reservationRepository;
    private final UserUtils userUtils;
    private final ParticipationRepository participationRepository;


    // 방 생성
    @Transactional
    public Long addReservation(AddReservationDto addReservationDto){

        User user = userUtils.getUserFromSecurityContext();

        log.info("user={}",user);

        log.info("userUid={}",user.getUid());
        
        matchSex(user.getSex(),addReservationDto.getSex());

        Reservation reservation = Reservation.createReservation(
                user,
                addReservationDto.getReserveDate(),
                addReservationDto.getReserveTime(),
                addReservationDto.getTitle(),
                addReservationDto.getStartPlace(),
                addReservationDto.getDestination(),
                addReservationDto.getSex(),
                addReservationDto.getPassengerNum(),
                addReservationDto.getChallengeWord(),
                addReservationDto.getCountersignWord(),
                addReservationDto.getStartLatitude(),
                addReservationDto.getStartLongitude(),
                addReservationDto.getFinishLatitude(),
                addReservationDto.getFinishLongitude());

        reservationRepository.save(reservation);
        log.info("=================================================");

        return reservation.getId();
    }


    //방 삭제
    @Transactional
    public Long deleteReservation(Long reservationId){

        User user = userUtils.getUserFromSecurityContext();

        Reservation reservation = findReservation(reservationId);

        reservation.validUserIsHost(user.getUid());

        reservationRepository.deleteById(reservationId);

        return reservationId;
    }


    // 방 업데이트
    @Transactional
    public Long updateReservation(UpdateReservationDto updateReservationDto){

        User user = userUtils.getUserFromSecurityContext();

        Reservation reservation = findReservation(updateReservationDto.getId());

        reservation.validUserIsHost(user.getUid());

        reservation.changeTitle(updateReservationDto.getTitle());

        return reservation.getId();
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

        Reservation findReservation = findReservation(reservationId);

        ReserveDetailResponseDto reserveDetailResponseDto = new ReserveDetailResponseDto(findReservation);

        return reserveDetailResponseDto;
    }


    // 내가 작성한 게시글
    @Transactional
    public List<ReservedByMeResponseDto> reservedByMe(){

        User user = userUtils.getUserFromSecurityContext();

        List<Reservation> reservations = reservationRepository.reservedByMe(user.getId());

        return reservations.stream().map(r -> new ReservedByMeResponseDto(r)).collect(Collectors.toList());

    }

    // 내가 참여한 게시글 조회
    @Transactional
    public List<ParticipatedReserveResponseDto> participatedReservation(){

        String userUid = SecurityUtils.getCurrentUserUid();

        List<Reservation> participatedReserve = reservationRepository.findParticipatedReserve(userUid);

        return participatedReserve.stream().map( r -> new ParticipatedReserveResponseDto(r)).collect(Collectors.toList());
    }

    // 암구호 보여주기
    @Transactional
    public PassphraseResponseDto getPassphrase(Long reservationId){

        User user = userUtils.getUserFromSecurityContext();

        // TODO: 2023-01-21 순환 관계 제거
        //participationUtils.participatedReservation(reservationId, user.getId());

        participationRepository.findParticipation(reservationId, user.getId()).orElseThrow(
                () -> ParticipationNotFound.EXCEPTION);

        Reservation reservation = findReservation(reservationId);

        PassphraseResponseDto passphraseResponseDto = new PassphraseResponseDto(reservation);

        return passphraseResponseDto;
    }


    @Override
    public Reservation findReservation(Long id) {
        log.info("id={}",id);
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
