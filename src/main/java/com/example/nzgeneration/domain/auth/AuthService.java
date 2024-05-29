package com.example.nzgeneration.domain.auth;

import com.example.nzgeneration.domain.auth.dto.AuthRequestDto.CreateUserRequest;
import com.example.nzgeneration.domain.auth.dto.AuthResponseDto.LoginSimpleInfo;
import com.example.nzgeneration.domain.auth.dto.AuthResponseDto.OIDCDecodePayload;
import com.example.nzgeneration.domain.auth.enums.ResponseType;
import com.example.nzgeneration.domain.user.User;
import com.example.nzgeneration.domain.user.UserRepository;
import com.example.nzgeneration.global.common.response.ApiResponse;
import com.example.nzgeneration.global.common.response.code.status.ErrorStatus;
import com.example.nzgeneration.global.common.response.exception.GeneralException;
import com.example.nzgeneration.global.security.JwtTokenProvider;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AppleOauthHelper appleOauthHelper;
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    @Transactional
    public LoginSimpleInfo login(String idToken){
        OIDCDecodePayload oidcDecodePayload = appleOauthHelper.getOIDCDecodePayload(idToken);
        String email = oidcDecodePayload.email();
        Optional<User> optionalMember = userRepository.findByEmail(email);
        String accessToken, refreshToken;
        if(optionalMember.isPresent()){ //로그인 로직
            accessToken = jwtTokenProvider.createAccessToken(optionalMember.get().getPayload());
            refreshToken = jwtTokenProvider.createRefreshToken(optionalMember.get().getId());
            return LoginSimpleInfo.toDTO(accessToken, refreshToken, ResponseType.SIGN_IN);
        }
        accessToken = jwtTokenProvider.generateTempToken(email);
        return LoginSimpleInfo.toDTO(accessToken, null, ResponseType.SIGN_UP);

    }

    @Transactional
    public LoginSimpleInfo signUp(CreateUserRequest createUserRequest){
        String email = jwtTokenProvider.validateTempTokenAndGetEmail(createUserRequest.getToken());
        if(userRepository.findByEmail(email).isPresent())
            throw new GeneralException(ErrorStatus._DUPLICATE_USER);
        User user = User.toEntity(email, createUserRequest);
        System.out.println("Saving user with badgeCount: " + user.getBadgeCount());
        userRepository.save(user);
        String accessToken = jwtTokenProvider.createAccessToken(user.getPayload());
        String refreshToken = jwtTokenProvider.createRefreshToken(user.getId());
        user.updateToken(accessToken, refreshToken);
        System.out.println("Saving user with badgeCount: " + user.getBadgeCount());
        return LoginSimpleInfo.toDTO(accessToken, refreshToken, ResponseType.SIGN_IN);
    }

    @Transactional
    public LoginSimpleInfo updateUserToken(String refreshToken) {
        if(userRepository.findByRefreshToken(refreshToken).isEmpty()){
            throw new GeneralException(ErrorStatus._EXPIRED_JWT);
        }
        Long userId = Long.valueOf(jwtTokenProvider.getPayload(refreshToken));
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new GeneralException(ErrorStatus._INVALID_JWT));
        String newRefreshToken = jwtTokenProvider.createRefreshToken(userId);
        String newAccesstoken = jwtTokenProvider.refreshAccessToken(refreshToken);
        user.updateToken(newAccesstoken, newRefreshToken);
        return new LoginSimpleInfo(newAccesstoken, newRefreshToken, ResponseType.TOKEN_REFRESH);

    }


}
