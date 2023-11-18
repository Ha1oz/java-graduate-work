package ru.skypro.homework.service.inter;

import ru.skypro.homework.dto.Register;

public interface AuthService {
    boolean login(String userName, String password);
    boolean register(Register register);
}