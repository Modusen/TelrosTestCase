package com.vaadin.TelrosTestCase.service;


import com.vaadin.TelrosTestCase.model.User;
import com.vaadin.TelrosTestCase.repository.UserRepository;
import lombok.AllArgsConstructor;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    public boolean login(String login, String password) {
        User user1 = userRepository.findByLogin(login).orElse(null);
        System.out.println(user1.getPassword());
        return userRepository.findByLogin(login)
                .map(user -> (password.equals(user.getPassword())))
                .orElse(false);
    }

}
