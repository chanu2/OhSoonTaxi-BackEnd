package ohsoontac.serverapi.global.error.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
@AllArgsConstructor
public enum ErrorCode2 {

    /* 400 BAD_REQUEST : 잘못된 요청 */
    /* 401 UNAUTHORIZED : 인증되지 않은 사용자 */
    DUPLICATE_PARTICIPATION(400,"이미 참여하신 방입니다"),
    MISMATCH_SEX(400,"성별이 일치 해야 참여 할 수 있습니다."),

    INVALID_AUTH_TOKEN(401, "권한 정보가 없는 토큰입니다."),

    /* 404 NOT_FOUND : Resource를 찾을 수 없음 */
    USER_NOT_FOUND(404, "해당하는 정보의 사용자를 찾을 수 없습니다."),
    DUPLICATE_LOGIN(404, "해당 아이디는 이미 존재합니다."),

    RESERVATION_NOT_FOUND(404, "해당하는 정보의 방을 찾을 수 없습니다."),

    GROUP_NOT_HOST(400,  "방의 호스트가 아닙니다"),
    PARTICIPATION_NOT_FOUND(404, "참여하지 않은 방입니다."),

    /* 409 : CONFLICT : Resource의 현재 상태와 충돌. 보통 중복된 데이터 존재 */
    DUPLICATE_RESOURCE(409, "데이터가 이미 존재합니다."),

    INVALID_REFRESH_TOKEN(400, "리프레시 토큰이 유효하지 않습니다"),
    INVALID_ACCESS_TOKEN(400, "Access 토큰이 유효하지 않습니다"),
    MISMATCH_REFRESH_TOKEN(400, "리프레시 토큰의 유저 정보가 일치하지 않습니다"),
    REFRESH_TOKEN_NOT_EXIST(400, "리프레시 토큰 정보가 올바르지 않습니다"),

    /* 401 UNAUTHORIZED : 인증되지 않은 사용자 */

    UNAUTHORIZED_MEMBER(401, "현재 내 계정 정보가 존재하지 않습니다"),

    /* 404 NOT_FOUND : Resource 를 찾을 수 없음 */
    MEMBER_NOT_FOUND(404, "해당 유저 정보를 찾을 수 없습니다"),
    LOGIN_NOT_FOUND(404, "로그인 또는 비밀번호 오류입니다"),
    REFRESH_TOKEN_NOT_FOUND(404, "로그아웃 된 사용자입니다"),
    NO_ERROR_TYPE(404, "오류 발생"),

    ;

    private int status;
    private String reason;
}