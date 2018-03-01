package org.rta.security.utills;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.rta.security.model.CustomUserDetail;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenUtil implements Serializable {

    private static final long serialVersionUID = -3787799406661972082L;
    private static final String CLAIM_KEY_USERNAME = "sub";
    private static final String CLAIM_KEY_SCOPE = "scope";
    private static final String CLAIM_KEY_JTI = "jti";
    private static final String CLAIM_KEY_CREATED = "created";
    private static final String CLAIM_LIST_DELIMITERS = ",";

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.claim.secret}")
    private String claimSecret;

    @Value("${jwt.expiration}")
    private Long expiration;

    public String getUsernameFromToken(String token) {
        String username;
        try {
            final Claims claims = getClaimsFromToken(token);
            username = RtaCryptoUtil.parseSecureToken(claims.getSubject(), claimSecret);
        } catch (Exception e) {
            username = null;
        }
        return username;
    }

    public Date getCreatedDateFromToken(String token) {
        Date created;
        try {
            final Claims claims = getClaimsFromToken(token);
            created = new Date((Long) claims.get(CLAIM_KEY_CREATED));
        } catch (Exception e) {
            created = null;
        }
        return created;
    }

    public Date getExpirationDateFromToken(String token) {
        Date expiration;
        try {
            final Claims claims = getClaimsFromToken(token);
            expiration = claims.getExpiration();
        } catch (Exception e) {
            expiration = null;
        }
        return expiration;
    }

    public Long getUserIdFromToken(String token) {
        Long userId = null;
        try {
            final Claims claims = getClaimsFromToken(token);
            String userIdStr = RtaCryptoUtil.parseSecureToken(claims.getId(), claimSecret);
            if (!StringUtils.isEmpty(userIdStr)) {
                userId = Long.parseLong(userIdStr);
            }
        } catch (Exception e) {
        }
        return userId;
    }

    public List<String> getUserRoleFromToken(String token) {
        List<String> roles;
        try {
            final Claims claims = getClaimsFromToken(token);
            String roleStr = RtaCryptoUtil.parseSecureToken((String) claims.get(CLAIM_KEY_SCOPE), claimSecret);
            roles = Arrays.asList(roleStr.split(CLAIM_LIST_DELIMITERS));
        } catch (Exception e) {
            roles = null;
        }
        return roles;
    }

    private Claims getClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        } catch (Exception e) {
            claims = null;
        }
        return claims;
    }

    private Date generateExpirationDate() {
        return new Date(System.currentTimeMillis() + expiration * 1000);
    }

    public Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public String generateToken(CustomUserDetail userDetails) {
        Map<String, Object> claims = new HashMap<>();

        claims.put(CLAIM_KEY_JTI, RtaCryptoUtil.generateSecureToken(String.valueOf(userDetails.getId()), claimSecret));
        claims.put(CLAIM_KEY_USERNAME, RtaCryptoUtil.generateSecureToken(userDetails.getUsername(), claimSecret));
        String roles = userDetails.getUserRoles().stream().map(i -> i.toString())
                .collect(Collectors.joining(CLAIM_LIST_DELIMITERS));
        claims.put(CLAIM_KEY_SCOPE, RtaCryptoUtil.generateSecureToken(roles, claimSecret));
        claims.put(CLAIM_KEY_CREATED, new Date());

        return generateToken(claims);
    }

    private String generateToken(Map<String, Object> claims) {
        return Jwts.builder().setClaims(claims).setExpiration(generateExpirationDate())
                .signWith(SignatureAlgorithm.HS512, secret).compact();
    }

    public String refreshToken(String token) {
        String refreshedToken;
        try {
            final Claims claims = getClaimsFromToken(token);
            claims.put(CLAIM_KEY_CREATED, new Date());
            refreshedToken = generateToken(claims);
        } catch (Exception e) {
            refreshedToken = null;
        }
        return refreshedToken;
    }

    public Boolean validateToken(String token, CustomUserDetail userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
