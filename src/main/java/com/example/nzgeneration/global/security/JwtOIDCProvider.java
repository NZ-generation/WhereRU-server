package com.example.nzgeneration.global.security;

import com.example.nzgeneration.global.common.response.code.status.ErrorStatus;
import com.example.nzgeneration.global.common.response.exception.GeneralException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.math.BigInteger;
import java.security.Key;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Base64;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import com.example.nzgeneration.domain.auth.dto.AuthResponseDto.OIDCDecodePayload;
import org.springframework.beans.factory.annotation.Value;

@Component
@Configuration
@RequiredArgsConstructor
@Service
@Slf4j
public class JwtOIDCProvider {

    @Value("${social-login.provider.apple.key-id}")
    private String keyId;

    @Value("${social-login.provider.apple.team-id}")
    private String teamId;

    @Value("${social-login.provider.apple.issuer}")
    private String issuer;

    @Value("${social-login.provider.apple.client-id}")
    private String clientId;

    @Value("${social-login.provider.apple.private-key}")
    private String privateKey;


    //idToken의 header와 payload 받아옴
    private String getUnsignedToken(String token){
        String[] splitToken = token.split("\\.");
        if(splitToken.length!=3)
            throw new GeneralException(ErrorStatus._INVALID_JWT);
        return splitToken[0] + "." + splitToken[1] + ".";
    }


    //인증되지 않은 IdToken에서 payload 받아오는 로직
    private Jwt<Header, Claims> getTokenClaims(String token, String iss, String aud) {
        try {
            return Jwts.parserBuilder()
                .requireAudience(aud)
                .requireIssuer(iss)
                .build()
                .parseClaimsJwt(getUnsignedToken(token));
        } catch (ExpiredJwtException e) { //파싱하면서 만료된 토큰인지 확인.
            throw new GeneralException(ErrorStatus._EXPIRED_JWT);
        } catch (Exception e) {
            log.error(e.toString());
            throw new GeneralException(ErrorStatus._INVALID_JWT);
        }
    }

    private final String KID = "kid";
    public String getKidFromUnsignedTokenHeader(String token, String iss, String aud){
        return (String) getTokenClaims(token, iss, aud).getHeader().get(KID);
    }

    //공개키로 토큰 검증
    public Jws<Claims> getOIDCTokenJws(String token, String modulus, String exponent){
        try{
            return Jwts.parserBuilder()
                .setSigningKey(getRSAPublicKey(modulus, exponent))
                .build()
                .parseClaimsJws(token);
        } catch (ExpiredJwtException e){
            throw new GeneralException(ErrorStatus._EXPIRED_JWT);
        } catch (Exception e){
            log.error(e.toString());
            throw new GeneralException(ErrorStatus._INVALID_JWT);
        }
    }

    //OIDCDecodePayload를 가져옴. OIDC 스펙 -> 공통 사용
    public OIDCDecodePayload getOIDCTokenBody(String token, String modulus, String exponent){
        Claims body = getOIDCTokenJws(token, modulus, exponent).getBody();
        return new OIDCDecodePayload(
            body.getIssuer(),
            body.getAudience(),
            body.getSubject(),
            body.get("email", String.class)
        );
    }

    //n, e 값으로 RSA 퍼블릭 키 연산
    private Key getRSAPublicKey(String modulus, String exponent)
        throws NoSuchAlgorithmException, InvalidKeySpecException {
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        byte[] decodeN = Base64.getUrlDecoder().decode(modulus);
        byte[] decodeE = Base64.getUrlDecoder().decode(exponent);
        BigInteger n = new BigInteger(1, decodeN);
        BigInteger e = new BigInteger(1, decodeE);

        RSAPublicKeySpec keySpec = new RSAPublicKeySpec(n, e);
        return keyFactory.generatePublic(keySpec);
    }
    
    //apple Secret Key 생성 함수
    public String createSecretKey() throws Exception {
        Date expirationDate = Date.from(LocalDateTime.now().plusDays(30).atZone(
            ZoneId.systemDefault()).toInstant());
        Date issuedAtDate = new Date(System.currentTimeMillis());

        byte[] encoded = Base64.getDecoder().decode(privateKey);
        KeyFactory keyFactory = KeyFactory.getInstance("EC");
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(encoded);
        PrivateKey newPrivateKey = keyFactory.generatePrivate(keySpec);
        return Jwts.builder()
            .setHeaderParam("alg", "ES256")
            .setHeaderParam("kid", keyId)
            .setIssuer(teamId)
            .setIssuedAt(issuedAtDate)
            .setExpiration(expirationDate)
            .setAudience(issuer)
            .setSubject(clientId)
            .signWith(SignatureAlgorithm.ES256, newPrivateKey)
            .compact();
    }

}
