package com.ex.passagiram.service;


import com.ex.passagiram.model.User;
import com.ex.passagiram.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AuthService {

    private final UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Map<String, Object> register(User user) {
        if (user.getLogin() == null || !user.getLogin().matches("^[A-Za-z0-9]{6,}$")) {
            return Map.of(
                    "success", false,
                    "message", "Логин должен содержать латинские буквы и цифры, минимум 6 символов"
            );
        }

        if (user.getPassword() == null || user.getPassword().length() < 8) {
            return Map.of(
                    "success", false,
                    "message", "Пароль должен быть не менее 8 символов"
            );
        }

        if (user.getFullName() == null || user.getFullName().isBlank()) {
            return Map.of(
                    "success", false,
                    "message", "Введите ФИО"
            );
        }

        if (user.getPhone() == null || user.getPhone().isBlank()) {
            return Map.of(
                    "success", false,
                    "message", "Введите номер телефона"
            );
        }

        if (user.getEmail() == null || user.getEmail().isBlank()) {
            return Map.of(
                    "success", false,
                    "message", "Введите e-mail"
            );
        }

        if (userRepository.existsByLogin(user.getLogin())) {
            return Map.of(
                    "success", false,
                    "message", "Такой логин уже занят"
            );
        }

        User savedUser = userRepository.save(user);

        return Map.of(
                "success", true,
                "message", "Регистрация успешна",
                "userId", savedUser.getId()
        );
    }

    public Map<String, Object> login(String login, String password) {
        if ("Admin26".equals(login) && "Demo20".equals(password)) {
            return Map.of(
                    "success", true,
                    "role", "admin",
                    "message", "Вход администратора выполнен"
            );
        }

        return userRepository.findByLoginAndPassword(login, password)
                .<Map<String, Object>>map(user -> Map.of(
                        "success", true,
                        "role", "user",
                        "userId", user.getId(),
                        "message", "Вход выполнен"
                ))
                .orElse(Map.of(
                        "success", false,
                        "message", "Неверный логин или пароль"
                ));
    }
}