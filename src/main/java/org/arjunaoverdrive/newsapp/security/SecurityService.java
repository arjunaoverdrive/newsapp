package org.arjunaoverdrive.newsapp.security;

import lombok.RequiredArgsConstructor;
import org.arjunaoverdrive.newsapp.dao.UserRepository;
import org.arjunaoverdrive.newsapp.exception.RefreshTokenException;
import org.arjunaoverdrive.newsapp.model.AppUser;
import org.arjunaoverdrive.newsapp.model.RefreshToken;
import org.arjunaoverdrive.newsapp.security.jwt.JwtUtils;
import org.arjunaoverdrive.newsapp.web.dto.auth.AuthResponse;
import org.arjunaoverdrive.newsapp.web.dto.auth.LoginRequest;
import org.arjunaoverdrive.newsapp.web.dto.auth.RefreshTokenRequest;
import org.arjunaoverdrive.newsapp.web.dto.auth.RefreshTokenResponse;
import org.arjunaoverdrive.newsapp.web.dto.user.UserRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SecurityService  {

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final RefreshTokenService refreshTokenService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthResponse authenticateUser(LoginRequest loginRequest){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginRequest.getEmail(), loginRequest.getPassword()
        ));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        AppUserDetails userDetails = (AppUserDetails) authentication.getPrincipal();

        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        RefreshToken token = refreshTokenService.createRefreshToken(userDetails.getId());

        return AuthResponse.builder()
                .id(userDetails.getId())
                .token(jwtUtils.generateJwtToken(userDetails))
                .refreshToken(token.getToken())
                .email(userDetails.getUsername())
                .roles(roles)
                .build();
    }

    public void register (UserRequest userRequest){
        var user = AppUser.builder()
                .firstName(userRequest.getFirstName())
                .lastName(userRequest.getLastName())
                .email(userRequest.getEmail())
                .password(passwordEncoder.encode(userRequest.getPassword()))
                .build();
        user.setRoles(userRequest.getRoles());
        userRepository.save(user);
    }

    public RefreshTokenResponse refreshToken(RefreshTokenRequest request){
        String requestRefreshToken = request.getRefreshToken();

        return refreshTokenService.findByRefreshToken(requestRefreshToken)
                .map(refreshTokenService::checkRefreshToken)
                .map(RefreshToken::getUserId)
                .map(userId -> {
                    AppUser tokenOwner = userRepository.findById(userId).orElseThrow(() ->
                            new RefreshTokenException(
                                    MessageFormat.format("Exception trying to get token for userId: {0}", userId)
                            ));
                    String token = jwtUtils.generateTokenFromUsername(tokenOwner.getEmail());
                    return new RefreshTokenResponse(token, refreshTokenService.createRefreshToken(userId).getToken());
                })
                .orElseThrow(() -> new RefreshTokenException(
                        requestRefreshToken, "Refresh token not found!"
                ));
    }

    public void logout(){
        var currentPrincipal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(currentPrincipal instanceof AppUserDetails appUserDetails){
            Long userId = appUserDetails.getId();
            refreshTokenService.deleteByUserId(userId);
        }
    }
}
