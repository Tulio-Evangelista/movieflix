package com.movieflix.service;


import com.movieflix.entity.user.User;
import com.movieflix.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public User criarUsuario(User user) {
        String password = user.getPassword();
        user.setPassword(passwordEncoder.encode(password));
        return userRepository.save(user);


    }


}
