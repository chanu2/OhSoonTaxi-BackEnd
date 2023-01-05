package ohsoontac.serverapi.domain.user.dto.request;


import lombok.Getter;
import ohsoontac.serverapi.domain.common.Sex;

@Getter
public class SignUpDto {
    // TODO :: 검증 조건 추가하기
    private String uid;
    private String password;

    private String name;
    private String schoolNum;
    private String phoneNum;

    private Sex sex;


}
