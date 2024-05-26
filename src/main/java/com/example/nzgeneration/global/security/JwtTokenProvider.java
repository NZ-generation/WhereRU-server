package com.example.nzgeneration.global.security;

import com.example.nzgeneration.domain.user.User;
import com.example.nzgeneration.domain.user.UserRepository;
import com.example.nzgeneration.global.common.response.code.status.ErrorStatus;
import com.example.nzgeneration.global.common.response.exception.GeneralException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.util.Base64;
import java.util.Date;
import javax.crypto.SecretKey;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtTokenProvider {

    @Value("${jwt.access-token.expire-length}")
    private long accessTokenValidityInMilliseconds;

    @Value("${jwt.refresh-token.expire-length}")
    private long refreshTokenValidityInMillseconds;

    @Value("${jwt.custom.secretKey}")
    private String SECRET_KEY;

    private SecretKey cachedSecretKey;
    private final UserRepository userRepository;

    private SecretKey _getSecretKey() {
        return Keys.hmacShaKeyFor(Base64.getDecoder().decode(SECRET_KEY));
    }

    public SecretKey getSecretKey() {
        if (cachedSecretKey == null)
            cachedSecretKey = _getSecretKey();

        return cachedSecretKey;
    }

    public String createAccessToken(String payload) {
        return createToken(payload, accessTokenValidityInMilliseconds);
    }

    public String createRefreshToken(Long climberId) {
        return createToken(climberId.toString(), refreshTokenValidityInMillseconds);
    }

    public String refreshAccessToken(String refreshToken) {
        if (validateToken(refreshToken)) {
            Long userId = Long.parseLong(getPayload(refreshToken));
            User user = userRepository.findById(userId)
                .orElseThrow(() -> new GeneralException(ErrorStatus._INVALID_USER));
            String payload = user.getPayload();
            return createAccessToken(payload);
        } else {
            throw new GeneralException(ErrorStatus._INVALID_JWT);
        }
    }

    public String createToken(String payload, long expireLength) {
        Claims claims = Jwts.claims().setSubject(payload);
        Date now = new Date();
        long nowMillis = now.getTime();
        long expireMillis = nowMillis + (expireLength * 1000);
        Date validity = new Date(expireMillis);
        return Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(now)
            .setExpiration(validity)
            .signWith(SignatureAlgorithm.HS256, getSecretKey())
            .compact();
    }

    public String getPayload(String token) {
        try {
            return Jwts.parser()
                .setSigningKey(getSecretKey())
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
        } catch (ExpiredJwtException e) {
            return e.getClaims().getSubject();
        } catch (JwtException e) {
            log.error(e.toString());
            throw new GeneralException(ErrorStatus._INVALID_JWT);
        }
    }

    public boolean validateToken(String token) {
        try {
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(getSecretKey()).parseClaimsJws(token);
            if (claimsJws.getBody().getExpiration().before(new Date())) {
                throw new GeneralException(ErrorStatus._EXPIRED_JWT);
            }
            return true;
        } catch (ExpiredJwtException e) {
            log.error(e.toString());
            throw new GeneralException(ErrorStatus._EXPIRED_JWT);
        } catch (JwtException | IllegalArgumentException exception) {
            throw new GeneralException(ErrorStatus._INVALID_JWT);
        }
    }

    public String generateTempToken(String email) {
        long now = System.currentTimeMillis();
        return Jwts.builder()
            .setSubject(email)
            .setIssuedAt(new Date(now))
            .setExpiration(new Date(now + 1000 * 60 * 60))
            .signWith(SignatureAlgorithm.HS256, getSecretKey())
            .compact();
    }

    public String validateTempTokenAndGetEmail(String token) {
        try {
            return Jwts.parser()
                .setSigningKey(getSecretKey())
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
        } catch (ExpiredJwtException e) {
            throw new GeneralException(ErrorStatus._EXPIRED_JWT);
        } catch (Exception e) {
            throw new GeneralException(ErrorStatus._BAD_REQUEST);
        }
    }
}
