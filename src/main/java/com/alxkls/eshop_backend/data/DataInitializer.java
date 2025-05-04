package com.alxkls.eshop_backend.data;

import com.alxkls.eshop_backend.model.Role;
import com.alxkls.eshop_backend.model.User;
import com.alxkls.eshop_backend.repository.role.RoleRepository;
import com.alxkls.eshop_backend.repository.user.UserRepository;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements ApplicationListener<ApplicationReadyEvent> {
  private final static List<String> EMAILS = List.of("alex1@gmail.com", "alex2@gmail.com");
  private final static List<String> ROLES = List.of("ROLE_ADMIN", "ROLE_USER");
  private final UserRepository userRepository;
  private final RoleRepository roleRepository;
  private final  PasswordEncoder passwordEncoder;

  @Override
  public void onApplicationEvent(ApplicationReadyEvent event) {
    initializeDefaultRolesIfNotPresent();
    initializeTestUsers();
    initializeDefaultAdminUsers();

  }

  private void initializeDefaultRolesIfNotPresent(){
    for(String role : ROLES){
      if(!roleRepository.existsByName(role)){
        roleRepository.save(new Role(role));
      }
    }
  }

  private void initializeTestUsers() {

    for (String email : EMAILS) {
      if (!userRepository.existsByEmail(email)) {
        User user = new User();
        user.setPassword(passwordEncoder.encode( "123456"));
        user.setUsername("Alex");
        user.setEmail(email);
        user.setRoles(Set.of(roleRepository.findByName("ROLE_USER")));
        userRepository.save(user);
      }
    }
  }

  private void initializeDefaultAdminUsers() {


    if (!userRepository.existsByEmail("admin@gmail.com")){

      User user = new User();
      user.setPassword(passwordEncoder.encode( "123456"));
      user.setUsername("Admin");
      user.setEmail("admin@gmail.com");
      user.setRoles(Set.of(roleRepository.findByName("ROLE_ADMIN")));
      userRepository.save(user);
    }

  }
}
