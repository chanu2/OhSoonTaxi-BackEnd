package ohsoontac.serverapi.domain.participation.service;



import lombok.RequiredArgsConstructor;
import ohsoontac.serverapi.domain.common.ReservationStatus;
import ohsoontac.serverapi.domain.participation.dto.request.AddParticipationDto;
import ohsoontac.serverapi.domain.participation.entity.Participation;
import ohsoontac.serverapi.domain.participation.exception.DuplicatedParticipationException;
import ohsoontac.serverapi.domain.participation.exception.ParticipationNotFound;
import ohsoontac.serverapi.domain.participation.repository.ParticipationRepository;
import ohsoontac.serverapi.domain.reservation.entity.Reservation;
import ohsoontac.serverapi.domain.reservation.repository.ReservationRepository;
import ohsoontac.serverapi.domain.reservation.service.ReservationUtils;
import ohsoontac.serverapi.domain.user.entity.User;
import ohsoontac.serverapi.domain.user.repository.UserRepository;
import ohsoontac.serverapi.global.utils.user.UserUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ParticipationService implements ParticipationUtils {


    private final UserRepository userRepository;

    private final ReservationRepository reservationRepository;

    private final UserUtils userUtils;

    private final ReservationUtils reservationUtils;
    private final ParticipationRepository participationRepository;



//    //참여하기
//    @Transactional
//    public Long addParticipation(AddParticipationDto addParticipationDto, String userUid) {
//
//
//        User findUser = userUtils.getUserUid(userUid);
//
//        Reservation findReservation = reservationUtils.findReservation(addParticipationDto.getReservationId());
//
//        duplicationParticipation(findReservation.getId(), findUser.getId());
//
//        reservationUtils.matchSex(findUser.getSex(),findReservation.getSex());
//
//        Participation participation = participationRepository.save(
//
//                Participation.builder()
//                .user(findUser)
//                .reservation(findReservation)
//                .seatPosition(addParticipationDto.getSeatPosition())
//                .build());
//
//        findReservation.addCurrentNum();
//        findReservation.addParticipation(participation);
//        findReservation.changeReservationStatus();
//
//
//        return participation.getId();
//
//
//    }

    @Transactional
    public Long addParticipation(AddParticipationDto addParticipationDto) {


        User user = userUtils.getUserFromSecurityContext();

        Reservation findReservation = reservationUtils.findReservation(addParticipationDto.getReservationId());

        duplicationParticipation(findReservation.getId(), user.getId());

        reservationUtils.matchSex(user.getSex(),findReservation.getSex());

        Participation participation = participationRepository.save(

                Participation.builder()
                        .user(user)
                        .reservation(findReservation)
                        .seatPosition(addParticipationDto.getSeatPosition())
                        .build());

        findReservation.addCurrentNum();
        findReservation.addParticipation(participation);
        findReservation.changeReservationStatus();


        return participation.getId();


    }


    //참여 취소
//    @Transactional
//    public Long deleteParticipation(Long reservationId, String userUid) {
//
//        User findUser = userUtils.getUserUid(userUid);
//        Reservation reservation = reservationUtils.findReservation(reservationId);
//        Participation participation = participatedReservation(reservation.getId(), findUser.getId());
//
//        participation.getReservation().subtractCurrentNum();
//        participation.getReservation().subParticipation(participation);
//        participation.getReservation().changeReservationStatus();
//
//        participationRepository.deleteById(participation.getId());
//
//        return participation.getId();
//
//    }

    @Transactional
    public Long deleteParticipation(Long reservationId) {

        User user = userUtils.getUserFromSecurityContext();
        Reservation reservation = reservationUtils.findReservation(reservationId);
        Participation participation = participatedReservation(reservation.getId(), user.getId());

        participation.getReservation().subtractCurrentNum();
        participation.getReservation().subParticipation(participation);
        participation.getReservation().changeReservationStatus();

        participationRepository.deleteById(participation.getId());

        return participation.getId();

    }


    //방에 참여했는지 확인
//    @Transactional
//    public String checkParticipation(Long reservationId, String userUid) {
//
//        User user = userUtils.getUserUid(userUid);
//
//        Reservation findReservation = reservationUtils.findReservation(reservationId);
//
//
//        Optional<Participation> participation = participationRepository.findParticipation1(findReservation.getId(),user.getId());
//
//        LocalDateTime nowDateTime = LocalDateTime.now();
//        LocalDateTime reserveDateTime = LocalDateTime.of(findReservation.getReserveDate(), findReservation.getReserveTime());
//
//        if (participation.isPresent()) {
//
//            if (nowDateTime.isAfter(reserveDateTime.minusMinutes(30)) && nowDateTime.isBefore(reserveDateTime)) {
//                return "1";
//            } else if (nowDateTime.isBefore(reserveDateTime)) {
//                System.out.println(nowDateTime.isBefore(reserveDateTime));
//                return "2";
//            }
//            return "4";
//        } else if (nowDateTime.isBefore(reserveDateTime)
//                && findReservation.getReservationStatus() != ReservationStatus.DEADLINE) {
//            return "3";
//        }
//        return "4";
//    }

    @Transactional
    public String checkParticipation(Long reservationId) {

        User user = userUtils.getUserFromSecurityContext();

        Reservation findReservation = reservationUtils.findReservation(reservationId);


        Optional<Participation> participation = participationRepository.findParticipation1(findReservation.getId(),user.getId());

        LocalDateTime nowDateTime = LocalDateTime.now();
        LocalDateTime reserveDateTime = LocalDateTime.of(findReservation.getReserveDate(), findReservation.getReserveTime());

        if (participation.isPresent()) {

            if (nowDateTime.isAfter(reserveDateTime.minusMinutes(30)) && nowDateTime.isBefore(reserveDateTime)) {
                return "1";
            } else if (nowDateTime.isBefore(reserveDateTime)) {
                System.out.println(nowDateTime.isBefore(reserveDateTime));
                return "2";
            }
            return "4";
        } else if (nowDateTime.isBefore(reserveDateTime)
                && findReservation.getReservationStatus() != ReservationStatus.DEADLINE) {
            return "3";
        }
        return "4";
    }


    @Override
    public void duplicationParticipation(Long reservationId, Long userId) {
        participationRepository.findParticipation1(reservationId, userId).ifPresent(p -> {
                    throw DuplicatedParticipationException.EXCEPTION;
                }
        );
    }

    @Override
    public Participation participatedReservation(Long reservationId, Long userId) {
        return participationRepository.findParticipation1(reservationId,userId).orElseThrow(
                () -> ParticipationNotFound.EXCEPTION);
    }
}
