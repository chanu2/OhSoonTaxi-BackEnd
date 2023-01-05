package ohsoontac.serverapi.domain.user.repository;


import ohsoontac.serverapi.domain.user.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    boolean existsByRefreshToken(String token);

    RefreshToken findByRefreshToken(String token);

    @Transactional
    void deleteByRefreshToken(String token);
}

