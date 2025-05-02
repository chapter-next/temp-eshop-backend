package com.alxkls.eshop_backend.security.user;

import com.alxkls.eshop_backend.model.User;
import com.alxkls.eshop_backend.repository.user.UserRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ShopUserDetailsService implements UserDetailsService {
  private final UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user =
        Optional.ofNullable(userRepository.findByEmail(username))
            .orElseThrow(() -> new UsernameNotFoundException("'" + username + "' not found!"));
    return ShopUserDetails.createShopUserDetails(user);
  }
}
