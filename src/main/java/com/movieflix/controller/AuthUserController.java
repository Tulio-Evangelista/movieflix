package com.movieflix.controller;


import com.movieflix.configuration.TokenService;
import com.movieflix.controller.request.LoginRequest;
import com.movieflix.controller.request.UserRequest;
import com.movieflix.controller.response.LoginResponse;
import com.movieflix.controller.response.UserResponse;
import com.movieflix.entity.user.User;
import com.movieflix.mapper.UserMapper;
import com.movieflix.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthUserController {


    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

        @PostMapping("/register")
        public ResponseEntity<UserResponse> criarUsuario(@RequestBody UserRequest userRequest) {
            User usuarioCriado = userService.criarUsuario(UserMapper.toUser(userRequest));
            return ResponseEntity.status(HttpStatus.CREATED).body(UserMapper.toUserResponse(usuarioCriado));

        }

        @PostMapping("/login")
        public  ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest){
            UsernamePasswordAuthenticationToken userAndPass =
                    new UsernamePasswordAuthenticationToken(loginRequest.email(), loginRequest.password());
            Authentication authenticate = authenticationManager.authenticate(userAndPass);

            User user = (User) authenticate.getPrincipal();
            String token = tokenService.gerarToken(user);
            return ResponseEntity.ok(new LoginResponse(token));

        }

}
