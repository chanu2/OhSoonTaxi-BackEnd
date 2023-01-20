package ohsoontac.serverapi.global.utils.user;


import lombok.RequiredArgsConstructor;
import ohsoontac.serverapi.domain.user.entity.User;
import ohsoontac.serverapi.domain.user.exception.UserNotFoundException;
import ohsoontac.serverapi.domain.user.repository.UserRepository;
import ohsoontac.serverapi.global.utils.security.SecurityUtils;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserUtilsImpl implements UserUtils {

    private final UserRepository userRepository;

    @Override
    public User getUserByUid(String uid) {
        return userRepository.findByUid(uid).orElseThrow(() -> UserNotFoundException.EXCEPTION);
    }

    @Override
    public User getUserUid(String uid) {
        return userRepository.findByUid(uid).orElseThrow(() -> UserNotFoundException.EXCEPTION);
    }

    @Override
    public User getUserFromSecurityContext() {
        String userUid = SecurityUtils.getCurrentUserUid();
        User user = getUserByUid(userUid);
        return user;
    }
}
