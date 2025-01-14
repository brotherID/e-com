package com.management.product.web.authentication;

import com.management.product.dtos.authentication.AuthenticationRequest;
import com.management.product.dtos.authentication.AuthenticationResponse;
import com.management.product.config.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zalando.problem.Problem;

import static org.zalando.problem.Status.UNAUTHORIZED;

@RestController
@RequestMapping("/api/authentication")
@Slf4j
@RequiredArgsConstructor
public class AuthenticationControllerImpl implements AuthenticationController {
    public static final String INCORRECT_USERNAME_PASSWORD = "Incorrect username or password ";
    public static final String ERROR_CREDENTIALS = "Error credentials";
    private  final AuthenticationManager authenticationManager;
    private  final JwtTokenProvider jwtUtil;
    public ResponseEntity<AuthenticationResponse> getToken( AuthenticationRequest authenticationRequest) {
        log.info("Begin getToken : {}", authenticationRequest.toString());
        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(), authenticationRequest.getPassword())
            );
        } catch (BadCredentialsException e) {
            throw Problem.builder()
                    .withTitle(ERROR_CREDENTIALS)
                    .withStatus(UNAUTHORIZED)
                    .withDetail(INCORRECT_USERNAME_PASSWORD +e.getMessage())
                    .build();
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtUtil.generateToken(authentication);
        log.info("End getToken ");
        return ResponseEntity.ok(new AuthenticationResponse(token));
    }
}
