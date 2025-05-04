package com.alxkls.eshop_backend.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import com.alxkls.eshop_backend.security.user.ShopUserDetails;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

@ExtendWith(MockitoExtension.class)
class AuthNControllerTest {
  @Mock private Authentication authentication;
  @Mock private ShopUserDetails userDetails;


  @Test
  public void testJwtGeneration() {
    when(authentication.getPrincipal()).thenReturn(userDetails);
    when(userDetails.getId()).thenReturn(1L);
    when(userDetails.getEmail()).thenReturn("alex1@gmail.com");
    when(userDetails.getAuthorities()).thenReturn(Collections.emptySet());
    String jwt =
    AuthNController.generateToken(authentication);

    System.out.println(jwt);
    assertTrue(Objects.nonNull(jwt));

  }
}
