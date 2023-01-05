package ohsoontac.serverapi.domain.user.dto.request;

import lombok.Getter;

@Getter
public class SignInDto {
    // 검증 조건 추가하기
    private String uid;
    private String password;
}
