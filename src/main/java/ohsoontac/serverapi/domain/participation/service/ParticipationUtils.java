package ohsoontac.serverapi.domain.participation.service;

import ohsoontac.serverapi.domain.participation.entity.Participation;

public interface ParticipationUtils {

    void duplicationParticipation(Long reservationId, Long userId);

    Participation participatedReservation(Long reservationId, Long userId);
}
