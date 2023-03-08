package com.pi.kursy.security.jwt;

import com.pi.kursy.security.shared.RoleEnum;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.security.Key;
import java.util.Date;
import java.util.Objects;

class JwtTokenImpl implements JwtToken {

    private static final Key JWT_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS512);

    private final Configuration configuration;

    @Getter
    private final JwtData jwtData;
    @Getter
    private final String encodedToken;
    @Getter
    private final boolean isValid;
    @Getter
    private boolean isExpired;
    @Getter
    private boolean isRefreshExpired;



    JwtTokenImpl(Configuration configuration, JwtData jwtData) {
        Objects.requireNonNull(configuration);
        this.configuration = configuration;
        this.jwtData = jwtData;
        this.encodedToken = encodeJwt(jwtData);
        this.isValid = true;
        this.isExpired = false;
        this.isRefreshExpired = false;
    }

    JwtTokenImpl(Configuration configuration, String encodedToken) {
        Objects.requireNonNull(configuration);
        this.configuration = configuration;
        this.encodedToken = encodedToken;
        this.jwtData = decodeJwt(encodedToken);
        this.isValid = jwtData != null;
    }

    private String encodeJwt(JwtData jwtData) {
        var now = new Date();
        var expiryDate = new Date(now.getTime() + configuration.getExpirationTime());
        var refreshExpiryDate = new Date(expiryDate.getTime() + configuration.getExpirationTime());

        return Jwts.builder()
                .setSubject(jwtData.username())
                .claim(ClaimsEnum.USER_ID.getName(), jwtData.id())
                .claim(ClaimsEnum.ROLE.getName(), jwtData.role())
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .claim(ClaimsEnum.REFRESH_EXPIRATION_DATE.getName(), refreshExpiryDate)
                .signWith(JWT_KEY).compact();
    }

    private JwtData decodeJwt(String encodedToken) {
        try {
            var claims = Jwts.parserBuilder()
                    .setSigningKey(JWT_KEY)
                    .build()
                    .parseClaimsJws(encodedToken).getBody();
            this.isExpired = false;
            this.isRefreshExpired = false;
            return getDataFromClaims(claims);
        } catch (ExpiredJwtException e) {
            this.isExpired = true;
            return parseForRefresh(e);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private JwtData parseForRefresh(ExpiredJwtException e) {
        var claims = e.getClaims();
        var refreshExpiration = claims.get(ClaimsEnum.REFRESH_EXPIRATION_DATE.getName(), Date.class);
        this.isRefreshExpired = refreshExpiration.before(new Date());
        return getDataFromClaims(claims);
    }

    private JwtData getDataFromClaims(Claims claims) {
        return JwtData.builder()
                .id(claims.get(ClaimsEnum.USER_ID.getName(), String.class))
                .username(claims.getSubject())
                .role(claims.get(ClaimsEnum.ROLE.getName(), RoleEnum.class)).build();
    }

    interface Configuration {
        long getExpirationTime();
        long getRefreshExpirationTime();
    }

    @RequiredArgsConstructor
    @Getter
    private enum ClaimsEnum {
        ROLE("role"),
        REFRESH_EXPIRATION_DATE("refreshExpDate"),
        USER_ID("subId");

        private final String name;

    }
}
