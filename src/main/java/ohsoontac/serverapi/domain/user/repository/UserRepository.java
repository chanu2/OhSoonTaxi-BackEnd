package ohsoontac.serverapi.domain.user.repository;



import ohsoontac.serverapi.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    User save(User user);


    Optional<User> findByUid(String userUid);


//    public User getUserById(Long id) {
//        return userRepository.findById(id).orElseThrow(() -> UserNotFoundException.EXCEPTION);
//    }
    boolean existsByUid(String uid);

    @Transactional
    void deleteById(Long id);

    boolean findByUidAndPassword(String uid, String password);
}