package ru.skypro.homework.service.impl;


import ru.skypro.homework.service.inter.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.Register;
import ru.skypro.homework.dto.Role;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.mapper.UserMapper;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.inter.AuthService;

import java.util.Optional;

import static ru.skypro.homework.dto.Role.USER;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl  implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final UserMapper userMapper;

    /**
     * Проверяет аутентификацию пользователя.
     * Использует методы:
     * {@link UserRepository#findByEmail(String)},
     * {@link PasswordEncoder#matches(CharSequence, String)}.
     *
     * @param userName Имя пользователя (адрес электронной почты).
     * @param password Пароль пользователя.
     * @return true, если пользователь аутентифицирован успешно, иначе false.
     */
    @Override
    public boolean login(String userName, String password) {
        Optional<User> optionalUser = userRepository.findByEmail(userName);
        if (optionalUser.isEmpty()) {
            log.debug("User not found");
            return false;
        }
        return encoder.matches(password, optionalUser.get().getPassword());
    }

    /**
     * Регистрирует нового пользователя.
     * Использует методы:
     * {@link UserRepository#findByEmail(String)},
     * {@link UserMapper#toUser(Register)},
     * {@link PasswordEncoder#encode(CharSequence)},
     * {@link UserRepository#save(Object)}.
     *
     * @param register Объект Register с данными для регистрации нового пользователя.
     * @return true, если регистрация прошла успешно, иначе false (если пользователь с таким адресом электронной почты уже существует).
     */
    @Override
    public boolean register(Register register) {
        if (userRepository.findByEmail(register.getUsername().toLowerCase()).isPresent()) {
            return false;
        }
        User user = userMapper.toUser(register);
        user.setEmail(user.getEmail().toLowerCase());
        user.setPassword(encoder.encode(user.getPassword()));
        user.setRole(register.getRole() == null ? USER : register.getRole());
        userRepository.save(user);
        log.debug("Registered a new user");
        return true;
    }
}