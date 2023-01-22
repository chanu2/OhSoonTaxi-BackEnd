package ohsoontac.serverapi.domain.user.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ohsoontac.serverapi.domain.user.dto.request.SignInDto;
import ohsoontac.serverapi.domain.user.dto.request.SignUpDto;
import ohsoontac.serverapi.domain.user.entity.User;
import ohsoontac.serverapi.domain.user.service.UserService;
import ohsoontac.serverapi.global.response.StatusCode;
import ohsoontac.serverapi.global.security.JwtTokenProvider;
import ohsoontac.serverapi.global.successResponse.SuccessResponse;
import ohsoontac.serverapi.global.successResponse.ResponseMessage;
import ohsoontac.serverapi.global.utils.user.UserUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;
    private final UserUtils userUtils;


    // 아이디 중복체크
    @GetMapping("/checkUnique")
    public ResponseEntity check(@RequestParam(name = "userUid")String uid) {

        userService.checkUnique(uid);

        return SuccessResponse.successtoResponseEntity(StatusCode.OK,null, ResponseMessage.USE_USERID);

    }

    // 회원가입 요청
    @PostMapping("/signUp")
    public ResponseEntity signUp(@RequestBody SignUpDto user) {

        Long result = userService.signUp(user);
        // TODO: 2023-01-20  valid 적용

        return SuccessResponse.successtoResponseEntity(StatusCode.OK,null, ResponseMessage.CREATED_USER);
    }


    // 로그인
    @PostMapping("/signIn")
    public ResponseEntity signIn(@RequestBody SignInDto signInDto, HttpServletResponse response) {

        User user = userUtils.getUserByUid(signInDto.getUid());

        userService.checkPassword(user,signInDto);

        // 어세스, 리프레시 토큰 발급 및 헤더 설정
        String accessToken = jwtTokenProvider.createAccessToken(user.getUid(), user.getRoles());
        String refreshToken = jwtTokenProvider.createRefreshToken(user.getUid(), user.getRoles());

        jwtTokenProvider.setHeaderAccessToken(response, accessToken);
        jwtTokenProvider.setHeaderRefreshToken(response, refreshToken);

        // 리프레시 토큰 저장소에 저장
        userService.signIn(refreshToken, user);

        return SuccessResponse.successtoResponseEntity(StatusCode.OK,null, ResponseMessage.LOGIN_SUCCESS);
    }


    //로그아웃
    @PostMapping("/signOut")
    public ResponseEntity signOut(@RequestHeader("RefreshToken") String refreshToken) {


        userService.signOut(refreshToken);

        return SuccessResponse.successtoResponseEntity(StatusCode.OK,null, ResponseMessage.LOGOUT_SUCCESS);
    }



}
