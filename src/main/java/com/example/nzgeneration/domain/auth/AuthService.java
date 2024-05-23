package com.example.nzgeneration.domain.auth;

import com.example.nzgeneration.domain.auth.dto.AuthRequestDto.CreateUserRequest;
import com.example.nzgeneration.domain.auth.dto.AuthResponseDto.LoginSimpleInfo;
import com.example.nzgeneration.domain.auth.dto.AuthResponseDto.OIDCDecodePayload;
import com.example.nzgeneration.domain.auth.enums.ResponseType;
import com.example.nzgeneration.domain.user.User;
import com.example.nzgeneration.domain.user.UserRepository;
import com.example.nzgeneration.global.common.response.ApiResponse;
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
    public LoginSimpleInfo signUp(String token, CreateUserRequest createUserRequest){
        String email = jwtTokenProvider.validateTempTokenAndGetEmail(token);
        User user = User.toEntity(createUserRequest.getNickName(), email,
            createUserRequest.getProfileImgUrl(), createUserRequest.getWalletAddress(),
            createUserRequest.isAllowAdInfo(), createUserRequest.isAllowLocationInfo());
        userRepository.save(user);
        String accessToken = jwtTokenProvider.createAccessToken(user.getPayload());
        String refreshToken = jwtTokenProvider.createRefreshToken(user.getId());
        return LoginSimpleInfo.toDTO(accessToken, refreshToken, ResponseType.SIGN_IN);
    }


}
