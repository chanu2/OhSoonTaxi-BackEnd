package ohsoontac.serverapi.global.utils.user;

import ohsoontac.serverapi.domain.user.entity.User;

public interface UserUtils {

    User getUserByUid(String uid);
    User getUserUid(String uid);

    User getUserFromSecurityContext();
}
