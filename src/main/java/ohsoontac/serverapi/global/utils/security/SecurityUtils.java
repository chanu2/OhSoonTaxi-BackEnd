package ohsoontac.serverapi.global.utils.security;

import ohsoontac.serverapi.domain.user.exception.UserNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils {

    public static String getCurrentUserUid() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw UserNotFoundException.EXCEPTION;
        }
        return authentication.getName();
    }
}
