package ohsoontac.serverapi.domain.participation.repository;


import ohsoontac.serverapi.domain.participation.entity.Participation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ParticipationRepository extends JpaRepository<Participation,Long> {

    Participation save(Participation participation);

    Optional<Participation> findById(Long id);


    @Query("select p from Participation p"+
            " join fetch p.user u"+
            " join fetch p.reservation r"+
            " where r.id = :reservationId and u.id = :userId ")
    Optional<Participation> findParticipation(@Param("reservationId") Long reserveId, @Param("userId")Long userId);


    @Override
    void deleteById(Long id);
}
