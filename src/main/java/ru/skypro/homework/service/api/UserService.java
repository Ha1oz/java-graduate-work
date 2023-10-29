package ru.skypro.homework.service.api;

import org.springframework.security.core.Authentication;
import ru.skypro.homework.dto.UserDto;
import ru.skypro.homework.entity.User;

public interface UserService {
    UserDto updateUser(UserDto userDto, String username, Authentication authentication);
    User getUser(String username);
    UserDto getUserDtoByUsername(String username);
}
