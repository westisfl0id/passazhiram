package com.ex.passagiram.controller;

import com.ex.passagiram.model.User;
import com.ex.passagiram.repository.UserRepository;
import com.ex.passagiram.service.AuthService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public Map<String, Object> register(@RequestBody User user) {
        return authService.register(user);
    }

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody Map<String, String> body) {
        String login = body.get("login");
        String password = body.get("password");

        return authService.login(login, password);
    }
//    private final UserRepository userRepository;
//
//    public AuthController(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    }
//
//    @PostMapping("/register")
//    public Map<String, Object> register(@RequestBody User user) {
//        if (user.getLogin() == null || !user.getLogin().matches("^[A-Za-z0-9]{6,}$")) {
//            return Map.of("success", false, "message", "Логин должен содержать латинские буквы и цифры, минимум 6 символов");
//        }
//
//        if (user.getPassword() == null || user.getPassword().length() < 8) {
//            return Map.of("success", false, "message", "Пароль должен быть не менее 8 символов");
//        }
//
//        if (userRepository.existsByLogin(user.getLogin())) {
//            return Map.of("success", false, "message", "Такой логин уже занят");
//        }
//
//        User savedUser = userRepository.save(user);
//
//        return Map.of(
//                "success", true,
//                "message", "Регистрация успешна",
//                "userId", savedUser.getId()
//        );
//    }
//
//    @PostMapping("/login")
//    public Map<String, Object> login(@RequestBody Map<String, String> body) {
//        String login = body.get("login");
//        String password = body.get("password");
//
//        if ("Admin26".equals(login) && "Demo20".equals(password)) {
//            return Map.of(
//                    "success", true,
//                    "role", "admin",
//                    "message", "Вход администратора"
//            );
//        }
//
//        return userRepository.findByLoginAndPassword(login, password)
//                .<Map<String, Object>>map(user -> Map.of(
//                        "success", true,
//                        "role", "user",
//                        "userId", user.getId(),
//                        "message", "Вход выполнен"
//                ))
//                .orElse(Map.of(
//                        "success", false,
//                        "message", "Неверный логин или пароль"
//                ));
//    }
}
