package ohsoontac.serverapi.domain.user.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ohsoontac.serverapi.domain.user.dto.request.SignInDto;
import ohsoontac.serverapi.domain.user.dto.request.SignUpDto;
import ohsoontac.serverapi.domain.user.entity.RefreshToken;
import ohsoontac.serverapi.domain.user.entity.User;
import ohsoontac.serverapi.domain.user.exception.UserNotFoundException;
import ohsoontac.serverapi.domain.user.repository.RefreshTokenRepository;
import ohsoontac.serverapi.domain.user.repository.UserRepository;
import ohsoontac.serverapi.global.error.exception.ErrorCode;
import ohsoontac.serverapi.global.error.exception.ErrorCode1;
import ohsoontac.serverapi.global.security.CustomAuthenticationEntryPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;


@Component
@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final Logger logger = LoggerFactory.getLogger(CustomAuthenticationEntryPoint.class);

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final RefreshTokenRepository refreshTokenRepository;


    // 회원 가입
    public Long signUp(SignUpDto signUpDto) {
        Long id = userRepository.save(
                        User.builder()
                                .uid(signUpDto.getUid())
                                .password(passwordEncoder.encode(signUpDto.getPassword()))
                                .name(signUpDto.getName())
                                .schoolNum(signUpDto.getSchoolNum())
                                .phoneNum(signUpDto.getPhoneNum())
                                .sex(signUpDto.getSex())
                                .roles(Collections.singletonList("ROLE_USER"))
                                .build())
                .getId();
        return id;
    }



//    public Optional<User> findUidAndPassword(String uid,String password){
//
//        String encodePassword = passwordEncoder.encode(password);
//        Optional<User> member = userRepository.findByUidAndPassword(uid, encodePassword);
//        return member;
//
//
//    }

    public Optional<User> findUserByUid(String userUid) {
        Optional<User> member = userRepository.findByUid(userUid);
        return member;
    }

//    public User findUserByUid(String userUid) {
//
//        return userRepository.findByUid(userUid).orElseThrow(() -> new UserNotFoundException(ErrorCode1.USER_NOT_FOUND));
//
//    }



    public Boolean signIn (String refreshToken,User user) {

        refreshTokenRepository.save(new RefreshToken(refreshToken));


        logger.info(user.getUid() + " (id : " + user.getId() + ") login");
        return true;
    }


    // 로그아웃
    public Boolean signOut (String refreshToken,User user) {
        if(!refreshTokenRepository.existsByRefreshToken(refreshToken)) return false;

        refreshTokenRepository.deleteByRefreshToken(refreshToken);

        logger.info(user.getUid() + " (id : " + user.getId() + ") logout");

        return true;
    }


    public Boolean checkUnique(String uid) {
        Boolean result = userRepository.existsByUid(uid);  // 아이디가 존재하면 true

        return !result;
    }
    public boolean checkPassword(User member, SignInDto user) {
        return passwordEncoder.matches(user.getPassword(), member.getPassword());
    }

}
