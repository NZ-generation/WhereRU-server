package com.example.nzgeneration.domain.auth;

import com.example.nzgeneration.domain.auth.dto.AuthRequestDto.CreateUserRequest;
import com.example.nzgeneration.domain.auth.dto.AuthResponseDto.AppleTokenResponse;
import com.example.nzgeneration.domain.auth.dto.AuthResponseDto.LoginSimpleInfo;
import com.example.nzgeneration.domain.auth.dto.AuthResponseDto.OIDCDecodePayload;
import com.example.nzgeneration.domain.auth.dto.AuthResponseDto.TokenRefreshSimpleInfo;
import com.example.nzgeneration.domain.user.User;
import com.example.nzgeneration.domain.user.UserRepository;
import com.example.nzgeneration.domain.user.UserService;
import com.example.nzgeneration.global.common.response.code.status.ErrorStatus;
import com.example.nzgeneration.global.common.response.exception.GeneralException;
import com.example.nzgeneration.global.security.JwtOIDCProvider;
import com.example.nzgeneration.global.security.JwtTokenProvider;
import java.util.HashMap;
import java.util.Map;
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
    private final UserService userService;

    @Transactional
    public LoginSimpleInfo login(String code) throws Exception {
        AppleTokenResponse appleTokenResponse = appleOauthHelper.getTokenRequest(code);
        String idToken = appleTokenResponse.id_token();
        OIDCDecodePayload oidcDecodePayload = appleOauthHelper.getOIDCDecodePayload(idToken);
        String email = oidcDecodePayload.email();
        String appleRefreshToken = appleTokenResponse.refresh_token();
        String payload = email+"+"+appleRefreshToken;
        Optional<User> optionalMember = userRepository.findByEmail(email);
        String accessToken, refreshToken;
        if(optionalMember.isPresent()){ //로그인 로직
            accessToken = jwtTokenProvider.createAccessToken(optionalMember.get().getPayload());
            refreshToken = jwtTokenProvider.createRefreshToken(optionalMember.get().getId());
            optionalMember.get().updateToken(accessToken, refreshToken);
            return LoginSimpleInfo.toDTO(accessToken, refreshToken, true);
        }
        accessToken = jwtTokenProvider.generateTempToken(payload);
        return LoginSimpleInfo.toDTO(accessToken, null, false);

    }

    @Transactional
    public LoginSimpleInfo signUp(CreateUserRequest createUserRequest){
        String payload = jwtTokenProvider.validateTempTokenAndGetPayload(createUserRequest.getToken());
        Map<String, String> userInfo = getUserInfoInPayload(payload);
        String email = userInfo.get("email");
        String appleRefreshToken = userInfo.get("appleRefreshToken");
        if(userRepository.findByEmail(email).isPresent())
            throw new GeneralException(ErrorStatus._DUPLICATE_USER);
        User user = User.toEntity(email, createUserRequest);
        userRepository.save(user);
        String accessToken = jwtTokenProvider.createAccessToken(user.getPayload());
        String refreshToken = jwtTokenProvider.createRefreshToken(user.getId());
        user.signUpToken(accessToken, refreshToken, appleRefreshToken);
        return LoginSimpleInfo.toDTO(accessToken, refreshToken, true);
    }

    public Map<String, String> getUserInfoInPayload (String payload){
        String[] parts = payload.split("\\+");
        Map<String, String> map = new HashMap<>();

        String email = parts[0];
        String appleRefreshToken = parts[1];

        map.put("email", email);
        map.put("appleRefreshToken", appleRefreshToken);

        return map;
    }

    @Transactional
    public TokenRefreshSimpleInfo updateUserToken(String refreshToken) {
        if(userRepository.findByRefreshToken(refreshToken).isEmpty()){
            throw new GeneralException(ErrorStatus._EXPIRED_JWT);
        }
        Long userId = Long.valueOf(jwtTokenProvider.getPayload(refreshToken));
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new GeneralException(ErrorStatus._INVALID_JWT));
        String newRefreshToken = jwtTokenProvider.createRefreshToken(userId);
        String newAccesstoken = jwtTokenProvider.refreshAccessToken(refreshToken);
        user.updateToken(newAccesstoken, newRefreshToken);
        return TokenRefreshSimpleInfo.toDTO(newAccesstoken, newRefreshToken);

    }

    @Transactional
    public boolean checkNickNameDuplicate(String name){
        if(userRepository.findByNickname(name).isPresent()){
            return false;
        }
        return true;
    }

    public void deleteAccount(User user) throws Exception {
        appleOauthHelper.revokeToken(user.getAppleRefreshToken());
        userService.deleteUserWithName(user.getNickname());
    }

//    public String getSecretKey() throws Exception {
//        return jwtOIDCProvider.createSecretKey();
//    }
}