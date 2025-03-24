package site.easy.to.build.crm.webservice;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.easy.to.build.crm.config.CrmUserDetails;
import site.easy.to.build.crm.config.api.JWTUtil;
import site.easy.to.build.crm.entity.User;
import site.easy.to.build.crm.service.user.OAuthUserService;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class AuthController {

    private final CrmUserDetails crmUserDetails;
    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String googleClientId;

    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;
    private final OAuthUserService oAuthUserService;

    public AuthController(AuthenticationManager authenticationManager, JWTUtil jwtUtil, OAuthUserService oAuthUserService, CrmUserDetails crmUserDetails) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.oAuthUserService = oAuthUserService;
        this.crmUserDetails = crmUserDetails;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String token = jwtUtil.generateToken(userDetails);
            return ResponseEntity.ok(new AuthResponse(token));
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Invalid username or password"));
        }
        catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }

    @PostMapping("/google-login")
    public ResponseEntity<?> googleLogin(@RequestBody GoogleLoginRequest request) {
        System.out.println(request);
        try {
            // Create a verifier for Google ID tokens
            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(
                    new NetHttpTransport(), new GsonFactory())
                    .setAudience(Collections.singletonList(googleClientId))
                    .build();

            // Verify the token
            GoogleIdToken idToken = verifier.verify(request.getIdToken());
            if (idToken == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Map.of("error", "Invalid Google token"));
            }

            // Get user information from the token
            GoogleIdToken.Payload payload = idToken.getPayload();
            String email = payload.getEmail();

            // Process the Google user (find or create in your system)
            User user = oAuthUserService.processGoogleUser(email, payload);

            if (user == null) {
                throw new BadCredentialsException("Invalid username or password");
            }

            // Generate JWT token for API access
            UserDetails userDetails = crmUserDetails.loadUserByUsername(user.getUsername());
            String token = jwtUtil.generateToken(userDetails);

            return ResponseEntity.ok(new AuthResponse(token));
        } catch (GeneralSecurityException | IOException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Failed to verify Google token: " + e.getMessage()));
        }
    }
}

@Data
class AuthRequest {
    private String username;
    private String password;
}

@Data
class GoogleLoginRequest {
    private String idToken;
}

class AuthResponse {
    private String token;
    public AuthResponse(String token) {
        this.token = token;
    }
    public String getToken() {
        return token;
    }
}