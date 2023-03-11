package com.pi.kursy.security.jwt;

import com.pi.kursy.security.shared.RoleEnum;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.security.Key;
import java.util.Date;
import java.util.Objects;

@Slf4j
class JwtTokenImpl implements JwtToken {

    private static final Key JWT_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS512);

    private final Configuration configuration;
    private final InvalidTokenCache invalidTokenCache;

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

    private Date refreshExpirationDate;



    JwtTokenImpl(InvalidTokenCache invalidTokenCache, Configuration configuration, JwtData jwtData) {
        Objects.requireNonNull(configuration);
        Objects.requireNonNull(invalidTokenCache);
        this.configuration = configuration;
        this.invalidTokenCache = invalidTokenCache;
        this.jwtData = jwtData;
        this.encodedToken = encodeJwt(jwtData);
        this.isValid = true;
        this.isExpired = false;
        this.isRefreshExpired = false;
    }

    JwtTokenImpl(InvalidTokenCache invalidTokenCache, Configuration configuration, String encodedToken) {
        Objects.requireNonNull(configuration);
        Objects.requireNonNull(invalidTokenCache);
        this.configuration = configuration;
        this.invalidTokenCache = invalidTokenCache;
        this.encodedToken = encodedToken;
        this.jwtData = decodeJwt();
        this.isValid = determineTokenValidity();
    }

    private String encodeJwt(JwtData jwtData) {
        var now = new Date();
        var expirationTime = configuration.getExpirationTime() * 60 * 1000;
        var refreshExpirationTime = configuration.getRefreshExpirationTime() * 60 * 1000;
        var expiryDate = new Date(now.getTime() + configuration.getExpirationTime() + expirationTime);
        refreshExpirationDate = new Date(expiryDate.getTime() + refreshExpirationTime);
        return Jwts.builder()
                .setSubject(jwtData.username())
                .claim(ClaimsEnum.USER_ID.getName(), jwtData.id())
                .claim(ClaimsEnum.ROLE.getName(), jwtData.role())
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .claim(ClaimsEnum.REFRESH_EXPIRATION_DATE.getName(), refreshExpirationDate.getTime())
                .signWith(JWT_KEY).compact();
    }

    private JwtData decodeJwt() {
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
        } catch (SignatureException e) {
            log.warn("Tried do decode jwt token with invalid signature");
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
        this.refreshExpirationDate = claims.get(ClaimsEnum.REFRESH_EXPIRATION_DATE.getName(), Date.class);
        return JwtData.builder()
                .id(claims.get(ClaimsEnum.USER_ID.getName(), String.class))
                .username(claims.getSubject())
                .role(RoleEnum.valueOf(claims.get(ClaimsEnum.ROLE.getName(), String.class))).build();
    }

    private boolean determineTokenValidity() {
        if (jwtData == null) {
            return false;
        }
        return !invalidTokenCache.containsToken(encodedToken);
    }

    interface Configuration {
        long getExpirationTime();
        long getRefreshExpirationTime();
    }

    @Override
    public void invalidateToken() {
        if (!this.isValid) {
            return;
        }
        invalidTokenCache.addToken(this.encodedToken, this.refreshExpirationDate);
    }

    @Override
    public JwtToken refreshToken() {
        return new JwtTokenImpl(invalidTokenCache, configuration, jwtData);
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
