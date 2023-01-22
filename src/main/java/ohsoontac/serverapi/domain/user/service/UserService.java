package ohsoontac.serverapi.domain.user.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ohsoontac.serverapi.domain.user.dto.request.SignInDto;
import ohsoontac.serverapi.domain.user.dto.request.SignUpDto;
import ohsoontac.serverapi.domain.user.entity.RefreshToken;
import ohsoontac.serverapi.domain.user.entity.User;
import ohsoontac.serverapi.domain.user.exception.DuplicateLoginException;
import ohsoontac.serverapi.domain.user.exception.RefreshTokenNotExistException;
import ohsoontac.serverapi.domain.user.exception.UserLoginException;
import ohsoontac.serverapi.domain.user.repository.RefreshTokenRepository;
import ohsoontac.serverapi.domain.user.repository.UserRepository;
import ohsoontac.serverapi.global.security.CustomAuthenticationEntryPoint;
import ohsoontac.serverapi.global.utils.user.UserUtils;
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

    private final UserUtils userUtils;


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




    public Optional<User> findUserByUid(String userUid) {
        Optional<User> member = userRepository.findByUid(userUid);
        return member;
    }


    public Boolean signIn (String refreshToken,User user) {

        refreshTokenRepository.save(new RefreshToken(refreshToken));
        logger.info(user.getUid() + " (id : " + user.getId() + ") login");
        return true;
    }


    // 로그아웃
    public void signOut (String refreshToken) {

        refreshToken = refreshToken.substring(7);

        User user = userUtils.getUserFromSecurityContext();


        if(!refreshTokenRepository.existsByRefreshToken(refreshToken)){
            throw RefreshTokenNotExistException.EXCEPTION;
        }

        refreshTokenRepository.deleteByRefreshToken(refreshToken);

        logger.info(user.getUid() + " (id : " + user.getId() + ") logout");

    }


    public void checkUnique(String uid) {

        if(userRepository.existsByUid(uid)){
            throw DuplicateLoginException.EXCEPTION;
        }
    }

    public void checkPassword(User member, SignInDto user) {
        if(!passwordEncoder.matches(user.getPassword(), member.getPassword())){
            throw UserLoginException.EXCEPTION;
        }
    }

}
