package org.arjunaoverdrive.newsapp.web.controller.user;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.arjunaoverdrive.newsapp.security.SecurityService;
import org.arjunaoverdrive.newsapp.web.dto.MessageResponse;
import org.arjunaoverdrive.newsapp.web.dto.auth.AuthResponse;
import org.arjunaoverdrive.newsapp.web.dto.auth.LoginRequest;
import org.arjunaoverdrive.newsapp.web.dto.auth.RefreshTokenRequest;
import org.arjunaoverdrive.newsapp.web.dto.auth.RefreshTokenResponse;
import org.arjunaoverdrive.newsapp.web.dto.user.UserRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.MessageFormat;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final SecurityService securityService;

    @PostMapping("/signin")
    public ResponseEntity<AuthResponse> authUser(@Valid @RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(securityService.authenticateUser(loginRequest));
    }

    @PostMapping("/signup")
    public ResponseEntity<MessageResponse> signup(@Valid @RequestBody UserRequest userRequest) {
        securityService.register(userRequest);
        return ResponseEntity.ok().body(new MessageResponse("User successfully created."));
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<RefreshTokenResponse> refreshToken(@Valid @RequestBody RefreshTokenRequest request) {
        return ResponseEntity.ok(securityService.refreshToken(request));
    }

    @PostMapping("/logout")
    public ResponseEntity<MessageResponse> logout(@AuthenticationPrincipal UserDetails userDetails) {
        securityService.logout();
        return ResponseEntity.ok().body(
                new MessageResponse(
                        MessageFormat.format("User {0} logged out.", userDetails.getUsername())
                )
        );
    }
}
