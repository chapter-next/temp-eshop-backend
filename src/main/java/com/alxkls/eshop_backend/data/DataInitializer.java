package com.alxkls.eshop_backend.data;

import com.alxkls.eshop_backend.model.User;
import com.alxkls.eshop_backend.repository.user.UserRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements ApplicationListener<ApplicationReadyEvent> {
  private final UserRepository userRepository;
  List<String> emails = List.of("alex1@gmail.com", "alex2@gmail.com");

  @Override
  public void onApplicationEvent(ApplicationReadyEvent event) {
    initializeTestData();
  }

  private void initializeTestData() {

    for (String email : emails) {
      if (!userRepository.existsByEmail(email)) {
        User user = new User();
        user.setPassword("123456");
        user.setUsername("Alex");
        user.setEmail(email);
        userRepository.save(user);
      }
    }
  }
}
