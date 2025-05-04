package com.alxkls.eshop_backend.controller;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;

import com.alxkls.eshop_backend.requests.AuthNRequest;
import com.alxkls.eshop_backend.response.AuthNResponse;
import com.alxkls.eshop_backend.security.user.ShopUserDetails;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.RSAKeyProvider;
import jakarta.validation.Valid;

import java.security.interfaces.RSAPrivateKey;
import java.security.spec.RSAPrivateKeySpec;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.encrypt.RsaAlgorithm;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}")
public class AuthNController {
  private final AuthenticationManager authenticationManager;
  @Value("${auth.secret}")
  private String secret;

  public static String generateToken(Authentication authentication) {
    ShopUserDetails userDetails = (ShopUserDetails) authentication.getPrincipal();
    List<String> roles =
        userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();

    return JWT.create()
        .withSubject(userDetails.getEmail())
        .withClaim("id", userDetails.getId())
        .withClaim("roles", roles)
        .withExpiresAt(Instant.now().plus(15, ChronoUnit.MINUTES))
        .withIssuedAt(Instant.now())
        .sign(Algorithm.HMAC256("secret"));
  }

  @PostMapping("/authenticate")
  public ResponseEntity<AuthNResponse> authenticate(@Valid @RequestBody AuthNRequest authNRequest) {
    try {
      Authentication authentication =
          authenticationManager.authenticate(
              new UsernamePasswordAuthenticationToken(
                  authNRequest.getEmail(), authNRequest.getPassword()));
      SecurityContextHolder.getContext().setAuthentication(authentication);

      String jwt = generateToken(authentication);
      ShopUserDetails userDetails = (ShopUserDetails) authentication.getPrincipal();
      AuthNResponse response = new AuthNResponse(userDetails.getId(), jwt);

      return ResponseEntity.ok().body(response);
    } catch (AuthenticationException e) {
      return ResponseEntity.status(UNAUTHORIZED).build();
    }
  }
}
