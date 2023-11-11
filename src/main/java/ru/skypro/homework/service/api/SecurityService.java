package ru.skypro.homework.service.api;

import org.springframework.security.core.Authentication;

public interface SecurityService {
    void checkIfUserHasPermissionToAlter(Authentication authentication, String username);
}
