package com.alxkls.eshop_backend.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@RequiredArgsConstructor
public class AuthTokenFilter extends OncePerRequestFilter {
  private final UserDetailsService userDetailsService;

  @Override
  protected void doFilterInternal(
      @NonNull HttpServletRequest request,
      @NonNull HttpServletResponse response,
      @NonNull FilterChain filterChain)
      throws ServletException, IOException {

    try {
      String username = getSubFromJwt(getJwt(request));
      UserDetails userDetails = userDetailsService.loadUserByUsername(username);
      Authentication authN =
          new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
      SecurityContextHolder.getContext().setAuthentication(authN);
    } catch (JWTDecodeException | UsernameNotFoundException e) {
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      //response.getWriter().write(e.getMessage());
    } catch (Exception e) {
      // TODO: Log statement
      response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }
    filterChain.doFilter(request, response);
  }

  private String getJwt(HttpServletRequest request) throws JWTDecodeException {
    return Optional.ofNullable(request.getHeader("Authorization"))
        .filter(header -> StringUtils.hasText(header) && header.startsWith("Bearer"))
        .map(header -> header.substring(7))
        .filter(
            jwt -> {
              try {
                JWT.decode(jwt);
                return true;
              } catch (JWTDecodeException e) {
                return false;
              }
            })
        .orElseThrow(
            () ->
                new JWTDecodeException(
                    "'Authorization' header is absent or does not contain JWT!"));
  }

  private String getSubFromJwt(@NonNull String jwt) {
    return JWT.decode(jwt).getSubject();
  }
}
