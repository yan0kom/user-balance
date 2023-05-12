package yan0kom.userbal.api.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.RestController;
import yan0kom.userbal.api.TokenApi;

import java.time.Instant;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
@RestController
public class TokenApiImpl implements TokenApi {
    private final JwtEncoder encoder;

    @Override
    public String token(Authentication authentication) {
        log.debug("User {} creates access token", authentication.getName());
        Instant now = Instant.now();
        long expiry = 36000L;

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(now.plusSeconds(expiry))
                .subject(authentication.getName())
                .build();

        return this.encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }
}
