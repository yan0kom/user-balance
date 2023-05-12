package yan0kom.userbal.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;

@Tag(name = "JWT token API", description = "JWT token related operations")
public interface TokenApi {
    @PostMapping("/token")
    @Operation(summary = "Generate user token, access with HTTP basic auth")
    String token(Authentication authentication);
}
