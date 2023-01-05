package ohsoontac.serverapi.global.security.auth;


import lombok.RequiredArgsConstructor;
import ohsoontac.serverapi.domain.user.entity.User;
import ohsoontac.serverapi.domain.user.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthUserService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public AuthUser loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUid(username)
                .orElseThrow(() -> new UsernameNotFoundException("회원이 존재하지 않습니다."));

        return new AuthUser(
                user.getId(),
                user.getPassword(),
                user.getUid(),
                user.getRoles()
        );
    }
}
