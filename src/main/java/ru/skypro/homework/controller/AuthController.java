package ru.skypro.homework.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.skypro.homework.dto.Login;
import ru.skypro.homework.dto.Register;
import ru.skypro.homework.dto.Role;
import ru.skypro.homework.service.inter.AuthService;

import static ru.skypro.homework.dto.Role.USER;


//@Slf4j
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequiredArgsConstructor

public class AuthController {
    private final AuthService authService;

    /**
     * Метод для выполнения входа пользователя в систему.
     *
     * @param login Объект {@link Login} с данными для входа (почта пользователя и пароль).
     * @return Объект {@link ResponseEntity} без тела ответа с кодом 200 (OK) в случае успешного входа,
     *         или объект {@link ResponseEntity} с кодом 401 (UNAUTHORIZED) в случае неудачной аутентификации.

     */

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Login login) {
        if (authService.login(login.getUsername(),login.getPassword())) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    /**
     * Метод для регистрации нового пользователя.
     *
     * @param register Объект {@link Register} с данными для регистрации нового пользователя.
     * @return Объект {@link ResponseEntity} без тела ответа с кодом 201 (CREATED) в случае успешной регистрации,
     *         или объект {@link ResponseEntity} с кодом 403 (FORBIDDEN) в случае неудачной регистрации.

     */

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Register register) {
        Role role = register.getRole() == null ? USER : register.getRole();
        if (authService.register(register)) {
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }
}