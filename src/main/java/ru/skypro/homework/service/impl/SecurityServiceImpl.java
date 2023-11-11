package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.Role;
import ru.skypro.homework.service.api.SecurityService;

@Slf4j
@RequiredArgsConstructor
@Service
public class SecurityServiceImpl implements SecurityService {
    @Override
    public void checkIfUserHasPermissionToAlter(Authentication authentication, String username) {
        boolean matchUser = authentication.getName().equals(username);
        boolean userIsAdmin = authentication.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().contains(Role.ADMIN.name()));

        if (!(userIsAdmin || matchUser)) {
            throw new RuntimeException("Вы не имеете права доступа");
        }
    }
}
